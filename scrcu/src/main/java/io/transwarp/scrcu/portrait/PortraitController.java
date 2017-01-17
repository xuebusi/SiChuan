package io.transwarp.scrcu.portrait;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.ehcache.CacheKit;

import io.transwarp.echarts.data.Data;
import io.transwarp.scrcu.base.inceptor.InceptorUtil;
import io.transwarp.scrcu.base.util.BaseUtils;
import io.transwarp.scrcu.base.util.ChartUtils;
import io.transwarp.scrcu.base.util.SQLConfig;
import io.transwarp.scrcu.sqlinxml.SqlKit;

@RequiresAuthentication
public class PortraitController extends Controller {

	public void groupUserList() {
		keepPara("code");
		String code = getPara("code");
		if (StringUtils.isNotBlank(code)) {
			String[] codes = code.substring(0, code.length() - 1).split(",");
			List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>();
			for (String c : codes) {
				tagList.add(allTagMap.get(c));
			}
			setAttr("tagList", tagList);
		}
	}

	public void userSearch() {
		if (BaseUtils.isAjax(getRequest())) {
			StringBuffer condition = new StringBuffer();
			// 条件
			String query = getRequest().getParameter("data");
			if (StringUtils.isNotBlank(query))
				condition.append(" and " + query);
			condition.append(" limit 10");
			// 执行查询
			List<List<String>> data = InceptorUtil
					.query(SqlKit.propSQL(SQLConfig.users) + getLevelCondition() + condition, 20, false);

			for (List<String> list : data) {
				list.add("<a class='btn btn-rounded btn-sm btn-icon btn-primary' href='/portrait/userPortrait?userid="
						+ list.get(0) + "'><i class='fa fa-user'></i></a>");
			}
			// 返回结果
			JSONObject result = new JSONObject();
			result.put("data", data);
			renderJson(result);
		}
	}

	public String getLevelCondition() {
		int banklevel = getSessionAttr("banklevel");
		return " where inn_org_lvl_cd >= " + banklevel;
	}

