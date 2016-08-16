<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Blog editing</title>
		
		<style type="text/css">
		.error {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		}
		</style>
	</head>
	<body>
	<h1>
	<c:choose>
	<c:when test="${entry.id == null}">
	New blog
	</c:when>
	<c:otherwise>
	Edit blog
	</c:otherwise>
	</c:choose>
    </h1>
	<form action="${action}" method="post">
	Title:
	<input name="title" type="text" id="title" size="50" value="${entry.title}" />
	<c:if test="${entry.containsError('title')}">
	<div class="error"><c:out value="${entry.getError('title')}"/></div>
	</c:if> 
	<br>
	Text:
	<br>
	<textarea name="text" id="text" cols="80" rows="15">${entry.text}</textarea>
	
	<input name="eid" type="hidden" value="${entry.id}" />
	<br>
    <input type="submit" name="metoda" value="OK">
    </form> 
	</body>
</html>