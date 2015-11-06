<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>企业内部文档搜索系统</TITLE>
<!--STATUS OK-->
<META http-equiv=content-type content=text/html;charset=UTF-8">
<link href="<%=request.getContextPath() %>/resources/css/search/css/result.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY>
	<TABLE height=54 cellSpacing=0 cellPadding=0 width="100%" align=center>
		<TBODY>
			<TR vAlign=center>
				<TD style="PADDING-LEFT: 8px; WIDTH: 137px" vAlign=top noWrap width="100%">
					<IMG height="46" alt="到搜索首页" src="<%=request.getContextPath() %>/resources/css/search/img/searchba.GIF" width=137 border=0>
				</TD>
				<TD>&nbsp;&nbsp;&nbsp;</TD>
				<TD vAlign="top" align="left" width="100%">
					<DIV class="Tit"></DIV>
					<TABLE style="MARGIN-LEFT: 18px; HEIGHT: 60px" cellSpacing=0 cellPadding=0>
						<TBODY>
							<TR>
								<TD vAlign="top" noWrap>
								    <form action="searchIndex.do">
										<input type="text" name="fieldname" size="42" value="${sk}" maxlength="100" style="height:20px;"/>
										<input type="submit"  value="搜吧" />
										<A href="advancesearch.jsp">高级搜索</A>
									</form>
								</TD>
								<TD align="top" noWrap>&nbsp;&nbsp; 
								</TD>
							 </TR>
						</TBODY>
					 </TABLE>
				 </TD>
			</TR>
		</TBODY>
	</TABLE>
	<TABLE class=bi cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
		<TBODY>
			<TR>
				<TD noWrap align="left">&nbsp;&nbsp;搜到相关文档约 &nbsp;${rsize}&nbsp;篇&nbsp;&nbsp;&nbsp;&nbsp;
				</TD>
			</TR>
		</TBODY>
	</TABLE>
	<!--开始处理分 -->
	
	<c:forEach items="${rlist}" var="eachdoc">
		<TABLE cellSpacing=0 cellPadding=0 border=0>
			<TBODY>
				<TR>
					<TD class="f EC_PP">
						<div>
							<A href="${eachdoc.originalFileName}" target=_blank>${eachdoc.filename} </A>
						</div>
						
						<div style="margin-top:3px;padding-top:3px;padding-bottom:10px;">
						${eachdoc.contents}</div>
						
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</c:forEach>
	<div style="padding-left:10px;">
	<c:choose>
		<c:when test="${not empty requestScope.pageNumBean.upPageNum}">
			<a href="${pageUrl}${requestScope.pageNumBean.upPageNum}&sk=${sk1}">上一页</a>
		</c:when>
		<c:otherwise>  
 			  上一页 
   		</c:otherwise>
	</c:choose>

	<c:forEach items="${requestScope.pageNumBean.pages}" var="item">
		<c:choose>
			<c:when test="${item == requestScope.pageNumBean.currentNum}">
				<a href="${pageUrl}${item}&sk=${sk1}">${item}</a>
			</c:when>
			<c:otherwise>
				<a href="${pageUrl}${item}&sk=${sk1}">[${item}]</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:choose>
		<c:when test="${not empty requestScope.pageNumBean.downPageNum}">
			<a href="${pageUrl}${requestScope.pageNumBean.downPageNum}&sk=${sk1}">下一页</a>
		</c:when>
		<c:otherwise>  
    		下一页 
    	</c:otherwise>
	</c:choose>
	</div>
	<DIV style="CLEAR: both; WIDTH: 100%; HEIGHT: 60px; BACKGROUND-COLOR: #eff2fa">
		<TABLE style="MARGIN-LEFT: 18px; HEIGHT: 60px" cellSpacing=0 cellPadding=0>
			<form action="searchIndex.do">
				<TBODY>
					<TR vAlign=center>
						<TD noWrap>
							<input type="text" name="fieldname" size="42" value="${sk}" maxlength="100" />
							<input type="submit"  value="搜吧" />
						<TD noWrap></TD>
					</TR>
				</TBODY>
			</form>
		</TABLE>
	 </DIV>
	<IMG style="DISPLAY: none" src="">
</BODY>
</HTML>
