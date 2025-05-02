<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.tca.entities.Teacher" %>
<%@ include file="Header.jsp" %>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Teacher Information</title>
  </head>

<body>
<%
  String ttsi = request.getParameter("tsi");
  String sbtn = request.getParameter("sbtn");

  if (ttsi == null)
    ttsi = "";

  if (sbtn != null && sbtn.equals("Refresh"))
    ttsi = "";
%>

<div class="container" style="margin-top:100px;"> 
  <h2 class="text-center mb-4 text-primary">Teacher Information</h2> 

  <!-- Search Form -->
  <div class="container-fluid mb-3 d-flex justify-content-end">
    <form class="d-flex" role="search" method="POST" action="./DisplayStudent">
      <input class="form-control me-2" type="search" name="tsi" value="<%= ttsi %>" placeholder="Enter teaching staff id" aria-label="Search">
      <button class="btn btn-outline-success" type="submit" name="sbtn" value="Search">Search</button>
      <button class="btn btn-outline-success ms-2" type="submit" name="sbtn" value="Refresh">Refresh</button>
    </form>
  </div>

  <!-- Data Table -->
  <table class="table table-hover table-bordered text-center">
    <thead> 
      <tr class="table-primary">
        <th scope="col">TSI</th>
        <th scope="col">NAME</th>
        <th scope="col">DESIGNATION</th>
      </tr>
    </thead>

    <tbody> 
    <%
      List<Teacher> L = (List<Teacher>) request.getAttribute("listofteachers");

      if (L == null || L.isEmpty()) {
    %>
        <tr>
          <td colspan="3" class="text-danger">No Data Found !!</td>
        </tr>
    <%
      } else {
        for (Teacher T : L) {
          int tsi = T.gettsi(); // ✅ Corrected method name
          String name = T.getName();
          String designation = T.getdesignation(); // ✅ Corrected method name

          String cls = "";
          if ("MA".equalsIgnoreCase(designation) || "MSC".equalsIgnoreCase(designation)) {
            cls = "table-danger"; // ✅ removed extra curly brace
          }
    %>
        <tr class="<%= cls %>">
          <td><%= tsi %></td>
          <td><%= name %></td>
          <td><%= designation %></td>
        </tr>
    <%
        } // for
      } // else
    %>
    </tbody>
  </table>
</div>
</body>
</html>
