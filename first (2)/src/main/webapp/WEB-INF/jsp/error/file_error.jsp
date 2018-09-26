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
		alert("파일이 너무 크거나 잘못된 접근입니다. 죄솧압니다.");
		
		$(document).ready(function() {
			$("#back").on("click", function(e) {			
				history.go(-1);
			})
		})
		
		
	</script>
</body>
</html>