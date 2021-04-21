<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="models.*, utils.*, java.util.List, java.util.Iterator" %>
<%@ page import="DAO.StdImpl.StdDAO_Factory" %>
<%@ page import="DAO.Interfaces.I_ReadersDAO" %>
<%@ page import="org.hibernate.boot.registry.StandardServiceRegistry" %>
<%@ page import="org.hibernate.boot.registry.StandardServiceRegistryBuilder" %>
<%@ page import="org.hibernate.boot.MetadataSources" %>
<%@ page import="org.hibernate.boot.Metadata" %>
<%@ page import="org.hibernate.SessionFactory" %>

<!DOCTYPE html>
<html>
    <head>
        <title>библиотека</title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="left-block">
            <div class="lb-but" id="page-name">читатели</div>
            <button class="lb-but" onclick="location.href='/jWeb/readers.jsp'">добавить читателя</button>
            <button class="lb-but" onclick="location.href='/jWeb/books.jsp'">найти читателя</button>
            <button class="lb-but" onclick="location.href='/jWeb/books.jsp'">
            <%
                StdDAO_Factory factory = StdDAO_Factory.getInstance();
                I_ReadersDAO reader_dao = factory.getReaderDao();
                List<Readers> rs = reader_dao.FindReader_Surname("surname");
                out.println(rs.size());
            %>
            </button>

    <!--
    -->
        </div>
    </body>
</html>