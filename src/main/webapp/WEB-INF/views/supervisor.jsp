<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="en">
<head>
<%@include file="../includes/header.jsp"%>
<title>AppEmployee - Employee Board</title>
</head>
<body>
	<%@include file="../includes/navbar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header">Employee Board</h1>

		<div class="row placeholders">
			<div class="col-xs-6 col-sm-3 placeholder">
				<a href="projects"><img
					src="../../resources/img/Operations/manageProjects.png"
					class="img-responsive" alt="Mountain View"
					style="width: 200px; height: 200px"></a>
			</div>
			<div class="col-xs-6 col-sm-3 placeholder">
				<a href="time"><img
					src="../../resources/img/Operations/manageTime.png"
					class="img-responsive" alt="Mountain View"
					style="width: 200px; height: 200px"></a>
			</div>
			<div class="col-xs-6 col-sm-3 placeholder">
				<a href="expenses"><img
					src="../../resources/img/Operations/manageExpenses.png"
					class="img-responsive" alt="Mountain View"
					style="width: 200px; height: 200px"></a>
			</div>
			<div class="col-xs-6 col-sm-3 placeholder">
				<a href="travel"><img
					src="../../resources/img/Operations/manageTravel.png"
					class="img-responsive" alt="Mountain View"
					style="width: 200px; height: 200px"></a>
			</div>
		</div>

		<h2 class="sub-header">Welcome!</h2>
	</div>

	<%@include file="../includes/footer.jsp"%>

</body>
</html>

