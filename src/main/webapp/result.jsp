<!DOCTYPE html>
<%@ page import="com.google.appengine.api.users.*" %>
<% UserService userService = UserServiceFactory.getUserService(); %>
<%@ page import="java.util.*"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.*" %>



<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.appengine.java8.ManageElection" %>
<html>
<head>
  <link href='//fonts.googleapis.com/css?family=Marmelad' rel='stylesheet' type='text/css'>
  <title>Voting Application</title>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
			<div class="form-group">
			<table class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">First name</th>
      <th scope="col">Surname</th>
      <th scope="col">Faculty</th>
      <th scope="col"> Choice</th>
    </tr>
  </thead>
   <tbody>
   	<%
			try {
				PreparedQuery pq  = (PreparedQuery)request.getAttribute("candidates");

				for (Entity e : pq.asIterable()) {
		%>
    <tr>
      <th scope="row"></th>
      <td><%=e.getProperty("firstname")%></td>
      <td><%=e.getProperty("surname")%></td>
      <td><%=e.getProperty("faculty")%></td>
      <td><%=e.getProperty("votes")%></td>
     
    </tr>
    			<%
			}
			} catch (Error e) {
			}
		%>
</tbody>
</table>

</div>

<div class = "container">
<b> The winner Candidate is </b>
 <%  
	      PreparedQuery pq  = (PreparedQuery)request.getAttribute("candidates");
          int winningNum = (int) request.getAttribute("winningCandidate");
          int counter = 0;
          for(Entity e   : pq.asIterable())
		  {
			  if(counter == winningNum )
			  {
				   %> <b><i> <%= e.getProperty("firstname") %></i> </b> <% 
				    break;
			  }
			  
			  else 
				  counter++;
		  } %>
		  
			</div>
		
<div class = "container">
<br><b> The Voting Percentage is: </b>

 <b><%= request.getAttribute("percentage") %></b> 
	
		  
</div>			



 
</div>
		<br>
		  <br>
      <br>
  <div><a href="<%=userService.createLogoutURL("/") %>">Log out</a>
  </div>
    <br>
		

   
<script src="http://code.jquery.com/jquery.js"></script>
</body>
</html>
