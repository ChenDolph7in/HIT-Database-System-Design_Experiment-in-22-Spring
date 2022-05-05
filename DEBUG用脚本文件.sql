DROP DATABASE IF EXISTS SOCIAL;
CREATE DATABASE SOCIAL;
USE SOCIAL;

CREATE TABLE REGISTER_USERS -- 注册的用户表
(	
	USERNAME varchar(18) not null, -- 姓名
	SEX char(2) not null, -- 性别
	BIRTHDAY date not null, -- 生日
	EMAIL varchar(20) not null, -- 邮箱（登录用户名）
	PHONE varchar(11) not null, -- 联系方式
	PASSWD varchar(18) not null, -- 密码
	Primary Key(EMAIL),
	Check (SEX in ('f','m'))
);

CREATE TABLE OTHER_EMAIL -- 其他邮箱表（用户注册的其他邮箱，但不能作为登录用户名）
(
	EMAIL varchar(20) not null, -- 用户名（登陆用邮箱）
	OTHER_EMAIL varchar(20) not null, -- 其他邮箱
	Primary Key(OTHER_EMAIL),
	Foreign Key(EMAIL) references REGISTER_USERS(EMAIL)
);

CREATE TABLE LEARNS_IN -- 学习经历表
(
	EMAIL varchar(20) not null, -- 用户名
	SCHOOL_NAME varchar(36) not null, -- 学校名
	EDUCATE_LEVEL varchar(20) not null, -- 教育级别
	DEGREE varchar(18) not null, -- 学位
	START_DATE DATE not null, -- 开始日期
	END_DATE DATE not null, -- 结束日期
	Primary Key(EMAIL,SCHOOL_NAME,DEGREE),
	Foreign Key(EMAIL) references REGISTER_USERS(EMAIL)
);

CREATE TABLE WORKS_ON -- 工作经历
(
	EMAIL varchar(20) not null, -- 用户名
	WORKPLACE_NAME varchar(36) not null, -- 工作单位名
	JOB varchar(18) not null, -- 工作名
	START_DATE DATE not null, -- 开始日期
	END_DATE DATE not null, -- 结束日期
	Primary Key(EMAIL,WORKPLACE_NAME,JOB),
	Foreign Key(EMAIL) references REGISTER_USERS(EMAIL)
);

CREATE TABLE LOGS -- 日志表
(
	LNO bigint(20) not null auto_increment, -- 唯一标志日志号
	LOG_NAME varchar(36) not null, -- 日志标题
	LOG_DATA TEXT not null, -- 日志内容
	Primary Key(LNO)
);

CREATE TABLE UPDATE_LOGS -- 更新日志记录表
(
	EMAIL varchar(20) not null, -- 更新日志用户名
	LNO bigint(20) not null unique, -- 更新日志的日志号
	LAST_UPDATE DATE not null, -- 最后更新日期
	Primary Key(EMAIL,LNO),
	Foreign Key(EMAIL) references REGISTER_USERS(EMAIL),
	Foreign Key(LNO) references LOGS(LNO)
);

CREATE TABLE REVIEW_LOGS -- 评论日志记录表
(
	RNO bigint(20) not null auto_increment, -- 评论日志记录唯一标志号
	EMAIL varchar(20) not null, -- 发表评论用户名
	LNO bigint(20) not null, -- 所评论的日志的日志号
	REVIEW_DATE DATE not null, -- 评论日期
	REVIEW_DATA TEXT not null, -- 评论内容
	Primary Key(RNO),
	Foreign Key(EMAIL) references UPDATE_LOGS(EMAIL),
	Foreign Key(LNO) references LOGS(LNO)
);

CREATE TABLE FRIEND_GROUP -- 好友分组表
(
	EMAIL varchar(20) not null, -- 拥有分组的用户名
	GROUP_NAME varchar(20) default 'ALL', -- 拥有的分组名，每个用户注册时默认“ALL”分组
	Primary Key(EMAIL,GROUP_NAME),
	Foreign Key(EMAIL) references REGISTER_USERS(EMAIL)
);

CREATE TABLE FRIENDS -- 好友表
(
	EMAIL varchar(20) not null, -- 拥有好友的用户名
	FRIEND varchar(20) not null, -- 好友的用户名
	GROUP_NAME varchar(20) default 'ALL', -- 好友所在的分组，新加好友时默认加入“ALL”分组
	Primary Key(EMAIL,FRIEND,GROUP_NAME),
	Foreign Key(FRIEND) references REGISTER_USERS(EMAIL),
	Foreign Key(EMAIL,GROUP_NAME) references FRIEND_GROUP(EMAIL,GROUP_NAME)
);

