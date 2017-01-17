package io.transwarp.scrcu.base.inceptor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.security.UserGroupInformation;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Logger;
import com.jfinal.plugin.ehcache.CacheKit;

import io.transwarp.scrcu.portrait.Tag;

public class InceptorUtil {
	static Logger logger = Logger.getLogger(InceptorUtil.class);
	private static DruidDataSource dataSource = null;

	private static DruidPooledConnection collection;

	private static Statement stateMent;

	public static boolean devMode;
	public static boolean encache;

	public static void initDataSource() {
		dataSource = new DruidDataSource();
		dataSource.setDriverClassName(PropKit.get("inceptor.driverClass"));
		dataSource.setUsername(PropKit.get("inceptor.username"));
		dataSource.setPassword(PropKit.get("inceptor.password"));
		dataSource.setUrl(PropKit.get("inceptor.jdbcUrl"));
		dataSource.setPoolPreparedStatements(false);
		dataSource.setInitialSize(10);
		dataSource.setDbType("hive");
		dataSource.setMaxActive(10);
	}

	public static Statement getDruidStatement() {
		try {
			if (stateMent != null && !stateMent.isClosed()) {
				return stateMent;
			}
			if (collection == null || collection.isClosed() || collection.isDisable()) {
				initDataSource();
				collection = dataSource.getConnection();
			}
			stateMent = collection.createStatement();
			//TODO set ngmr.exec.mode=local 用户画像根据标签进行检索时，执行自定义的SQL时，会抛出异常，提示把ngmr.exec.mode设置为cluster
			 stateMent.execute("set ngmr.exec.mode=cluster");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return stateMent;
	}

	public static Statement getStatement() {
		return getDruidStatement();
	}

	/**
	 * 测试模式
	 * 
	 * @param sql
	 * @return
	 */
	public static List<List<String>> testQuery(String sql, int testcount) {
		logger.fatal("Running: " + sql);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		int columnNum = sql.substring(0, sql.toLowerCase().indexOf("from")).split(",").length;
		List<List<String>> data = new ArrayList<List<String>>();
		Random r = new Random();
		int count = testcount == 0 ? r.nextInt(100) : testcount;
		for (int i = 0; i < count; i++) {
			List<String> array = new ArrayList<String>();
			for (int j = 0; j < columnNum; j++) {
				array.add(r.nextInt(100) + "");
			}
			data.add(array);
		}
		return data;
	}

	public static List<List<String>> query(String sql) {
		return query(sql, true, 0, true);
	}

	public static List<List<String>> query(String sql, boolean useTest) {
		return query(sql, true, 0, useTest);
	}

	public static List<List<String>> query(String sql, int testCount) {
		return query(sql, true, testCount, true);
	}

	public static List<List<String>> query(String sql, int testCount, boolean useTest) {
		return query(sql, true, testCount, useTest);
	}

	public static List<List<String>> query(String sql, boolean useCache, int testCount, boolean useTest) {
		if (encache && useCache) {
			List<List<String>> list = CacheKit.get("inceptor", sql);
			if (list == null) {
				list = SQLQuery(sql, testCount, useTest);
				CacheKit.put("inceptor", sql, list);
			}
			return list;
		} else {
			return SQLQuery(sql, testCount, useTest);
		}
	}

	public static List<List<String>> SQLQuery(String sql, int testcount, boolean useTest) {
		if (devMode && useTest) {
			return testQuery(sql, testcount);
		}
		logger.fatal("Running: " + sql);
		long start = System.currentTimeMillis();
		// 得到字段数目
		List<List<String>> result = new ArrayList<List<String>>();
		try {
			ResultSet rs = execute(sql, true);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				List<String> array = new ArrayList<String>();
				for (int i = 1; i <= columnCount; i++) {
					array.add(rs.getString(i));
				}
				result.add(array);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.fatal("查询时间: " + (System.currentTimeMillis() - start));
		return result;
	}

	public static ResultSet execute(String sql, boolean flag) {
		Statement sm = getDruidStatement();
		ResultSet rs = null;
		try {
//			sql = sql.replaceAll("as int", "as signed");
			rs = sm.executeQuery(sql);
		} catch (SQLException e) {
			if (stateMent != null) {
				try {
					stateMent.close();
				} catch (SQLException e1) {
					logger.error(e1.getMessage());
				}
				stateMent = null;
			}
			if (collection != null) {
				try {
					collection.close();
				} catch (SQLException e1) {
					logger.error(e1.getMessage());
				}
				collection = null;
			}
			logger.error(e.getMessage());
			logger.fatal("重新执行");
			if (flag) {
				execute(sql, false);
			}

		}
		return rs;
	}

	public static List<Map<String, Object>> SQLQuerylabel(String sql) {
		logger.fatal("Running: " + sql);
		long start = System.currentTimeMillis();
		// 得到字段数目
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			ResultSet rs = execute(sql, true);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("key", rsmd.getColumnName(i));
					if (!rsmd.getColumnName(i).equals("insert_date"))
						param.put("value", String.valueOf(rs.getObject(i)));
					result.add(param);
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.fatal("查询时间: " + (System.currentTimeMillis() - start));
		return result;
	}

	public static final Map<String, List<Tag>> tagQuery(String sql, boolean useCache, boolean isStatic) {
		if (encache && useCache) {
			Map<String, List<Tag>> list = CacheKit.get("inceptor", sql);
			if (list == null) {
				list = tagQuery(sql, isStatic);
				CacheKit.put("inceptor", sql, list);
			}
			return list;
		} else {
			return tagQuery(sql, isStatic);
		}
	}

	/**
	 * 测试模式
	 * 
	 * @param sql
	 * @return
	 */
	public static List<Map<String, Object>> testMapQuery(String sql, int testcount) {
		logger.fatal("Running: " + sql);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		String[] columns = sql.trim()
				.substring(sql.toLowerCase().indexOf("select") + 6, sql.toLowerCase().indexOf(" from")).split(",");
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Random r = new Random();
		int count = testcount == 0 ? r.nextInt(100) : testcount;
		for (int i = 0; i < count; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int j = 0; j < columns.length; j++) {
				map.put(columns[j], r.nextInt(100));
			}
			data.add(map);
		}
		return data;
	}

	public static final List<Map<String, Object>> mapQuery(String sql, int testcount) {
		if (devMode) {
			return testMapQuery(sql, testcount);
		} else if (encache) {
			List<Map<String, Object>> list = CacheKit.get("inceptor", sql);
			if (list == null) {
				list = mapQuery(sql);
				CacheKit.put("inceptor", sql, list);
			}
			return list;
		} else {
			return mapQuery(sql);
		}
	}

	public static final List<Map<String, Object>> mapQuery(String sql, boolean usecache) {
		if (encache && usecache) {
			List<Map<String, Object>> list = CacheKit.get("inceptor", sql);
			if (list == null) {
				list = mapQuery(sql);
				CacheKit.put("inceptor", sql, list);
			}
			return list;
		} else {
			return mapQuery(sql);
		}
	}

	public static final List<Map<String, Object>> mapQuery(String sql) {
		long start = System.currentTimeMillis();
		logger.fatal("Running: " + sql);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			ResultSet rs = null;
			rs = execute(sql, false);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(rsmd.getColumnLabel(i), rs.getString(i));
				}
				result.add(map);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.fatal("查询时间: " + (System.currentTimeMillis() - start));
		return result;
	}

