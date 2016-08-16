<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%
    String color = (String)session.getAttribute("color");
    if(color==null) {
    	color = "#FFFFFF";
    }
    
    long timeDiff = System.currentTimeMillis() - (long)getServletContext().getAttribute("time");
    final Calendar cal = Calendar.getInstance();
    final long days =  timeDiff / (24 * 60 * 60 * 1000);
    long dayTime = timeDiff-days*(24 * 60 * 60 * 1000);
    long hours = dayTime / (60 * 60 * 1000);
    cal.setTimeInMillis(dayTime);
    SimpleDateFormat df = new SimpleDateFormat("mm:ss:SSS");
    String[] timeUnits = df.format(cal.getTime()).split(":");
    final String timeString = (days < 10 ? "0"+days : days)+" days "+ (hours < 10 ? "0"+hours : hours) +" hours "
    		+timeUnits[0]+" minutes "+timeUnits[1]+" seconds "+timeUnits[2]+" miliseconds";
%>

<html>
<body bgcolor="<%=color%>">
	<h4>Time application is running:</h4>
	<p><%=timeString%></p>
</body>
</html>