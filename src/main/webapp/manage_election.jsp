<!DOCTYPE html>
<%@ page import="com.google.appengine.api.datastore.Entity"%>

<%@ page import="com.google.appengine.api.users.*" %>
<% UserService userService = UserServiceFactory.getUserService(); %>
<%@ page import="com.google.appengine.api.blobstore.*" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.appengine.java8.ManageElection" %>
<%@ page import="java.util.*"%>

<html lang="en">
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
	<style>
.my-custom-scrollbar {
position: relative;
height: 200px;
overflow: auto;
}
.table-wrapper-scroll-y {
display: block;
}
</style>
</head>
<body>
<div class = "container">
    <h1>Elections Management</h1>
    	<% if (userService.isUserAdmin() == false) { %>
			<script>
			window.location.href = '/index.jsp';
			</script>
		<% } %>
		
<div>
<form role="form" action="/admin/manage" method="post">
			<div class="form-group">
			<table class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Date</th>
      <th scope='col'> Existing date </th>
    </tr>
  </thead>
   <tbody>
    <tr>
      <th scope="row">Start</th>
      <td >					
      <input name="start_date" type="text" placeholder="dd/MM/yyyy HH:mm"
                        class="form-control" required>
      </td>
      <td>
      <%=request.getAttribute("startElection")%>
      </td>
    
    </tr>
    <tr>
      <th scope="row" >End</th>
      <td>					
      <input name="end_date" type="text" placeholder="dd/MM/yyyy HH:mm"
                        class="form-control" required>
      </td>
        <td>
      <%=request.getAttribute("endElection")%>
      </td>
    </tr>
  
</tbody>
</table>
			</div>
			<a href="/admin/manage" class="btn btn-default">Cancel</a>
			<button type="submit" class="btn btn-success">Save</button>
			
			


		</form>

 
</div>
<h2 >Manage the list of students allowed to vote : </h2>
		<br>
	 <h3>Upload the email list :</h3>
      Select a file to upload: Only .csv file are accepted 
      <br>
       <form action="<%= blobstoreService.createUploadUrl("/admin/students") %>" 
       method="post" enctype="multipart/form-data" lang="en">
         
    <input type="file" class="btn btn-default" name="file" accept=".csv" />
    <br>
    <input type="submit" class="btn btn-default" value="Upload"/>
    <br>

    <br>
      <h3> List of emails : </h3> 

<div class="table-wrapper-scroll-y my-custom-scrollbar">

  <table class="table table-bordered table-striped mb-0">    <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Email</th>
      </tr>
    </thead>
    <tbody>
     	<%
			try {
				List<String> emails = (List<String>) request.getAttribute("emails");
				if (emails != null ){

				for (String s : emails) {
		%>
    <tr>
      <th scope="row"></th>
      <td><%=s%></td>
    </tr>
    			<%
				}}
			} catch (Error e) {
			}
		%>
    </tbody>
  </table>
  </div>

            </form>	
         
      <br>
      <br>
<div>
<h2 >Manage the candidates : </h2>
			<div class="table-wrapper-scroll-y my-custom-scrollbar">

  <table class="table table-bordered table-striped mb-0">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">First name</th>
      <th scope="col">Surname</th>
      <th scope="col">Faculty</th>
    </tr>
  </thead>
   <tbody>
   	<%
			try {
				List<Entity> candidates = (List<Entity>) request.getAttribute("candidates");
				if (candidates != null ){

				for (Entity e : candidates) {
		%>
    <tr>
      <th scope="row"></th>
      <td><%=e.getProperty("firstname")%></td>
      <td><%=e.getProperty("surname")%></td>
      <td><%=e.getProperty("faculty")%></td>
    </tr>
    			<%
				}}
			} catch (Error e) {
			}
		%>
</tbody>
</table>
</div>


 <a href='/candidates' class="btn btn-default">Add Candidate</a>
  </div>          
      <br>
      <br>
  <div><a href="<%= userService.createLogoutURL("/") %>">Log out</a>
  </div>
    <br>
  
   <form action="/mailapi" method="post">
    <input type="submit" name="sendemail" value="Send Email!" />
    </form>
<script src="http://code.jquery.com/jquery.js"></script>
	</div>
</body>
</html>
