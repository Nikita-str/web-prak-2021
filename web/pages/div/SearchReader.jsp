<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <form id="search-reader-box" class="hide-layer-weird" method="get" action="find_reader" style="display: none;">
        <div class="inp-handler">
            <div>
                <label>имя </label>
                <input name="find_name" type="text" size="25">
            </div>
            <div>
                <label>фамилия </label>
                <input name="snake" type="text" size="25">
            </div>
            <div>
                <label>отчество </label>
                <input name="pat" type="text" size="25">
            </div>
            <br>
            <div>
                <label>телефон </label>
                <input name="phone" type="text" size="25">
            </div>
        </div>

        <div class="cancel bot-but" onclick="ShowById('search-reader-box')"><div class="center-it">отмена</div></div>
        <button type="submit" class="ok bot-but"><div class="center-it">подтвердить</div></button>
    </form>