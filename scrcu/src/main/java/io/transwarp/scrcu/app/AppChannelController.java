package io.transwarp.scrcu.app;

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
public class AppChannelController extends Controller {

	/**
	 * 获取app渠道列表
	 */
	@RequiresPermissions("/app/channel/list")
	public void list() {
		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_channel_list.toString(), condition));
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			renderJson(result);
		}
	}

	/**
	 * 获取app渠道详情
	 */
	@RequiresPermissions("/app/channel/detail")
	public void detail() {

		if (BaseUtils.isAjax(getRequest())) {
			// 得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			// 执行查询
			List<List<String>> data = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_channel_detail, condition), 7);

			JSONObject result = new JSONObject();
			// 返回结果
			List<Object> dataList = new ArrayList<Object>();
			for (List<String> list : data) {
				list.set(0, "官网");
				Data d = new Data(list.get(0), Integer.valueOf(list.get(2)));
				dataList.add(d);
			}

			//生成渠道详情的饼图数据
			String genPie = ChartUtils.genPie("启动用户数", dataList);
			result.put("chartOption", genPie);
			result.put("data", data);
			renderJson(result);
		}
	}

}
