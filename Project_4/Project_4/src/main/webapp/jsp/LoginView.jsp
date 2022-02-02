<%@page import="in.com.rays.proj4.controller.LoginCtl"%>
<%@page import="in.com.rays.proj4.controller.ORSView"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
   <form action="<%=ORSView.LOGIN_CTL %>" method="post">
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.com.rays.proj4.bean.UserBean"
            scope="request"></jsp:useBean>

      <% String URI=(String)request.getAttribute("uri");%>
              
              <input type="hidden" name="id" value="<%=bean.getId()%>">
              <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
              <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
              <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
              <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

  <center>
            <h1>Login</h1>

            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                 <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                 </font>
                </font>
            </H2>

    <%
      String msg=(String) request.getAttribute("message");
   if(msg!=null)
   {
	   %>
	   <h2 align="center"> <font color="red"><%=msg%></font> </h2>
  <%
   }
  %>
   
   
   <table>
                <tr>
                   <th align="left">LoginId<span style="color: red">*</span></th>
                    <td><input type="text" name="login" size=25
                        value="<%=DataUtility.getStringData(bean.getLogin())%>"> </td>
                     
                       <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>
                
                	<tr><th style="padding: 1px"></th></tr>
              	<tr><th></th></tr>
                
                <tr>
                    <th align="left">Password<span style="color: red">*</span></th>
                    <td><input type="password" name="password" size=25
                        value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
                        <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
                </tr>
                <tr><th style="padding: 1px"></th></tr>
              	<tr><th></th></tr>
                
                <tr>
                    <th></th>
                    <td colspan="2"><input type="submit" name="operation"
                        value="<%=LoginCtl.OP_SIGN_IN %>"> &nbsp; <input type="submit"
                        name="operation" value="<%=LoginCtl.OP_SIGN_UP %>" > &nbsp;</td>
                </tr>
                <tr><th></th>
                <td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget My Password</b></a>&nbsp;</td>
            </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>