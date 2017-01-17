/**
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.transwarp.scrcu.sqlinxml;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;

public class SqlKit {

	protected static final Logger LOG = Logger.getLogger(SqlKit.class);

	private static Map<String, String> sqlMap;

	public static final String separator = ",";

	public static final String header = ".header";

	public static final String sql = ".sql";

	public static final String name = ".name";

	public static final String SQL_CONDITION = "SQL_CONDITION";

	/*
	 * sql拼接
	 */
	public static String propSQL(Object name, String condition) {
		String s = propString(name + sql);
		if (s.contains(SQL_CONDITION)) {
			return s.replace(SQL_CONDITION, StringUtils.isNotBlank(condition) ? condition : "");
		} else {
			return s + condition;
		}

	}

	public static String propSQL(Object name) {
		return propString(name + sql);
	}

	public static List<String> propHeader(Object name) {
		return propStringList(name + header);
	}

	public static String propName(Object name) {
		return propString(name + SqlKit.name);
	}

	public static String propString(String name) {
		if (sqlMap == null) {
			throw new NullPointerException("SqlInXmlPlugin not start");
		}
		return sqlMap.get(name);
	}

	public static List<String> propStringList(String name) {
		if (sqlMap == null) {
			throw new NullPointerException("SqlInXmlPlugin not start");
		}
		return Arrays.asList(sqlMap.get(name).split(separator));
	}

	static void clearSqlMap() {
		sqlMap.clear();
	}

	static void init() {
		sqlMap = new HashMap<String, String>();
		File file = new File(PathKit.getRootClassPath());
		File[] files = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				if (pathname.getName().endsWith("sql.xml")) {
					return true;
				}
				return false;
			}
		});
		for (File xmlfile : files) {
			SqlGroupList list = JaxbKit.unmarshal(xmlfile, SqlGroupList.class);
			for (SqlGroup group : list.SqlGroups) {
				String name = group.name;
				if (name == null || name.trim().equals("")) {
					name = xmlfile.getName();
				}
				for (SqlItem sqlItem : group.sqlItems) {
					sqlMap.put(name + "." + sqlItem.id, sqlItem.value);
//					if(sqlItem.id.equals("sql")){
//						System.out.println(sqlItem.value);
//					}
				}
			}
		}
		LOG.debug("sqlMap" + sqlMap);
	}
}
