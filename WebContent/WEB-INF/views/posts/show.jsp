<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${post != null}">
                <c:if test="${flush != null}">
                    <div id="flush_success">
                        <c:out value="${flush}"></c:out>
                    </div>
                </c:if>
                <h2>投稿 詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${post.user.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${post.created_at}"
                                    pattern="yyyy-MM-dd HH:mm" /></td>
                        </tr>
                        <tr>
                            <th>タイトル</th>
                            <td><pre>
                                    <c:out value="${post.title}" />
                                </pre></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td><pre>
                                    <c:out value="${post.content}" />
                                </pre></td>
                        </tr>
                        <tr>
                            <th>画像</th>
                            <td><c:choose>
                                    <c:when test="${post.image != null}">
                                        <img src="https://quark2galaxy2quark.s3.amazonaws.com/photos/${post.image}"
                                            style="width: 30%">
                                    </c:when>
                                    <c:otherwise>
                                    画像はありません。
                                </c:otherwise>
                                </c:choose>
                             </td>
                        </tr>


                    </tbody>
                </table>

                <br />
            </c:when>
        </c:choose>

        <h2>新規コメント</h2>

        <form method="POST" action="<c:url value='/comments/create' />">
            <c:import url="../comments/_form.jsp" />
        </form>

        <h2>コメント一覧</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_date">コメント</th>

                </tr>
                <c:forEach var="comment" items="${post.commentList}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out value="${comment.user.name}" /></td>
                        <td class="report_date"><fmt:formatDate
                                value='${comment.created_at}' pattern='yyyy-MM-dd HH:mm' /></td>
                        <td class="report_title">${comment.content}</td>


                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <p>
            <a href="<c:url value="/top" />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>