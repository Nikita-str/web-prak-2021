<%@ page import="utils.HibernateSessionFactoryUtil" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <title>библиотека</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="left-block">
            <div class="lb-but" id="page-name">главная страница</div>
            <button class="lb-but" onclick="location.href='/jWeb/readers.jsp'">читатели</button>
            <button class="lb-but" onclick="location.href='/jWeb/books.jsp'">книги</button>
        </div>
        <% HibernateSessionFactoryUtil.getSessionFactory(); %>
    </body>
</html>