<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<head>
<title>로그인</title>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
</head>
<body>
	<div class="user_view wdp_30">

		<div class="con_center under_line">
			<h5>로그인</h5>
		</div>
		<form name="login_form" id="frm" class="padding_5" onsubmit="JavaScript:fn_loginUser();">
			<fieldset>
				<input type="text" class="input_text" name="UID" maxlength="15" placeholder="아이디" value="" id="uid"><br /> <br> 
				<input type="password" class="input_text" name="UPW" maxlength="15" placeholder="비밀번호" value="" id="upw"><br /> <br>
			</fieldset>
			<br />
			<c:if test="${error}">
                아이디나 비밀번호가 일치하지 않습니다.           
            </c:if>
			<button type="submit" class="btn con_center">로그인</button>
		</form>
		<a href="#this" class="btn_join" id="join">회원가입</a>
	</div>
	
</body>
<%@ include file="/WEB-INF/include/include-body.jspf"%>
<script type="text/javascript">
	alert("존재하지 않는 회원이거나 잘못된 접근입니다.");
	$(document).ready(function() {
		$("#join").on("click", function(e) {			
			e.preventDefault();
			fn_userJoin();
		})
	})

	function fn_loginUser() {
		var comSubmit = null;
		var uid_id = document.login_form.UID.value;
		var upw_id = document.login_form.UPW.value;
		
		
		if(!uid_id)
			alert("아이디를 입력하세요");
		else if(!upw_id)
			alert("패스워드를 입력하세요");
		else {
			comSubmit = new ComSubmit("frm");
			comSubmit.setUrl("<c:url value='/loginUser.do'/>");
			comSubmit.submit();
		}
	}

	function fn_userJoin() {
		var comSubmit = new ComSubmit();
		comSubmit.setUrl("<c:url value='/openUserJoin.do'/>")
		comSubmit.submit();
	}
</script>
</html>