CREATE TABLE REPLY_FRIENDS -- 回复好友记录表
(
	RNO bigint(20) not null auto_increment, -- 回复好友记录唯一标识号
	EMAIL varchar(20) not null, -- 回复者用户名
	FRIEND varchar(20) not null, -- 被回复者用户名
	REPLY_DATE DATE not null, -- 回复日期
	REPLY_DATA TEXT not null, -- 回复内容
	Primary Key(RNO),
	Foreign Key(EMAIL,FRIEND) references FRIENDS(EMAIL,FRIEND)
);

CREATE TABLE Signature -- 个人签名表
(
	EMAIL varchar(20) not null, -- 用户名
	DATA TEXT, -- 个人签名内容
	Foreign Key(EMAIL) references REGISTER_USERS(EMAIL)
);

-- 连接用户表、日志更新表和日志表，解决根据用户名查找日志号、日志名等信息的连接查询
CREATE VIEW user_update_logs(USERNAME,EMAIL,LNO,LOG_NAME,LAST_UPDATE) AS 
(
SELECT USERNAME,REGISTER_USERS.EMAIL,LOGS.LNO,LOG_NAME,LAST_UPDATE 
FROM  REGISTER_USERS,UPDATE_LOGS,LOGS 
WHERE REGISTER_USERS.EMAIL = UPDATE_LOGS.EMAIL AND UPDATE_LOGS.LNO = LOGS.LNO
);

-- 连接用户表、日志更新表和日志表，解决根据日志号查找日志拥有者用户名、日志内容等信息的连接查询
CREATE VIEW log_info(USERNAME,EMAIL,LNO,LOG_NAME,LAST_UPDATE,LOG_DATA) AS 
(
SELECT USERNAME,REGISTER_USERS.EMAIL,LOGS.LNO,LOG_NAME,LAST_UPDATE,LOG_DATA 
FROM  REGISTER_USERS,UPDATE_LOGS,LOGS 
WHERE REGISTER_USERS.EMAIL = UPDATE_LOGS.EMAIL AND UPDATE_LOGS.LNO = LOGS.LNO
);


-- 连接用户表、评论记录表和日志表，解决根据日志号查找评论者、评论内容等信息的连接查询
CREATE VIEW user_review_logs(LNO,REVIEW_DATE,REVIEW_DATA,REVIEW_USER,REVIEW_EMAIL) AS 
(
SELECT LOGS.LNO,REVIEW_DATE,REVIEW_DATA,REVIEWER.USERNAME,REVIEWER.EMAIL
FROM  REGISTER_USERS AS REVIEWER,REVIEW_LOGS,LOGS 
WHERE REVIEWER.EMAIL = REVIEW_LOGS.EMAIL AND REVIEW_LOGS.LNO = LOGS.LNO
);

-- 连接用户表、回复记录表，解决根据用户名查找回复者、对应回复内容等信息的连接查询
CREATE VIEW user_reply_friends(RESPONDER_NAME,RESPONDER_EMAIL,REPLY_DATE,REPLY_DATA,RECIEVER_EMAIL) AS 
(
SELECT RESPONDER.USERNAME,RESPONDER.EMAIL,REPLY_DATE,REPLY_DATA,RECIEVER.EMAIL
FROM  REGISTER_USERS AS RECIEVER,REGISTER_USERS AS RESPONDER,REPLY_FRIENDS
WHERE REPLY_FRIENDS.EMAIL = RESPONDER.EMAIL AND REPLY_FRIENDS.FRIEND = RECIEVER.EMAIL
);

-- 连接用户表和个人签名表，解决查找签名的连接查询
CREATE VIEW user_info(USERNAME,SEX,BIRTHDAY,SIGNUP_EMAIL,PHONE,DATA) AS 
(
SELECT REGISTER_USERS.USERNAME,REGISTER_USERS.SEX,REGISTER_USERS.BIRTHDAY,REGISTER_USERS.EMAIL,REGISTER_USERS.PHONE,Signature.DATA 
FROM  REGISTER_USERS,Signature 
WHERE REGISTER_USERS.EMAIL = Signature.EMAIL
);

-- 连接用户表和好友列表，解决根据用户名查找其好友的连接查询
CREATE VIEW friendsInfo(email,friend_name,friend_email,group_name) AS 
(
SELECT FRIENDS.EMAIL,REGISTER_USERS.USERNAME,FRIENDS.FRIEND ,FRIENDS.GROUP_NAME
FROM REGISTER_USERS,FRIENDS
WHERE FRIENDS.FRIEND = REGISTER_USERS.EMAIL
);