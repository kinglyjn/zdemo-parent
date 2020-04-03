### 建表语句

```sql
CREATE TABLE `tb_user`(
	`id` BIGINT(20) NOT NULL auto_increment,
	`username` VARCHAR(50) DEFAULT NULL COMMENT '用户名',
	`password` VARCHAR(100) DEFAULT NULL COMMENT '密码',
	`name` VARCHAR(50) DEFAULT NULL COMMENT '姓名',
	`age` int(4) DEFAULT NULL COMMENT '年龄',
	`gender` TINYINT(1) DEFAULT NULL COMMENT '性别，1男 0女',
	`birthday` date DEFAULT NULL COMMENT '出生日期',
	`remark` VARCHAR(255) DEFAULT NULL COMMENT '备注',
	`create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP DEFAULT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`),
	UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=UTF8
```