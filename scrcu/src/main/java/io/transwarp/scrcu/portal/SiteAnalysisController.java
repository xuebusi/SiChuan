package io.transwarp.scrcu.portal;

import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.base.util.BaseUtils;
import io.transwarp.scrcu.base.util.ChartUtils;
import io.transwarp.scrcu.base.util.SQLConfig;
import io.transwarp.scrcu.sqlinxml.SqlKit;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;

@RequiresAuthentication
public class SiteAnalysisController extends Controller {

	/**
	 * 访问分析
	 */
	@RequiresPermissions("/portal/siteAnalysis/visitAnalysis")
	public void visitAnalysis() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> timeData = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_visitAnalysis_time, condition), 4);
			List<List<String>> depthData = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_visitAnalysis_depth, condition), 4);
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("timeData", timeData);
			result.put("depthData", depthData);
			renderJson(result);
		}
	}

	/**
	 * 页面分析 现在功能暂时没有用到该方法
	 */
	// @RequiresPermissions("/portal/siteAnalysis/pageAnalysis")
	@Deprecated
	public void pageAnalysis() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_pageAnalysis.toString()) + condition);
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			renderJson(result);
		}

	}

	/**
	 * 页面排行
	 */
	@RequiresPermissions("/portal/siteAnalysis/pageRank")
	public void pageRank() {

		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil.query(SqlKit.propSQL(SQLConfig.portal_pageRank, condition));
			// 会员
			List<List<String>> userData = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_siteAnalysis_userVisitPage_query.toString(), condition));
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("userData", userData);
			result.put("data", data);
			renderJson(result);
		}
	}

	/**
	 * 流量趋势
	 */
	@RequiresPermissions("/portal/siteAnalysis/flowTrend")
	public void flowTrend() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_siteAnalysis_flowTrend.toString()) + condition, 24);

			List<List<String>> charData = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_siteAnalysis_flowTrend_chart.toString(), condition));
			// 返回结果
			JSONObject result = new JSONObject();
			List<Object> xAxisList = new ArrayList<Object>();
			List<Object> pvList = new ArrayList<Object>();
			List<Object> uvList = new ArrayList<Object>();
			List<Object> ipList = new ArrayList<Object>();
			List<Object> loginCntList = new ArrayList<Object>();
			for (List<String> list : charData) {
				xAxisList.add(list.get(0));
				pvList.add(list.get(1));
				uvList.add(list.get(2));
				ipList.add(list.get(3));
				loginCntList.add(list.get(4));
			}
			Object[] nameList = new Object[]{"浏览量(PV)", "访客数(UV)", "IP数", "登录用户数"};
			//获取折线图数据
			String flowTrendChart = ChartUtils.genMultiLineChart(xAxisList, nameList, pvList, uvList, ipList, loginCntList);
			result.put("chartOption", flowTrendChart);
			result.put("data", data);
			renderJson(result);
		}

	}

	/**
	 * 广告趋势
	 */
	@RequiresPermissions("/portal/siteAnalysis/adTrend")
	public void adTrend() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_siteAnalysis_adTrend_query.toString(), condition));
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			renderJson(result);
		}

	}

	@RequiresPermissions("/portal/siteAnalysis/real")
	public void real() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_siteAnalysis_real.toString(), condition), 24);
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			renderJson(result);
		}
	}

}
