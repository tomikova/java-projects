<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
<style type="text/css">
table.rez td {text-align: center;}
</style>
</head>
<body>
<h1>Rezultati glasanja</h1>
<p>Ovo su rezultati glasanja.</p>
<table border="1" cellspacing="0" class="rez">
<thead><tr><th>Sudionik</th><th>Broj glasova</th></tr></thead>
<tbody>
<c:forEach var="e" items="${opcije}">
<tr><td>${e.name}</td><td>${e.brGlasova}</td></tr>
</c:forEach>
</tbody>
</table>

<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-chart" src="/aplikacija5/glasanje-grafika?pollID=${pollID}" width="400" height="400" />
<h2>Rezultati u XLS formatu</h2>
<p>Rezultati u XLS formatu dostupni su <a href="/aplikacija5/glasanje-xls?pollID=${pollID}">ovdje</a></p>
<h2>Razno</h2>
<p>Pregled sudionika ankete:</p>
<ul>
<c:forEach var="e" items="${opcije}">
<li><a href="${e.link}"
target="_blank">${e.name}</a></li>
</c:forEach>
</ul>
</body>
</html>