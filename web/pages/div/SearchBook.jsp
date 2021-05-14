<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="search-book-box" class="hide-layer-weird" method="get" action="find_book" style="display: none;">
    <div class="inp-handler long">
        <div>
            <label>название </label>
            <input name="find_title" type="text" size="25">
        </div>
        <div>
            <label>издательство </label>
            <input name="find_pub" type="text" size="25">
        </div>
        <div>
            <label>год публикации </label>
            <input name="find_year" type="text" size="25">
        </div>
        <div>
            <label>ISBN </label>
            <input name="find_ISBN" type="text" size="25">
        </div>
    </div>
    <c:if test="${not_reader==false}"><input name="from_take_rid" type="hidden" value="${reader_id}"></c:if>
    <div class="cancel bot-but" onclick="ShowById('search-reader-box')"><div class="center-it">отмена</div></div>
    <button type="submit" class="ok bot-but"><div class="center-it">подтвердить</div></button>
</form>