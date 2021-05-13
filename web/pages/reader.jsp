<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="div/Head.jsp"/>
<body>
    <jsp:include page="div/Await.jsp"/>
    <div class="left-block">
        <div class="lb-but" id="page-name">читатель</div>
        <button class="lb-but">взять книгу</button>
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
            </div>
        </div>
    </div>
</body>
</html>
