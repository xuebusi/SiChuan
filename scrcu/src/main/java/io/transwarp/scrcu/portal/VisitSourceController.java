package io.transwarp.scrcu.portal;

import java.util.ArrayList;
import java.util.List;

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
public class VisitSourceController extends Controller {
	@RequiresPermissions("/portal/visitsource/searchEngine")
	public void searchEngine() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_sourceAnalysis_searchEngine.toString(), condition), 5);

			List<Object> dataList = new ArrayList<Object>();
			for (List<String> list : data) {
				Data d = new Data(list.get(0), Integer.valueOf(list.get(1)));
				dataList.add(d);
			}
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			// setAttr("data", data);
			String str = ChartUtils.genPie("访问次数", dataList);
			// 生成图
			result.put("chartOption", str);
			// setAttr("chartOption", str);
			renderJson(result);
		}
	}

	@RequiresPermissions("/portal/visitsource/source")
	public void source() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> dataSource = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_sourceAnalysis_source.toString(), condition), 2);

			List<List<String>> dataPage = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.portal_siteAnalysis_entryPage_query.toString(), condition));

			List<Object> dataList = new ArrayList<Object>();
			for (List<String> list : dataSource) {
				Data d = new Data(list.get(0), Integer.valueOf(list.get(3)));
				dataList.add(d);
			}
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("dataSource", dataSource);
			result.put("dataPage", dataPage);
			String str = ChartUtils.genPie("访客数（UV）", dataList);
			// 生成图
			result.put("chartOption", str);
			renderJson(result);
		}

	}

}
