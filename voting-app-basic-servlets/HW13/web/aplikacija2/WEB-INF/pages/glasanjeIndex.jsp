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
<h1>Glasanje za omiljeni bend:</h1>
<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!
</p>
<ol>
<c:forEach var="e" items="${bendovi}">
<li><a href="glasanje-glasaj?id=${e.key}">${e.value.name}</a></li>
</c:forEach>
</ol>
</body>
</html>