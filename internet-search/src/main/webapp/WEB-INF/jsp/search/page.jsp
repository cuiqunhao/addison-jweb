<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'page.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
-->
</head>

<body>
	This is my JSP page.
	<br>
	<a href="PageAction.action?page1=23">页面page</a>
	<c:choose>
		<c:when test="${not empty requestScope.pageNumBean.upPageNum}">
			<a href="${pageUrl}${requestScope.pageNumBean.upPageNum}">上一页</a>
		</c:when>
		<c:otherwise>  
        	上一页   
    </c:otherwise>
	</c:choose>

	<c:forEach items="${requestScope.pageNumBean.pages}" var="item">
		<c:choose>
			<c:when test="${item == requestScope.pageNumBean.currentNum}">
				<a href="${pageUrl}${item}">${item}</a>
			</c:when>
			<c:otherwise>
				<a href="${pageUrl}${item}">[${item}]</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:choose>
		<c:when test="${not empty requestScope.pageNumBean.downPageNum}">
			<a href="${pageUrl}${requestScope.pageNumBean.downPageNum}">下一页</a>
		</c:when>
		<c:otherwise>  
        	下一页   
    </c:otherwise>
	</c:choose>


</body>
</html>
