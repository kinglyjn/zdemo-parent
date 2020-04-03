### 建表语句

```sql
CREATE TABLE `tb_order`(
	`id` BIGINT(20) NOT NULL auto_increment,
	`order_number` VARCHAR(50) NOT NULL COMMENT '订单号',
	`price` DECIMAL(10, 3) NOT NULL COMMENT '价格',
	`img` VARCHAR(255) DEFAULT NULL COMMENT '图片',
	`create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
	`username` VARCHAR(50) DEFAULT NULL COMMENT '用户名',
	`product_id` BIGINT(20) NOT NULL COMMENT '商品ID',
	PRIMARY KEY (`id`),
	UNIQUE KEY `order_number` (`order_number`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=UTF8;
```

### RestTemplate类的说明

* 业务需求：根据用户的id调用用户服务接口数据，查询用户的名字
* 使用RestTemplate，它是java也是对java.net.URLConnection/Socket 的封装（注意java世界中，唯一与外界进行通信的接口只有java.net.URLConnection/Socket）
* 除了RestTemplate，apache httpClient、okHttp、netty、rpc、grpc、tomcat基本都是对java网络接口的封装

