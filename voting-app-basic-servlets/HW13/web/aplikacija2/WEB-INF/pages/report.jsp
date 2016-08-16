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
<h1>OS usage</h1>
<p>Here are the results of OS usage in survey that we completed:</p>
<img alt="Pie-chart" src="/aplikacija2/generateImage" width="600" height="600" />
</body>
</html>