/*
Navicat MySQL Data Transfer

Source Server         : cdx_connect
Source Server Version : 80026
Source Host           : localhost:3306
Source Database       : social

Target Server Type    : MYSQL
Target Server Version : 80026
File Encoding         : 65001

Date: 2022-04-26 11:12:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for friends
-- ----------------------------
DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
  `EMAIL` varchar(20) NOT NULL,
  `FRIEND` varchar(20) NOT NULL,
  `GROUP_NAME` varchar(20) NOT NULL DEFAULT 'ALL',
  PRIMARY KEY (`EMAIL`,`FRIEND`,`GROUP_NAME`),
  KEY `FRIEND` (`FRIEND`),
  KEY `EMAIL` (`EMAIL`,`GROUP_NAME`),
  CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`FRIEND`) REFERENCES `register_users` (`EMAIL`),
  CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`EMAIL`, `GROUP_NAME`) REFERENCES `friend_group` (`EMAIL`, `GROUP_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for friend_group
-- ----------------------------
DROP TABLE IF EXISTS `friend_group`;
CREATE TABLE `friend_group` (
  `EMAIL` varchar(20) NOT NULL,
  `GROUP_NAME` varchar(20) NOT NULL DEFAULT 'ALL',
  PRIMARY KEY (`EMAIL`,`GROUP_NAME`),
  CONSTRAINT `friend_group_ibfk_1` FOREIGN KEY (`EMAIL`) REFERENCES `register_users` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for learns_in
-- ----------------------------
DROP TABLE IF EXISTS `learns_in`;
CREATE TABLE `learns_in` (
  `EMAIL` varchar(20) NOT NULL,
  `SCHOOL_NAME` varchar(36) NOT NULL,
  `EDUCATE_LEVEL` varchar(20) NOT NULL,
  `DEGREE` varchar(18) NOT NULL,
  `START_DATE` date NOT NULL,
  `END_DATE` date NOT NULL,
  PRIMARY KEY (`EMAIL`,`SCHOOL_NAME`,`DEGREE`),
  CONSTRAINT `learns_in_ibfk_1` FOREIGN KEY (`EMAIL`) REFERENCES `register_users` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for logs
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `LNO` bigint NOT NULL AUTO_INCREMENT,
  `LOG_NAME` varchar(36) NOT NULL,
  `LOG_DATA` text NOT NULL,
  PRIMARY KEY (`LNO`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for other_email
-- ----------------------------
DROP TABLE IF EXISTS `other_email`;
CREATE TABLE `other_email` (
  `EMAIL` varchar(20) NOT NULL,
  `OTHER_EMAIL` varchar(20) NOT NULL,
  PRIMARY KEY (`OTHER_EMAIL`),
  KEY `EMAIL` (`EMAIL`),
  CONSTRAINT `other_email_ibfk_1` FOREIGN KEY (`EMAIL`) REFERENCES `register_users` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for register_users
-- ----------------------------
DROP TABLE IF EXISTS `register_users`;
CREATE TABLE `register_users` (
  `USERNAME` varchar(18) NOT NULL,
  `SEX` char(2) NOT NULL,
  `BIRTHDAY` date NOT NULL,
  `EMAIL` varchar(20) NOT NULL,
  `PHONE` varchar(11) NOT NULL,
  `PASSWD` varchar(18) NOT NULL,
  PRIMARY KEY (`EMAIL`),
  CONSTRAINT `register_users_chk_1` CHECK ((`SEX` in (_gbk'f',_gbk'm')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for reply_friends
-- ----------------------------
DROP TABLE IF EXISTS `reply_friends`;
CREATE TABLE `reply_friends` (
  `RNO` bigint NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(20) NOT NULL,
  `FRIEND` varchar(20) NOT NULL,
  `REPLY_DATE` date NOT NULL,
  `REPLY_DATA` text NOT NULL,
  PRIMARY KEY (`RNO`),
  KEY `EMAIL` (`EMAIL`,`FRIEND`),
  CONSTRAINT `reply_friends_ibfk_1` FOREIGN KEY (`EMAIL`, `FRIEND`) REFERENCES `friends` (`EMAIL`, `FRIEND`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for review_logs
-- ----------------------------
DROP TABLE IF EXISTS `review_logs`;
CREATE TABLE `review_logs` (
  `RNO` bigint NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(20) NOT NULL,
  `LNO` bigint NOT NULL,
  `REVIEW_DATE` date NOT NULL,
  `REVIEW_DATA` text NOT NULL,
  PRIMARY KEY (`RNO`),
  KEY `EMAIL` (`EMAIL`),
  KEY `LNO` (`LNO`),
  CONSTRAINT `review_logs_ibfk_1` FOREIGN KEY (`EMAIL`) REFERENCES `update_logs` (`EMAIL`),
  CONSTRAINT `review_logs_ibfk_2` FOREIGN KEY (`LNO`) REFERENCES `logs` (`LNO`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for signature
-- ----------------------------
DROP TABLE IF EXISTS `signature`;
CREATE TABLE `signature` (
  `EMAIL` varchar(20) NOT NULL,
  `DATA` text,
  KEY `EMAIL` (`EMAIL`),
  CONSTRAINT `signature_ibfk_1` FOREIGN KEY (`EMAIL`) REFERENCES `register_users` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for update_logs
-- ----------------------------
DROP TABLE IF EXISTS `update_logs`;
CREATE TABLE `update_logs` (
  `EMAIL` varchar(20) NOT NULL,
  `LNO` bigint NOT NULL,
  `LAST_UPDATE` date NOT NULL,
  PRIMARY KEY (`EMAIL`,`LNO`),
  UNIQUE KEY `LNO` (`LNO`),
  CONSTRAINT `update_logs_ibfk_1` FOREIGN KEY (`EMAIL`) REFERENCES `register_users` (`EMAIL`),
  CONSTRAINT `update_logs_ibfk_2` FOREIGN KEY (`LNO`) REFERENCES `logs` (`LNO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for works_on
-- ----------------------------
DROP TABLE IF EXISTS `works_on`;
CREATE TABLE `works_on` (
  `EMAIL` varchar(20) NOT NULL,
  `WORKPLACE_NAME` varchar(36) NOT NULL,
  `JOB` varchar(18) NOT NULL,
  `START_DATE` date NOT NULL,
  `END_DATE` date NOT NULL,
  PRIMARY KEY (`EMAIL`,`WORKPLACE_NAME`,`JOB`),
  CONSTRAINT `works_on_ibfk_1` FOREIGN KEY (`EMAIL`) REFERENCES `register_users` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- View structure for friendsinfo
-- ----------------------------
DROP VIEW IF EXISTS `friendsinfo`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `friendsinfo` (`email`,`friend_name`,`friend_email`,`group_name`) AS select `friends`.`EMAIL` AS `EMAIL`,`register_users`.`USERNAME` AS `USERNAME`,`friends`.`FRIEND` AS `FRIEND`,`friends`.`GROUP_NAME` AS `GROUP_NAME` from (`register_users` join `friends`) where (`friends`.`FRIEND` = `register_users`.`EMAIL`) ;

-- ----------------------------
-- View structure for log_info
-- ----------------------------
DROP VIEW IF EXISTS `log_info`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `log_info` (`USERNAME`,`EMAIL`,`LNO`,`LOG_NAME`,`LAST_UPDATE`,`LOG_DATA`) AS select `register_users`.`USERNAME` AS `USERNAME`,`register_users`.`EMAIL` AS `EMAIL`,`logs`.`LNO` AS `LNO`,`logs`.`LOG_NAME` AS `LOG_NAME`,`update_logs`.`LAST_UPDATE` AS `LAST_UPDATE`,`logs`.`LOG_DATA` AS `LOG_DATA` from ((`register_users` join `update_logs`) join `logs`) where ((`register_users`.`EMAIL` = `update_logs`.`EMAIL`) and (`update_logs`.`LNO` = `logs`.`LNO`)) ;

-- ----------------------------
-- View structure for user_info
-- ----------------------------
DROP VIEW IF EXISTS `user_info`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_info` (`USERNAME`,`SEX`,`BIRTHDAY`,`SIGNUP_EMAIL`,`PHONE`,`DATA`) AS select `register_users`.`USERNAME` AS `USERNAME`,`register_users`.`SEX` AS `SEX`,`register_users`.`BIRTHDAY` AS `BIRTHDAY`,`register_users`.`EMAIL` AS `EMAIL`,`register_users`.`PHONE` AS `PHONE`,`signature`.`DATA` AS `DATA` from (`register_users` join `signature`) where (`register_users`.`EMAIL` = `signature`.`EMAIL`) ;

-- ----------------------------
-- View structure for user_reply_friends
-- ----------------------------
DROP VIEW IF EXISTS `user_reply_friends`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_reply_friends` (`RESPONDER_NAME`,`RESPONDER_EMAIL`,`REPLY_DATE`,`REPLY_DATA`,`RECIEVER_EMAIL`) AS select `responder`.`USERNAME` AS `USERNAME`,`responder`.`EMAIL` AS `EMAIL`,`reply_friends`.`REPLY_DATE` AS `REPLY_DATE`,`reply_friends`.`REPLY_DATA` AS `REPLY_DATA`,`reciever`.`EMAIL` AS `EMAIL` from ((`register_users` `reciever` join `register_users` `responder`) join `reply_friends`) where ((`reply_friends`.`EMAIL` = `responder`.`EMAIL`) and (`reply_friends`.`FRIEND` = `reciever`.`EMAIL`)) ;

-- ----------------------------
-- View structure for user_review_logs
-- ----------------------------
DROP VIEW IF EXISTS `user_review_logs`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_review_logs` (`LNO`,`REVIEW_DATE`,`REVIEW_DATA`,`REVIEW_USER`,`REVIEW_EMAIL`) AS select `logs`.`LNO` AS `LNO`,`review_logs`.`REVIEW_DATE` AS `REVIEW_DATE`,`review_logs`.`REVIEW_DATA` AS `REVIEW_DATA`,`reviewer`.`USERNAME` AS `USERNAME`,`reviewer`.`EMAIL` AS `EMAIL` from ((`register_users` `reviewer` join `review_logs`) join `logs`) where ((`reviewer`.`EMAIL` = `review_logs`.`EMAIL`) and (`review_logs`.`LNO` = `logs`.`LNO`)) ;

-- ----------------------------
-- View structure for user_update_logs
-- ----------------------------
DROP VIEW IF EXISTS `user_update_logs`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_update_logs` (`USERNAME`,`EMAIL`,`LNO`,`LOG_NAME`,`LAST_UPDATE`) AS select `register_users`.`USERNAME` AS `USERNAME`,`register_users`.`EMAIL` AS `EMAIL`,`logs`.`LNO` AS `LNO`,`logs`.`LOG_NAME` AS `LOG_NAME`,`update_logs`.`LAST_UPDATE` AS `LAST_UPDATE` from ((`register_users` join `update_logs`) join `logs`) where ((`register_users`.`EMAIL` = `update_logs`.`EMAIL`) and (`update_logs`.`LNO` = `logs`.`LNO`)) ;
