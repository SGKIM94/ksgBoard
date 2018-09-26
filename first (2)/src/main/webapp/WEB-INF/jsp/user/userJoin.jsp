<p><%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
	<!DOCTYPE>
	<html>
<head>
<title>회원가입</title>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
</head>
<body>
	<div class="user_view wdp_30">
		<div class="con_center under_line">
			<h5>회원 가입</h5>
		</div>
		<form id="frm" class="padding_5" onSubmit="JavaScript:fn_joinUser()">
			<fieldset>
				<input type="text" class="input_text" name="UID" maxlength="15"
					placeholder="아이디" value="" id="uid" required><br /> <br>
				<input type="password" class="input_text" name="UPW" maxlength="15"
					placeholder="비밀번호" value="" id="upw" required><br /> <br>
				<input type="email" class="input_text" name="EMAIL" maxlength="50"
					placeholder="이메일" value="" id="email" required><br /> <br>
				<input type="text" class="input_text" name="UNAME" maxlength="15"
					placeholder="이름" value="" id="uname" required><br /> <br>
				<input type="text" class="input_text" name="UNICK" maxlength="15"
					placeholder="닉네임" value="" id="unick" required><br /> <br>
			</fieldset>
			<br />
			<c:if test="${! empty error}">
                ${error} 값이 중복된 값입니다.           
            </c:if>
			<button type="submit" class="btn con_center">회원 가입</button>
		</form>
		<a href="#this" id="cancel" class="btn_join">취소</a>
	</div>

</body>
<%@ include file="/WEB-INF/include/include-body.jspf"%>


<script type="text/javascript">
	$(document).ready(function() {
		$("#cancel").on("click", function(e) {
			e.preventDefault();
			fn_joinCancel();
		})
	})
	
	
	function fn_joinUser() {
		var comSubmit = new ComSubmit("frm");
		comSubmit.setUrl("<c:url value='/joinUser.do'/>");
		comSubmit.submit();
		alert("회원가입 되었습니다.");
	}

	function fn_joinCancel() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/openUserLogin.do'/>");
		comSubmit.submit();
		alert("회원가입 취소되었습니다.");
	}
</script>
	</html>