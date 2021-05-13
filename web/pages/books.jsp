<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <jsp:include page="div/Head.jsp"/>
    <body>
        <jsp:include page="div/Await.jsp"/>

        <div class="left-block">
            <div class="lb-but" id="page-name">книги</div>
            <button class="lb-but" onclick="ShById('add-book-box', 'hide-layer-weird')">добавить книгу</button>
            <button class="lb-but"  onclick="ShById('search-book-box', 'hide-layer-weird')">найти книгу</button>
            <a href="index"><button class="lb-but to-index">на главную</button></a>
        </div>

        <div class="right-block">
            <jsp:include page="div/AddBook.jsp"/>
            <jsp:include page="div/SearchBook.jsp"/>
        </div>

    </body>
</html>