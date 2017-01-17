package io.transwarp.scrcu.base.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

@SuppressWarnings({ "serial", "rawtypes" })
public class BaseModel<M extends BaseModel> extends Model<M> {
	private Integer isNew = 1;

	protected void setIsNew(boolean flg) {
		if (flg)
			this.isNew = 1;
		else
			this.isNew = 0;
	}

	public boolean isNew() {
		if (this.isNew == 1) {
			return true;
		} else if (this.isNew == 0) {
			return false;
		} else
			return false;
	}

	public List<M> find(String sql, Object... paras) {
		List<M> r = super.find(sql, paras);
		for (M m : r) {
			m.setIsNew(false);
		}
		return r;
	}

	public Map<String, Object> getAttrs() {
		return super.getAttrs();
	}

	public Table getTable() {
		return TableMapping.me().getTable(this.getClass());
	}

	public String getTableName() {
		return getTable().getName();
	}

	public boolean exists(String whereSql, Object... args) {
		return exists("id", whereSql, args);
	}

	public boolean exists(String pk, String whereSql, Object... args) {
		Long count=0l;
		if (DbKit.getConfig().getDialect().isOracle()){
			count = Db.queryNumber("select count(" + pk + ") from " + getTableName() + " where " + whereSql, args).longValue();
		}else{
			count = Db.queryLong("select count(" + pk + ") from " + getTableName() + " where " + whereSql, args);
		}
		if (count == 0)
			return false;
		else
			return true;
	}

	public List<M> findAll() {
		return find("select * from " + getTableName());
	}

	public Integer deleteAll() {
		return Db.update("delete from " + getTableName());
	}

	public Integer deleteAll(String sqlWhere) {
		return Db.update("delete from " + getTableName() + " where " + sqlWhere);
	}

	public Integer deleteAll(String sqlWhere, Object... paras) {
		return Db.update("delete from " + getTableName() + " where " + sqlWhere, paras);
	}

	public List<M> where(String sqlWhere) {
		String sql = "select * from " + getTableName();
		if (!StringKit.isBlank(sqlWhere))
			sql += " where " + sqlWhere;
		return find(sql);
	}

	public List<M> where(String sqlWhere, Object... paras) {
		return find("select * from " + getTableName() + " where " + sqlWhere, paras);
	}

