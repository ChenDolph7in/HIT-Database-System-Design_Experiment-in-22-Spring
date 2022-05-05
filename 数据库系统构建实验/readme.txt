操作系统：win10
IDE：IDEA 2021.2
语言： Java 1.8 (version1.8.0_291)
数据库：mysql  Ver 8.0.26 for Win64 on x86_64 (MySQL Community Server - GPL) 
编码：UTF-8
第三方库：mysql-connector-java-8.0.11.jar
（在项目的lib文件夹下。图形化界面使用Java的SWT和Swing库，程序只需要 mysql-connector-java 连接数据库）

复现步骤：
1.导入sql脚本
2.修改User.java和Register.java中的username和password，改为自己本地对应Mysql数据库的用户名和密码
3.将mysql-connector-java-8.0.11.jar放入Libraries
4.程序入口：SocialNet.main