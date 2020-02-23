<%@ page import="com.example.appengine.java8.*" %>
<%@ page import="com.google.appengine.api.users.*" %>
<% UserService userService = UserServiceFactory.getUserService(); %>

<!DOCTYPE html>
<!-- [START_EXCLUDE] -->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.appengine.java8.HelloAppEngine" %>

<html>
<head>
  <link href='//fonts.googleapis.com/css?family=Marmelad' rel='stylesheet' type='text/css'>
  <title>Election App</title>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  
</head>
<body>

<div class="container">
    <h1>Hello <%= userService.getCurrentUser().getNickname() %></h1>
<br>
  <p>Welcome to the election application</p>
      <% if (userService.isUserAdmin() ) { %>
      <br>
      <br>
			      <a href='/results'>Results of the election</a>

			      <br>
			      <br>
			     <a href='/admin/manage'>Manage Election</a>
			
		<% }
		else { %>
					      <a href='/results'>Results of the election</a>
		
		<% } %>
      
    <br>
          			<p><a href="<%= userService.createLogoutURL("/") %>">Log out</a></p>
          			
                  
<script src="http://code.jquery.com/jquery.js"></script>
	
	
	</div>
</body>
</html>
