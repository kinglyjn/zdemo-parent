
spring整合jpa（使用hibernate作为jpa的实现产品）
	整合目标:
		1. 使用spring管理jpa的entityManagerFactory（ioc内容）
		2. 使用spring的声明式事务管理（aop内容）
		
		要求整合之后entityManger的获取方式和使用方式：
		1. 使用 类属性注解 @PersistenceContext 来获取 entityManager
		2. 使用 entityManager 必须要在【spring事务（ @Transactional ）】管理之下，
		   否则将抛出javax.persistence.TransactionRequiredException异常


	三种整合方式：
		1. LocalEntityManagerFactoryBean：
			适用于那些仅使用JPA进行数据访问的项目，该FactoryBean根据JPA PersistenceProvider自动检测
			配置文件进行工作，一般从“META-INF/persistence.xml”读取配置信息，这种方式最简单，但是不能
			设置spring中定义的DataSource，且不支持spring管理的全局事务。
		2. 从JNDI中获取：
			用于从javaEE服务器获取指定的EntityManagerFactory，这种方式在进行spring事务管理时一般使用
			JTA事务管理。
		3. LocalContainerEntityManagerFacvtoryBean：（推荐方式）
			使用于所有环境的FactoryBean，能够全面控制EntityManagerFactory配置，如指定Spring定义的
			DataSource等等。
	
			
	jar包：
		c3p0
		mysql-connector-java
		hibernate-core
		hibernate-entitymanager（hibernate jpa 驱动包）
		hibernate-ehcache
		spring-orm
		spring-context
		spring-context-support
		spring-aop & aspectjrt & aspectjweaver
		
	
	spring基本配置：
	---------------------------------------------
		<!-- 配置扫描组件包 -->
		<context:component-scan base-package="spring08.integrate_jpawithhibernate" />
	
		<!-- 引入外部配置参数 -->
		<context:property-placeholder location="my.properties" />
	
		<!-- 装配数据源（c3p0实现） -->
		<bean id="c3p0Datasource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
			<property name="user" value="${username}" />
			<property name="password" value="${password}" />
			<property name="jdbcUrl" value="${jdbcUrl}" />
			<property name="driverClass" value="${driverClass}" />
			<property name="initialPoolSize" value="${initialPoolSize}" />
			<property name="minPoolSize" value="${minPoolSize}" />
			<property name="maxPoolSize" value="${maxPoolSize}" />
			<property name="acquireIncrement" value="${acquireIncrement}" />
			<property name="maxStatements" value="${maxStatements}" />
			<property name="maxIdleTime" value="${maxIdleTime}" />
			<property name="checkoutTimeout" value="${checkoutTimeout}" />
			<property name="idleConnectionTestPeriod" value="${idleConnectionTestPeriod}" />
		</bean>
	
		<!-- 装配entityManagerFactory（jpa采用hibernate实现） -->
		<bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
			<!-- 数据源 -->
			<property name="dataSource" ref="c3p0Datasource" />
			<!-- JPA提供商 -->
			<property name="jpaVendorAdapter">
				<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter" />
			</property>
			<!-- 实体类所在的包 -->
			<property name="packagesToScan" value="spring08.integrate_jpawithhibernate"/>
			<!-- jpa实现产品进本属性 -->
			<property name="jpaProperties">
				<props>
					<prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
					<prop key="hibernate.show_sql">true</prop>
					<prop key="hibernate.format_sql">true</prop>
					<prop key="hibernate.hbm2ddl.auto">update</prop>
					<prop key="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</prop>    	
					<prop key="hibernate.cache.use_second_level_cache">true</prop>
					<prop key="hibernate.cache.use_query_cache">true</prop>
				</props>
			</property>
		</bean>
		
		<!-- 装配事务管理器 -->
		<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
			<property name="entityManagerFactory" ref="entityManagerFactory" />
		</bean>
		
		<!-- 配置开启事务注解 -->
		<tx:annotation-driven transaction-manager="transactionManager"/>
			
		
		
	
 
 	
		
		
	