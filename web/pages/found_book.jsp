<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="div/Head.jsp"/>
<body>
<div class="left-block">
    <div class="lb-but" id="page-name">поиск</div>
    <c:choose>
        <c:when test="${from_take_rid>-1}">
            <a href="book_take?r_id=${from_take_rid}"><button class="lb-but back">к выбору книги</button></a>
        </c:when>
        <c:otherwise><a href="books"><button class="lb-but back">к книгам</button></a></c:otherwise>
    </c:choose>
    <a href="index"><button class="lb-but to-index">на главную</button></a>
</div>
<div class="right-block">
    <table class="table">
        <thead>
        <tr>
            <th scope="col"> Название</th>
            <th scope="col"> ОК</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${bs.size()==0}">
                <tr><td colspan="5"> книги не найдены</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach items="${bs}" var="b">
                    <tr>
                        <td><span>${b.getTitle()}</span></td>
                        <c:choose>
                            <c:when test="${from_take_rid>-1 && b.ExistSpare()==false}">
                                <td>нету свободных экземпляров</td>
                            </c:when>
                            <c:when test="${from_take_rid>-1}">
                                <td><a href="book_take?ex_id=${b.GetFirstSpare()}&r_id=${from_take_rid}">OK</a></td>
                            </c:when>
                            <c:otherwise><td><a href="book?id=${b.getBookId()}">OK</a></td></c:otherwise>
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
