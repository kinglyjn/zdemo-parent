<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/tags.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>success page</title>
</head>
<body>
	<h4>springmvc01.hello</h4><hr/>
	<b>test15:</b> \${requestScope.test15_time} is ${requestScope.test15_time} <br>
	<b>test16:</b> \${requestScope.test16_names} is ${requestScope.test16_names} <br>
	<b>test17:</b> \${requestScope.test17_user} is ${requestScope.test17_user} <br>
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   \${sessionScope.test17_user} is ${sessionScope.test17_user} <br>
				   
			
	
</body>
</html>