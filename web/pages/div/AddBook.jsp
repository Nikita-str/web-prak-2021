<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!-- layer-weird cause layer-1 -->
    <form id="add-book-box" class="hide-layer-weird" method="post" action="add_book" style="display: none;">
        <div class="inp-handler long">
            <div>
                <label class="inp-not-empty">название </label>
                <input name="add_title" type="text" size="25">
            </div>
            <div>
                <label>о книге </label>
                <input name="add_about" type="text" size="25">
            </div>
            <div>
                <label>издательство </label>
                <input name="add_pub" type="text" size="25">
            </div>
            <div>
                <label>дата написания </label>
                <input name="add_year" type="text", size="5">
            </div>
            <div>
                <label>ISBN </label>
                <input name="add_ISBN" type="text" size="25">
            </div>
            <br>
            <div>
                <label>количество </label>
                <input name="amount" type="text" size="5" value="0">
            </div>
        </div>

        <div class="cancel bot-but" onclick="ShowById('add-book-box')"><div class="center-it">отмена</div></div>
        <button type="submit" class="ok bot-but"><div class="center-it">подтвердить</div></button>
    </form>