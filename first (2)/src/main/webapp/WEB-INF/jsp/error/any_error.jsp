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
				<a href="#this" id="back" class="btn_back">�ڷ��̵�</a>
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		alert("�߸��� �����̰ų� �� �� ���� �����Դϴ�. �����ϰڽ��ϴ�. ���غ�Ź�帳�ϴ�.");
		
		$(document).ready(function() {
			$("#back").on("click", function(e) {			
				history.go(-1);
			})
		})
		
		
	</script>
</body>
</html>