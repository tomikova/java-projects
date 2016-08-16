<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.awt.Color" %>

<%
    String color = (String)session.getAttribute("color");
    if(color==null) {
    	color = "#FFFFFF";
    }
    
    Random rand = new Random();
    float r = rand.nextFloat();
    float g = rand.nextFloat();
    float b = rand.nextFloat();
    Color randomColor = new Color(r, g, b);
    String textColor = String.format("#%06x", randomColor.getRGB() & 0x00FFFFFF);
%>

<html>
<body bgcolor="<%=color%>">
<font color="<%=textColor%>">
Today, I was on a blind date with a girl my friend set me up with.<br>
We went to a fancy restaurant and she ordered the shrimp.<br> 
I told her, "I'm allergic to shrimp, so you shouldn't order it in case I want to kiss you later."<br> 
She looked at the waiter and said, "I'll have the shrimp." FML<br>
</font>
</body>
</html>