	/**
	 * 根据当前模型字段属性检索数据库
	 * 
	 * @return
	 */
	public List<M> findByModel(String order) {
		Map<String, Object> attrs = getAttrs();
		ArrayList<Object> vals = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + getTableName() + " where 1=1");
		for (Map.Entry<String, Object> attr : attrs.entrySet()) {
			sql.append(" and " + attr.getKey() + "=?");
			vals.add(attr.getValue());
		}
		sql.append(" order by " + order);
		return find(sql.toString(), vals.toArray());
	}

	public List<M> findByModel() {
		return findByModel("id asc");
	}

	public M findFirstByModel() {
		return findFirstByModel("id asc");
	}

	public M findFirstByModel(String order) {
		Map<String, Object> attrs = getAttrs();
		ArrayList<Object> vals = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + getTableName() + " where 1=1");
		for (Map.Entry<String, Object> attr : attrs.entrySet()) {
			sql.append(" and " + attr.getKey() + "=?");
			vals.add(attr.getValue());
		}
		sql.append(" order by " + order);
		List<M> result = find(sql.toString(), vals.toArray());
		return result.size() > 0 ? result.get(0) : null;
	}

	public Page<M> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from " + getTableName() + " order by id asc");
	}
	// List<M> result = find(sql, NULL_PARA_ARRAY);
	// return result.size() > 0 ? result.get(0) : null;
	// public void injectByPost(String modelName, HttpServletRequest request,
	// boolean skipConvertError) {
	// Table table = TableMapping.me().getTable(this.getClass());
	//
	// String modelNameAndDot = modelName + ".";
	//
	// Map<String, String[]> parasMap = request.getParameterMap();
	// for (Entry<String, String[]> e : parasMap.entrySet()) {
	// String paraKey = e.getKey();
	// if (paraKey.startsWith(modelNameAndDot)) {
	// String paraName = paraKey.substring(modelNameAndDot.length());
	// Class colType = table.getColumnType(paraName);
	// if (colType == null)
	// throw new ActiveRecordException("The model attribute " + paraKey + " is
	// not exists.");
	// String[] paraValue = e.getValue();
	// try {
	// // Object value = Converter.convert(colType, paraValue != null ?
	// paraValue[0] : null);
	// Object value = paraValue[0] != null ? TypeConverter.convert(colType,
	// paraValue[0]) : null;
	// this.set(paraName, value);
	// } catch (Exception ex) {
	// if (skipConvertError == false)
	// throw new RuntimeException("Can not convert parameter: " +
	// modelNameAndDot + paraName, ex);
	// }
	// }
	// }
	// }
	private static final long serialVersionUID = -992334519496260591L;
	protected static String oracleSequenceName = null;
	protected static String primaryKey = null;

	/**
	 * Get attribute of any mysql type
	 */
	public <T> T get(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.get(attr.toUpperCase());
		else
			return super.get(attr);
	}

	/**
	 * Get attribute of any mysql type. Returns defaultValue if null.
	 */
	public <T> T get(String attr, Object defaultValue) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.get(attr.toUpperCase(), defaultValue);
		else
			return super.get(attr);
	}

	/**
	 * Get attribute of mysql type: varchar, char, enum, set, text, tinytext,
	 * mediumtext, longtext
	 */
	public String getStr(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getStr(attr.toUpperCase());
		else
			return super.getStr(attr);
	}

	/**
	 * Get attribute of mysql type: int, integer, tinyint(n) n > 1, smallint,
	 * mediumint
	 */
	public Integer getInt(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getNumber(attr.toUpperCase()) == null ? null : super.getNumber(attr.toUpperCase()).intValue();
		else
			return super.getInt(attr);
	}

	/**
	 * Get attribute of mysql type: bigint, unsign int
	 */
	public Long getLong(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getNumber(attr.toUpperCase()) == null ? null : super.getNumber(attr.toUpperCase()).longValue();
		else
			return super.getLong(attr);
	}

	/**
	 * Get attribute of mysql type: unsigned bigint
	 */
	public java.math.BigInteger getBigInteger(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getBigInteger(attr.toUpperCase());
		else
			return getBigInteger(attr);
	}

	/**
	 * Get attribute of mysql type: date, year
	 */
	public Date getDate(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getDate(attr.toUpperCase());
		else
			return super.getDate(attr);
	}

	/**
	 * Get attribute of mysql type: time
	 */
	public java.sql.Time getTime(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getTime(attr.toUpperCase());
		else
			return super.getTime(attr);
	}

	/**
	 * Get attribute of mysql type: timestamp, datetime
	 */
	public Timestamp getTimestamp(String attr) throws RuntimeException {
		Timestamp re = null;
		if (DbKit.getConfig().getDialect().isOracle()) {
			Object obj = super.get(attr.toUpperCase());
			if (obj == null) {
				return re;
			}
			if (obj instanceof oracle.sql.TIMESTAMP) {
				try {
					re = ((oracle.sql.TIMESTAMP) obj).timestampValue();
				} catch (Exception e) {
					throw (new RuntimeException(e));
				}
			}
		} else {
			re = super.getTimestamp(attr);
		}
		return re;
	}

	/**
	 * Get attribute of mysql type: real, double
	 */
	public Double getDouble(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getNumber(attr.toUpperCase()).doubleValue();
		else
			return super.getDouble(attr);
	}

	/**
	 * Get attribute of mysql type: float
	 */
	public Float getFloat(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getNumber(attr.toUpperCase()).floatValue();
		else
			return super.getFloat(attr);
	}

	/**
	 * Get attribute of mysql type: bit, tinyint(1)
	 */
	public Boolean getBoolean(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getBoolean(attr.toUpperCase());
		else
			return super.getBoolean(attr);
	}

	/**
	 * Get attribute of mysql type: decimal, numeric
	 */
	public java.math.BigDecimal getBigDecimal(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getBigDecimal(attr.toUpperCase());
		else
			return super.getBigDecimal(attr);
	}

	/**
	 * Get attribute of mysql type: binary, varbinary, tinyblob, blob,
	 * mediumblob, longblob
	 */
	public byte[] getBytes(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getBytes(attr.toUpperCase());
		else
			return super.getBytes(attr);
	}

	/**
	 * Get attribute of any type that extends from Number
	 */
	public Number getNumber(String attr) {
		if (DbKit.getConfig().getDialect().isOracle())
			return super.getNumber(attr.toUpperCase());
		else
			return super.getNumber(attr);
	}

	public boolean save() {
		if (DbKit.getConfig().getDialect().isOracle()) {
			if (oracleSequenceName != null) {
				if (primaryKey != null) {
					super.set(primaryKey, oracleSequenceName + ".nextval");
				} else {
					super.set("id", oracleSequenceName + ".nextval");
				}
			}
		}
		return super.save();
	}

	public M set(String attr, Object value) {
		if (DbKit.getConfig().getDialect().isOracle()) {
			if (value instanceof Date) {
				Date dt = (Date) value;
				value = new Timestamp(((Date) value).getTime());
			}
		}
		return super.set(attr, value);
	}
}
