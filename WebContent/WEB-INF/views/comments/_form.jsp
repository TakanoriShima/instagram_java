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

<label for="content">内容</label><br />
<textarea name="content"></textarea>
<br /><br />

<input type="hidden" name="post_id" value="<c:out value='${post.id}'/>">

<button type="submit">コメント投稿</button>