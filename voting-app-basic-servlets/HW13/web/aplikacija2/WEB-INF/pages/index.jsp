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
<h4>Welcome</h4>
<p><a href="/aplikacija2/colorpick">Background color chooser</a></p>
<p><a href="/aplikacija2/trigonometric?a=0&b=90">Trigonometry</a></p>
<p><a href="/aplikacija2/stories/funny">Funny story</a></p>
<p><a href="/aplikacija2/reportImage">Os usage</a></p>
<p><a href="/aplikacija2/powers?a=1&b=100&n=3">Generate XLS</a></p>
<p><a href="/aplikacija2/appinfo">Working time</a></p>
<p><a href="/aplikacija2/glasanje">Glasanje za omiljeni bend</a></p>
</body>
</html>