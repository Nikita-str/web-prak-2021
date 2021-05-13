<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
            <input name="find_year" type="date">
        </div>
        <div>
            <label>ISBN </label>
            <input name="find_ISBN" type="text" size="25">
        </div>
    </div>

    <div class="cancel bot-but" onclick="ShowById('search-reader-box')"><div class="center-it">отмена</div></div>
    <button type="submit" class="ok bot-but"><div class="center-it">подтвердить</div></button>
</form>