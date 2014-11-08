<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
	<head>
		<%@include file="../includes/header.jsp" %>
		<title>AppEmployee - Create Project</title>
	</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2 class="sub-header">New time entry</h2>
				<h3>
			Pay period from
			<c:out value="${timeForm.payPeriodStartDate}" />
			to
			<c:out value="${timeForm.payPeriodEndDate}" />
		</h3>
		<form:form method="post" action="/time/previousTime/add" modelAttribute="timeForm">
			<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Task</th>
						<th>Hours</th>
						<th>Comment</th>
					</tr>
					<tr>
						<td><form:label path="dateTimeEntry"></form:label> <form:input
								class="form-control" type="date"
								min="${timeForm.payPeriodStartDate}"
								max="${timeForm.payPeriodEndDate}" path="dateTimeEntry"
								value="${dateTimeEntry}" required="required" /></td>
						<td><form:select class="form-control" path="taskIdTimeEntry">
								<form:option value="NONE"> --SELECT--</form:option>
								<form:options items="${timeForm.availableTasks}" itemValue="uId"
									itemLabel="name"></form:options>
							</form:select></td>
						<td><form:label path="hoursTimeEntry"></form:label> <form:input
								class="form-control" type="number" min="1" max="24"
								path="hoursTimeEntry" value="${hoursTimeEntry}"
								required="required" /></td>
						<td><form:label path="commentTimeEntry"></form:label> <form:input
								class="form-control" path="commentTimeEntry"
								value="${commentTimeEntry}" /></td>
					</tr>
				</table>
			</div>
			<br />
			<input type="submit" class="btn btn-primary" name="submit"
				value="Save" />
		</form:form>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>