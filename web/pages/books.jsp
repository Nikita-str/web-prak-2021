<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <jsp:include page="div/Head.jsp"/>
    <body>
        <div class="left-block">
            <div class="lb-but" id="page-name">читатели</div>
            <button class="lb-but" onclick="ShowById('add-book-box')">добавить читателя</button>
            <button class="lb-but"  onclick="ShowById('search-book-box')">найти читателя</button>
            <a href="index"><button class="lb-but to-index">на главную</button></a>
        </div>

        <div class="right-block">
            <jsp:include page="div/AddBook.jsp"/>
            <jsp:include page="div/SearchBook.jsp"/>
        </div>

    </body>
</html>