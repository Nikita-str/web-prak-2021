<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="div/Head.jsp"/>
<body>
<jsp:include page="div/Await.jsp"/>
<div class="left-block">
    <div class="lb-but" id="page-name">выдача книги</div>
    <button class="lb-but" onclick="ShById('about-book-take', 'hide-layer-weird')">о выдаче</button>
    <c:if test="${not_book}"><button class="lb-but" onclick="ShById('search-book-box', 'hide-layer-weird')">найти книгу</button></c:if>
    <c:if test="${not_reader}"><button class="lb-but" onclick="ShById('search-reader-box', 'hide-layer-weird')">найти читателя</button></c:if>
    <a href="index"><button class="lb-but to-index">на главную</button></a>
</div>
<div class="right-block">
    <c:if test="${not_book}"><jsp:include page="div/SearchBook.jsp"/></c:if>
    <c:if test="${not_reader}"><jsp:include page="div/SearchReader.jsp"/></c:if>
    <div id="about-book-take" class="info-header hide-layer-weird">
        <div>
            <c:if test="${not_book && not_reader}"><label>тут будет информация о выдаче книги</label></c:if>
            <c:if test="${!not_book}"><label>название книги: ${title}</label></c:if>
            <c:if test="${!not_reader}"><label>читательский билет: ${reader_id}</label></c:if>
            <c:if test="${!not_book}"><label>номер экземпляра книги: ${book_ex_id}</label></c:if>
            <c:if test="${!can_take && !not_book && !not_reader}"><label class="bad-info">у читателя уже есть экземпляр такой книги</label></c:if>
            <c:if test="${can_take && !not_book && !not_reader}">
            <form action="give_out">
                <div>
                    <legend>дата выдачи: </legend>
                    <input name="date_take" type="text" size="25">
                </div>
                <div>
                    <legend>планируемая дата возвращения: </legend>
                    <input name="date_take" type="text" size="25">
                </div>
                <input name="r_id" type="hidden" value="${reader_id}">
                <input name="b_ex_id" type="hidden" value="${book_ex_id}">
                <button type="submit" class="lb-but">выдать</button>
            </form>
            </c:if>
        </div>
    </div>

</div>
</body>
</html>
