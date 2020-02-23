<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.appengine.java8.CandidateServlet" %>
<%@ page import="com.google.appengine.api.users.*" %>
<% UserService userService = UserServiceFactory.getUserService(); %>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Candidate</title>
<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
		<h1>Add Candidate</h1>
		<% if (userService.isUserAdmin() == false) { %>
			<script>
			window.location.href = '/index.jsp';
			</script>
		<%} %>
		

		<p>&nbsp;</p>

		<form role="form" action="/candidates" method="post">
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">First Name</span> 
					<input name="firstname" type="text" class="form-control">
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Last Name</span> 
					<input name="surname" type="text" class="form-control">
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Faculty</span> 
					<input name="faculty" type="text" class="form-control">
				</div>
				<br>
				
				<br>

			</div>
			<a href="/candidates" class="btn btn-default">Cancel</a>
			<button type="submit" class="btn btn-success">Save</button>
			
						<a href="/admin/manage" class="btn btn-default">Back to manage election</a>
			


		</form>
		</div>

</body>
</html>
