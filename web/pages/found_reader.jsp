<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="div/Head.jsp"/>
<body>
<div class="left-block">
    <div class="lb-but" id="page-name">поиск</div>
    <a href="readers"><button class="lb-but back">к читателям</button></a>
    <a href="index"><button class="lb-but to-index">на главную</button></a>
</div>
<div class="right-block">
    <table class="table">
        <thead>
        <tr>
            <th scope="col"> Имя</th>
            <th scope="col"> Фамилия</th>
            <th scope="col"> Отчество</th>
            <th scope="col"> Телефон</th>
            <th scope="col"> ОК</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${rs.size()==0}">
                <tr><td colspan="5"> читатели не найдены</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach items="${rs}" var="r">
                    <tr>
                        <td><span>${r.getFirstName()}</span></td>
                        <td><span>${r.getSecondName()}</span></td>
                        <td>${r.getPatronymic()}</td>
                        <td>${r.getPhoneNumber()}</td>
                        <td><a href="reader?id=${r.getLibraryCardId()}">OK</a></td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        </tbody>
    </table>
</div>
</body>
</html>
