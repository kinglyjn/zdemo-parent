
springmvc配置文件说明：
==========================================================
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd
		http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd">

	
	<!-- 配置扫描组件包 -->
	<context:component-scan base-package="springmvc??"/>
	
	
	<!-- 开启springmvc注解 -->
	<mvc:annotation-driven conversion-service="conversionService"/>
	
	
	<!-- 
		装配转换器和格式化器
		1. 数据格式化从本质上讲属于数据转换的范畴，Spring就是基于数据转换框架植入“格式化”功能的。
		2. 可以使用ConversionServiceFactoryBean 或 FormattingConversionServiceFactoryBean
		   ConversionService只有数据转换功能，而 FormattingConversionServiceFactoryBean
		   既有数据转换也有数据格式化的功能，因而推荐使用FormattingConversionServiceFactoryBean
	 -->
	<bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
		<property name="converters">
			<set>
				<!-- 在这里可以添加自定义类型转换器 -->
				<bean class="springmvc03.converter_formatter_validator.MyConverter"/>
			</set>
		</property>
		<property name="formatters">
			<set>
				<!-- 在这里可以添加自定义格式化器 -->
			</set>
		</property>
	</bean>
	
	
	<!-- 
		装配DefaultServletHttpRequestHandler处理静态资源
		default-servlet-handler将在springmvc的上下文中定义一个spring#DefaultServletHttpRequestHandler，
		它会对进入DispatcherServlet的请求进行筛查，如果发现是没有被映射过的请求，就将该请求交由web应用服务器
		默认的servlet进行处理，如果发现不是静态资源的请求，才交由DispatcherServlet继续处理。
		一般web服务器默认的servlet名称都是default，若所用的web服务器的默认servlet名称不是default，则需要通过
		default-servlet-name属性进行指定。
	-->
	<mvc:default-servlet-handler/>
	
	
	<!-- 配置直接转发的页面（注意需要开启mvc:annotation-driven，否则会影响请求handler -->
	<!--配置mvc:view-controller之后，handlerMapping中会有两个SimpleUrlHandlerMapping-->
	<mvc:view-controller path="/" view-name="index"/>
	<mvc:view-controller path="/success" view-name="success"/>
	
	
	<!-- 装配视图解析器（把handler方法返回值解析为实际的物理视图，视图解析器order值越小就越优先使用）-->
	<!-- InternalResourceViewResolver（主要解析jsp和jstl页面）-->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/views/" />
		<property name="suffix" value=".jsp" />
	</bean>
	<!-- BeanNameViewResolver（根据bean#id解析自定义视图） -->
	<bean class="org.springframework.web.servlet.view.BeanNameViewResolver">
		<property name="order" value="1"/>
	</bean>
	<!-- 主要映射异常视图 -->
	<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
		<property name="exceptionMappings">
			<props>
				<!-- 默认的异常信息放在requestScope.exception中，可通过exceptionAttribute配置 -->
				<prop key="java.lang.ClassNotFoundException">error/500</prop>
			</props>
		</property>
	</bean>
	
	
	<!--装配国际化语言解析器和拦截器-->
	<mvc:interceptors>
	    <bean class="org.springframework.web.servlet.i18n.LocaleChangeInterceptor"></bean>
	</mvc:interceptors>
	
	
	<!-- 
		装配国际化使用到的SessionLocaleResolver和国际化资源文件
		注意解析器的名称必须为localeResolver，这样DispatcherServlet才能自动侦测到它
		并且需要开启mvc:annotation-driven
 	-->
	<bean id="localeResolver" class="org.springframework.web.servlet.i18n.SessionLocaleResolver"></bean>
	<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
		<property name="basenames">
			<list>
				<value>i18n.messages</value>
			</list>
		</property>
	</bean>
	
</beans>
