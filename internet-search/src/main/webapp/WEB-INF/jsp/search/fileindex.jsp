<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="../common/common_css.jsp"%>

</head>
<body class="no-skin">
	<div class="main-content-inner">
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="#">站内搜索</a>
				</li>
				<li class="active">索引初始化</li>
			</ul><!-- /.breadcrumb -->			
		</div>
		<div class="page-content">
			
			<div class="row">
				<div class="col-xs-12">
					<a href="fileIndex?flag=init" class="btn btn-lg btn-success">索引初始化</a>&nbsp;&nbsp;
					<a href="fileIndex?flag=pdf" class="btn btn-lg btn-info">PDF文档索引</a>&nbsp;&nbsp;
					<a href="fileIndex?flag=doc" class="btn btn-lg btn-primary">Word文档索引</a>
				</div>
			</div><!-- /.row -->
		</div>
	</div>
<%@ include file="../common/common_js.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>

</body>
</html>