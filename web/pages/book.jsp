<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="div/Head.jsp"/>
<body>
    <jsp:include page="div/Await.jsp"/>
    <div class="left-block">
        <div class="lb-but" id="page-name">книга</div>
        <button class="lb-but">взять книгу</button>
        <a href="books"><button class="lb-but back">к книгам</button></a>
        <a href="index"><button class="lb-but to-index">на главную</button></a>
    </div>
    <!-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -->
    <div class="right-block">
        <div class="info-header">
            <div>
                <label>название: ${book.getTitle()}</label>
                <label>о книге: ${book.getAbout()==null?"---":book.getAbout()}</label>
                <label>издатель: ${book_pub}</label>
                <label>год написания: ${pub_year}</label>
                <label>ISBN: ${book.getIsbn()==null?"---":book.getIsbn()}</label>

                <c:choose>
                    <c:when test="${auths.size()==0}"><label>авторы: не указаны</label></c:when>
                    <c:otherwise>
                        <label>авторы:
                            <c:forEach items="${auths}" var="a">
                            <span>${a.getSecondName()} ${a.getFirstName()}<c:if test="${a.getPatronymic()!=null}"> </c:if>${a.getPatronymic()}; </span>
                            </c:forEach>
                        </label>
                    </c:otherwise>
                </c:choose>
                <br>
                <label>добавить автора:</label>
                <form method="post" action="add_auth">
                    <div class="inp-handler">
                        <input name="book_id" type="hidden" value="${id}">
                        <div>
                            <label class="inp-not-empty">имя </label>
                            <input name="add_name" type="text" size="25">
                        </div>
                        <div>
                            <label class="inp-not-empty">фамилия </label>
                            <input name="add_snake" type="text" size="25">
                        </div>
                        <div>
                            <label>отчество </label>
                            <input name="add_pat" type="text" size="25">
                        </div>
                        <div>
                            <span class="tab-1"></span>
                            <button type="submit" class="mini-ok">+</button>
                        </div>
                    </div>
                </form>
                <label>TODO:кол-во книг</label>
                <label>TODO:к книгам</label>
            </div>
        </div>
    </div>
</body>
</html>
