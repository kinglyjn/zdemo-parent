<%@ page import="java.util.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/tags.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>add</title>
</head>
<body>
	
	<!-- 测试增删改查 -->
	<h4>测试增删改查</h4><hr>
	
	<form:form action="${ctx}/springmvc03/emp" method="POST" modelAttribute="emp">
		
		<!-- 要求name添加时显示，修改时不显示且不能被修改 -->
		<c:if test="${empty emp.id}">
			name: <form:input path="name"/> 
			&nbsp; <form:errors path="name"/> <br>
		</c:if>
		<c:if test="${!empty emp.id}">
			<form:hidden path="id"/>
			<input type="hidden" name="_method" value="PUT">
		</c:if>
		
		email: <form:input path="email"/>
		&nbsp; <form:errors path="email"/> <br>
		
		<%
			Map<String,String> genders = new HashMap<String,String>();
			genders.put("1", "Male");
			genders.put("0", "Female");
			request.setAttribute("genders", genders);
		%>
		gender: <br><form:radiobuttons path="gender" items="${genders}" delimiter="<br>"/><br><br>
		dept name: <form:select path="dept.id" items="${requestScope.depts}" itemLabel="name" itemValue="id" /><br>
		birthday: <form:input path="birthday"/>
		&nbsp; <form:errors path="birthday"/> <br><br>
			
		<input type="submit" value="提交">
	</form:form><br><br>
	
	
	
	<!-- 测试类型转换器 -->
	<h4>测试类型转换器</h4><hr>
	<form action="${ctx}/springmvc03/testconverter01" method="post">
		<textarea name="emp" rows="5" cols="60" style="resize:none;">12-张三-zhangsan@keyllo.com-1</textarea><br>
		<input type="submit" value="提交">
	</form><br><br>
	
	
	<!-- 测试格式化器 -->
	<h4>测试格式化器</h4><hr>
	<form action="${ctx}/springmvc03/testformatter01" method="post">
		birthday: <input type="text" name="birthday" value="1990-05-27"><br>
		salary: <input type="text" name="salary" value="24,000.00"><br>
		<input type="submit" value="提交">
	</form><br><br>
</body>
</html>