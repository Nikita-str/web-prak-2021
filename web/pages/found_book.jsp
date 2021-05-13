<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="div/Head.jsp"/>
<body>
<div class="left-block">
    <div class="lb-but" id="page-name">поиск</div>
    <a href="readers"><button class="lb-but back">к книгам</button></a>
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
                        <td><a href="book?id=${b.getBookId()}">OK</a></td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>

        </tbody>
    </table>
</div>
</body>
</html>
