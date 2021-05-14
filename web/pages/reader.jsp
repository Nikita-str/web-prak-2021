<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="div/Head.jsp"/>
<body>
    <jsp:include page="div/Await.jsp"/>
    <div class="left-block">
        <div class="lb-but" id="page-name">читатель</div>
        <a href="book_take?r_id=${id}"><button class="lb-but">взять книгу</button></a>
        <a href="readers"><button class="lb-but back">к читателям</button></a>
        <a href="index"><button class="lb-but to-index">на главную</button></a>
    </div>
    <!-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -->
    <div class="right-block">
        <div class="info-header">
            <div>
                <label>номер читательского билета: ${id}</label>
                <br>
                <label>имя: ${reader.getFirstName()}</label>
                <label>фамилия: ${reader.getSecondName()}</label>
                <label>отчество: ${patr}</label>
                <br>
                <label>адрес: ${addr}</label>
                <label>тел. номер: ${p_number}</label>
                <br>
                <label>всего книг [задолжности/выдано]:${overdue_all}/${taked_book_all}</label>
                <label>книг на руках [задолжности/выдано]:${overdue_now}/${taked_book_now}</label>
                <c:if test="${cur_bs.size()>0}">
                <nav id="nav-layer-distortion" class="scrollable">
                    <ul>
                        <c:forEach items="${cur_bs}" var="beh">
                            <li>${beh.getBookEx().getBookExId()}  |  ${beh.getBookEx().getBook().getTitle()}  |
                                <a href="book_ret?ex_id=${beh.getBookEx().getBookExId()}&to_r_id=${id}">сдать</a></li>
                        </c:forEach>
                    </ul>
                </nav>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
