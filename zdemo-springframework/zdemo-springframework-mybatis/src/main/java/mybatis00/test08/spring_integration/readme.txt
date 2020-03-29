
=====================
mybatis 与 spring 整合
=====================

1、依赖包
<!--mybatis-->
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis</artifactId>
	<version>3.4.1</version>
</dependency>
<!--mybatis-spring-->
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis-spring</artifactId>
	<version>1.3.1</version>
</dependency>
<!--spring&aspectj-->
<dependency>
	<groupId>org.springframework</groupId>
	<artifactId>spring-context</artifactId>
	<version>4.3.8.RELEASE</version>
</dependency>
<dependency>
	<groupId>org.aspectj</groupId>
	<artifactId>aspectjrt</artifactId>
	<version>1.8.10</version>
</dependency>
<dependency>
	<groupId>org.aspectj</groupId>
	<artifactId>aspectjweaver</artifactId>
	<version>1.8.10</version>
</dependency>


2、mybatis-config.xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration SYSTEM "http://mybatis.org/dtd/mybatis-3-config.dtd" >
<configuration>
	<!-- 全局配置项 -->
	<settings>
		<setting name="lazyLoadingEnabled" value="true"/>
		<setting name="aggressiveLazyLoading" value="false"/>
		<setting name="mapUnderscoreToCamelCase" value="true"/>
	</settings>
	<!-- 别名管理器 -->
	<typeAliases>
		<package name="test08.spring"/>
	</typeAliases>
</configuration>


3、spring applicationContext.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mybatis-spring="http://mybatis.org/schema/mybatis-spring"
	xmlns:p="http://www.springframework.org/schema/p"
	xmlns:tx="http://www.springframework.org/schema/tx"
	xsi:schemaLocation="http://mybatis.org/schema/mybatis-spring http://mybatis.org/schema/mybatis-spring-1.2.xsd
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.3.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.3.xsd">

	
	<!-- 引入外部配置文件 -->
	<context:property-placeholder location="test08/spring/jdbc.properties"/>
	
	<!-- 扫描组件包 -->
	<context:component-scan base-package="test08.spring"></context:component-scan>
	
	<!-- 数据源 -->
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="${jdbc.driver}"/>
		<property name="jdbcUrl" value="${jdbc.url}"/>
		<property name="user" value="${jdbc.username}"/>
		<property name="password" value="${jdbc.password}"/>
	</bean>
	
	<!-- 事务管理器 -->
	<bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<property name="dataSource" ref="dataSource"/>
	</bean>
	<tx:annotation-driven transaction-manager="transactionManager"/>
	
	<!-- 
		整合mybatis: 目的：1、spring自动注入mapper；2、管理事务
		SqlSessionFactoryBean 实现了 spring FactoryBean接口，
		spring ioc容器启动即会创建SqlSessionFactory对象
	 -->
	<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource"/>
		<property name="configLocation" value="test08/spring/mytais-config.xml"/>
		<property name="mapperLocations" >
			<list>
				<value>classpath:/test08/spring/*Mapper.xml</value>
			</list>
		</property>
	</bean>
	<!-- 扫描所有mapper接口实现，让这些mapper被容器管理 -->
	<mybatis-spring:scan base-package="test08.spring"/>
</beans>


4、编写mapper
package test08.spring;
public interface EmpMapper {
	Emp getEmpById(Integer id);
}
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper SYSTEM "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="test08.spring.EmpMapper">
	<select id="getEmpById" resultType="emp">
		select * from t_emp where id=#{id}
	</select>	
</mapper>


5、测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	"classpath:/test08/spring/applicationContext.xml"
})
public class Test1 {
	@Autowired
	private EmpMapper empMapper;
	
	@Test
	public void test01() {
		Emp emp = empMapper.getEmpById(1);
		System.err.println(emp);
	}
}
