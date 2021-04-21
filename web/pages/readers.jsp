<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="models.*, java.util.List" %>
<%@ page import="DAO.StdImpl.StdDAO_Factory" %>
<%@ page import="DAO.Interfaces.I_ReadersDAO" %>

<!DOCTYPE html>
<html>
    <head>
        <title>библиотека</title>
        <link rel="stylesheet" href="css/style.css">
        <script src="js/show_by_id.js"></script>
    </head>
    <body>
        <div class="left-block">
            <div class="lb-but" id="page-name">читатели</div>
            <button class="lb-but" onclick="ShowById('add-reader-box')">добавить читателя</button>
            <a href="TODO"><button class="lb-but">найти читателя</button></a>
            <a href="index">
                <button class="lb-but">
                    <%
                    StdDAO_Factory factory = StdDAO_Factory.getInstance();
                    I_ReadersDAO reader_dao = factory.getReaderDao();
                    List<Readers> rs = reader_dao.FindReader_Surname("surname");
                    out.println(rs.size());
                    %>
                </button>
            </a>
        </div>

        <div class="right-block">
            <div id="add-reader-box">
                <div class="inp-handler">
                    <div>
                        <label class="inp-not-empty">имя </label>
                        <input type="text" size="25">
                    </div>
                    <div>
                        <label class="inp-not-empty">фамилия </label>
                        <input type="text" size="25">
                    </div>
                    <div>
                        <label>отчество </label>
                        <input type="text" size="25">
                    </div>
                    <br>
                    <div>
                        <label>адрес </label>
                        <input type="text" size="25">
                    </div>
                    <div>
                        <label>телефон </label>
                        <input type="text" size="25">
                    </div>
                </div>

                <div class="cancel bot-but"><div class="center-it">отмена</div></div>
                <div class="ok bot-but"><div class="center-it">подтвердить</div></div>
            </div>
        </div>

    </body>
</html>