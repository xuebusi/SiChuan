package io.transwarp.scrcu.base.controller;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;

import io.transwarp.scrcu.base.util.BaseUtils;

public class BaseController extends Controller {

	protected boolean isPost() {
		return "post".equals(getRequest().getMethod().toLowerCase());
	}

	protected String getControllerKey() {
		return this.getAttr("ControllerKey");
	}

	protected String getActionKey() {
		return this.getAttr("ActionKey");
	}

	public void setNav() {
		int roleId = getSessionAttr("roleId");
		setSessionAttr("nav", BaseUtils.getNav(roleId));
		setSessionAttr("site_type", BaseUtils.getNav(this.getActionKey()));
	}

	public String getCsvFileName(String name) {
		String start_dt = getPara("start_dt");
		String end_dt = getPara("end_dt");
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		if (StringUtils.isNotBlank(start_dt)) {
			sb.append("_" + start_dt);
		}
		if (StringUtils.isNotBlank(end_dt)) {
			sb.append("_" + end_dt);
		}
		sb.append(".csv");
		return sb.toString();
	}

	protected void goBack() {
		goBack(-1);
	}

	protected void goBack(int step) {
		renderJS("history.go(" + step + ")");
	}

	protected void alertAndGoback(String msg) {
		alertAndGoback(msg, -1);
	}

	protected void alertAndGoback(String msg, int step) {
		renderJS("alert('" + msg + "');history.go(" + step + ")");
	}

	protected void renderJS(String jsContent) {
		renderHtml("<script type=\"text/javascript\">" + jsContent + "</script>");
	}

}