	// 标签查询
	public static final Map<String, List<Tag>> tagQuery(String sql, boolean isStatic) {
		long start = System.currentTimeMillis();
		logger.fatal("Running: " + sql);
		// 得到字段数目
		// int columnNum = sql.substring(0,
		// sql.toLowerCase().indexOf("from")).split(",").length;

		Map<String, List<Tag>> result = new HashMap<String, List<Tag>>();
		try {
			ResultSet rs = execute(sql, true);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				Tag tag = new Tag();
				String type = null;
				for (int i = 1; i <= columnCount; i++) {
					// 不为空的时候放到map中
					String value = rs.getString(i);
					if (StringUtils.isNotBlank(value)) {
						if (rsmd.getColumnLabel(i).equals("total")) {
							tag.setTotal(Integer.valueOf(rs.getString(i)));
						} else if (StringUtils.isBlank(tag.getCode())) {
							type = rsmd.getColumnLabel(i);
							tag.setType(type);
							tag.setStatic(isStatic);
							tag.setCode(rs.getString(i));
						} else {
							throw new Exception("数据异常，标签表出现第三列有值");
						}
					}
				}
				if (type != null && result.get(type) == null) {
					result.put(type, new ArrayList<Tag>());
				}
				if (type != null && result.get(type).size() < 5) {
					result.get(type).add(tag);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.fatal("查询时间: " + (System.currentTimeMillis() - start));
		return result;
	}

	public static void druidTest() {
		String sql = "show tables ";
		dataSource = new DruidDataSource();
		dataSource.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
		dataSource.setUsername("yanl");
		dataSource.setPassword("123456");
		dataSource.setUrl("jdbc:hive2://10.129.32.17:10000/default");
		dataSource.setPoolPreparedStatements(false);
		Statement druidStmt;
		try {
			druidStmt = dataSource.getConnection().getConnection().createStatement();
			ResultSet res = druidStmt.executeQuery(sql);
			while (res.next()) {
				System.out.println(res.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static double getDouble(Object object) {
		if (object != null && StringUtils.isNotBlank(object.toString())) {
			return Double.valueOf(object.toString());
		}
		return 0;
	}

	public static int getInt(String key, Map<String, Object> map) {
		Object o = map.get(key);
		if (o != null) {
			return Double.valueOf(o.toString()).intValue();
		}
		return 0;
	}

	// 开始时间不空，结束时间不空，则是>，否则等于
	// 开始时间为空，结束时间不空，则是<
	public static String getDateCondition(HttpServletRequest request) {
		String start_dt = request.getParameter("start_dt");
		String end_dt = request.getParameter("end_dt");
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotBlank(end_dt) && StringUtils.isNotBlank(start_dt)) {
			sb.append(" where statt_dt >= '" + start_dt + "' and statt_dt <= '" + end_dt + "'");
		} else if (StringUtils.isNotBlank(end_dt) && StringUtils.isBlank(start_dt)) {
			sb.append(" where statt_dt <= '" + end_dt + "'");
		} else if (StringUtils.isNotBlank(start_dt)) {
			sb.append(" where statt_dt = '" + start_dt + "'");
		}
		return sb.toString();
	}

	public static Statement getOriginalStatement() {
		Statement stmt = null;
		try {
			Class.forName(PropKit.get("inceptor.driverClass"));

			Connection con = DriverManager.getConnection(PropKit.get("inceptor.jdbcUrl"),
					PropKit.get("inceptor.username"), PropKit.get("inceptor.password"));

			stmt = con.createStatement();
			return stmt;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return stmt;
	}

	public static List<List<String>> qriginalQuery(String sql, int testcount) {
		if (PropKit.getBoolean("inceptor.devMode")) {
			return testQuery(sql, testcount);
		}
		Statement stmt = getStatement();
		logger.fatal("Running: " + sql);
		// 得到字段数目
		int columnNum = sql.substring(0, sql.toLowerCase().indexOf("from")).split(",").length;
		List<List<String>> result = new ArrayList<List<String>>();
		try {
			ResultSet res = execute(sql, true);
			while (res.next()) {
				for (int i = 1; i <= columnNum; i++) {
					List<String> array = new ArrayList<String>();
					array.add(res.getString(i));
					result.add(array);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public static void main2(String[] args) throws Exception {
		dataSource = new DruidDataSource();
		dataSource.setDriverClassName("org.apache.hive.jdbc.HiveDriver");
		UserGroupInformation.loginUserFromKeytab("yanl@TDH", "D:\\transwarp\\kerberos\\weisx.keytab");
		// dataSource.setUsername("weisx");
		// dataSource.setPassword("123456");
		dataSource.setUrl("jdbc:hive2://10.129.32.18:10000/wsx_label1109;principal=weisx/123456@TDH");
		dataSource.setPoolPreparedStatements(false);
		Statement druidStmt;
		String sql = "select * from tcs_pty_agmt_rel_h limit 10";
		System.out.println("Running: " + sql);
		try {
			druidStmt = dataSource.getConnection().getConnection().createStatement();
			druidStmt.execute("set ngmr.exec.mode=local");
			long start = System.currentTimeMillis();
			ResultSet res = druidStmt.executeQuery(sql);
			System.out.println(System.currentTimeMillis() - start);
			res = druidStmt.executeQuery(sql);
			System.out.println(System.currentTimeMillis() - start);
			ResultSetMetaData rsmd = res.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (res.next()) {
				for (int i = 1; i <= columnCount; i++) {
					if (res.getString(i) != null) {
						System.out.print(rsmd.getColumnLabel(i) + ":" + res.getString(i) + ",");
					}
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Date vo(String s) {
		final int YEAR_LENGTH = 4;
		final int MONTH_LENGTH = 2;
		final int DAY_LENGTH = 2;
		final int MAX_MONTH = 12;
		final int MAX_DAY = 31;
		int firstDash;
		int secondDash;
		Date d = null;

		if (s == null) {
			throw new IllegalArgumentException();
		}

		firstDash = s.indexOf('-');
		secondDash = s.indexOf('-', firstDash + 1);
		if ((firstDash > 0) && (secondDash > 0) && (secondDash < s.length() - 1)) {
			String yyyy = s.substring(0, firstDash);
			String mm = s.substring(firstDash + 1, secondDash);
			String dd = s.substring(secondDash + 1);
			if (yyyy.length() == YEAR_LENGTH && mm.length() == MONTH_LENGTH && dd.length() == DAY_LENGTH) {
				int year = Integer.parseInt(yyyy);
				int month = Integer.parseInt(mm);
				int day = Integer.parseInt(dd);
				if (month >= 1 && month <= MAX_MONTH) {
					int maxDays = MAX_DAY;
					switch (month) {
					// February determine if a leap year or not
					case 2:
						if ((year % 4 == 0 && !(year % 100 == 0)) || (year % 400 == 0)) {
							maxDays = MAX_DAY - 2; // leap year so 29 days in
													// February
						} else {
							maxDays = MAX_DAY - 3; // not a leap year so 28 days
													// in February
						}
						break;
					// April, June, Sept, Nov 30 day months
					case 4:
					case 6:
					case 9:
					case 11:
						maxDays = MAX_DAY - 1;
						break;
					}
					if (day >= 1 && day <= maxDays) {
						d = new Date(year - 1900, month - 1, day);
					}
				}
			}
		}
		if (d == null) {
			throw new IllegalArgumentException();
		}
		return d;
	}

	public static void main(String[] args) throws Exception {
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		// UserGroupInformation.loginUserFromKeytab("weisx",
		// "D:\\transwarp\\kerberos\\weisx.keytab");
		// Connection con = DriverManager
		// .getConnection("jdbc:hive2://10.129.32.18:10000/wsx_label1109;principal=weisx/bigdata03@TDH",
		// "", "");
		Connection con = DriverManager.getConnection("jdbc:hive2://10.129.32.18:10000/wsx_label1109;"
				+ "principal=weisx/bigdata04@TDH;" + "kuser=weisx;" + "keytab=D:/transwarp/kerberos/weisx.keytab;"
				+ "authentication=kerberos;" + "krb5conf=D:/transwarp/kerberos/krb5.conf");

		/*
		 * Connection con = DriverManager.getConnection(
		 * "jdbc:hive2://10.129.32.18:10000/wsx_label1109;", "weisx", "123456");
		 */

		Statement druidStmt;
		String sql = "select * from tcs_pty_agmt_rel_h limit 10";
		System.out.println("Running: " + sql);
		try {
			druidStmt = con.createStatement();
			// druidStmt.execute("set ngmr.exec.mode=local");
			ResultSet res = druidStmt.executeQuery(sql);
			ResultSetMetaData rsmd = res.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (res.next()) {
				for (int i = 1; i <= columnCount; i++) {
					if (res.getString(i) != null) {
						System.out.print(rsmd.getColumnLabel(i) + ":" + res.getString(i) + ",");
					}
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
