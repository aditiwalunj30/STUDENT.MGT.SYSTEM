<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.tca.entities.Teacher" %>
<%@ include file="Header.jsp" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Teacher Update</title>
</head>
<body>

<div class="container" style="margin-top:100px;"> 
<h2 class="text-center mb-4 text-primary"> Teacher Information </h2>

<!-- Search Logic -->
<div class="container-fluid mb-3 d-flex justify-content-end">
    <form class="d-flex" role="search" method="GET" action="./UpdateTeacher">
        <input class="form-control me-2" type="search" name="tsi" value="<%= request.getParameter("tsi") == null ? "" : request.getParameter("tsi") %>" placeholder="Enter Teaching Staff ID" aria-label="Search">
        <button class="btn btn-outline-success" type="submit" name="sbtn" value="Search">Search</button>
        <button class="btn btn-outline-success ms-2" type="submit" name="sbtn" value="Refresh">Refresh</button>
    </form>
</div>

<!-- Table Logic -->
<table class="table table-hover table-bordered text-center">
<thead>
    <tr class="table-primary">
        <th scope="col">TSI</th>
        <th scope="col">NAME</th>
        <th scope="col">DESIGNATION</th>
        <th scope="col">ACTION</th>
    </tr>
</thead>
<tbody>
<%
    List<Teacher> L = (List<Teacher>) request.getAttribute("listofteachers");

    if (L == null || L.isEmpty()) {
%>
    <tr>
        <td colspan="4" class="text-danger">No Data Found!</td>
    </tr>
<%
    } else {
    	for (Teacher T : L) {
            int tsi = T.gettsi(); // ✅ Corrected method name
            String name = T.getName();
            String designation = T.getdesignation(); // ✅ Corrected method name

%>
    <tr id="<%= tsi %>">
        <td> <%= tsi %> </td>
        <td> <input type="text" value="<%= name %>" class="form-control" /> </td>
        <td> <input type="text" value="<%= designation %>" class="form-control" /> </td>
        <td> <button class="btn btn-primary" onclick="modify('<%= tsi %>')">Update</button></td>
    </tr>
<%
        }
    }
%>
</tbody>
</table>

<script>
function modify(ttsi) {
    var tr = document.getElementById(ttsi);
    var inputs = tr.getElementsByTagName("input");

    var tname = inputs[0].value;
    var tdesignation = inputs[1].value;

    fetch('./UpdateTeacher', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({ 'tsi': ttsi, 'tname': tname, 'tdesignation': tdesignation })
    })
    .then(response => response.text())
    .then(data => {
        if (data.trim() === "success") {
            alert("Record updated successfully!");
            location.reload();
        } else {
            alert("Failed to update record.");
        }
    })
    .catch(error => console.error("Error while updating:", error));
}
</script>

</div>
</body>
</html>
