package io.transwarp.scrcu.base.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import io.transwarp.scrcu.system.nav.SysNav;

public class BaseUtils {

	public static boolean isAjax(HttpServletRequest request) {
		String str = request.getHeader("X-Requested-With");
		return StringUtils.isNotBlank(str) ? str.equals("XMLHttpRequest") : false;
	}

	public static Map<Integer, Map<Integer, SysNav>> navTree;

	public static Map<String, Integer> nav;

	public static Map<Integer, SysNav> getNav(int roleId) {
		return navTree.get(roleId);
	}

	public static int getNav(String type) {
		return nav.get(type);
	}

}
