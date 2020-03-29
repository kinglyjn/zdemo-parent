create database if not exists test;

drop table if exists test.t_user;
create table test.t_user(
	id int not null primary key auto_increment,
	name varchar(50) not null,
	age int,
	birthday datetime
);

insert into t_user values(default, 'zhangsan', 23, '1990-09-09 12:12:23');
insert into t_user values(default, 'lisi', 25, '1990-10-19 12:22:23');
insert into t_user values(default, 'xiaojuan', 23, '1991-09-09 12:12:23');