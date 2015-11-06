<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv=Content-Type content="text/html;charset=UTF-8">
		<title>企业内部文档搜索系统</title>
		<link href="<%=request.getContextPath() %>/resources/css/search/css/souba.css" rel="stylesheet" type="text/css">
		<script type="text/javascript">
			function check() {
				var val = document.forms[0].fieldname.value;
				if (val == "") {
					return false;
				} else {
					document.forms[0].submit();
				}
			}
		</script>
	</head>
	<body>
        <br>
		<br>
		<br>
		<br>
		<br>
		<center>
			<img src="<%=request.getContextPath() %>/resources/css/search/img/searchba.GIF" width=340 height=148 usemap="#mp" id=lg>
			<br>
			<br>
			<br>
			<br>
			<form action="searchIndex.do">
				<input type="text" name="fieldname" size="100" maxlength="100" style="height:40px;">
				<input type="button" value="搜索"  onclick="check()" style="padding:9px 20px 9px 20px;"/>
				<a href="advancesearch.jsp" onclick="">高级搜索</a>
			</form>
			<p id="km"> </p>
			<p style="height: 60px">
			<table cellpadding=0 cellspacing=0 id=lk>
				<tr>
					<td></td>
				</tr>
			</table>
			<p style="height: 30px">
				<a
					href="#"><br>
				</a>
			</p>
			<p style="height: 14px"> </p>
			</p>
		</center>
	</body>
</html>
