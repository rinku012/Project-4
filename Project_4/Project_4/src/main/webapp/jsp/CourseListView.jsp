<%@page import="in.com.rays.proj4.util.DataUtility"%>
<%@page import="in.com.rays.proj4.util.HTMLUtility"%>
<%@page import="in.com.rays.proj4.controller.CourseListCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.com.rays.proj4.bean.CourseBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.com.rays.proj4.controller.CourseCtl"%>
<%@page import="in.com.rays.proj4.util.ServletUtility"%>
<%@page import="in.com.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Course List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js""></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

<script type="text/javascript">
	function selectAll(source) {
		var checkboxes = document.getElementsByName('ids');
		for (var i = 0, n = checkboxes.length; i < n; i++) {
			checkboxes[i].checked = source.checked;
		}
	}
</script>
</head>
<body>
	<%@include file="Header.jsp"%>
	<center>
		<!-- <h1>Course List</h1> -->
		<form action="<%=ORSView.COURSE_LIST_CTL%>" method="POST">
			<jsp:useBean id="bean" class="in.com.rays.proj4.bean.CourseBean"
				scope="request"></jsp:useBean>

			<center>
				<h1 style="font-size: 40px;">Course List</h1>
			</center>
			<center>
				<font color="red" size="5px"><%=ServletUtility.getErrorMessage(request)%></font>
			</center>
			<center>
				<font color="green" size="5px"><%=ServletUtility.getSuccessMessage(request)%></font>
			</center>

			<%
				List list1 = (List) request.getAttribute("CourseList");
			%>
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextList").toString());
				List list = ServletUtility.getList(request);
				Iterator<CourseBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table>
				<tr>
					<td><label class="">Course Name </label>&emsp;<%=HTMLUtility.getList("id", String.valueOf(bean.getId()), list1)%>&emsp;&emsp;&emsp;
						<label class="">Duration</label>&emsp;
						<%
							HashMap map = new HashMap();
								map.put("1 Year", "1 Year");
								map.put("2 Year", "2 Year");
								map.put("3 Year", "3 Year");
								map.put("4 Year", "4 Year");
								map.put("5 Year", "5 Year");

								String HtmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
						%><%=HtmlList%>&emsp; <input type="submit" name="operation"
						value="<%=CourseListCtl.OP_SEARCH%>" style="padding: 5px;">&emsp;
						<input type="submit" name="operation"
						value="<%=CourseListCtl.OP_RESET%>" style="padding: 5px;"></td>
				</tr>
			</table>

			<table border="1" width="100%">
				<tr>
					<tr style="background: skyblue">
					<th><input type="checkbox" onClick="selectAll(this)">Select
						All</th>
					<th>S.No</th>
					<th>Course Name</th>
					<th>Duration</th>
					<th>Description</th>
					<th>Edit</th>
				</tr>
				<%
					while (it.hasNext()) {
							CourseBean cbean = it.next();
				%>
				<tr>

					<td align="center"><input type="checkbox" class="checkbox"
						name="ids" value="<%=cbean.getId()%>"></td>
					<td align="center"><%=index++%></td>
					<td align="center"><%=cbean.getCourseName()%></td>
					<td align="center"><%=cbean.getDuration()%></td>
					<td align="center"><%=cbean.getDescription()%></td>
					<td align="center"><a href="CourseCtl?id=<%=cbean.getId()%>">Edit</a></td>
				</tr>

				<%
					}
				%>
			</table>


			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CourseListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CourseListCtl.OP_NEW%>"></td>
					<td><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CourseListCtl.OP_DELETE%>"></td>
					<td align="right"><input type="submit" name="operation"
						style="padding: 5px;" value="<%=CourseListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>

			</table>

			<%
				}
				if (list.size() == 0) {
			%>

			<input type="submit" name="operation" style="padding: 5px;"
				value="<%=CourseListCtl.OP_BACK%>">
			<%
				}
			%>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>

	</center>
	<br>
	<br>
	<br>
	<br>
	<%@include file="Footer.jsp"%>

</body>
</html>