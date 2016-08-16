<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>${nick}'s blog entries</title>
	</head>
	<body>
		<h4>
		Blog entries
		</h4>
		<c:forEach var="e" items="${blogEntries}">
        <li><a href="/aplikacija5/servleti/author/${nick}/${e.id}"target="_blank">${e.title}</a></li>    
        </c:forEach>
        <br>
         <c:if test="${nick == currentUserNick}">
  		 <a href="/aplikacija5/servleti/author/${nick}/new"target="_blank">Add new blog entry</a>
		</c:if>
	</body>
</html>