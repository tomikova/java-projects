<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Blogs</title>
		
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
	    <c:choose>
    	<c:when test="${empty currentUserId}">
    		<div><c:out value="Not loged in"/></div>
        	<br/>
        	<h4>
			User login
			</h4>
			<form action="main" method="post">		
			Username: <input type="text" name="nick" value='<c:out value="${entry.nick}"/>' size="30"><br>
			<c:if test="${entry.containsError('nick')}">
			<div class="error"><c:out value="${entry.getError('nick')}"/></div>
			</c:if>
			<br>
			Password: <input type="text" name="password" value='<c:out value=""/>' size="30"><br>
			<c:if test="${entry.containsError('password')}">
				<div class="error"><c:out value="${entry.getError('password')}"/></div>
			</c:if>
			<br>
			<input type="submit" name="metoda" value="Login">		
			</form>
			<br>
			<h4>
			Registration
			</h4>
			<p><a href="/aplikacija5/servleti/register">New user registration</a></p>
			<br>
    	</c:when>    
    	<c:otherwise>
       		<div><c:out value="${currentUserFn}	${currentUserLn}	|	"/>
       		<a href="/aplikacija5/servleti/logout">Logout</a></div>
   		</c:otherwise>
		</c:choose>	
		<h4>
		Authors:
		</h4>
		<c:forEach var="e" items="${authors}">
        	<li><a href="/aplikacija5/servleti/author/${e.nick}"target="_blank">${e.nick}</a></li>    
       </c:forEach>
	</body>
</html>