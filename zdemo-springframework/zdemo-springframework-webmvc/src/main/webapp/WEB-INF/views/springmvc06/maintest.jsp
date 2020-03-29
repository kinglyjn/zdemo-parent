<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/tags.jsp"%>    
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>maintest</title>
</head>
<body>
	<h4>test01: 测试使用 @ExceptionHandler 标注handler异常处理方法来处理单个控制器异常</h4><hr>
	<a href="${ctx}/springmvc06/test01?i=0">test01: 测试使用@ExceptionHandler标注handler异常处理方法来处理单个控制器异常</a><br><br>
	
	<h4>test02: 测试使用 @ControllerAdvice 注解来定义全局的异常处理</h4><hr>
	<a href="${ctx}/springmvc06/test02?i=11">test02: 测试使用 @ControllerAdvice 注解来定义全局的异常处理</a><br><br>
	
	<h4>test03: 测试使用 SimpleMappingExceptionResolver 映射全局的异常处理</h4><hr>
	<a href="${ctx}/springmvc06/test03?i=0">test03: 测试使用 SimpleMappingExceptionResolver 映射全局的异常处理</a><br><br>
	
	<h4>test04: 测试使用 @ResponseStatus 注解定制页面（正确的用户名和密码为：zhangsan/123456）</h4><hr>
	<a href="${ctx}/springmvc06/test04?username=aaa&password=123">test04: 测试使用 @ResponseStatus 注解定制页面</a><br><br>
</body>
</html>