/*
SQLyog Enterprise - MySQL GUI v7.14 
MySQL - 5.5.20 : Database - zm_manager
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`zm_manager` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `zm_manager`;

/*Table structure for table `s_resource` */

DROP TABLE IF EXISTS `s_resource`;

CREATE TABLE `s_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `type` char(1) DEFAULT NULL COMMENT '0:菜单，1：按钮',
  `create_user` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `s_resource` */

insert  into `s_resource`(`id`,`pid`,`name`,`url`,`seq`,`type`,`create_user`,`create_time`) values (1,0,'add','user/add',NULL,NULL,NULL,NULL),(2,0,'del','user/del',NULL,NULL,NULL,NULL),(3,0,'list','user/list',NULL,NULL,NULL,NULL),(4,0,'add','resource/add',NULL,NULL,NULL,NULL),(5,0,'del','resource/del',NULL,NULL,NULL,NULL),(6,0,'list','resource/list',NULL,NULL,NULL,NULL);

/*Table structure for table `s_role` */

DROP TABLE IF EXISTS `s_role`;

CREATE TABLE `s_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(20) DEFAULT NULL,
  `role_name` varchar(20) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `s_role` */

insert  into `s_role`(`id`,`role_code`,`role_name`,`remark`,`create_user`,`create_time`) values (1,'ROLE_USER','用户角色',NULL,NULL,NULL),(2,'ROLE_ADMIN','管理员角色',NULL,NULL,NULL);

/*Table structure for table `s_role_resource` */

DROP TABLE IF EXISTS `s_role_resource`;

CREATE TABLE `s_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `res_url` varchar(50) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `s_role_resource` */

insert  into `s_role_resource`(`id`,`res_url`,`role_id`,`create_time`,`create_user`) values (1,'1',2,NULL,NULL),(2,'2',2,NULL,NULL),(3,'3',2,NULL,NULL),(4,'4',2,NULL,NULL),(5,'5',2,NULL,NULL),(6,'6',2,NULL,NULL),(7,'3',1,NULL,NULL),(8,'6',1,NULL,NULL);

/*Table structure for table `s_user` */

DROP TABLE IF EXISTS `s_user`;

CREATE TABLE `s_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL COMMENT '0:女，1：男',
  `pwd` varchar(50) DEFAULT NULL,
  `user_type` char(1) DEFAULT NULL COMMENT '0:前台用户，1：后台用户',
  `head_url` varchar(100) DEFAULT NULL,
  `we_chat` varchar(20) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `s_user` */

insert  into `s_user`(`id`,`user_code`,`user_name`,`sex`,`pwd`,`user_type`,`head_url`,`we_chat`,`mobile`,`email`,`qq`,`create_user`,`create_time`) values (1,'admin','admin',NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'user','user','1','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `s_user_role` */

DROP TABLE IF EXISTS `s_user_role`;

CREATE TABLE `s_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `create_user` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `s_user_role` */

insert  into `s_user_role`(`id`,`user_id`,`role_id`,`create_user`,`create_time`) values (1,1,1,NULL,NULL),(2,2,2,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
