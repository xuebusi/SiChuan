package io.transwarp.scrcu.app;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.base.util.BaseUtils;
import io.transwarp.scrcu.base.util.SQLConfig;
import io.transwarp.scrcu.sqlinxml.SqlKit;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.List;

@RequiresAuthentication
public class AppEventController extends Controller {

    /**
     * 事件列表
     */
    @RequiresPermissions("/app/event/list")
    public void list() {
        if (BaseUtils.isAjax(getRequest())) {
            // 得到时间查询条件
            String condition = InceptorUtil.getDateCondition(getRequest());
            // 执行查询
            List<List<String>> eventData = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_event_list, condition));
            JSONObject listResult = new JSONObject();
            listResult.put("eventData", eventData);
            renderJson(listResult);
        }

    }

    /**
     * 事件详情
     */
    @RequiresPermissions("/app/event/detail")
    public void detail() {
        if (BaseUtils.isAjax(getRequest())) {
            // 得到时间查询条件
            String condition = InceptorUtil.getDateCondition(getRequest());
            // 执行查询
            List<List<String>> detailData = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_event_detail, condition));
            JSONObject detailResult = new JSONObject();
            detailResult.put("detailData", detailData);
            renderJson(detailResult);
        }
    }

    /**
     * 事件趋势
     */
    @RequiresPermissions("/app/event/tendency")
    public void tendency() {
        if (BaseUtils.isAjax(getRequest())) {
            // 得到时间查询条件
            String condition = InceptorUtil.getDateCondition(getRequest());
            // 执行查询
            List<List<String>> tendencyData = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_event_tendency, condition));
            JSONObject tendencyResult = new JSONObject();
            tendencyResult.put("tendencyData", tendencyData);
            renderJson(tendencyResult);
        }
    }

}
