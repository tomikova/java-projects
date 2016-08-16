<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>${entry.title}</title>
	</head>
	<body>
		<h4>
		${entry.title}
		</h4>
		<textarea name="text" id="text" cols="80" rows="15">${entry.text}</textarea>
		<br>
        <c:if test="${nick == currentUserNick}">
  			<a href="/aplikacija5/servleti/author/${nick}/edit?eid=${entry.id}"target="_blank">Edit blog entry</a>
  			<br>
		</c:if>
		<h4>
		Comments:
		</h4>
		<c:forEach var="e" items="${comments}">
        	[${e.email}][${e.postedOn}]
        	<li>${e.message}</li>   
        </c:forEach>
        <br>
        <form action="/aplikacija5/addComment" method="post">
		Add comment:
		<br>
		<textarea name="text" id="text" cols="40" rows="7"></textarea>
		<input name="eid" type="hidden" value="${entry.id}" />
		<input name="nick" type="hidden" value="${nick}" />
		<br>
   	 	<input type="submit" name="metoda" value="OK">
   	 	</form>
	</body>
</html>