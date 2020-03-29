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
	
	<h4>test01: 使用HttpMessageConverter的@ResponseBody修饰handler方法的返回值，来处理json</h4><hr>
	<a href="${ctx}/springmvc04/test01">test01: 使用HttpMessageConverter的@ResponseBody修饰handler方法的返回值，来处理json</a><br><br>
	
	
	<h4>test02: 使用HttpMessageConverter的@RequestBody修饰handler方法的入参，@ResponseBody修饰返回值，测试</h4><hr>
	<form action="${ctx}/springmvc04/test02" method="post" enctype="multipart/form-data">
		file: <input type="file" name="file"><br>
		desc: <input type="text" name="desc"><br>
		<input type="submit" value="提交">
	</form><br><br>
	
	
	<h4>test03: 使用HttpMessageConverter的@ResponseBody修饰返回值实现下载的效果</h4><hr>
	<a href="${ctx}/springmvc04/test03">test03: 使用HttpMessageConverter的@ResponseBody修饰返回值实现下载的效果</a><br><br>	
	
	
	<h4>test04: 使用HttpMessageConverter的ResponseEntity&lt;T&gt;修饰返回值实现下载功能</h4><hr>
	<a href="${ctx}/springmvc04/test03">test04: 使用HttpMessageConverter的ResponseEntity&lt;T&gt;修饰返回值实现下载功能</a><br><br>	
	
	
	<h4>test05: 使用CommonsMutipartResolver组件实现上传功能</h4><hr>
	<form action="${ctx}/springmvc04/test05" method="post" enctype="multipart/form-data">
		file: <input type="file" name="file"><br>
		desc: <input type="text" name="desc"><br>
		<input type="submit" value="提交">
	</form><br><br>
</body>
</html>