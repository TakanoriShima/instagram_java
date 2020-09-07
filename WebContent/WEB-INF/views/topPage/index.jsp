<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>

        <h2>投稿一覧</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_title">操作</th>
                </tr>
                <c:forEach var="post" items="${posts}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out value="${post.user.name}" /></td>
                        <td class="report_date"><fmt:formatDate
                                value='${post.created_at}' pattern='yyyy-MM-dd HH:mm' /></td>
                        <td class="report_title">${post.title}(コメント件数: ${fn:length(post.commentList)})</td>

                        <td class="report_action"><a
                            href="<c:url value='/posts/show?id=${post.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <p>
            <a href="<c:url value='/posts/new' />">新規投稿</a>
        </p>
        <p>
            <a href="<c:url value='/logout' />">ログアウト</a>
        </p>
    </c:param>
</c:import>