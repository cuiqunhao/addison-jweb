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
			<script type="text/javascript">
				try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
			</script>

			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="#">用户管理</a>
				</li>
				<li class="active">用户信息管理</li>
			</ul><!-- /.breadcrumb -->			
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
									<div class="profile-info-name"> 组名称</div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${group.name }</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name"> 组描述 </div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">${group.descr }</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name"> 组中的用户 </div>

									<div class="profile-info-value">
										<c:forEach items="${us }" var="u">
											<span class="editable editable-click" style="display: inline;">
												<a href="<%=request.getContextPath()%>/admin/user/${u.id}" class="list_op">[${u.nickname }]</a>
											</span>
										</c:forEach>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name"> </div>

									<div class="profile-info-value">
										<span class="editable editable-click" style="display: inline;">
											<a class="btn btn-primary no-radius" href="update/${group.id }">
												<i class="ace-icon fa fa-pencil-square-o"></i>
												修改用户组
											</a>
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