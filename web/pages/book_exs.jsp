<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="div/Head.jsp"/>
<body>
<jsp:include page="div/Await.jsp"/>
<div class="left-block">
    <div class="lb-but" id="page-name">экземпляры</div>
    <c:if test="${can_take}"><a href=""><button class="lb-but">выдать любую</button></a></c:if>
    <a href="book?id=${book_id}"><button class="lb-but back">к книге</button></a>
    <a href="index"><button class="lb-but to-index">на главную</button></a>
</div>
<div class="right-block">
    <div class="info-header">
        <div>
            <label>название: ${title}</label>
            <br/>
            <label>экземпляры:</label>
            <nav id="nav-layer-distortion" class="scrollable">
                <c:choose>
                    <c:when test="${exs.size()==0}">
                        <ul><li class="bad-info" colspan="4"> экземпляров книги нет</li></ul>
                    </c:when>
                    <c:otherwise>
                        <ul>
                        <c:forEach items="${exs}" var="ex">
                                <li>${ex[0]}  |  ${ex[1]}  |  <a href="book_ex?ex_id=${ex[0]}">OK</a></li>
                        </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </nav>
        </div>
    </div>
</div>

</body>
</html>
