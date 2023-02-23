<%-- 
    Document   : Test
    Created on : Feb 23, 2023, 2:09:10 PM
    Author     : kunal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <sql:setDataSource dataSource="jdbc/TLT"/>
        <sql:query var="query">
            select * from countries
        </sql:query>
        <table border="1">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Capital</th>
                <th>Currency</th>
            </tr>
            <c:forEach var="row" items="${query.rowsByIndex}">
                <tr>
                    <td>${row[0]}</td>
                    <td>${row[1]}</td>
                    <td>${row[6]}</td>
                    <td>${row[7]}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
