# Host: localhost  (Version: 5.5.40)
# Date: 2015-09-02 10:58:18
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='自定义报表';

#
# Data for table "custom"
#

INSERT INTO `custom` VALUES (1,'用户分析','sss2','这个是用户新增分析的柱状图'),(3,'网站分析','网站分析','这个是页面的网站分析'),(4,'用户分析','用户分析','用户分析'),(5,'用户分析','用户分析','用户分析'),(7,'厦航自定义报表','http://10.129.34.145:8080/?proc=1&action=viewer&hback=true&db=^6cd5^^9662^^2f^^884c^^4e1a^^5f8b^^6240^^6392^^540d^.db&browserType=Chrome','测试');

#
# Structure for table "permissions"
#

DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `permission` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

#
# Data for table "permissions"
#

INSERT INTO `permissions` VALUES (1,5,'用户编辑','editUser',NULL,'2014-02-26 17:50:14'),(2,5,'用户展示','showUser',NULL,'2014-02-26 17:50:26'),(3,5,'添加用户','addUser',NULL,'2014-02-26 17:50:37'),(4,5,'删除用户','deleteUser',NULL,'2014-02-26 17:51:11'),(6,0,'系统管理','#this','系统管理','2015-08-05 10:48:38'),(7,6,'用户管理','users','用户管理','2015-08-05 10:49:13'),(8,6,'角色管理','role','角色管理','2015-08-05 10:49:44'),(9,6,'资源管理','permissions','资源管理','2015-08-05 10:50:09'),(10,9,'资源新增','permissions.add','资源新增','2015-08-05 10:52:36');

#
# Structure for table "roles"
#

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0激活 1注销',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "roles"
#

INSERT INTO `roles` VALUES (1,'admin','系统管理员',0),(2,'普通用户','普通用户',0),(3,'Test','test',1);

#
# Structure for table "roles_permissions"
#

DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `rolse_permissions_permissions_id_fk_idx` (`permission_id`),
  CONSTRAINT `rolse_permissions_permissions_id_fk` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `rolse_permissions_roles_id_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "roles_permissions"
#

INSERT INTO `roles_permissions` VALUES (1,6,'2015-08-05 16:08:13'),(1,7,'2015-08-05 16:08:13'),(1,8,'2015-08-05 16:08:13'),(3,6,'2015-08-05 15:40:59'),(3,7,'2015-08-05 15:40:59'),(3,8,'2015-08-05 15:40:59'),(3,9,'2015-08-05 15:40:59');

#
# Structure for table "users"
#

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0激活 1注销',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Data for table "users"
#

INSERT INTO `users` VALUES (1,'xiaoming','xiaoming',1,'2014-02-26 17:44:21'),(2,'xiaoming','xiaohong',1,'2014-02-26 17:44:45'),(3,'xiaohuang','xiaohuang',1,'2014-02-26 23:31:20'),(4,'admin','admin',0,'2015-08-05 17:20:38');

#
# Structure for table "user_roles"
#

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_roles_roles_id_fk_idx` (`role_id`),
  CONSTRAINT `user_roles_roles_id_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_roles_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "user_roles"
#

INSERT INTO `user_roles` VALUES (1,1,'2014-02-26 17:51:56'),(2,2,'2014-02-26 17:52:00'),(3,1,'2014-02-26 23:34:06'),(4,1,'2015-08-05 17:21:12');
