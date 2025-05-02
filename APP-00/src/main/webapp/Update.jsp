<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.tca.entities.Student" %>
<%@ include file="Header.jsp" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Student Update</title>
</head>
<body>

<div class="container" style="margin-top:100px;"> 
<h2 class="text-center mb-4 text-primary"> Student Information </h2>

<div class="container-fluid mb-3 d-flex justify-content-end">
    <form class="d-flex" role="search" method="GET" action="./UpdateStudent">
        <input class="form-control me-2" type="search" name="rno" value="<%= request.getParameter("rno") == null ? "" : request.getParameter("rno") %>" placeholder="Enter roll num" aria-label="Search">
        <button class="btn btn-outline-success" type="submit" name="sbtn" value="Search">Search</button>
        <button class="btn btn-outline-success ms-2" type="submit" name="sbtn" value="Refresh">Refresh</button>
    </form>
</div>

<table class="table table-hover table-bordered text-center">
<thead>
    <tr class="table-primary">
        <th scope="col">RNO</th>
        <th scope="col">NAME</th>
        <th scope="col">PER</th>
        <th scope="col">ACTION</th>
    </tr>
</thead>
<tbody>
<%
    List<Student> studentList = (List<Student>) request.getAttribute("listofstudents");

    if (studentList == null || studentList.isEmpty()) {
%>
    <tr>
        <td colspan="4" class="text-danger">No Data Found!</td>
    </tr>
<%
    } else {
        for (Student S : studentList) {
            int rno = S.getRno();
            String name = S.getName();
            double per = S.getPer();
%>
    <tr id="<%= rno %>">
        <td> <%= rno %> </td>
        <td> <input type="text" value="<%= name %>" class="form-control" /> </td>
        <td> <input type="text" value="<%= per %>" class="form-control" /></td>
        <td> <button class="btn btn-primary" onclick="modify('<%= rno %>')">Update</button></td>
    </tr>
<%
        }
    }
%>
</tbody>
</table>

<script>
function modify(trno) {
    var tr = document.getElementById(trno);
    var inputs = tr.getElementsByTagName("input");

    var tname = inputs[0].value;
    var tper = inputs[1].value;

    fetch('./UpdateStudent', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({ 'trno': trno, 'tname': tname, 'tper': tper })
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
