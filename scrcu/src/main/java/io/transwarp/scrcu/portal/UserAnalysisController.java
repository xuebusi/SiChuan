package io.transwarp.scrcu.portal;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.ActionKey;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;

import io.transwarp.echarts.data.Data;
import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.base.util.BaseUtils;
import io.transwarp.scrcu.base.util.ChartUtils;
import io.transwarp.scrcu.base.util.SQLConfig;
import io.transwarp.scrcu.sqlinxml.SqlKit;

@RequiresAuthentication
public class UserAnalysisController extends Controller {

	@ActionKey("/portal/userAnalysis")
	public void index() {
	}

	/**
	 * 地域分布
	 */
	@RequiresPermissions("/portal/userAnalysis/area")
	public void area() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_siteAnalysis_area_query, condition), 20);
			// 返回结果
			JSONObject result = new JSONObject();
			List<Object> dataList = new ArrayList<Object>();
			for (List<String> list : data) {
				String name=list.get(0).replace("甘孜州", "甘孜藏族自治州").replace("阿坝州", "阿坝藏族羌族自治州").replace("凉山州", "凉山彝族自治州");
				Data d = new Data(name, list.get(1));
				dataList.add(d);
			}
			String str = ChartUtils.genMapChart("访客", dataList);
			result.put("chartOption", str);
			result.put("data", data);
			renderJson(result);

		}

	}

	/**
	 * 访问次数
	 */
	@RequiresPermissions("/portal/userAnalysis/terminal")
	public void terminal() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> terminalos = InceptorUtil.query(SqlKit.propSQL(SQLConfig.portal_terminal_os, condition),
					5);
			List<List<String>> terminalbrowser = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_terminal_browserDiv, condition), 5);

			// 返回结果
			List<Object> dataList2 = new ArrayList<Object>();
			List<Object> dataList3 = new ArrayList<Object>();
			for (List<String> list : terminalos) {
				Data d = new Data(list.get(0), InceptorUtil.getDouble(list.get(1)));
				dataList2.add(d);
			}
			for (List<String> list : terminalbrowser) {
				Data d = new Data(list.get(0), InceptorUtil.getDouble(list.get(1)));
				dataList3.add(d);
			}
			// 返回结果
			JSONObject result = new JSONObject();
			String str2 = ChartUtils.genPie("访问次数", dataList2);
			String str3 = ChartUtils.genPie("访问次数", dataList3);
			// 生成图
			result.put("chartOption2", str2);
			result.put("chartOption3", str3);

			result.put("data_terminalos", terminalos);
			result.put("data_terminalbrowser", terminalbrowser);
			renderJson(result);
		}

	}

	@RequiresPermissions("/portal/userAnalysis/userLevel")
	public void userLevel() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_userAnalysis_userLevel, condition), 5);

			List<Object> dataList = new ArrayList<Object>();
			for (List<String> list : data) {
				Data d = new Data(list.get(0), Integer.valueOf(list.get(1)));
				dataList.add(d);
			}
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			String str = ChartUtils.genPie("会员数", dataList);
			// 生成图
			result.put("chartOption", str);
			renderJson(result);
		}

	}

	// @RequiresPermissions("/portal/useranalysis/userVisitPage")
	@Deprecated
	public void userVisitPage() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_siteAnalysis_userVisitPage_query.toString()) + condition);
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			renderJson(result);
		}

	}

	/**
	 * 流失用户分析
	 */
	@RequiresPermissions("/portal/userAnalysis/userLoss")
	public void userLoss() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_siteAnalysis_userLoss_query.toString()) + condition);
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			renderJson(result);
		}

	}

	/**
	 * 唯一用户分析
	 */
	@RequiresPermissions("/portal/userAnalysis/userOnly")
	public void userOnly() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到时间查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_userAnalysis_userOnly.toString()) + condition);
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			renderJson(result);
		}
	}

}
