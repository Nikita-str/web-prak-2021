<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="models.*, java.util.List" %>
<%@ page import="DAO.StdImpl.StdDAO_Factory" %>
<%@ page import="DAO.Interfaces.I_ReadersDAO" %>

<!DOCTYPE html>
<html>
    <jsp:include page="div/Head.jsp"/>
    <body>
        <div class="left-block">
            <div class="lb-but" id="page-name">читатели</div>
            <button class="lb-but" onclick="ShowById('add-reader-box')">добавить читателя</button>
            <!--a href="TODO"-->
            <button class="lb-but"  onclick="ShowById('search-reader-box')">найти читателя</button>
            <!--/a-->
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
            <jsp:include page="div/AddReader.jsp"/>
            <jsp:include page="div/SearchReader.jsp"/>
        </div>

    </body>
</html>