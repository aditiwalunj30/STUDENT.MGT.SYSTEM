<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registration Form</title>
    
  </head>
  <body>
  
  <div class="container" style="margin-top:100px; width:500px;">
    <h2>Registration Form</h2>

    <!-- Single Form for all inputs -->
    <form method="POST" action="./AddStudent">
      
      <!-- Roll Number -->
      <div class="mb-3">
        <label class="form-label">Roll Number</label>
        <input type="text" name="rno" class="form-control" required>
        <div class="form-text"><!-- This is just to provide info about textfield --></div>
      </div>

      <!-- Name -->
      <div class="mb-3">
        <label class="form-label">Name</label>
        <input type="text" name="name" class="form-control" required>
      </div>

      <!-- Percentage -->
      <div class="mb-3">
        <label class="form-label">Percentage</label>
        <input type="text" name="per" class="form-control" required>
      </div>

      <!-- Submit Button -->
      <div class="d-grid gap-2">
      	<input type="submit" class="btn btn-primary" value="Save">
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

   

  </div>
  
  </body>
</html>
