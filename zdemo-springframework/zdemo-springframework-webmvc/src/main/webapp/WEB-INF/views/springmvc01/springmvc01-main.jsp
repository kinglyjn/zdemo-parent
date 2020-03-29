<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/tags.jsp"%>
   
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>springmvc01-main</title>
</head>
<body style="margin:20px;">
	
	<!-- 测试 @RequestMapping 映射请求 -->
	<h4>测试 @RequestMapping 映射请求</h4><hr>
	<a href="${ctx}/springmvc01/test01">test01: 使用@RequestMapping映射请求路径</a><br>
	<a href="${ctx}/springmvc01/test02">test02: 使用@RequestMapping映射请求方法</a><br>
	<a href="${ctx}/springmvc01/test03?username=张三&age=10">test03: 使用@RequestMapping映射请求参数</a><br>
	<a href="${ctx}/springmvc01/test04">test04: 使用@RequestMapping映射请求头</a><br>
	<a href="${ctx}/springmvc01/test05/xxxx/abc">test05: 使用@RequestMapping映射ant风格的路径</a><br>
	<a href="${ctx}/springmvc01/test06/delete/3">test06: 使用@PathVariable映射请求路径中的参数</a><br><br>
	
	
	<!-- 测试springmvc对REST风格请求的支持 -->
	<h4>测试springmvc对REST风格请求的支持</h4><hr>
	<div>
		<span>test07:</span> 
		<form action="${ctx}/springmvc01/test07/post" method="post" style="display: inline;">
			<input type="submit" value="REST POST REQUEST">
		</form>
	</div>
	<div>
		<span>test08:</span> 
		<form action="${ctx}/springmvc01/test08/delete/1" method="post" style="display: inline;">
			<input type="hidden" name="_method" value="DELETE">
			<input type="submit" value="REST DELETE REQUEST">
		</form>
	</div>
	<div>
		<span>test09:</span> 
		<form action="${ctx}/springmvc01/test09/put/1" method="post" style="display: inline;">
			<input type="hidden" name="_method" value="PUT">
			<input type="submit" value="REST PUT REQUEST">
		</form>
	</div>
	<div>
		<span>test10:</span> 
		<form action="${ctx}/springmvc01/test10/get/1" method="get" style="display: inline;">
			<input type="submit" value="REST GET REQUEST">
		</form>
	</div>
	<br>
	
	
	<!-- 测试使用@RequestParam绑定参数 -->
	<h4>测试使用@RequestParam绑定参数</h4><hr>
	<a href="${ctx}/springmvc01/test11?username=张三&age=0">test11: 测试使用@RequestParam绑定参数</a><br><br>
	
	
	<!-- 测试使用@CookieValue绑定cookie参数 -->
	<h4>使用@CookieValue绑定cookie参数</h4><hr>
	<a href="${ctx}/springmvc01/test12">test12: 使用@CookieValue绑定cookie参数</a><br><br>
	
	
	<!-- 测试绑定pojo对象 -->
	<h4>测试绑定pojo对象</h4><hr>
	<form action="${ctx}/springmvc01/test13" method="post">
		<input type="hidden" name="id" value="1">
		用户名：<input type="text" name="username">
		密码：<input type="password" name="password">
		年龄：<input type="text" name="age">
		省：<input type="text" name="address.province">
		市：<input type="text" name="address.city">
		<input type="submit" value="提交">
	</form><br>
	
	
	<!-- 测试使用springmvc获取servlet原生API -->
	<h4>测试使用springmvc获取servlet原生API</h4><hr>
	<a href="${ctx}/springmvc01/test14">test14: 测试使用springmvc获取servlet原生API</a><br><br>
	
	
	<!-- springmvc处理数据模型 -->
	<h4>springmvc处理数据模型</h4><hr>
	<a href="${ctx}/springmvc01/test15">test15: springmvc处理数据模型之 modelAndView（作为返回值）</a><br>
	<a href="${ctx}/springmvc01/test16">test16: springmvc处理数据模型之 Map|Model（作为参数或者是返回值）</a><br>
	<a href="${ctx}/springmvc01/test17">test17: springmvc处理数据模型之 @SessionAttributes (将值放在session域中) </a><br>
	test18: springmvc处理数据模型之 @ModelAttributes（在目标方法执行之前将数据放到数据模型中）<br>
	<form action="${ctx}/springmvc01/test18" method="post">
		<input type="hidden" name="id" value="1">
		用户名：<input type="text" name="username">
		年龄：<input type="text" name="age">
		省：<input type="text" name="address.province">
		市：<input type="text" name="address.city">
		<input type="submit" value="提交"> &nbsp;&nbsp;&nbsp;&nbsp;注: 意密码不能修改！
	</form><br>
	
	
	<!-- springmvc视图解析流程断点分析 -->
	<h4>springmvc视图解析流程断点分析</h4><hr>
	<a href="${ctx}/springmvc01/test19">test19: springmvc视图解析流程断点分析</a><br><br>
	
	
	<h4>使用浏览器切换国际化参数（首先需要通过taglib导入fmt标签，并且需要在springmvc配置文件中配置国际化资源文件）</h4><hr>
	<fmt:message key="i18n.username"></fmt:message> &nbsp;
	<fmt:message key="i18n.password"></fmt:message><br><br>
	
	
	<h4>使用超链接切换国际化参数（除了配置国际化资源文件，还需要在springmvc配置文件中装配SessionLocaleResolver和LocaleChangeInterceptor）</h4><hr>
	<a href="${ctx}/springmvc01/tohello?locale=zh_CN">中文</a>
	<a href="${ctx}/springmvc01/tohello?locale=en_US">英文</a> &nbsp; 
	<fmt:message key="i18n.username"></fmt:message> &nbsp;
	<fmt:message key="i18n.password"></fmt:message><br><br>
	
	
	<h4>springmvc创建自定义视图（Component HelloView implements View）测试</h4><hr>
	<a href="${ctx}/springmvc01/test20">test20: springmvc自定义视图测试</a><br><br>
	
	
	<h4>转发和重定向测试</h4><hr>
	<a href="${ctx}/springmvc01/test21">test21: 转发到success页面</a><br>
	<a href="${ctx}/springmvc01/test22">test22: 重定向到success页面</a><br><br>
</body>
</html>