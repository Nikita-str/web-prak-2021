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
            <table class="table" id="table-layer-weird">
                <thead id="thead-layer-weird">
                <tr>
                    <th scope="col"> ID</th>
                    <th scope="col"> Состояние</th>
                    <th scope="col"> Перейти</th>
                </tr>
                </thead>
                <tbody id="tbody-layer-weird">
                <c:choose>
                    <c:when test="${exs.size()==0}">
                        <tr><td class="bad-info" colspan="4"> экземпляров книги нет</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${exs}" var="ex">
                            <li>
                                <td>${ex.size()}</td>
                                <td>${ex[1]}</td>
                                <td><a href="book_ex?ex_id=${ex[0]}">OK</a></td>
                            </li>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
