drop database if exists qianfanmall;
drop user if exists 'qianfanmall'@'%';
-- 支持emoji：需要mysql数据库参数： character_set_server=utf8mb4
create database qianfanmall default character set utf8mb4 collate utf8mb4_unicode_ci;
use qianfanmall;
create user 'qianfanmall'@'%' identified by 'qianfanmall123456';
grant all privileges on qianfanmall.* to 'qianfanmall'@'%';
flush privileges;