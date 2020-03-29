<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/tags.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>index</title>
</head>
<body>
	<h4>测试页面列表</h4><hr/>
	<a href="${ctx}/doc/index.html">API参考文档</a><hr>
	<a href="${ctx}/springmvc01/tohello">springmvc01.hello</a><br>
	<a href="${ctx}/springmvc02/emps">springmvc02.curd</a><br>
	<a href="${ctx}/springmvc03/emps">springmvc03.converter_formatter_validator</a><br>
	<a href="${ctx}/springmvc04/tomaintest">springmvc04.httpmessageconverter</a><br>
	<a href="${ctx}/springmvc06/tomaintest">springmvc06.handlexception</a><br>
	
</body>
</html>