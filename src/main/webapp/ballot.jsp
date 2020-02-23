<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.appengine.java8.HelloAppEngine" %>
<%@ page import="com.google.appengine.api.datastore.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Voting Ballot</title>
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

<h1>Voting Ballot</h1>

<form action="/ballot"  method="post">
  
  <p><b>Please Vote for ONE of the following Candidates</b></p>
 
  <% PreparedQuery pq  = (PreparedQuery)request.getAttribute("candidates");
     String token = (String) request.getAttribute("token");
			
		  int candidateNumber = 0;
		  
		  for (Entity result : pq.asIterable()) { %>
			
			   <br><input type="radio" name="vote" value="<%= candidateNumber%>"> <%= result.getProperty("firstname")%><br>
            
              <% candidateNumber ++;
              
			
		      } %>
		     
 
  <input type="hidden" name="token" value="<%=token%>">
 
  <input type="submit" value="Submit">

</form>
</div>
</body>
</html>
