<%@ page import="utils.HibernateSessionFactoryUtil" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <jsp:include page="div/Head.jsp"/>
    <body>
        <jsp:include page="div/Await.jsp"/>
        <div class="left-block">
            <div class="lb-but" id="page-name">главная страница</div>
            <a href="readers"><button class="lb-but">читатели</button></a>
            <a href="books"><button class="lb-but">книги</button></a>
        </div>
        <% HibernateSessionFactoryUtil.getSessionFactory(); %>
    </body>
</html>