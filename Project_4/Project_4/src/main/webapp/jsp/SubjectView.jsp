<%@page import="in.com.rays.proj4.bean.CourseBean"%>
<%@page import="java.util.List"%>
<%@page import="in.com.rays.proj4.util.HTMLUtility"%>
<%@page import="in.com.rays.proj4.controller.SubjectCtl"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="10*10"/>
<title> Subject Registration Page</title>


</head>
<body>
<jsp:useBean id="bean" class="in.com.rays.proj4.bean.SubjectBean" scope="request"></jsp:useBean><!-- create a bean class object -->
<form action="<%=ORSView.SUBJECT_CTL%>" method="post">

<%@include file ="Header.jsp"%>
	

	<%
		List<CourseBean> cl =(List<CourseBean>) request.getAttribute("CourseList");
	%>
<center>
	<h1>
		<% if(bean != null && bean.getId() > 0 ) {%>
			<tr><th>Update Subject</th></tr>
		<%}else{ %>
			<tr><th>Add Subject</th></tr>
		<% }%>
	</h1>
<div>	
	<h3>
	<font style="color: green"><%=ServletUtility.getSuccessMessage(request) %>
	</font>
	</h3>
	
	<h3>
	<font style="color: red"><%=ServletUtility.getErrorMessage(request)%>
	</font>
	</h3>
</div>
	
	<input type="hidden" name="id" value="<%=bean.getId()%>">
 	<input type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
 	<input type="hidden" name="modifiedby" value="<%=bean.getModifiedBy()%>">
 	<input type="hidden" name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
 	<input type="hidden" name="modifieddatetime" value="<%=bean.getModifiedDatetime()%>">
 	
	<table>
	
	<tr>
	<th align="left">Course Name <span style="color: red"> * </span> :  </th>
	<td><%=HTMLUtility.getList("courseName", String.valueOf(bean.getCourseId()), cl) %>
	</td><td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("courseName",request) %>
	</font></td></tr>
	
	 <tr><th style="padding: 3px"></th></tr>    
	
	<th align="left">Subject Name <span style="color: red"> * </span> :  </th>
	<td><input type="text" name="name" placeholder="Enter Subject Name" size="20" value="<%=DataUtility.getStringData(bean.getSubjectName())%>">
	</td><td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("name",request) %>
	</font>	</td></th></tr>
	
	 <tr><th style="padding: 3px"></th></tr> 
	    
	<th align="left">Description <span style="color: red"> * </span> :  </th>
	<td><input type="text" name="description" placeholder="Enter Description" size="20" value="<%=DataUtility.getStringData(bean.getDescription())%>">
	</td><td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("description",request) %>
	</font></td></th></tr>
	
	 <tr><th style="padding: 3px"></th></tr>    
	 
	<tr><th></th>
	<%
		if(bean.getId() > 0){	%>
	<td>
	 &nbsp;  &emsp;
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_UPDATE %>">
	 &nbsp;  &nbsp;
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_CANCEL %>">	
	</td>
	<%}else{ %>
	
	<td>
	 &nbsp;  &emsp;
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_SAVE %>">
	 &nbsp;  &nbsp;
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_RESET %>">	
	</td>
	<%} %>
	
	</tr>
	</table>


</form>
</br>
</br>
</center>

<%@include file ="Footer.jsp"%>
</body>
</html>