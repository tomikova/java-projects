<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
<h1>${anketa.naziv}</h1>
<p>${anketa.poruka}
</p>
<ol>
<c:forEach var="e" items="${opcije}">
<li><a href="glasanje-glasaj?pollID=${anketa.id}&id=${e.id}">${e.name}</a></li>
</c:forEach>
</ol>
<br>
<br>
<a href="index.html">Početna stranica</a>
</body>
</html>