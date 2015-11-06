<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/common_css.jsp"%>
</head>
<body class="no-skin">
	<div class="main-content-inner">
		<!-- #section:basics/content.breadcrumbs -->
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="#">系统设置</a>
				</li>
				<li class="active">网站信息管理</li>
			</ul>		
		</div>
		
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12 col-sm-9">
							<!-- #section:pages/profile.info -->
							<div class="profile-user-info profile-user-info-striped">
								<div class="profile-info-row">
									<div class="profile-info-name"> 系统名称 </div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${baseInfo.name }</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name"> 系统所在地址</div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${baseInfo.address }</span>
									</div>
								</div>
								
								<div class="profile-info-row">
									<div class="profile-info-name"> 邮政编码</div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${baseInfo.zipCode }</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name"> 联系电话</div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${baseInfo.phone }</span>
									</div>
								</div>
								
								<div class="profile-info-row">
									<div class="profile-info-name"> 企业联系邮箱</div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${baseInfo.email }</span>
									</div>
								</div>
								
								<div class="profile-info-row">
									<div class="profile-info-name"> 网站访问域名</div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${baseInfo.domainName }</span>
									</div>
								</div>
								
								<div class="profile-info-row">
									<div class="profile-info-name"> 网站备案号</div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${baseInfo.recordCode }</span>
									</div>
								</div>
								
								<div class="profile-info-row">
									<div class="profile-info-name"> 首页图片宽度</div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${baseInfo.indexPicWidth }</span>
									</div>
								</div>
								
								<div class="profile-info-row">
									<div class="profile-info-name"> 首页图片高度</div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${baseInfo.indexPicHeight }</span>
									</div>
								</div>
								
								<div class="profile-info-row">
									<div class="profile-info-name"> </div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">
											<a href="<%=request.getContextPath() %>/admin/system/baseinfo/update" style="color:#FFF;text-decoration:none;" class="btn btn-info fa fa-share" title="修改网站基本信息">修改网站基本信息</a>
										</span>
									</div>
								</div>
								
							</div>
						</div>
					</div><!-- /.row -->
				</div><!-- /.col -->
			</div><!-- /.row -->
		</div>
	</div>
<%@ include file="../common/common_js.jsp"%>

<script src="<%=request.getContextPath() %>/resources/ace/assets/js/jquery.dataTables.js"></script>
<script src="<%=request.getContextPath() %>/resources/ace/assets/js/jquery.dataTables.bootstrap.js"></script>

</body>
</html>