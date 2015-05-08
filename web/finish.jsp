<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : finish
    Created on : May 7, 2015, 1:05:25 PM
    Author     : Eugeny
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="test.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Результат</title>
    </head>
    <body>
        <h1>Результат тестирования</h1>
        <h2>${fio} - группа ${group}</h2>
        <h3>Правильно: ${total} из 50</h3>
        <table border="1">
            <thead>
                <tr>
                    <th>№</th>
                    <th>Ответ</th>
                    <th>Правильно</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="r" items="${result}">
                <c:choose>
                    <c:when test="${r.yours==r.correct}">
                        <tr class="correct">
                    </c:when>
                    <c:otherwise>
                        <tr class="wrong">
                    </c:otherwise>
                </c:choose>
                    <td>${r.n+1}</td>
                    <td>${r.yours+1}</td>
                    <td>${r.correct+1}</td>
                </tr>                
            </c:forEach>
            </tbody>
        </table>
        
    </body>
</html>
