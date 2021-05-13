<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <form id="add-reader-box"  class="hide-layer-weird" method="post" action="add_reader" style="display: none;">
        <div class="inp-handler">
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
            <br>
            <div>
                <label>адрес </label>
                <input name="add_addr" type="text" size="25">
            </div>
            <div>
                <label>телефон </label>
                <input name="add_phone" type="text" size="25">
            </div>
        </div>

        <div class="cancel bot-but" onclick="ShowById('add-reader-box')"><div class="center-it">отмена</div></div>
        <button type="submit" class="ok bot-but"><div class="center-it">подтвердить</div></button>
    </form>