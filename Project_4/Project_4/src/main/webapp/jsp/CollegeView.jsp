<%@page import="in.com.rays.proj4.controller.CollegeCtl"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>
<%@page import="in.com.rays.proj4.controller.ORSView" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title> College Registration Page</title>

<meta charset="ISO-8859-1">

</head>
<body>
 <jsp:useBean id="bean" class="in.com.rays.proj4.bean.CollegeBean" scope="request"></jsp:useBean>
 <form action="CollegeCtl" method="post">
  <%@ include file="Header.jsp"%>
    
        <center>
           <h1>
            	<%
            		if(bean != null && bean.getId()>0){
            	%>
            		<tr><th><font>Update College</font></th></tr>
            	
            	<% }else{%>
            		
            		<tr><th><font>Add College</font></th></tr>
            	<% }%>            
            </h1>
 <div>
            <H3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></H3>
            <H3><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font></H3>     
     </div>
            <input type="hidden" name="id" value="<%=bean.getId()%>"> 
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
<table>
                <tr>
                    <th align="left">Name <span style="color: red">*</span> :</th>
                    <td><input type="text" name="name" placeholder="Enter College Name" size="25px" value="<%=DataUtility.getStringData(bean.getName())%>">
                    </td><td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
                </tr>
       <tr><th style="padding: 3px"></th></tr>        
                <tr>
                    <th align="left">Address <span style="color: red">*</span> :</th>
                    <td><input type="text" name="address" placeholder="Enter College Address" size="25px" value="<%=DataUtility.getStringData(bean.getAddress())%>">
                    </td><td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
                </tr>
      <tr><th style="padding: 3px"></th></tr>          
                <tr>
                    <th align="left">State <span style="color: red">*</span> :</th>
                    <td><input type="text" name="state"  placeholder="Enter State" size="25px" value="<%=DataUtility.getStringData(bean.getState())%>">
                    </td><td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
                </tr>
      <tr><th style="padding: 3px"></th></tr>
                <tr>
                    <th align="left">City <span style="color: red">*</span> :</th>
                    <td><input type="text" name="city" placeholder="Enter City" size="25px" value="<%=DataUtility.getStringData(bean.getCity())%>">
                    </td><td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
                </tr>
     <tr><th style="padding: 3px"></th></tr>            
                <tr>
                    <th align="left">MobileNo <span style="color: red">*</span> :</th>
                    <td><input type="text" name="phoneNo" placeholder="Enter Mobile No" maxlength="10"  size="25px" value="<%=DataUtility.getStringData(bean.getPhonoNo())%>">
                   </td><td style="position: fixed"> <font color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
                </tr>
     <tr><th style="padding: 3px"></th></tr>            
                <tr><th></th>
                <%if(bean.getId()>0) {%>
                    <td colspan="2">
                     &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=CollegeCtl.OP_UPDATE%>">                    
                      &nbsp;  &nbsp;
                     <input type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL%>"></td>
                <%}else{ %>
                	 <td colspan="2">
                	  &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE%>">                    
                      &nbsp;  &nbsp;
                     <input type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>"></td>
                <%} %>
                </tr>
            </table>
    </form>
           
    </center>
    <br>
    <br>
    <br>
    <br>
    <br>
 
    <%@ include file="Footer.jsp"%>


</body>
</html>