	public void home() {

		if (BaseUtils.isAjax(getRequest())) {
			List<Map<String, Object>> count = InceptorUtil
					.mapQuery(SqlKit.propSQL(SQLConfig.label_colony_users) + getLevelCondition(), true);
			int allcount = 0;
			/*if (count.size() > 0) {
				allcount = Integer.valueOf(count.get(0).get("allusers").toString());
			}
			allcount=3200;*/
			//TODO 手动增加了if else 判断，如果此处逻辑错误，则使用上面注释掉的原代码
			if (count.size() > 0) {
				allcount = Integer.valueOf(count.get(0).get("allusers").toString());
			} else {
				allcount=3200;
			}
			JSONObject result = new JSONObject();
			List<Map<String, Object>> tagList = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.label_label_grouping)
					+ getLevelCondition() + " group by topic,label_code order by total desc", true);
			final Map<String, List<Map<String, Object>>> tagMap = new HashMap<String, List<Map<String, Object>>>();
			for (Map<String, Object> map : tagList) {
				String key = ((String) map.get("topic")).toLowerCase();
				allTagMap.put((String) map.get("label_only"), map);
				if (tagMap.get(key) == null) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					tagMap.put(key, list);
				}
				tagMap.get(key).add(map);
			}

			// 交易类型
			List<Map<String, Object>> trade = tagMap.get("trade");
			List<Object> tradeList = new ArrayList<Object>();
			if (trade != null) {
				for (Map<String, Object> m : trade) {
					Data data = new Data((String) m.get("label_desc"), m.get("total"));
					tradeList.add(data);
				}
				result.put("trade", ChartUtils.genPie("交易类型", tradeList));
			}

			// 职业
			List<Map<String, Object>> job = tagMap.get("job");
			List<Object> dataList = new ArrayList<Object>();
			if (job != null) {
				for (Map<String, Object> m : job) {
					Data data = new Data((String) m.get("label_desc"), m.get("total"));
					dataList.add(data);
				}
				result.put("job", ChartUtils.genPie("职业", dataList));
			}

			// 群体类型
			List<Map<String, Object>> colony = tagMap.get("colony");
			List<Object> xAxisList = new ArrayList<Object>();
			List<Object> colonyList = new ArrayList<Object>();
			if (colony != null) {
				for (Map<String, Object> m : colony) {
					xAxisList.add((String) m.get("label_desc"));
					colonyList.add(InceptorUtil.getInt("total", m));
				}
				result.put("colony", ChartUtils.genBar("群体类型", "群体类型", xAxisList, colonyList));
			}

			// 注册年限
			List<Map<String, Object>> zc_year = tagMap.get("zc_year");
			List<String> keyList = new ArrayList<String>();
			List<Integer> zc_yearList = new ArrayList<Integer>();
			if (zc_year != null) {
				for (Map<String, Object> m : zc_year) {
					keyList.add((String) m.get("label_desc"));
					zc_yearList.add(InceptorUtil.getInt("total", m));
				}
				result.put("zc_year", ChartUtils.genRadar("注册年限", keyList, zc_yearList));
			}

			// 持卡年限
			List<Map<String, Object>> year = tagMap.get("ck_year");
			keyList = new ArrayList<String>();
			List<Integer> yearList = new ArrayList<Integer>();
			if (year != null) {
				for (Map<String, Object> m : year) {
					keyList.add((String) m.get("label_desc"));
					yearList.add(InceptorUtil.getInt("total", m));
				}
				result.put("year", ChartUtils.genRadar("持卡年限", keyList, yearList));
			}

			// 使用时段
			List<Map<String, Object>> time = tagMap.get("use_time");
			List<Object> timeList = new ArrayList<Object>();
			xAxisList = new ArrayList<Object>();
			if (time != null) {
				for (Map<String, Object> m : time) {
					xAxisList.add((String) m.get("label_desc"));
					timeList.add(InceptorUtil.getInt("total", m));
				}
				result.put("time", ChartUtils.genBar("使用时段", "使用时段", xAxisList, timeList));
			}

			// 搜索引擎
			List<Map<String, Object>> search = tagMap.get("searchengine");
			List<Object> searchList = new ArrayList<Object>();
			if (search != null) {
				for (Map<String, Object> m : search) {
					Data data = new Data((String) m.get("label_desc"), m.get("total"));
					searchList.add(data);
				}
				result.put("search", ChartUtils.genPie("搜索引擎", searchList));
			}

			// 用户卡类别
			List<Map<String, Object>> card = tagMap.get("card_cat_cd");
			List<Object> cardList = new ArrayList<Object>();
			if (card != null) {
				for (Map<String, Object> m : card) {
					Data data = new Data((String) m.get("label_desc"), m.get("total"));
					cardList.add(data);
				}
				result.put("card", ChartUtils.genPie("用户卡类别", cardList));
			}

			// 操作系统
			List<Map<String, Object>> os = tagMap.get("terminal");
			List<Object> osList = new ArrayList<Object>();
			if (os != null) {
				for (Map<String, Object> m : os) {
					Data data = new Data((String) m.get("label_desc"), m.get("total"));
					osList.add(data);
				}
				result.put("os", ChartUtils.genPie("终端", osList));
			}

			// 年代
			List<Map<String, Object>> generation = tagMap.get("generation");
			xAxisList = new ArrayList<Object>();
			List<Object> generationList = new ArrayList<Object>();
			if (generation != null) {
				for (Map<String, Object> m : generation) {
					xAxisList.add((String) m.get("label_desc"));
					generationList.add(InceptorUtil.getInt("total", m));
				}
				result.put("generation", ChartUtils.genBar("年代", "年代", xAxisList, generationList));
			}

			// 性别
			List<Map<String, Object>> sex = tagMap.get("sex");
			List<Object> sexList = new ArrayList<Object>();
			if (sex != null) {
				for (Map<String, Object> m : sex) {
					Data data = new Data((String) m.get("label_desc"), m.get("total"));
					sexList.add(data);
				}
			}
			// 教育程度
			List<Map<String, Object>> edu = tagMap.get("edu_bg");
			List<Object> eduList = new ArrayList<Object>();
			if (edu != null) {
				for (Map<String, Object> m : edu) {
					Data data = new Data((String) m.get("label_desc"), m.get("total"));
					eduList.add(data);
				}
				result.put("edu", ChartUtils.genPie("教育程度", eduList));
			}
			// 终端
			List<Map<String, Object>> terminal = tagMap.get("terminal");
			List<Object> terminalList = new ArrayList<Object>();
			if (terminal != null) {
				for (Map<String, Object> m : terminal) {
					Data data = new Data((String) m.get("label_desc"), m.get("total"));
					terminalList.add(data);
				}
			}
			result.put("sex", ChartUtils.genMutilPie("性别和终端", sexList, terminalList));

			Map<String, List<Map<String, Object>>> rateMap = new HashMap<String, List<Map<String, Object>>>();
			List<String> types = new ArrayList<String>();
			types.add("inn_org_zone_cd");
			types.add("risk_level");
			types.add("pay_channel");
			types.add("security_type");
			types.add("user_cert_level");
			types.add("loan_use");
			types.add("guaranteemethod");
			for (Map<String, Object> map : tagList) {
				String s = ((String) map.get("topic")).toLowerCase();
				if (types.contains(s)) {
					String key = (String) map.get("topic_desc");
					if (rateMap.get(key) == null) {
						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						rateMap.put(key, list);
					}
					if (rateMap.get(key).size() < 3) {
						double c = Double.valueOf((String) map.get("total"));
						double d = c / allcount * 100;
						BigDecimal b = new BigDecimal(d);
						double r = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						map.put("rate", r);
						rateMap.get(key).add(map);
					}
				}
			}
			result.put("rateMap", rateMap);
			renderJson(result);
		}
	}

	Logger log = Logger.getLogger(getClass());
	public static Map<String, Map<String, Object>> allTagMap = new HashMap<String, Map<String, Object>>();

	@RequiresPermissions("/portrait/tags")
	public void tags() throws Exception {
		if (BaseUtils.isAjax(getRequest())) {
			List<Map<String, Object>> tagList = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.label_label_grouping)
					+ getLevelCondition() + " group by topic,label_code order by total desc", true);
			final Map<String, List<Map<String, Object>>> tagMap = new TreeMap<String, List<Map<String, Object>>>();
			for (Map<String, Object> map : tagList) {
				String key = (String) map.get("topic_desc");
				allTagMap.put((String) map.get("label_only"), map);
				if (tagMap.get(key) == null) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					tagMap.put(key, list);
				}
				tagMap.get(key).add(map);
			}
			Map<String, List<Map<String, Object>>> m = new TreeMap<String, List<Map<String, Object>>>(
					new Comparator<String>() {
						@Override
						public int compare(String o1, String o2) {
							if (tagMap.get(o1).size() > tagMap.get(o2).size())
								return -1;
							else
								return 1;
						}
					});
			for (String key : tagMap.keySet()) {
				m.put(key, tagMap.get(key));
			}
			renderJson("tagMap", m);
		}
	}

	@RequiresPermissions("/portrait/groupTagList")
	public void groupTagList() {
		List<List<Tag>> groupList = CacheKit.get("inceptor", "groupList");
		if (groupList == null) {
			groupList = new ArrayList<List<Tag>>();
			CacheKit.put("inceptor", "groupList", groupList);
		}
		setAttr("groupList", groupList);
	}

	@RequiresPermissions("/portrait/groupTagList")
	public void grouptags() {
		if (BaseUtils.isAjax(getRequest())) {
			StringBuffer condition = new StringBuffer(getPara("condition"));
			String code = getPara("code");
			String[] codes = code.substring(0, code.length() - 1).split(",");
			List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>();
			for (String c : codes) {
				tagList.add(allTagMap.get(c));
			}
			// #################是否具有标签占比######################
			List<Map<String, Object>> count = InceptorUtil.mapQuery(
					SqlKit.propSQL(SQLConfig.label_colony_users) + getLevelCondition() + condition.toString(), true);
			int have = 0;
			if (count.size() > 0) {
				have = Integer.valueOf(count.get(0).get("allusers").toString());
			}

			count = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.label_colony_users) + getLevelCondition(), true);
			int allcount = 0;
			Double val = null;
			if (count.size() > 0) {
				allcount = Integer.valueOf(count.get(0).get("allusers").toString());
				val = (double) have / allcount;
			}

			JSONObject result = new JSONObject();
			result.put("val", new BigDecimal(val * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			// #################是否具有标签占比######################
			// 总数
			List<Object> keys = new LinkedList<Object>();
			List<Object> values = new LinkedList<Object>();
			for (Map map : tagList) {
				keys.add(map.get("label_desc"));
				double v = (Double.valueOf(map.get("total").toString()) / allcount);
				values.add((int) (v * 100));
			}
			result.put("tagsRankOption", ChartUtils.genPercentBar("群体标签占比", "群体标签占比", keys, values));

			// #################标签占比######################

			String sql = "select max(topic) as topic,max(topic_desc) as topic_desc,max(label_only) as label_only,"
					+ "min(label_desc) as label_desc,count(*) as total "
					+ "from(select a1.user_id,a2.topic,a2.topic_desc,a2.label_code,a2.label_desc,a2.label_only "
					+ "from (select user_id from label_user_result " + getLevelCondition() + condition.toString()
					+ " ) as a1 "
					+ "left join label_all_summary as a2 on a1.user_id=a2.user_id) group by label_only,label_desc having label_desc<>'其它' and label_desc<>'未知' order by total desc;";
			List<Map<String, Object>> tags = InceptorUtil.mapQuery(sql, true);
			if (tags != null && !tags.isEmpty()) {
				result.put("word", genString(tags, codes, have, allcount));

				// #################群体主要标签start######################
				// 标签比例

				Map<String, List<Map<String, Object>>> tagMap = new HashMap<String, List<Map<String, Object>>>();
				List<String> types = new ArrayList<String>();
				types.add("colony");
				types.add("pay_channel");
				types.add("security_type");
				types.add("user_cert_level");
				types.add("loan_use");
				types.add("guaranteemethod");
				for (Map<String, Object> map : tags) {
					String s = ((String) map.get("topic")).toLowerCase();
					if (types.contains(s)) {
						String key = (String) map.get("topic_desc");
						allTagMap.put((String) map.get("label_only"), map);
						if (tagMap.get(key) == null) {
							List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
							tagMap.put(key, list);
						}
						if (tagMap.get(key).size() < 3) {
							double c = Double.valueOf((String) map.get("total"));
							double d = c / allcount * 100;
							BigDecimal b = new BigDecimal(d);
							double r = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							map.put("rate", r);
							tagMap.get(key).add(map);
						}
					}
				}
				result.put("groupTagsRate", tagMap);
			}
			renderJson(result);

		} else {
			StringBuffer condition = new StringBuffer("");
			String assets_from = getPara("assets_from");
			String assets_to = getPara("assets_to");
			String debt_from = getPara("debt_from");
			String debt_to = getPara("debt_to");
			if (StringUtils.isNotBlank(assets_from) && NumberUtils.isNumber(assets_from)) {
				condition.append(" and assets > " + assets_from);
			}
			if (StringUtils.isNotBlank(assets_to) && NumberUtils.isNumber(assets_from)) {
				condition.append(" and assets < " + assets_to);
			}
			if (StringUtils.isNotBlank(debt_from) && NumberUtils.isNumber(assets_from)) {
				condition.append(" and debt > " + debt_from);
			}
			if (StringUtils.isNotBlank(debt_to) && NumberUtils.isNumber(assets_from)) {
				condition.append(" and debt < " + debt_to);
			}
			keepPara("code", "assets_from", "assets_to", "debt_from", "debt_to");
			String code = getPara("code");
			if (StringUtils.isNotBlank(code)) {
				String[] codes = code.substring(0, code.length() - 1).split(",");
				List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>();
				for (String c : codes) {
					tagList.add(allTagMap.get(c));
					condition.append(" and " + c + " = 1");
				}
				List<List<Map<String, Object>>> groupList = CacheKit.get("inceptor", "groupList");
				if (groupList == null) {
					groupList = new ArrayList<List<Map<String, Object>>>();
					CacheKit.put("inceptor", "groupList", groupList);
				}

				groupList.add(tagList);
				setAttr("tagList", tagList);
			}
			setAttr("condition", condition.toString());
		}
	}

	public String genString(List<Map<String, Object>> tags, String[] codes, int have, int count) {

		Map<String, Map<String, Object>> tagMap = new HashMap<String, Map<String, Object>>();
		List<String> types = new ArrayList<String>();
		types.add("colony");
		types.add("ass_grade");
		types.add("generation");
		types.add("sex");
		types.add("inn_org_zone_cd");
		types.add("kh_year");
		types.add("industry");
		types.add("edu_bg");
		types.add("interest");
		types.add("trans_type");
		types.add("risk_level");
		types.add("pay_channel");
		types.add("trans_dept");
		types.add("route_way");
		types.add("loan_type");
		types.add("transfer_type");
		StringBuffer sb = new StringBuffer();
		sb.append("同时具有");
		String s = "";
		for (Map<String, Object> map : tags) {
			String key = ((String) map.get("topic")).toLowerCase();
			if (types.contains(key)) {
				if (tagMap.get(key) == null) {
					tagMap.put(key, map);
				} else {
					int t = Integer.valueOf((String) tagMap.get(key).get("total"));
					int t2 = Integer.valueOf((String) map.get("total"));
					if (t < t2) {
						tagMap.put(key, map);
					}
				}
				tagMap.get(key).put("label_desc",
						"<font class='font-bold text-success'>" + tagMap.get(key).get("label_desc") + "</font>");
			}

			for (String str : codes) {
				if (map.get("label_only").equals(str)) {
					s = s + map.get("label_desc") + "、";
				}
			}

		}
		sb.append(s.substring(0, s.lastIndexOf("、")));
		sb.append("标签的用户共").append(have).append("人，占所有用户数的").append(new BigDecimal((double) have / count * 100)
				.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()).append("%，");

		sb.append("这群人主要以").append(tags.get(0).get("label_desc")).append("为主，占比")
				.append(new BigDecimal((Double.valueOf((String) tags.get(0).get("total"))) / count * 100)
						.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())
				.append("%，");
		if (tagMap.get("ass_grade") != null)
			sb.append("用户信用级别主要为").append(tagMap.get("ass_grade").get("label_desc") + ",");

		if (tagMap.get("generation") != null) {
			sb.append("从用户构成来看，主要以").append(tagMap.get("generation").get("label_desc"));
			if (tagMap.get("sex") != null) {
				sb.append("的").append(tagMap.get("sex").get("label_desc"));
			}
			sb.append("为主，");
		}

		if (tagMap.get("inn_org_zone_cd") != null) {
			sb.append("集中在").append(tagMap.get("inn_org_zone_cd").get("label_desc")).append("一带，");
		}

		if (tagMap.get("kh_year") != null) {
			sb.append("大部分人开户").append(tagMap.get("kh_year").get("label_desc")).append("时间，");
		}

		if (tagMap.get("industry") != null) {
			sb.append("这些人主要").append(tagMap.get("industry").get("label_desc")).append("，");
		}

		if (tagMap.get("edu_bg") != null) {
			sb.append("且具有").append(tagMap.get("edu_bg").get("label_desc")).append("的学历，");
		}

		if (tagMap.get("interest") != null) {
			sb.append(tagMap.get("interest").get("label_desc")).append("。");
		}

		if (tagMap.get("trans_type") != null) {
			sb.append("这群人当中有").append(tagMap.get("trans_type").get("total")).append("的人有")
					.append(tagMap.get("trans_type").get("label_desc"));
		}
		if (tagMap.get("risk_level") != null) {
			sb.append("喜欢买").append(tagMap.get("risk_level").get("label_desc")).append("的理财产品，");
		}
		if (tagMap.get("pay_channel") != null) {
			sb.append("且习惯于").append(tagMap.get("pay_channel").get("label_desc")).append("，");
		}

		if (tagMap.get("route_way") != null) {
			sb.append("他们具有").append(tagMap.get("route_way").get("label_desc")).append("的偏好。");
			return sb.toString();
		} else {
			String str = sb.toString();
			str = str.substring(0, str.length() - 1) + "。";
			return str;
		}

	}

	@RequiresPermissions("/portrait/userPortrait")
	public void userPortrait() {
		JSONObject result = new JSONObject();
		String userid = getPara("userid");
		Map<String, Object> user = null;
		if (StringUtils.isBlank(userid)) {
			redirect("/portrait/groupUserList");
		} else {
			List<Map<String, Object>> userLable = InceptorUtil
					.mapQuery(SqlKit.propSQL(SQLConfig.portal_userLabel.toString()) + getLevelCondition()
							+ " and  user_id ='" + userid + "'", true);
			if (userLable != null && userLable.size() > 0) {
				user = userLable.get(0);
			} else {
				redirect("/portrait/groupUserList");
			}
		}
		if (user != null && BaseUtils.isAjax(getRequest())) {
			Map<String, String> r = new HashMap<String, String>();
			Map<String, Object> labelTopic = JSON.parseObject(label_topic);
			Map<String, Object> labelDesc = JSON.parseObject(label_desc);
			List<String> notKeys = new ArrayList<>();
			notKeys.add("sex");
			notKeys.add("zc_year");
			notKeys.add("ck_year");
			notKeys.add("marriage");
			for (String str : user.keySet()) {
				String key = str.toLowerCase();
				if (labelTopic.containsKey(key)) {
					if ("1".equals(user.get(key))) {
						r.put((String) labelTopic.get(key), (String) labelDesc.get(key));
					}
				} else if (!notKeys.contains(key)) {
					r.put(key, (String) user.get(key));
				}
			}
			result.put("userTags", r);
			// 调用交易偏好
			tradepreference(userid, result);
			// 调用人际关系
			relation(userid, result);
			renderJson(result);

		}

	}

	public String label_topic = "{'generation_others':'generation','xz_juxie':'constellation','user_cert_level_high':'user_cert_level','user_cert_level_low':'user_cert_level','hy_40':'marriage','xz_chunv':'constellation','guaranteemethod_53':'guaranteemethod','guaranteemethod_54':'guaranteemethod','xz_shuangyu':'constellation','searchengine_bing':'searchengine','guaranteemethod_51':'guaranteemethod','guaranteemethod_52':'guaranteemethod','edu_college_below':'edu_bg','industry_s':'industry','industry_t':'industry','industry_q':'industry','use_time_01':'use_time','xz_mojie':'constellation','industry_r':'industry','use_time_02':'use_time','trans_transfer':'trade','use_time_03':'use_time','org_zone_19':'inn_org_zone_cd','use_time_04':'use_time','use_time_05':'use_time','xz_shuangzi':'constellation','industry_d':'industry','generation_90s':'generation','trans_dept':'trade','industry_c':'industry','org_zone_20':'inn_org_zone_cd','industry_b':'industry','ck_year_1':'ck_year','org_zone_21':'inn_org_zone_cd','industry_a':'industry','xz_shizi':'constellation','industry_h':'industry','hy_30':'marriage','industry_g':'industry','industry_f':'industry','industry_e':'industry','industry_l':'industry','industry_k':'industry','industry_j':'industry','industry_i':'industry','guaranteemethod_40':'guaranteemethod','industry_p':'industry','generation_70s':'generation','industry_o':'industry','industry_n':'industry','industry_m':'industry','org_zone_09':'inn_org_zone_cd','zc_others':'zc_year','org_zone_08':'inn_org_zone_cd','loan_use_10':'loan_use','security_uk_type':'security_type','loan_use_11':'loan_use','generation_50s':'generation','pay_channel_06':'pay_channel','pay_channel_07':'pay_channel','loan_use_12':'loan_use','loan_use_13':'loan_use','generation_10s':'generation','sex_others':'sex','hy_20':'marriage','hy_21':'marriage','generation_50b':'generation','org_zone_10':'inn_org_zone_cd','edu_college_above':'edu_bg','hy_22':'marriage','trans_fin':'trade','hy_23':'marriage','zc_3_5':'zc_year','org_zone_13':'inn_org_zone_cd','xz_tianping':'constellation','org_zone_14':'inn_org_zone_cd','org_zone_11':'inn_org_zone_cd','ck_year_3_5':'ck_year','org_zone_12':'inn_org_zone_cd','org_zone_17':'inn_org_zone_cd','xz_jinniu':'constellation','zc_1_3':'zc_year','org_zone_18':'inn_org_zone_cd','org_zone_15':'inn_org_zone_cd','org_zone_16':'inn_org_zone_cd','ass_grade_1':'ass_grade','xz_others':'constellation','searchengine_baidu':'searchengine','ass_grade_0':'ass_grade','colony_4':'colony','ass_grade_3':'ass_grade','colony_3':'colony','ass_grade_2':'ass_grade','colony_2':'colony','whitegold_card':'card_cat_cd','colony_1':'colony','ck_year_1_3':'ck_year','ass_grade_4':'ass_grade','citizen_card':'card_cat_cd','golden_card':'card_cat_cd','others_card':'card_cat_cd','hy_10':'marriage','org_zone_01':'inn_org_zone_cd','colony_5':'colony','org_zone_02':'inn_org_zone_cd','colony_6':'colony','org_zone_03':'inn_org_zone_cd','org_zone_04':'inn_org_zone_cd','org_zone_05':'inn_org_zone_cd','org_zone_06':'inn_org_zone_cd','org_zone_07':'inn_org_zone_cd','job_1':'job','guaranteemethod_10':'guaranteemethod','terminal_mobile':'terminal','zc_1':'zc_year','ass_grade_others':'ass_grade','zc_10_':'zc_year','loan_use_04':'loan_use','risk_level_2':'risk_level','loan_use_03':'loan_use','risk_level_1':'risk_level','loan_use_02':'loan_use','loan_use_01':'loan_use','loan_use_05':'loan_use','job_6':'job','xz_shuiping':'constellation','trans_loan':'trade','job_5':'job','risk_level_5':'risk_level','job_4':'job','risk_level_4':'risk_level','job_3':'job','sex_woman':'sex','risk_level_3':'risk_level','ck_year_10_':'ck_year','job_2':'job','zc_5_10':'zc_year','social_security_card':'card_cat_cd','cert_others':'user_cert_level','generation_80s':'generation','sex_man':'sex','xz_baiyang':'constellation','searchengine_haosou':'searchengine','security_secret_type':'security_type','guaranteemethod_30':'guaranteemethod','security_others':'security_type','generation_60s':'generation','xz_tianxie':'constellation','marriage_others':'marriage','ck_year_5_10':'ck_year','ordinary_card':'card_cat_cd','xz_sheshou':'constellation','generation_00s':'generation','diamond_card':'card_cat_cd','job_x':'job','terminal_pc':'terminal','use_time_06':'use_time','use_time_07':'use_time','guaranteemethod_20':'guaranteemethod','trans_addedservice':'trade','searchengine_sogou':'searchengine','college_others':'edu_bg','ck_year_others':'ck_year'}";
	public String label_desc = "{'generation_others':'未知','xz_juxie':'巨蟹座','user_cert_level_high':'高认证级别用户','user_cert_level_low':'低认证级别用户','hy_40':'离婚','xz_chunv':'处女座','guaranteemethod_53':'质押+保证担保','guaranteemethod_54':'抵押+质押+保证担保','xz_shuangyu':'双鱼座','searchengine_bing':'必应','guaranteemethod_51':'抵押+保证担保','guaranteemethod_52':'质押+抵押担保','edu_college_below':'本科以下','industry_s':'公共管理和社会组织人员','industry_t':'国际组织人员','industry_q':'从事卫生、社会保障和社会福利业','use_time_01':'夜猫子','xz_mojie':'摩羯座','industry_r':'从事文化、体育和娱乐业','use_time_02':'早起鸟','trans_transfer':'转账','use_time_03':'上午时间','org_zone_19':'泸州市','use_time_04':'下午茶','use_time_05':'下午时间','xz_shuangzi':'双子座','industry_d':'从事生产供应业','generation_90s':'90后','trans_dept':'存款','industry_c':'从事制造业','org_zone_20':'德阳市','industry_b':'从事采矿业','ck_year_1':'1年','org_zone_21':'绵阳市','industry_a':'从事农业','xz_shizi':'狮子座','industry_h':'从事批发和零售业','hy_30':'丧偶','industry_g':'从事互联网行业','industry_f':'从事交通运输业','industry_e':'从事建筑业','industry_l':'从事租赁和商务服务业','industry_k':'从事房地产业','industry_j':'从事金融业','industry_i':'从事住宿和餐饮业','guaranteemethod_40':'质押担保','industry_p':'从事教育','generation_70s':'70后','industry_o':'从事居民服务和其他服务业','industry_n':'从事水利、环境和公共设施管理业','industry_m':'从事科学研究、技术服务和地质勘查业','org_zone_09':'广安市','zc_others':'未知','org_zone_08':'宜宾市','loan_use_10':'种植业贷款','security_uk_type':'uk安全认证','loan_use_11':'养殖业贷款','generation_50s':'50后','pay_channel_06':'网银交易','pay_channel_07':'手机银行','loan_use_12':'服务业贷款','loan_use_13':'餐饮业贷款','generation_10s':'10后','sex_others':'其它','hy_20':'已婚','hy_21':'初婚','generation_50b':'50前','org_zone_10':'达州市','edu_college_above':'本科及以上','hy_22':'再婚','trans_fin':'理财','hy_23':'复婚','zc_3_5':'3-5年','org_zone_13':'资阳市','xz_tianping':'天秤座','org_zone_14':'阿坝州','org_zone_11':'雅安市','ck_year_3_5':'3-5年','org_zone_12':'巴中市','org_zone_17':'自贡市','xz_jinniu':'金牛座','zc_1_3':'2年','org_zone_18':'攀枝花市','org_zone_15':'甘孜州','org_zone_16':'凉山州','ass_grade_1':'优秀级','xz_others':'未知','searchengine_baidu':'百度','ass_grade_0':'未评级','colony_4':'高活跃一般贡献客户','ass_grade_3':'一般级','colony_3':'潜力客户','ass_grade_2':'较好级','colony_2':'高活跃低贡献客户','whitegold_card':'白金卡','colony_1':'一般客户','ck_year_1_3':'2年','ass_grade_4':'未能评级','citizen_card':'社保卡','golden_card':'金卡','others_card':'其它','hy_10':'未婚','org_zone_01':'成都市','colony_5':'高贡献一般活跃用户','org_zone_02':'广元市','colony_6':'高贡献高活跃客户','org_zone_03':'遂宁市','org_zone_04':'内江市','org_zone_05':'乐山市','org_zone_06':'南充市','org_zone_07':'眉山市','job_1':'事业单位工作','guaranteemethod_10':'信用担保','terminal_mobile':'手机','zc_1':'1年','ass_grade_others':'未知','zc_10_':'10年以上','loan_use_04':'商用房贷款','risk_level_2':'中低风险','loan_use_03':'住房建设贷款','risk_level_1':'低风险','loan_use_02':'助学贷款','loan_use_01':'生产经营贷款','loan_use_05':'汽车贷款','job_6':'生产、运输设备操作人员','xz_shuiping':'水瓶座','trans_loan':'贷款','job_5':'农、林、牧、渔、水利业','risk_level_5':'高风险','job_4':'商业、服务业','risk_level_4':'中高风险','job_3':'办事人员','sex_woman':'女','risk_level_3':'中风险','ck_year_10_':'10年以上','job_2':'技术人员','zc_5_10':'6-10年','social_security_card':'市民卡','cert_others':'未知','generation_80s':'80后','sex_man':'男','xz_baiyang':'白羊座','searchengine_haosou':'好搜','security_secret_type':'密码安全认证','guaranteemethod_30':'抵押担保','security_others':'其它','generation_60s':'60后','xz_tianxie':'天蝎座','marriage_others':'未知','ck_year_5_10':'6-10年','ordinary_card':'普卡','xz_sheshou':'射手座','generation_00s':'00后','diamond_card':'钻石卡','job_x':'军人','terminal_pc':'pc','use_time_06':'下班后','use_time_07':'夜生活','guaranteemethod_20':'保证担保','trans_addedservice':'增值服务','searchengine_sogou':'搜狗','college_others':'未知','ck_year_others':'未知'}";

	// 交易偏好方法
	public void tradepreference(String userid, JSONObject result) {
		// 查询交易历史表,获取本月交易次数/交易金额总和
		List<Map<String, Object>> data = InceptorUtil.mapQuery(
				SqlKit.propSQL(SQLConfig.portrait_trading.toString()) + " where user_id= '" + userid + "'", true);
		List<Object> xAxisListCount = new ArrayList<Object>();
		// 雷达图计算各类型次数占比
		// 获取总次数与各个类型次数
		Map<String, String> focusmap = new HashMap<String, String>();
		focusmap.put("trans_dept", "存款");
		focusmap.put("trans_fin", "理财");
		focusmap.put("trans_loan", "贷款");
		focusmap.put("trans_addedservice", "增值服务");
		focusmap.put("trans_transfer", "转账");
		Map<String, Integer> radarMap = new HashMap<String, Integer>();
		radarMap.put("存款", 0);
		radarMap.put("理财", 0);
		radarMap.put("贷款", 0);
		radarMap.put("增值服务", 0);
		radarMap.put("转账", 0);
		Map<String, Integer> moneyMap = new HashMap<String, Integer>();
		moneyMap.put("存款", 0);
		moneyMap.put("理财", 0);
		moneyMap.put("贷款", 0);
		moneyMap.put("增值服务", 0);
		moneyMap.put("转账", 0);
		int tradeCount = 0;
		int tradeMoney = 0;
		int maxCountType = 0;
		String maxCountTypeDesc = "";
		int maxMoneyType = 0;
		String maxMoneyTypeDesc = "";

		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				Map ration = data.get(i);
				String key = (String) ration.get("trade_type");
				if (focusmap.containsKey(key)) {
					int num = Double.valueOf((String) ration.get("trade_thirty_count")).intValue();
					int m = Double.valueOf((String) ration.get("trade_thirty_money")).intValue();
					radarMap.put(focusmap.get(key), num);
					moneyMap.put(focusmap.get(key), m);
					tradeCount += num;
					tradeMoney += m;
					if (num > maxCountType) {
						maxCountType = num;
						maxCountTypeDesc = focusmap.get(key);
					}
					if (m > maxMoneyType) {
						maxMoneyType = m;
						maxMoneyTypeDesc = focusmap.get(key);
					}

				}
			}
		}
		// 3个月交易
		List<Object> xAxisList = new ArrayList<Object>();
		data = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.portrait_30_trading.toString()) + " where user_id= '"
				+ userid + "' group by date_month", true);
		if (data != null) {
			for (int i = 0; i < data.size(); i++) {
				Map ration = data.get(i);
				int num = Double.valueOf((String) ration.get("tc")).intValue();
				xAxisList.add(ration.get("date_month") + "月");
				xAxisListCount.add(num);
			}
		}
		// 进行图表展示
		String str = ChartUtils.genLineChart("月交易次数", xAxisList, xAxisListCount);
		result.put("chartOption", str);
		// 雷达图
		result.put("radar1", ChartUtils.genRadar("用户关注度", radarMap));
		result.put("moneyData", ChartUtils.genPortraitPie("用户交易金额分布", radarMap));
		// 交易总额
		result.put("tradeMoney", tradeMoney);
		result.put("tradeCount", tradeCount);
		// 金额和次数倾向
		result.put("maxCountTypeDesc", maxCountTypeDesc);
		result.put("maxMoneyTypeDesc", maxMoneyTypeDesc);

	}

	// 人际关系
	public void relation(String userid, JSONObject result) {
		// 查询人员支出
		List<Map<String, Object>> dataPay = InceptorUtil.mapQuery(SqlKit.propSQL(SQLConfig.portrait_relation.toString())
				+ " where user_id= '" + userid + "' or receive_id= '" + userid + "'", true);
		// 查询人员收入
		List<Object> pay = new ArrayList<Object>();
		List<Object> receive = new ArrayList<Object>();
		String name = "";
		if (dataPay != null) {
			for (int i = 0; i < dataPay.size(); i++) {
				Map para = dataPay.get(i);
				if (userid.equals(para.get("user_id").toString())) {
					pay.add(para.get("receive_name"));
					name = para.get("pay_name").toString();
				} else if (userid.equals(para.get("receive_id").toString())) {
					receive.add(para.get("pay_name"));
				}
			}
		}
		String str1 = ChartUtils.getForce(name, pay, receive);
		result.put("relationdiv", str1);
		// 类别信息
		List<Map<String, Object>> datatransfer = InceptorUtil.mapQuery(
				SqlKit.propSQL(SQLConfig.portrait_relation_group.toString()) + " where user_id= '" + userid + "'",
				true);
		if (datatransfer != null) {
			for (int i = 0; i < datatransfer.size(); i++) {
				Map para = datatransfer.get(i);
				result.put("hotName", para.get("closed_name"));
				result.put("relationname", para.get("recent_trans_name"));
				result.put("relationtime", para.get("recent_trans_time"));
			}
		}
	}

}
