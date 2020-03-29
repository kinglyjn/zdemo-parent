
/** 建库*/
create database if not exists test;

/** 员工表*/
drop table if exists test.t_emp;
create table if not exists test.t_emp(
	id int(11) not null primary key auto_increment,
	name varchar(50) not null,
	salary double default '0',
	dept_id int(11) default null
);
insert into test.t_emp(id, name, salary, dept_id) values(default, 'zhangsan', 30000, 1);
insert into test.t_emp(id, name, salary, dept_id) values(default, 'lisi', 28000, 2);
insert into test.t_emp(id, name, salary, dept_id) values(default, 'xiaojuan', 32000, 1);


/** 部门表*/
drop table if exists test.t_dept;
create table if not exists test.t_dept(
	id int(11) not null primary key auto_increment,
	name varchar(50) not null
);
insert into test.t_dept(id, name) values(default, '研发部');
insert into test.t_dept(id, name) values(default, '测试部');

