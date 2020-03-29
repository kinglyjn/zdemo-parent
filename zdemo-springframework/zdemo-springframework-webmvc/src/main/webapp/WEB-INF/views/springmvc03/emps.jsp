<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/tags.jsp" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>list</title>
</head>
<body>
	<c:if test="${empty requestScope.emps}">
		没有任何员工信息！
	</c:if>
	<c:if test="${!empty requestScope.emps}">
		<table border="1" cellpadding="10" cellspacing="0">
			<tr>
				<th>id</th>
				<th>name</th>
				<th>email</th>
				<th>gender</th>
				<th>dept</th>
				<th>birthday</th>
				<th>edit</th>
				<th>delete</th>
			</tr>
			<c:forEach items="${requestScope.emps }" var="emp" >
				<tr>
					<td>${emp.id }</td>
					<td>${emp.name }</td>
					<td>${emp.email }</td>
					<td>${emp.gender==0 ? "Female" : "Male" }</td>
					<td>${emp.dept.name }</td>
					<td><fmt:formatDate value="${emp.birthday}" pattern="yyyy-MM-dd"/></td>
					<td><a href="${ctx}/springmvc03/emp/${emp.id}">edit</a></td>
					<td><a href="${ctx}/springmvc03/emp/${emp.id}" class="delete" >delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if><br>
	
	<!-- 增加员工 -->
	<a href="${ctx}/springmvc03/emp">增加新的员工</a>
	
	<!-- 删除员工 -->
	<form class="delete" action="#" method="post">
		<input type="hidden" name="_method" value="DELETE">
	</form>
	
	
	<!-- js -->
	<script type="text/javascript" src="${ctx}/static/js/jquery/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
		$(function(){
			//将post请求转化为delete请求
			$(".delete").click(function(){
				$("form.delete").attr("action", $(this).attr("href")).submit();	
				return false;
			});
		});
	</script>
</body>
</html>