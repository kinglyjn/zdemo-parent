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

	<!-- 
		使用springmvc的form表单标签可以实现将模型数据中的属性和html表单元素进行绑定，实现表单数据更便捷的编辑和回显
		一般情况下，通过get请求获取表单页面，而通过post请求提交表单页面，因此获取和提交表单页面的url是相同的，这要满
		足该条件的最佳契约，springmvc#form标签就无需通过action属性指定表单提交的url
		
		可以通过form#modelAttribute属性来指定绑定的模型属性，若没有指定该属性，则默认从request域对象中读取command
		的表单bean，如果该属性值也不存在，则会发横错误！
		
		表单标签：
		springmvc提供了多个表单组件标签，如input、select等，用以绑定表单字段的属性值，他们共有的属性如下：
			path：表单字段，对应html的name属性，支持级联属性
			htmlEscape：是否对表单值的html特殊字符进行转换，默认值为true
			cssClass：表单组件对应的css样式类名
			cssErrorClass：表单组件的数据存在错误时采取的css样式
			
			form:input、form:password、form:hidden、form:textarea
			对应html表单的text、password、hidden、textarea
			
			form:radiobutton 单选框组件标签，当表单bean对应的属性值和value值相等时，单选框被选中
			form:radiobuttons 单选框组件标签，用于构建多个单选框
				items：可以使list、string[]、或者map
				itemValue：指定radio的value值，可以是集合中bean的一个属性
				itemLabel：指定radio的label值
				delimiter：多个单选框可以通过delimiter指定分割符
	-->
	
	<form:form action="${ctx}/springmvc02/emp" method="POST" modelAttribute="emp">
		
		<!-- 要求name添加时显示，修改时不显示且不能被修改 -->
		<c:if test="${empty emp.id}">
			name: <form:input path="name"/><br>
		</c:if>
		<c:if test="${!empty emp.id}">
			<form:hidden path="id"/>
			<input type="hidden" name="_method" value="PUT">
		</c:if>
		
		email: <form:input path="email"/><br><br>
		
		<%
			Map<String,String> genders = new HashMap<String,String>();
			genders.put("1", "Male");
			genders.put("0", "Female");
			request.setAttribute("genders", genders);
		%>
		gender: <br><form:radiobuttons path="gender" items="${genders}" delimiter="<br>"/><br><br>
		dept name: <form:select path="dept.id" items="${requestScope.depts}" itemLabel="name" itemValue="id" /><br><br>
			
		<input type="submit" value="提交">
	</form:form>
	
	
</body>
</html>