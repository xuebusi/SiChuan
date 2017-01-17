package io.transwarp.scrcu.app;

import com.alibaba.fastjson.JSONObject;
import io.transwarp.scrcu.base.controller.BaseController;
import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.base.util.BaseUtils;
import io.transwarp.scrcu.base.util.SQLConfig;
import io.transwarp.scrcu.sqlinxml.SqlKit;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.List;

/**
 * Created by jianfei.tang on 2017/1/5.
 */
@RequiresAuthentication
public class AppMemberController extends BaseController {

	/**
	 * 会员页面分析
	 */
	@RequiresPermissions("/app/memberAnalysis/memberPage")
	public void memberPage(){

		if (BaseUtils.isAjax(getRequest())){
			//得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			//执行查询
			List<List<String>> data = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_memberPage) + condition, 4);
			//返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			renderJson(result);
		}
	}

	/**
	 * 会员流失分析
	 */
	@RequiresPermissions("/app/memberAnalysis/memberRunOff")
	public void memberRunOff(){

		if (BaseUtils.isAjax(getRequest())){
			//得到查询条件
			String condition = InceptorUtil.getDateCondition(getRequest());
			//执行查询
			List<List<String>> data = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_member_runoff) + condition, 4);
			//返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			renderJson(result);
		}
	}
}
