<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <jsp:include page="div/Head.jsp"/>
    <body>
        <jsp:include page="div/Await.jsp"/>

        <div class="left-block">
            <div class="lb-but" id="page-name">читатели</div>
            <button class="lb-but" onclick="ShById('add-reader-box', 'hide-layer-weird')">добавить читателя</button>
            <button class="lb-but"  onclick="ShById('search-reader-box', 'hide-layer-weird')">найти читателя</button>
            <a href="index"><button class="lb-but to-index">на главную</button></a>
        </div>

        <div class="right-block">
            <jsp:include page="div/AddReader.jsp"/>
            <jsp:include page="div/SearchReader.jsp"/>
        </div>

    </body>
</html>