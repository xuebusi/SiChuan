package io.transwarp.scrcu.base.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.ActionKey;

import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.base.util.BaseUtils;
import io.transwarp.scrcu.base.util.ChartUtils;
import io.transwarp.scrcu.base.util.SQLConfig;
import io.transwarp.scrcu.csv.CsvRender;
import io.transwarp.scrcu.sqlinxml.SqlKit;
import io.transwarp.scrcu.system.users.UserRoles;
import io.transwarp.scrcu.system.users.Users;

public class IndexController extends BaseController {

	public void login() {
		setAttr("message", "hello word");render("signin.html");
	}

	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.isAuthenticated()) {
			currentUser.logout();
		}
	}

	public void signin() {
		String username = getPara("username");
		String password = getPara("password");
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			currentUser.login(token);
			UserRoles ur = UserRoles.dao.findByUserId(username);
			// 增加的银行级别，做个人画像权限判断
			Users user = Users.dao.findByUserName(username);
			int banklevel = user.getInt("banklevel");
			setSessionAttr("banklevel", banklevel);
			setSessionAttr("roleId", ur.getInt("role_id"));
			setSessionAttr("username", username);
			String action = getPara("from");
			if (StringUtils.isNotBlank(action)) {
				redirect(action);
			} else {
				redirect("/");
			}
		} catch (Exception e) {
			// 登录失败
			forwardAction("/");
			e.printStackTrace();
		}
	}

	public void unauthorized() {
		render("unauthorized.html");
	}

	@RequiresAuthentication
	public void index() {

		if (BaseUtils.isAjax(getRequest())) {
			JSONObject result = new JSONObject();

			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 概览
			List<Map<String, Object>> mapData = InceptorUtil
					.mapQuery(SqlKit.propSQL(SQLConfig.portal_chart.toString()) + condition, 48);
			Date date = new Date();
			int today = date.getYear() + date.getMonth() + date.getDay();

			// 返回结果
			List<Object> xAxisList = new ArrayList<Object>();
			//存放之前的数据
			List<Object> dataList_yesterday_1 = new ArrayList<Object>();
			List<Object> dataList_yesterday_2 = new ArrayList<Object>();
			List<Object> dataList_yesterday_3 = new ArrayList<Object>();
			List<Object> dataList_yesterday_4 = new ArrayList<Object>();
			//存放今天的数据
			List<Object> dataList_today_1 = new ArrayList<Object>();
			List<Object> dataList_today_2 = new ArrayList<Object>();
			List<Object> dataList_today_3 = new ArrayList<Object>();
			List<Object> dataList_today_4 = new ArrayList<Object>();
			for (int i = 0; i < mapData.size(); i++) {
				Map<String, Object> map = mapData.get(i);
				if (i < 24) {
					xAxisList.add(i);
					int pv = InceptorUtil.getInt("pv", map);
					dataList_today_1.add(pv);
					dataList_today_2.add(InceptorUtil.getInt("uv", map));
					dataList_today_3.add(InceptorUtil.getInt("logon_user_cnt", map));
					dataList_today_4.add(InceptorUtil.getInt("nuv", map));
				} else {
					dataList_yesterday_1.add(InceptorUtil.getInt("pv", map));
					dataList_yesterday_2.add(InceptorUtil.getInt("uv", map));
					dataList_yesterday_3.add(InceptorUtil.getInt("logon_user_cnt", map));
					dataList_yesterday_4.add(InceptorUtil.getInt("nuv", map));
				}
			}

			// for (Map<String, Object> map : mapData) {
			// String day = String.valueOf(map.get("statt_dt"));
			// xAxisList.add(String.valueOf(map.get("statt_hr")));
			// if (day.equals(today)) {
			// dataList_today_1.add(Integer.valueOf(map.get("pv").toString()));
			// dataList_today_2.add(Integer.valueOf(map.get("uv").toString()));
			// dataList_today_3.add(Integer.valueOf(map.get("logon_user_cnt").toString()));
			// dataList_today_4.add(Integer.valueOf(map.get("nuv").toString()));
			// } else {
			// dataList_yesterday_1.add(Integer.valueOf(map.get("pv").toString()));
			// dataList_yesterday_2.add(Integer.valueOf(map.get("uv").toString()));
			// dataList_yesterday_3.add(Integer.valueOf(map.get("logon_user_cnt").toString()));
			// dataList_yesterday_4.add(Integer.valueOf(map.get("nuv").toString()));
			// }
			// }
			Object[] nameList1 = new Object[] { "今日PV", "昨日PV" };
			String chart1 = ChartUtils.genMultiLineChart(xAxisList, nameList1, dataList_today_1, dataList_yesterday_1);
			Object[] nameList2 = new Object[] { "今日VV", "昨日VV" };
			String chart2 = ChartUtils.genMultiLineChart(xAxisList, nameList2, dataList_today_2, dataList_yesterday_2);
			Object[] nameList3 = new Object[] { "今日登录用户数", "昨日登录用户数" };
			String chart3 = ChartUtils.genMultiLineChart(xAxisList, nameList3, dataList_today_3, dataList_yesterday_3);
			Object[] nameList4 = new Object[] { "今日新访客", "昨日新访客" };
			String chart4 = ChartUtils.genMultiLineChart(xAxisList, nameList4, dataList_today_4, dataList_yesterday_4);

			result.put("chart1", chart1);
			result.put("chart2", chart2);
			result.put("chart3", chart3);
			result.put("chart4", chart4);

			// 得到查询条件
			// 概览
			// statt_Dt,PV,UV,IP_Cnt,VV,NUV,User_Cnt,New_User_Cnt,Sleep_Mem_Cnt,Logon_User_Cnt,Run_Off_User_Cnt,
			List<List<String>> data = InceptorUtil.query(SqlKit.propSQL(SQLConfig.portal_index.toString(), condition),
					7);
			result.put("data", data);

			if (data != null && data.size() > 0) {
				int sum_1 = Integer.valueOf(data.get(0).get(1));// pv
				int sum_2 = Integer.valueOf(data.get(0).get(2));// uv
				int sum_3 = Integer.valueOf(data.get(0).get(8));// Logon_User_Cnt
				int sum_4 = Integer.valueOf(data.get(0).get(5));// nuv

				result.put("sum_1", sum_1);
				result.put("sum_2", sum_2);
				result.put("sum_3", sum_3);
				result.put("sum_4", sum_4);
			} else {
				result.put("sum_1", 0);
				result.put("sum_2", 0);
				result.put("sum_3", 0);
				result.put("sum_4", 0);
			}

			// 入口页面
			List<List<String>> landingPage = InceptorUtil.query(
					SqlKit.propSQL(SQLConfig.portal_siteAnalysis_entryPage_query.toString(), condition) + " limit 5",
					5);
			// 返回结果
			result.put("landingPage", landingPage);
			// 页面
			List<List<String>> visitPage = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_pageRank.toString(), condition) + " limit 5", 5);
			// 返回结果
			result.put("visitPage", visitPage);
			renderJson(result);
		} else {
			setNav();
		}
	}

	public void businessAnalysis() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil.query(SqlKit.propSQL(SQLConfig.business_analysis) + condition, 5);
			List<Object> xAxisList = new ArrayList<Object>();
			List<Object> dataList = new ArrayList<Object>();
			for (List<String> list : data) {
				xAxisList.add(list.get(0));
				dataList.add(Integer.valueOf(list.get(3)));
			}
			String str = ChartUtils.genLineChart("交易户数", xAxisList, dataList);
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			result.put("chartOption", str);
			renderJson(result);
		}

	}

	@RequiresAuthentication
	public void app() {
		setNav();
		redirect("/app/userAnalysis/activeUser");
	}

	@RequiresAuthentication
	public void system() {
		setNav();
		redirect("/system/users");
	}

	@RequiresAuthentication
	public void portrait() {
		setNav();
		redirect("/portrait/home");
	}

	// @RequiresRoles({ "user", "admin" })
	// public void userAdmin() {
	// setAttr("message", "role:user,admin");
	// render("/message.html");
	//
	// }
	//
	// @RequiresRoles(value = { "user", "admin" }, logical = Logical.OR)
	// public void userOradmin() {
	// setAttr("message", "role:user or admin");
	// render("/message.html");
	// }

	// @RequiresRoles("admin")
	// public void admin(){
	// setAttr("message", "role:admin");
	// render("/message.html");
	// }

	// @RequiresRoles("user")
	// public void user(){
	// setAttr("message", "role:user");
	// render("/message.html");
	// }

	// public void showUser() {
	//
	// }
	//
	// @RequiresPermissions("addUser")
	// public void addUser() {
	// setAttr("message", "permission:addUser");
	// render("/message.html");
	// }

	@ActionKey("csv")
	public void exportCsv() {
		String type = getPara();
		List<String> headerList = SqlKit.propHeader(type);
		// 得到查询条件
		String condition = InceptorUtil.getDateCondition(getRequest());
		String sql = SqlKit.propSQL(type, condition);
		// 执行查询
		List<List<String>> data_time = InceptorUtil.query(sql);
		// 导出CSV
		render(CsvRender.me(headerList, data_time).fileName(getCsvFileName(SqlKit.propName(type))));
	}
}
