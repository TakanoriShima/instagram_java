<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="title">タイトル</label><br />
<input type="text" name="title" value="" />
<br /><br />

<label for="content">内容</label><br />
<textarea name="content"></textarea>
<br /><br />

<label for="image">画像</label><br />
<input type="file" name="image" />
<br /><br />


<button type="submit">登録</button>


