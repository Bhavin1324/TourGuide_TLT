<%-- 
    Document   : Test
    Created on : Feb 23, 2023, 2:09:10 PM
    Author     : kunal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <sql:setDataSource dataSource="tltjdbc"/>
        <sql:query var="query">
            select * from city_master
        </sql:query>
        <h1>connection success!</h1>
    </body>
</html>
