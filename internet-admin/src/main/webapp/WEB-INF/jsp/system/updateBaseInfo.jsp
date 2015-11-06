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
		<!-- #section:basics/content.breadcrumbs -->
		<div class="breadcrumbs" id="breadcrumbs">
			<script type="text/javascript">
				try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
			</script>

			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="#">用户管理</a>
				</li>
				<li class="active">添加用户</li>
			</ul><!-- /.breadcrumb -->			
		</div>
		
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<sf:form method="post" modelAttribute="baseInfo" id="updateForm" cssClass="form-horizontal" role="form">
					<!-- #section:elements.form -->
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 网站名称: </label>
						<div class="col-sm-9">
							<sf:input path="name" size="30" cssClass="col-xs-10 col-sm-5" placeholder="用户名"/>
							<sf:errors cssClass="errorContainer" path="name"/>
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 网站所在地址: </label>
						<div class="col-sm-9">
							<sf:input path="address" size="30" cssClass="col-xs-10 col-sm-5" placeholder="显示名称"/>
							<sf:errors cssClass="errorContainer" path="address"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 邮政编码: </label>
						<div class="col-sm-9">
							<sf:input path="zipCode" size="30" cssClass="col-xs-10 col-sm-5" placeholder="显示名称"/>
							<sf:errors cssClass="errorContainer" path="zipCode"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 联系电话: </label>
						<div class="col-sm-9">
							<sf:input path="phone" size="30" cssClass="col-xs-10 col-sm-5" placeholder="显示名称"/>
							<sf:errors cssClass="errorContainer" path="phone"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 网站联系邮箱: </label>
						<div class="col-sm-9">
							<sf:input path="email" size="30" cssClass="col-xs-10 col-sm-5" placeholder="显示名称"/>
							<sf:errors cssClass="errorContainer" path="email"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 网站访问域名: </label>
						<div class="col-sm-9">
							<sf:input path="domainName" size="30" cssClass="col-xs-10 col-sm-5" placeholder="显示名称"/>
							<sf:errors cssClass="errorContainer" path="domainName"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 网站备案号: </label>
						<div class="col-sm-9">
							<sf:input path="recordCode" size="30" cssClass="col-xs-10 col-sm-5" placeholder="显示名称"/>
							<sf:errors cssClass="errorContainer" path="recordCode"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 首页图片宽度: </label>
						<div class="col-sm-9">
							<sf:input path="indexPicWidth" size="30" cssClass="col-xs-10 col-sm-5" placeholder="显示名称"/>
							<sf:errors cssClass="errorContainer" path="indexPicWidth"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">首页图片高度: </label>
						<div class="col-sm-9">
							<sf:input path="indexPicHeight" size="30" cssClass="col-xs-10 col-sm-5" placeholder="显示名称"/>
							<sf:errors cssClass="errorContainer" path="indexPicHeight"/>
						</div>
					</div>
					
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit">
								<i class="ace-icon fa fa-check bigger-110"></i>
								提交
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset">
								<i class="ace-icon fa fa-undo bigger-110"></i>
								重置
							</button>
						</div>
					</div>
				</sf:form>
				</div>
			</div><!-- /.row -->
		</div>
	</div>
<%@ include file="../common/common_js.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/validate/main.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.validate.js"></script>
<script type="text/javascript">
$(function(){
	$("#updateForm").cmsvalidate();
});
</script>

</body>
</html>