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

public class InceptorTest {
	static Logger logger = Logger.getLogger(InceptorTest.class);
	private static DruidDataSource dataSource = null;

	 

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


	public static void main(String[] args) throws Exception {
		Class.forName("org.apache.hive.jdbc.HiveDriver");
	    UserGroupInformation.loginUserFromKeytab("scrcu@TDH", "d:/scrcu.keytab");
	 
	    String querySQL="show tables";
	    Connection con = DriverManager.getConnection("jdbc:hive2://10.16.8.10:10000/default;principal=hive/10.16.8.10@TDH", "", "");
	    Statement stmt = con.createStatement();
	    ResultSet res = stmt.executeQuery(querySQL);
	 
	    while (res.next()) {
	      System.out.println("Result: "+res.getString(1));
	    }
	}

}
