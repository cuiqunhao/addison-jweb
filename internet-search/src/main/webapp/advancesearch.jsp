<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv=Content-Type content="text/html;charset=gb2312">
		<title>文档高级搜索</title>
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
		<center><img src="<%=request.getContextPath() %>/resources/css/search/img/searchba.GIF" width=340 height=148 usemap="#mp" id=lg>
			<br>
			<br>
			<br>
			<br>
			<form action="advanceSearchIndex">
			<table>
				<tr>
				<td><font><b>选择检索的类型</b></font></td>
				<td>
				<select name="qtype" style="height:40px;">
                    <option value="term" selected="selected">精确
                	<option value="fuzz">模糊
                	<option value="pref">前缀
                </select>
					<input name="fieldname" size="42" maxlength="100"  style="height:40px;"> 
		            <input type="button" value="搜索"  onclick="check()" style="padding:9px 20px 9px 20px;"/>
					</td>
					</tr>
					<tr>
					</tr>
					<tr>
					<td><font><b>选择每页显示的文档数</b></font></td>
					<td>
					<select name="pagetype" style="height:40px;">
	                    <option value="10" selected="selected">每页显示10条
	                	<option value="20">每页显示20条
	                	<option value="50">每页显示50条
	                </select>
					</td>
					</tr>
					<tr>
					</tr>
					<tr>
	                <td><font><b>检索结果数目设置</b></font></td>
					<td>
					<select name="totalpage" style="height:40px;">
	                    <option value="100" selected="selected">处理前100条
	                	<option value="200">处理前200条
	                	<option value="500">处理前500条
	                </select>
					</td>
					</tr>
					<tr>
					</tr>				
					<tr>
					<td><font><b>选择检索的文档类型</b></font></td>
					<td>
					<select name="filetype" style="height:40px;">
	                    <option value="pdf" selected="selected">PDF文档
	                	<option value="doc">Word文档
	                	<option value="all">全部文档
	                </select>
					</td>
					</tr>
				</table>
			</form>
		</center>
	</body>
</html>
