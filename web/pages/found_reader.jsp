<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="div/Head.jsp"/>
<body>
<div class="left-block">
    <div class="lb-but" id="page-name">поиск</div>
    <c:choose>
        <c:when test="${from_take_ex_id>-1}">
            <a href="book_take?ex_id=${from_take_ex_id}"><button class="lb-but back">к выбору книги</button></a>
        </c:when>
        <c:otherwise><a href="readers"><button class="lb-but back">к читателям</button></a></c:otherwise>
    </c:choose>
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
                        <c:choose>
                            <c:when test="${from_take_ex_id>-1}">
                                <td><a href="book_take?ex_id=${from_take_ex_id}&r_id=${r.getLibraryCardId()}">OK</a></td>
                            </c:when>
                            <c:otherwise><td><a href="reader?id=${r.getLibraryCardId()}">OK</a></td></c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        </tbody>
    </table>
</div>
</body>
</html>
