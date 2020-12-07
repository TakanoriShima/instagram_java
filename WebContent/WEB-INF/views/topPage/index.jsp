<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success" class="row">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

		<div class="row mt-3">
			<h3 class="col-sm-12 text-center message">会員登録して写真を共有しよう</h3>
		</div>
		<div class="row mt-5 mb-4">
			<div class="offset-sm-3 col-sm-3 text-center mb-4">
				<a href="<c:url value='/users/new' />" class="btn btn-primary col-sm-12">新規会員登録</a>
			</div>
			<div class="col-sm-3 mb-4">
				<a href="<c:url value='/login' />" class="btn btn-primary col-sm-12">ログイン</a>
			</div>
		</div>

		<div class="row mt-4">
			<c:forEach var="post" items="${posts}">
				<div class="col-sm-3 mb-4"><img src="https://quark2galaxy2quark.s3.amazonaws.com/photos/${post.image}"></div>
			</c:forEach>
		</div>
    </c:param>
</c:import>