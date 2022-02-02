<%@page import="in.com.rays.proj4.bean.SubjectBean"%>
<%@page import="in.com.rays.proj4.bean.CollegeBean"%>
<%@page import="in.com.rays.proj4.bean.CourseBean"%>
<%@page import="java.util.List"%>
<%@page import="in.com.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.com.rays.proj4.controller.FacultyCtl"%>
<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>
<%@page import="in.com.rays.proj4.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Faculty Registration Page</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#date").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2020',
			dateFormat : 'dd-mm-yy'
		});
	});
</script>


</head>
<body>
	<jsp:useBean id="bean" class="in.com.rays.proj4.bean.FacultyBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.FACULTY_CTL%>" method="post">

		<%
			List<CollegeBean> colist = (List<CollegeBean>) request.getAttribute("CollegeList");
			List<CourseBean> clist = (List<CourseBean>) request.getAttribute("CourseList");
			List<SubjectBean> slist = (List<SubjectBean>) request.getAttribute("SubjectList");
		%>

		<center>
			<h1>
				<%
					if (bean != null && bean.getId() > 0) {
				%>

				<tr>
					<th>Update Faculty</th>
				</tr>
				<%
					} else {
				%>

				<tr>
					<th>Add Faculty</th>
				</tr>
				<%
					}
				%>
			</h1>

			<div>
				<h3>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
				</h3>
			</div>

			<input type="hidden" name="id" value=<%=bean.getId()%>> <input
				type="hidden" name="createdby" value=<%=bean.getCreatedBy()%>>
			<input type="hidden" name="modifiedby"
				value=<%=bean.getModifiedBy()%>> <input type="hidden"
				name="createdDatetime"
				value=<%=DataUtility.getStringData(bean.getCreatedDatetime())%>>
			<input type="hidden" name="modifiedDatetime"
				value=<%=DataUtility.getStringData(bean.getModifiedDatetime())%>>

			<table>

				<tr>
					<th align="left">First Name <span style="color: red">*</span>
						:
					</th>
					</th>
					<td><input type="text" name="firstName"
						placeholder=" Enter First Name" size="22"
						value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">Last Name <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="lastName"
						placeholder=" Enter last Name" size="22"
						value="<%=DataUtility.getStringData(bean.getLastName())%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">Gender <span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");

							String hlist = HTMLUtility.getList("Gender", String.valueOf(bean.getGender()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("Gender", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">CollegeName <span style="color: red">*</span>
						:
					</th>
					<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), colist)%>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">CourseName <span style="color: red">*</span>
						:
					</th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), clist)%>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font>
					</td>
				</tr>



				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">SubjectName <span style="color: red">*</span>
						:
					</th>
					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), slist)%>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("subjectId ", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">Date Of Birth <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="dob"
						placeholder="Enter Date Of Birth" size="22" readonly="readonly"
						id="date" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>


				<tr>
					<th align="left">EmailId <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="emailId"
						placeholder=" Enter Login Id" size="22"
						value="<%=DataUtility.getStringData(bean.getEmailId())%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("emailId", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th align="left">MobileNo <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="mobileNo" size="22"
						maxlength="10" placeholder=" Enter Mobile No"
						value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
					</td>
				</tr>
				<tr>
					<th style="padding: 2px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>

					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=FacultyCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_SAVE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=FacultyCtl.OP_RESET%>"></td>
					<%
						}
					%>
				</tr>
					
			</table>
			
	
		</center>
			
	
	</form>
	
	<br>
<br>
<br>
<br>
<br>
<br>
<br>
	<%@include file="Footer.jsp"%>


</body>
</html>