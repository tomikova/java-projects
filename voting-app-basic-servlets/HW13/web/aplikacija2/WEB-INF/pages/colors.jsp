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
<h4>Pick a color:</h4>
<p><a href="/aplikacija2/setcolor/white">WHITE</a></p>
<p><a href="/aplikacija2/setcolor/red">RED</a></p>
<p><a href="/aplikacija2/setcolor/green">GREEN</a></p>
<p><a href="/aplikacija2/setcolor/cyan">CYAN</a></p>
</body>
</html>