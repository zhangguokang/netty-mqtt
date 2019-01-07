/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.17-log : Database - netty-mqtt
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`netty-mqtt` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `netty-mqtt`;

/*Table structure for table `msg_rep` */

DROP TABLE IF EXISTS `msg_rep`;

CREATE TABLE `msg_rep` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `messageid` int(10) DEFAULT NULL,
  `content` binary(255) DEFAULT NULL,
  `topname` varchar(20) DEFAULT NULL,
  `sendiden` varchar(20) CHARACTER SET utf8 COLLATE utf8_estonian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `msg_rep` */

/*Table structure for table `sendedmsg_record` */

DROP TABLE IF EXISTS `sendedmsg_record`;

CREATE TABLE `sendedmsg_record` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `clientid` varchar(20) DEFAULT NULL,
  `sendmsgid` int(10) DEFAULT NULL,
  `sendtimes` int(10) DEFAULT NULL,
  `sendclientid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sendedmsg_record` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `creaetdate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`creaetdate`) values (1,'tom','123456','2018-12-14 16:42:50');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
