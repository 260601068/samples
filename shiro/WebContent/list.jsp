<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
List Page<br><br>
Welcome: <shiro:principal></shiro:principal><br>
<shiro:hasRole name="user">Curent Role:user</shiro:hasRole>
<shiro:hasRole name="admin">Curent Role:admin</shiro:hasRole><br><br>
<a href="user.jsp">User Page</a><br><br>
<a href="admin.jsp">Admin Page</a><br><br>
<a href="shiro/testAuthWithMethod">testAuthAndSessionWithMethod</a><br><br>
<a href="shiro/logout">Logout</a>
</body>
</html>