# Host: localhost  (Version: 5.5.40)
# Date: 2016-01-18 10:07:32
# Generator: MySQL-Front 5.3  (Build 4.120)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "custom"
#

DROP TABLE IF EXISTS `custom`;
CREATE TABLE `custom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `url` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='自定义报表';

#
# Data for table "custom"
#

INSERT INTO `custom` VALUES (1,'用户分析','sss2','这个是用户新增分析的柱状图'),(4,'留存分析','用户分析','2015年7-10月份的留存分析'),(5,'门户概况分析','用户分析','每日门户概况分析'),(7,'厦航自定义报表','http://10.129.34.145:8080/?proc=1&action=viewer&hback=true&db=^6cd5^^9662^^2f^^884c^^4e1a^^5f8b^^6240^^6392^^540d^.db&browserType=Chrome','测试'),(8,'行为分析',NULL,'农信7月的用户行为分析');

#
# Structure for table "sys_nav"
#

DROP TABLE IF EXISTS `sys_nav`;
CREATE TABLE `sys_nav` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统id',
  `title` varchar(100) NOT NULL COMMENT '文字',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父级id',
  `res_id` int(11) NOT NULL DEFAULT '0' COMMENT '资源Id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=615 DEFAULT CHARSET=utf8 COMMENT='导航';

#
# Data for table "sys_nav"
#

/*!40000 ALTER TABLE `sys_nav` DISABLE KEYS */;
INSERT INTO `sys_nav` VALUES (200,'门户统计','',0,200),(210,'系统概况','',200,0),(211,'网站统计','icon-list',210,200),(212,'实时数据','icon-bar-chart',210,214),(220,'网站统计','',200,0),(221,'访问分析','icon-eye',220,215),(222,'页面排行','fa-file-text fa',220,213),(223,'广告趋势','icon-pie-chart',220,211),(224,'流量趋势','fa-bar-chart-o fa',220,212),(230,'用户统计','',200,0),(231,'区域分布','fa-map-marker fa',230,221),(232,'终端详情','icon-screen-desktop',230,222),(233,'用户等级','fa-foursquare fa',230,223),(234,'流失分析','fa-sort-amount-desc fa',230,224),(240,'来源统计','',200,0),(241,'来源分析','icon-social-facebook',240,232),(242,'搜索引擎','fa-search-plus fa',240,231),(300,'移动统计','',0,300),(320,'用户分析','',300,0),(321,'活跃用户','icon-bulb',320,341),(322,'留存用户','fa-save fa',320,344),(323,'注册用户','fa-male fa',320,343),(324,'登录用户','icon-login icon',320,342),(325,'用户等级','fa-certificate fa',320,345),(330,'使用行为分析','',300,0),(331,'使用时长','icon-clock',330,325),(332,'使用频率','fa-bar-chart-o fa',330,324),(333,'访问深度','fa-files-o fa',330,322),(334,'访问间隔','icon-calendar',330,323),(335,'地域分布','fa-map-marker fa',330,321),(340,'应用统计','',300,0),(341,'启动次数','fa-sort-numeric-asc fa',340,311),(342,'版本分布','fa-align-center fa',340,312),(350,'渠道统计','',300,330),(351,'渠道详细','icon-credit-card',350,331),(400,'自定义报表','',0,400),(410,'报表管理','',400,0),(411,'自定义报表','icon-film',410,400),(500,'用户画像','',0,500),(510,'群体画像','',500,0),(511,'标签墙','icon-tag',510,502),(512,'群体画像','fa-tags fa',510,501),(520,'个人画像','',500,0),(521,'个人画像','icon-user',520,503),(600,'系统管理','',0,600),(610,'系统管理','',600,600),(611,'用户管理','icon-users',610,640),(612,'角色管理','icon-ghost',610,630),(613,'菜单管理','icon-wallet',610,620),(614,'资源管理','icon-list',610,610);
/*!40000 ALTER TABLE `sys_nav` ENABLE KEYS */;

#
# Structure for table "sys_res"
#

DROP TABLE IF EXISTS `sys_res`;
CREATE TABLE `sys_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统Id',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父级Id',
  `cname` varchar(50) NOT NULL COMMENT '名称',
  `ak` varchar(200) DEFAULT NULL COMMENT 'actionKey',
  `seq` int(11) DEFAULT '10' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=702 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

#
# Data for table "sys_res"
#

