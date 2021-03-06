<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Edit Project</title>
</head>

<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h2 class="sub-header">Edit a time entry</h2>
		<h3>
			Pay period from
			<c:out value="${previousPayPeriod.payPeriodStartDate}" />
			to
			<c:out value="${previousPayPeriod.payPeriodEndDate}" />
		</h3>
					<c:if
					test="${not empty message }">
					<div class="alert alert-danger" style="margin-top: 10px;"
						role="alert">${message.message}</div>
			</c:if>
		<div></div>
		<form:form role="form" method="POST"
			action="/time/previousTime/${timeForm.uid}/edit" modelAttribute="timeForm">
			<div class="form-group">
		        <form:hidden path="userEmail" />
				<form:hidden path="uid" />
			</div>
	
				<div class="table-responsive">
				<table class="table table-striped table-hover table-condensed">
					<tr>
						<th>Date</th>
						<th>Task</th>
						<th>Hours</th>
						<th>Comment</th>
					</tr>
					<tr>
						<td><form:label path="date"></form:label> <form:input
								class="form-control" type="date"
								min="${previousPayPeriod.payPeriodStartDate}"
								max="${previousPayPeriod.payPeriodEndDate}" path="date"
								value="${date}" required="required" /></td>
						<td><form:select class="form-control" path="taskId">
								<form:options items="${timeForm.availableTasks}" itemValue="uid"
									itemLabel="name"></form:options>
							</form:select></td>
						<td><form:label path="hours"></form:label> <form:input
								class="form-control" type="number" step="any" min="1" max="24"
								path="hours" value="${hours}"
								required="required" /></td>
						<td><form:label path="comment"></form:label> <form:input
								class="form-control" path="comment"
								value="${comment}" /></td>
					</tr>
				</table>
			</div>

			<div class="form-group">
				<input type="submit" value="Save" class="btn btn-primary"></input> 
				<input
					type="button"
					onclick="javascript:window.location.href = '/time/previousTime/'"
					value="Cancel" class="btn btn-default"></input>
			</div>
		</form:form>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>