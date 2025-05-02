<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <div class="container" style="margin-top:100px; width:500px;">
    <h2>Registration Form</h2>
	 <!-- Single Form for all inputs -->
    <form method="POST" action="./AddTeacher">
    
     <!--  TEACHING STAFF ID -->
      <div class="mb-3">
        <label class="form-label">Teaching Staff id</label>
        <input type="text" name="tsi" class="form-control" required>
        <div class="form-text"><!-- This is just to provide info about textfield --></div>
      </div>
      
      <!-- Name -->
      <div class="mb-3">
        <label class="form-label">Name</label>
        <input type="text" name="name" class="form-control" required>
      </div>
      
       <!-- Designation -->
      <div class="mb-3">
        <label class="form-label">designation</label>
        <input type="text" name="per" class="form-control" required>
        
         <!-- Submit Button -->
      <div class="d-grid gap-2">
      	<input type="submit" class="btn btn-primary" value="Save">
      </div>
      </div>
</form>
<% 
    String msg = (String) request.getAttribute("msg"); 
    if (msg != null) { 
%>
	<div class="alert alert-danger mt-3 text-center container" role="alert">
    <p style="color:red;"><%= msg %></p>
    </div>
<% 
    } 
%>
</body>
</html>