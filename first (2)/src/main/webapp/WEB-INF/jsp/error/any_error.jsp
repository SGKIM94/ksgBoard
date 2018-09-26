<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>
<meta charset="EUC-KR">
<title>Page Error</title>
</head>
<body>

	<div id="wrapper">
		<div id="page-wrapper">
			<div align="center">
				<a href="#this" id="back" class="btn_back">뒤로이동</a>
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		alert("잘못된 접근이거나 알 수 없는 오류입니다. 수정하겠습니다. 양해부탁드립니다.");
		
		$(document).ready(function() {
			$("#back").on("click", function(e) {			
				history.go(-1);
			})
		})
		
		
	</script>
</body>
</html>