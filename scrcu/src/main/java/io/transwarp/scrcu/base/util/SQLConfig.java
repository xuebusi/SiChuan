package io.transwarp.scrcu.base.util;

public enum SQLConfig {
	// 图
	portal_chart,
	// 概览
	portal_index,
	// 门户网站分析 页面排行
	//实时
	portal_siteAnalysis_real,
	portal_pageRank,
	// 门户网站分析 页面分析
	portal_pageAnalysis,
	// 门户 网站统计 访问分析1 时长
	portal_visitAnalysis_time,
	// 门户 网站统计 访问分析2 深度
	portal_visitAnalysis_depth,
	// 门户 网站分析 会员页面
	portal_siteAnalysis_userVisitPage_query,
	// 用户统计 地域分布
	portal_siteAnalysis_area_query,
	// 用户统计 流失统计
	portal_siteAnalysis_userLoss_query,
	// 用户统计 唯一用户分析
	portal_userAnalysis_userOnly,
	// 门户 来源统计 来源
	portal_sourceAnalysis_source,
	// 门户 来源统计 搜索引擎
	portal_sourceAnalysis_searchEngine,
	// 门户 来源统计 入口页面
	portal_siteAnalysis_entryPage_query,
	// 门户 网站统计 广告趋势
	portal_siteAnalysis_adTrend_query,
	// 门户 网站统计 流量趋势
	portal_siteAnalysis_flowTrend,
	//门户 网站统计 流量趋势 chart图表 
	portal_siteAnalysis_flowTrend_chart,
	// 门户 用户统计 用户等级
	portal_userAnalysis_userLevel,
	// 门户 事件统计 事件列表
	portal_eventAnalysis_list,
	// 门户 事件统计 事件明细
	portal_eventAnalysis_detail,
	// 门户 事件统计 事件趋势
	portal_eventAnalysis_tendency,

	// 门户 来源统计 活跃用户 日期纬度
	app_activeUser,
	// 门户 来源统计 活跃用户 手机纬度
	app_activeUser_phone,
	// 门户 来源统计 活跃用户 渠道纬度
	app_activeUser_channel,

	// 门户 终端类型 操作系统占比
	portal_terminal_os,
	// 门户 终端类型 浏览器占比
	portal_terminal_browserDiv,
	//业务分析
	business_analysis,

	// 移动 留存用户分布
	app_retainUser,
	// 移动 手机留存用户分布
	app_retainUser_phone,
	// 移动 渠道留存用户分布
	app_retainUser_channel,
	// 移动 注册用户分析
	app_regUser,
	// 移动 渠道注册分布
	app_regUser_channel,
	// 移动 手机注册分布
	app_regUser_phone,
	// 移动 登录分析
	app_loginUser,
	// 移动 渠道登录分析
	app_loginUser_channel,
	// 移动 手机登录分布
	app_loginUser_phone,

	// 移动 新增用户分析
	app_newUser,
	// 移动 渠道新增用户分析
	app_newUser_channel,
	// 移动 手机新增用户分析
	app_newUser_phone,

	// APP 用户统计 用户等级
	app_userAnalysis_userLevel,
	// 移动 使用时长
	app_useTime,
	// 移动 使用版本时长
	app_useTime_phone,
	// 移动 使用渠道时长
	app_useTime_channel,
	// 移动 手机os时长
	app_useTime_os,
	// 移动 使用频率时长
	app_useRate,
	// 移动 手机使用频率时长
	app_useRate_phone,
	// 移动 渠道使用频率时长
	app_useRate_channel,
	// 移动 手机OS使用频率时长
	app_useRate_os,
	// 移动 访问深度分布
	app_depth,
	// 移动 按手机深度分布
	app_depth_phone,
	// 移动 按渠道深度分布
	app_depth_channel,
	// 移动 按手机os深度分布
	app_depth_os,
	// 移动 访问间隔分布
	app_interval,
	// 移动 按手机使用间隔分布
	app_interval_phone,
	// 移动 按渠道使用间隔分布
	app_interval_channel,
	// 移动 按手机os使用间隔分布
	app_interval_os,

	// 移动地域
	app_area_query,
	// 移动地域分布
	app_area_channel,
	// 移动版本地域分布
	app_area_phone,
	// 移动手机os地域分布
	app_area_os,

	// 移动 渠道明细
	app_channel_detail,
	// 移动 渠道列表
	app_channel_list,
	// 移动 渠道启动次数
	app_startCount_channel,
	// 移动 手机启动次数
	app_startCount_phone,

	//移动 会员页面分析
	app_memberPage,
	//移动 会员流失分析
	app_member_runoff,

	// 全部事件查询
	app_event_list,
	// 事件趋势
	app_event_tendency,
	// 事件详情
	app_event_detail,

	// 画像 交易曲线
	portrait_trading,
	// 画像 交易曲线
	portrait_30_trading,
	// 画像 交易曲线计数
	portrait_trading_sum,
	// 画像 交易次数最大
	portrait_trading_count,
	// 画像 交易金额最大
	portrait_trading_money,
	// 画像 交易占比
	portrait_trading_proportion,
	// 画像 人际关系
	portrait_relation,
	// 画像 用户标签汇总
	portal_userLabelDesc,
	// 当前用户的标签
	portal_userLabel,

	// 画像 上周标签汇总
	portal_userTrends_lastWeek,
	// 画像 交易衰减
	portrait_damping,
	// 画像 交易类型次数总数
	portrait_trading_ratio_sum,
	// 画像 交易类型次数
	portrait_trading_ratio,
	// 移动 版本分布
	app_version,
	// 静态标签
	label_label_grouping,
	// 画像 家庭关系
	portrait_family,
	// 画像 转账关系
	portrait_transfer,
	// 人际列表
	portrait_relation_group,

	// 群体,群体静态标签表总数，需要带标签的条件
	label_staticLabel_colony_grouping_tagCount,
	// 群体,群体动态标签表总数，需要带标签的条件
	label_dynamicLabel_colony_grouping_tagCount,
	// 群体用户
	label_colony_users, users,
	// 群体,群体静态标签表总数，需要带标签的条件
	label_staticLabel_colony_grouping,
	// 群体,群体动态标签表总数，需要带标签的条件
	label_dynamicLabel_colony_grouping,

}
