package io.transwarp.scrcu.app;

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
public class AppController extends Controller {

    /**
     * 获取启动次数
     */
    @RequiresPermissions("/app/appAnalysis/startCount")
    public void startCount() {
        if (BaseUtils.isAjax(getRequest())) {
            // 得到查询条件
            String condition = InceptorUtil.getDateCondition(getRequest());
            // 执行查询
            List<List<String>> dataPhone = InceptorUtil
                    .query(SqlKit.propSQL(SQLConfig.app_startCount_phone) + condition, 35);
            List<List<String>> dataChannel = InceptorUtil
                    .query(SqlKit.propSQL(SQLConfig.app_startCount_channel) + condition, 35);

            JSONObject result = new JSONObject();
            // 返回结果
            List<Object> xAxisList = new ArrayList<Object>();
            List<Object> dataList = new ArrayList<Object>();
            for (List<String> list : dataPhone) {
                xAxisList.add(list.get(0));
                dataList.add(Integer.valueOf(list.get(3)));
            }

            // 生成启动次数折线图数据
            String genLineChart = ChartUtils.genLineChart("启动次数", xAxisList, dataList);
            result.put("chartOption", genLineChart);
            result.put("dataPhone", dataPhone);
            result.put("dataChannel", dataChannel);
            renderJson(result);
        }
    }

    /**
     * 获取app版本
     */
    @RequiresPermissions("/app/appAnalysis/version")
    public void version() {
        if (BaseUtils.isAjax(getRequest())) {
            // 得到查询条件
            String condition = InceptorUtil.getDateCondition(getRequest());
            // 执行查询
            List<List<String>> data = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_version.toString(), condition),
                    4);
            // 返回结果
            JSONObject result = new JSONObject();
            result.put("data", data);
            renderJson(result);
        }
    }

}
