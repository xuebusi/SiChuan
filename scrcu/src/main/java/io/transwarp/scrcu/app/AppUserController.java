package io.transwarp.scrcu.app;

import com.alibaba.fastjson.JSONObject;
import io.transwarp.scrcu.base.controller.BaseController;
import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.base.util.BaseUtils;
import io.transwarp.scrcu.base.util.ChartUtils;
import io.transwarp.scrcu.base.util.SQLConfig;
import io.transwarp.scrcu.sqlinxml.SqlKit;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.util.ArrayList;
import java.util.List;

@RequiresAuthentication
public class AppUserController extends BaseController {


    /**
     * app活跃用户数据
     */
    @RequiresPermissions("/app/userAnalysis/activeUser")
    public void activeUser() {

        if (BaseUtils.isAjax(getRequest())) {
            // 得到查询条件
            String condition = InceptorUtil.getDateCondition(getRequest());
            // 执行查询
            List<List<String>> dataTime = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_activeUser) + condition, 7);
            List<List<String>> dataPhone = InceptorUtil
                    .query(SqlKit.propSQL(SQLConfig.app_activeUser_phone) + condition, 35);
            List<List<String>> dataChannel = InceptorUtil
                    .query(SqlKit.propSQL(SQLConfig.app_activeUser_channel) + condition, 35);
            // 定义json类型结果
            JSONObject result = new JSONObject();
            // 返回结果
            List<Object> xAxisList = new ArrayList<Object>();
            List<Object> dataList = new ArrayList<Object>();
            for (List<String> list : dataTime) {
                xAxisList.add(list.get(0));
                dataList.add(Integer.valueOf(list.get(1)));
            }
//            Object[] nameList = new Object[]{"启动用户数", "7日启动用户数", "30日启动用户数"};
//            String str = ChartUtils.genMultiLineChart(xAxisList, nameList, dataList, data7List, data30List);
            String str = ChartUtils.genLineChart("启动用户数", xAxisList, dataList);
            result.put("chartOption", str);
            result.put("dataTime", dataTime);
            result.put("dataPhone", dataPhone);
            result.put("dataChannel", dataChannel);
            renderJson(result);
        }

    }

    /**
     * app留存用户数据
     */
    @SuppressWarnings("unchecked")
    @RequiresPermissions("/app/userAnalysis/retainUser")
    public void retainUser() {
        if (BaseUtils.isAjax(getRequest())) {
            // 得到查询条件
            String condition = InceptorUtil.getDateCondition(getRequest());
            // 执行查询
            List<List<String>> dataTime = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_retainUser) + condition, 7);
            List<List<String>> dataPhone = InceptorUtil
                    .query(SqlKit.propSQL(SQLConfig.app_retainUser_phone) + condition, 7);
            List<List<String>> dataChannel = InceptorUtil
                    .query(SqlKit.propSQL(SQLConfig.app_retainUser_channel) + condition, 7);
            // 定义json类型结果
            JSONObject result = new JSONObject();
            // 返回结果
            List<Object> xAxisList = new ArrayList<Object>();
            List<Object> dataList1 = new ArrayList<Object>();
            List<Object> dataList2 = new ArrayList<Object>();
            List<Object> dataList3 = new ArrayList<Object>();
            for (List<String> list : dataTime) {
                xAxisList.add(list.get(0));
                dataList1.add(InceptorUtil.getDouble(list.get(2)));
                dataList2.add(InceptorUtil.getDouble(list.get(3)));
                dataList3.add(InceptorUtil.getDouble(list.get(4)));
            }
            Object[] nameList = new Object[]{"次日留存", "7日留存", "30日留存"};
            String str = ChartUtils.genMultiLineChart(xAxisList, nameList, dataList1, dataList2, dataList3);
            result.put("chartOption", str);
            result.put("dataTime", dataTime);
            result.put("dataPhone", dataPhone);
            result.put("dataChannel", dataChannel);
            renderJson(result);
        }
    }

    /**
     * app注册用户数据
     */
    @RequiresPermissions("/app/userAnalysis/regUser")
    public void regUser() {
        if (BaseUtils.isAjax(getRequest())) {
            // 得到查询条件
            String condition = InceptorUtil.getDateCondition(getRequest());
            // 执行查询
            List<List<String>> dataTime = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_regUser) + condition, 7);
            List<List<String>> dataChannel = InceptorUtil
                    .query(SqlKit.propSQL(SQLConfig.app_regUser_channel) + condition, 7);
            List<List<String>> dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_regUser_phone) + condition,
                    7);
            // 定义json类型结果
            JSONObject result = new JSONObject();
            // 返回结果
            List<Object> xAxisList = new ArrayList<Object>();
            List<Object> dataList1 = new ArrayList<Object>();
            List<Object> dataList2 = new ArrayList<Object>();
            for (List<String> list : dataTime) {
                xAxisList.add(list.get(0));
                dataList1.add(Integer.valueOf(list.get(1)));
                dataList2.add(Integer.valueOf(list.get(2)));
            }
            Object[] nameList = new Object[]{"新增用户数", "注册用户数"};
            String str = ChartUtils.genMultiLineChart(xAxisList, nameList, dataList1, dataList2);
            result.put("chartOption", str);
            result.put("dataTime", dataTime);
            result.put("dataPhone", dataPhone);
            result.put("dataChannel", dataChannel);
            renderJson(result);

        }
    }

    /**
     * app登录用户数据
     */
    @RequiresPermissions("/app/userAnalysis/loginUser")
    public void loginUser() {
        if (BaseUtils.isAjax(getRequest())) {
            // 得到查询条件
            String condition = InceptorUtil.getDateCondition(getRequest());
            // 执行查询
            List<List<String>> dataTime = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_loginUser) + condition, 35);
            List<List<String>> dataPhone = InceptorUtil
                    .query(SqlKit.propSQL(SQLConfig.app_loginUser_phone) + condition, 35);
            List<List<String>> dataChannel = InceptorUtil
                    .query(SqlKit.propSQL(SQLConfig.app_loginUser_channel) + condition, 35);
            // 定义json类型结果
            JSONObject result = new JSONObject();
            // 返回结果
            List<Object> xAxisList = new ArrayList<Object>();
            List<Object> dataList1 = new ArrayList<Object>();
            List<Object> dataList2 = new ArrayList<Object>();
            for (List<String> list : dataTime) {
                xAxisList.add(list.get(0));
                dataList1.add(list.get(1));
                dataList2.add(list.get(2));
            }
            Object[] nameList = new Object[]{"登录用户数", "启动用户数"};
            String str = ChartUtils.genMultiLineChart(xAxisList, nameList, dataList1, dataList2);
            result.put("chartOption", str);
            result.put("dataTime", dataTime);
            result.put("dataPhone", dataPhone);
            result.put("dataChannel", dataChannel);
            renderJson(result);
        }
    }

    /**
     * app新增用户数据
     */
    @RequiresPermissions("/app/userAnalysis/newUser")
    public void newUser() {
        if (BaseUtils.isAjax(getRequest())) {
            // 得到查询条件
            String condition = InceptorUtil.getDateCondition(getRequest());
            // 执行查询
            List<List<String>> dataTime = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_newUser) + condition, 35);
            List<List<String>> dataPhone = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_newUser_phone) + condition, 35);
            List<List<String>> dataChannel = InceptorUtil.query(SqlKit.propSQL(SQLConfig.app_newUser_channel) + condition, 35);
            // 定义json类型结果
            JSONObject result = new JSONObject();
            //返回结果
            List<Object> xAxisList = new ArrayList<Object>();
            List<Object> dataList = new ArrayList<Object>();
            for (List<String> list : dataTime) {
                xAxisList.add(list.get(0));
                dataList.add(list.get(1));
            }
            String str = ChartUtils.genLineChart("新增用户数", xAxisList, dataList);
            result.put("chartOption", str);
            result.put("dataTime", dataTime);
            result.put("dataPhone", dataPhone);
            result.put("dataChannel", dataChannel);
            renderJson(result);
        }
    }
}
