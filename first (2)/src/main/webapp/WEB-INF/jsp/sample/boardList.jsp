<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
</head>
<body>
	<div align="right">
		<c:choose>
			<c:when test="${empty userInfo }">
				<a href="#this" class="-btn" id="login">로그인</a>
				<a href="#this" class="btn" id="join">회원가입</a>
			</c:when>
			<c:otherwise>
				<a href="#this" class="btn" id="logout">로그아웃</a>
			</c:otherwise>
		</c:choose>
	</div>

	<h2>게시판 목록</h2>
	<table class="board_list">
		<colgroup>
			<col width="10%" />
			<col width="*" />
						
			<col width="15%" />
			<col width="20%" />
		</colgroup>
		<thead>
			<tr>
				<th scope="col">글번호</th>
				<th scope="col">제목</th>
				<th scope="col">조회수</th>
				<th scope="col">작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${fn:length(list) > 0}">
					<c:forEach var="row" items="${list}" varStatus="status">
						<tr>
							<td>${row.IDX}</td>
							<td class="title"><a href="#this" name="title">${row.TITLE}</a>
								<input type="hidden" id="IDX" value="${row.IDX}"></td>
							<td>${row.HIT_CNT}</td>
							<td>${row.CREA_DTM}</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="4">조회된 결과가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<br><br>
	<div>
		<c:if test="${not empty paginationInfo}">
		<ul class="pagination">
			<ui:pagination paginationInfo="${paginationInfo}" type="text" jsFunction="fn_search" />
		</ul>
		</c:if>
	</div>
	<input type="hidden" id="currentPageNo" name="currentPageNo" />

	<br>
	<a href="#this" class="btn" id="write">글쓰기</a>

	<%@ include file="/WEB-INF/include/include-body.jspf"%>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#write").on("click", function(e) { //글쓰기 버튼
				e.preventDefault();
				fn_openBoardWrite();
			})

			$("a[name='title']").on("click", function(e) { //제목
				e.preventDefault();
				fn_openBoardDetail($(this));
			})

			$("#login").on("click", function(e) {
				e.preventDefault();
				fn_openUserLogin();
			})

			$("#join").on("click", function(e) {
				e.preventDefault();
				fn_openUserJoin();
			})

			$("#logout").on("click", function(e) {
				e.preventDefault();
				fn_logoutUser(); 	
			});
		});

		function fn_openBoardWrite() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/openBoardWrite.do' />");
			comSubmit.submit();
		}

		function fn_openBoardDetail(obj) {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/openBoardDetail.do' />");
			comSubmit.addParam("IDX", obj.parent().find("#IDX").val());
			comSubmit.submit();
		}

		function fn_search(pageNo) {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/openBoardList.do' />");
			comSubmit.addParam("currentPageNo", pageNo);
			comSubmit.submit();
		}

		function fn_openUserLogin() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/openUserLogin.do'/>");
			comSubmit.submit();
		}

		function fn_openUserJoin() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/openUserJoin.do'/>");
			comSubmit.submit();
		}

		function fn_logoutUser() {
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/logoutUser.do'/>");
			comSubmit.submit();
			alert("로그아웃 되었습니다.");
		}
	</script>
</body>
</html>
