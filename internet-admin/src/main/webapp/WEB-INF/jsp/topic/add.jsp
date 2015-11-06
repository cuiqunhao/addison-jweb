<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="../common/common_css.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/admin/main.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/base/jquery.ui.all.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/admin/article.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/validate/main.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/zTree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/core/jquery.cms.keywordinput.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.keywordinput.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/tree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/ui/jquery-ui-1.10.0.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/admin/main.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/xheditor/xheditor-1.1.14-zh-cn.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/uploadify/uploadify.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/dwrService.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/admin/topicAdd.js"></script>

</head>
<body class="no-skin">
<input type="hidden" id="sid" value="<%=session.getId()%>"/>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;background:#eee;z-index: 999;border:1px solid #999">
	<ul id="mytree" class="ztree" style="margin-top:0;"></ul>
</div>
<input type="hidden" id="ctx" value="<%=request.getContextPath() %>"/>

	<div class="main-content-inner">
		<!-- #section:basics/content.breadcrumbs -->
		<div class="breadcrumbs" id="breadcrumbs">

			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="#">用户管理</a>
				</li>
				<li class="active">添加文章</li>
			</ul><!-- /.breadcrumb -->			
		</div>
		
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<sf:form method="post" modelAttribute="topicDto" id="addForm" cssClass="form-horizontal" role="form">
					<!-- #section:elements.form -->
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 文章标题: </label>
						<div class="col-sm-10">
							<sf:input path="title" size="30" cssClass="col-xs-10 col-sm-5"/>
							<sf:errors cssClass="errorContainer" path="title"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 文章栏目: </label>
						<div class="col-sm-10">
							<input type="text" readonly="readonly" name="cname" id="cname" />
							<input type="text" readonly="readonly" id="cid" name="cid" value="0" cssClass="col-xs-10 col-sm-5"/>
							<sf:errors cssClass="errorContainer" path="cid"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 文章关键字: </label>
						<div class="col-sm-10">
							<sf:input path="keyword" />
						</div>
					</div>

					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 发布时间: </label>
						<div class="col-sm-10">
							<sf:input path="publishDate"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 文章附件: </label>
						<div class="col-sm-10">
							<div id="attachs"></div>
							<input type="file" id="attach" name="attach"/>
							<button class="btn btn-app btn-purple btn-sm" id="uploadFile">
								<i class="ace-icon fa fa-cloud-upload bigger-200"></i>
								上传文件
							</button>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 已传附件: </label>
						<div class="col-sm-10" style="padding-top:10px;">
							<table id="ok_attach" width="890px" class="table table-striped table-bordered table-hover">
							<!-- <table id="ok_attach" width="890px" cellpadding="0" cellspacing="0"> -->
								<thead>
									<tr>
									<Td>文件名缩略图</Td>
									<td width="180">文件名</td>
									<td>文件大小</td>
									<td>主页图片</td>
									<td>栏目图片</td>
									<td>附件信息</td>
									<td width="160">操作</td>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 文章内容: </label>
						<div class="col-sm-10">
							<sf:textarea path="content" rows="25" cols="110"/>
						</div>
					</div>
					
					<c:choose>
						<c:when test="${isAudit||isAdmin }">
							<!-- /section:elements.form -->
							<div class="space-4"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-2">文章状态: </label>
		
								<div class="col-sm-10" style="padding-top:10px;">
									<sf:radiobutton path="status" value="0"/>未发布
									<sf:radiobutton path="status" value="1"/>已发布
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<sf:hidden path="status"/>
						</c:otherwise>
					</c:choose>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right" for="form-field-2">是否推荐该文章: </label>

						<div class="col-sm-10" style="padding-top:10px;">
							<sf:radiobutton path="recommend" value="0"/>不推荐
							<sf:radiobutton path="recommend" value="1"/>推荐
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 文章摘要: </label>
						<div class="col-sm-10">
							<sf:textarea path="summary" id="summary" cols="60" rows="4" style="overflow: hidden; overflow-y: hidden; word-wrap: break-word;"/>
						</div>
					</div>
					
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="button" id="addBtn">
								<i class="ace-icon fa fa-check bigger-110"></i>
								Submit
							</button>

							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset">
								<i class="ace-icon fa fa-undo bigger-110"></i>
								Reset
							</button>
						</div>
					</div>
				</sf:form>
				</div>
			</div><!-- /.row -->
		</div>
	</div>
<%@ include file="../common/common_js.jsp"%>

<script type="text/javascript">
$(function(){
	$("#addForm").cmsvalidate();
});
</script>

</body>
</html>