<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Registration</title>
		
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
		User registration
		</h1>
		<form action="register" method="post">
		First name: <input type="text" name="firstName" value='<c:out value="${entry.firstName}"/>' size="30"><br>
		<c:if test="${entry.containsError('firstName')}">
			<div class="error"><c:out value="${entry.getError('firstName')}"/></div>
		</c:if>
		<br>	
		Last name: <input type="text" name="lastName" value='<c:out value="${entry.lastName}"/>' size="30"><br>
		<c:if test="${entry.containsError('lastName')}">
			<div class="error"><c:out value="${entry.getError('lastName')}"/></div>
		</c:if>
		<br>
		Email: <input type="text" name="email" value='<c:out value="${entry.email}"/>' size="30"><br>
		<c:if test="${entry.containsError('email')}">
			<div class="error"><c:out value="${entry.getError('email')}"/></div>
		</c:if>
		<br>						
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
		<input type="submit" name="metoda" value="Register">		
		</form>
	</body>
</html>