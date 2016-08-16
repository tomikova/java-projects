<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
<h4>Odaberite anketu:</h4>
<ol>
<c:forEach var="e" items="${ankete}">
<li><a href="glasanje?pollID=${e.id}">${e.naziv}</a></li>
</c:forEach>
</ol>
</body>
</html>