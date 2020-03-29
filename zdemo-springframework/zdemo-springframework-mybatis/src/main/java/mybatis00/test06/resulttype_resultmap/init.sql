
create database if not exists test;

/**员工表*/
drop table if exists test.t_emp;
create table if not exists test.t_emp(
	id int not null primary key auto_increment,
	last_name varchar(50),
	email varchar(50),
	gender varchar(10) default '0',
	dept_id int
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into t_emp values(default, 'zhangsan', 'zhangsan@keyllo.com', '1', 1);
insert into t_emp values(default, 'lisi', 'lisi@keyllo.com', '1', 1);
insert into t_emp values(default, 'xiaojuan', 'xiaojuan@keyllo.com', '0', 1);
insert into t_emp values(default, 'wangwu', 'wangwu@keyllo.com', '1', 2);
insert into t_emp values(default, 'zhaoliu', 'zhaoliu@keyllo.com', '1', 2);


/**部门表*/
drop table if exists test.t_dept;
create table if not exists test.t_dept(
	id int not null primary key auto_increment,
	dept_name varchar(50) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into t_dept values(default, '研发部');
insert into t_dept values(default, '测试部');



