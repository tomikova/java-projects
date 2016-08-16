<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    String color = (String)session.getAttribute("color");
    if(color==null) {
    	color = "#FFFFFF";
    }
%>

<html>
<body bgcolor="<%=color%>">
<h1>Results:</h1>
<table border="1">
<thead> 
<tr><th>Number</th><th>Sine</th><th>Cosine</th></tr>
</thead>
<tbody>
<c:forEach var="entry" items="${results}">
<tr><td>${entry.number}</td><th>${entry.sine}</th><th>${entry.cosine}</th></tr>
</c:forEach>
</tbody>
</table>
</body>
</html>