/*!40000 ALTER TABLE `sys_res` DISABLE KEYS */;
INSERT INTO `sys_res` VALUES (200,0,'门户统计','/',0),(210,200,'网站统计','/portal/siteAnalysis',0),(211,210,'广告趋势','/portal/siteAnalysis/adTrend',0),(212,210,'流量趋势','/portal/siteAnalysis/flowTrend',0),(213,210,'页面排行','/portal/siteAnalysis/pageRank',0),(214,210,'实时数据','/portal/siteAnalysis/real',0),(215,210,'访问分析','/portal/siteAnalysis/visitAnalysis',0),(220,200,'用户统计','/portal/useranalysis',0),(221,220,'区域分布','/portal/useranalysis/area',0),(222,220,'终端详情','/portal/useranalysis/terminal',0),(223,220,'用户等级','/portal/useranalysis/userLevel',0),(224,220,'流失分析','/portal/useranalysis/userLoss',0),(230,200,'来源统计','/portal/visitSource',0),(231,230,'搜索引擎','/portal/visitSource/searchEngine',0),(232,230,'来源分析','/portal/visitSource/source',0),(300,0,'移动统计','/app',0),(310,300,'应用统计','/app/appAnalysis',0),(311,310,'启动次数','/app/appAnalysis/startCount',0),(312,310,'版本分布','/app/appAnalysis/version',0),(320,300,'使用行为分析','/app/behavior',0),(321,320,'地域分布','/app/behavior/area',0),(322,320,'访问深度','/app/behavior/depth',0),(323,320,'访问间隔','/app/behavior/interval',0),(324,320,'使用频率','/app/behavior/useRate',0),(325,320,'使用时长','/app/behavior/useTime',0),(330,300,'渠道分析','/app/channel',0),(331,330,'渠道详细','/app/channel/detail',0),(340,300,'用户分析','/app/userAnalysis',0),(341,340,'活跃用户','/app/userAnalysis/activeUser',0),(342,340,'登录用户','/app/userAnalysis/loginUser',0),(343,340,'注册用户','/app/userAnalysis/regUser',0),(344,340,'留存用户','/app/userAnalysis/retainUser',0),(345,340,'用户等级','/app/userAnalysis/userLevel',0),(400,0,'自定义报表','/custom',0),(401,400,'删除报表','/custom/delete',0),(402,400,'保存报表','/custom/save',0),(403,400,'更新报表','/custom/update',0),(404,400,'查看报表','/custom/view',0),(500,0,'用户画像','/portrait',0),(501,500,'群体画像','/portrait/groupTagList',0),(502,500,'标签墙','/portrait/tags',0),(503,500,'个人画像','/portrait/userPortrait',0),(600,0,'系统管理','/system',0),(610,600,'资源管理','/system/Resource',0),(611,610,'资源新增','/system/Resource/create',0),(612,610,'资源删除','/system/Resource/delete',0),(613,610,'资源查看','/system/Resource/getlist',0),(614,610,'资源更新','/system/Resource/update',0),(620,600,'菜单管理','/system/nav',0),(621,620,'菜单新增','/system/nav/create',0),(622,620,'菜单删除','/system/nav/delete',0),(623,620,'菜单查看','/system/nav/getlist',0),(624,620,'菜单更新','/system/nav/update',0),(630,600,'角色管理','/system/role',0),(631,630,'角色新增','/system/role/create',0),(632,630,'角色删除','/system/role/delete',0),(633,630,'角色查看','/system/role/getlist',0),(634,630,'角色更新','/system/role/update',0),(640,600,'用户管理','/system/users',0),(641,640,'用户新增','/system/users/add',0),(642,640,'用户删除','/system/users/delete',0),(643,640,'用户保存','/system/users/save',0),(644,640,'用户更新','/system/users/update',0),(700,0,'数据导出','/csv',0),(701,500,'测试',NULL,10);
/*!40000 ALTER TABLE `sys_res` ENABLE KEYS */;

#
# Structure for table "sys_role"
#

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `cname` varchar(20) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

#
# Data for table "sys_role"
#

/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (10,'管理员'),(11,'测试'),(12,'user');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

#
# Structure for table "sys_role_res"
#

DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `res_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

#
# Data for table "sys_role_res"
#

/*!40000 ALTER TABLE `sys_role_res` DISABLE KEYS */;
INSERT INTO `sys_role_res` VALUES (1,10,200),(2,10,210),(3,10,211),(4,10,212),(5,10,213),(6,10,214),(7,10,215),(8,10,220),(9,10,221),(10,10,222),(11,10,223),(12,10,224),(13,10,230),(14,10,231),(15,10,232),(16,10,300),(17,10,310),(18,10,311),(19,10,312),(20,10,320),(21,10,321),(22,10,322),(23,10,323),(24,10,324),(25,10,325),(26,10,330),(27,10,331),(28,10,340),(29,10,341),(30,10,342),(31,10,343),(32,10,344),(33,10,345),(34,10,400),(35,10,401),(36,10,402),(37,10,403),(38,10,404),(39,10,500),(40,10,501),(41,10,502),(42,10,503),(43,10,600),(44,10,610),(45,10,611),(46,10,612),(47,10,613),(48,10,614),(49,10,620),(50,10,621),(51,10,622),(52,10,623),(53,10,624),(54,10,630),(55,10,631),(56,10,632),(57,10,633),(58,10,634),(59,10,640),(60,10,641),(61,10,642),(62,10,643),(63,10,644),(64,10,700),(65,11,200),(66,12,200),(67,12,300),(68,12,210),(69,12,211),(70,12,212),(71,12,213),(72,12,214),(73,12,215),(74,12,220),(75,12,221),(76,12,222),(77,12,223),(78,12,224),(79,12,230),(80,12,231),(81,12,232),(82,12,310),(83,12,320),(84,12,330),(85,12,340),(86,12,311),(87,12,312),(88,12,321),(89,12,322),(90,12,323),(91,12,324),(92,12,325),(93,12,341),(94,12,342),(95,12,343),(96,12,344),(97,12,345),(98,12,331),(99,12,401),(100,12,402),(101,12,403),(102,12,404),(103,12,400);
/*!40000 ALTER TABLE `sys_role_res` ENABLE KEYS */;

#
# Structure for table "sys_users"
#

DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Data for table "sys_users"
#

INSERT INTO `sys_users` VALUES (4,'admin','admin','2015-08-05 17:20:38'),(5,'yanlei:','yanlei','2015-09-12 18:07:23'),(6,'user','user','2015-09-15 21:24:38');

#
# Structure for table "sys_user_roles"
#

DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`role_id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "sys_user_roles"
#

INSERT INTO `sys_user_roles` VALUES (4,10,'2015-09-15 10:16:26'),(5,11,'2015-09-15 10:16:17'),(6,12,'2015-09-15 21:24:38');
