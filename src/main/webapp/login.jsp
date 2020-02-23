<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.google.appengine.api.users.*" %>

<% UserService userService = UserServiceFactory.getUserService(); %>

<!DOCTYPE html>

<html>
	<head>
		<title>Application App Engine</title>
		<meta charset="utf-8" />
	</head>

	<body>
	<div class = "container">
		<h1>Welcome in the voting application</h1>
		
		<% if (userService.getCurrentUser() == null) { %>
			<p><a href="<%= userService.createLoginURL("/index.jsp") %>">Log in</a></p>
		<% }
		else { %>
			<p>Hello <%= userService.getCurrentUser().getNickname() %></p>
			
			  <% if (userService.isUserAdmin() ) { %>
			      <a href='/results'>Results of the election</a>

			      <br>
			      <br>
			     <a href='/admin/manage'>Manage Election</a>
			
		<% }
		else { %>
					      <a href='/results'>Results of the election</a>
		
		<% } %>
			<p><a href="<%= userService.createLogoutURL("/") %>">Log out</a></p>
		<% } %>
		</div>
	</body>
</html>