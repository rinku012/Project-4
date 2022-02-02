<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.com.rays.proj4.controller.ORSView"%>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>"/img/customLogo.png" sizes="16*16" />
<title>Welcome Page</title>
</head>

<body>
<center>

	<form action="<%=ORSView.WELCOME_CTL%>">
	
		<%@ include file="Header.jsp"%>
		
		<br>
<br>
<br>
<br>
<br>
		
		<h1 align="Center">
			<font size="100px" color="red">Welcome to ORS </font>
		</h1>

	</form>
	</center>
 
 
 
	<%@ include file="Footer.jsp"%>
</body>
</html>