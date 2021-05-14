<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="div/Head.jsp"/>
<body>
<jsp:include page="div/Await.jsp"/>
<div class="left-block">
    <div class="lb-but" id="page-name">экземпляр</div>
    <c:if test="${!dereg && can_take}"><a href="book_take?ex_id=${ex_id}"><button class="lb-but">взять книгу</button></a></c:if>
    <c:if test="${!dereg && !can_take}"><a href="book_ret?ex_id=${ex_id}"><button class="lb-but">сдать книгу</button></a></c:if>
    <c:if test="${!dereg && !can_take}"><a href="book_lost?ex_id=${ex_id}"><button class="lb-but bad-info">утерена</button></a></c:if>
    <a href="history?ex_id=${ex_id}"><button class="lb-but">история</button></a>
    <a href="book?id=${book_id}"><button class="lb-but back">к книге</button></a>
    <a href="index"><button class="lb-but to-index">на главную</button></a>
</div>
<!-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -->
<div class="right-block">
    <div class="info-header"><div><label>название: ${title}</label></div></div>
</div>

</body>
</html>