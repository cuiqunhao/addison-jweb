<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/common_css.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/ace/assets/css/jquery-ui.custom.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/ace/assets/css/jquery.gritter.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/ace/assets/css/select2.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/ace/assets/css/datepicker.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/ace/assets/css/bootstrap-editable.css" />
</head>
<body class="no-skin">
	<div class="main-content-inner">
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="#">用户管理</a>
				</li>
				<li class="active">个人设置</li>
			</ul><!-- /.breadcrumb -->			
		</div>
		
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<div class="clearfix">
						<div class="pull-left alert alert-success no-margin">
							<button class="close" type="button" data-dismiss="alert">
								<i class="ace-icon fa fa-times"></i>
							</button>

							<i class="ace-icon fa fa-umbrella bigger-120 blue"></i>
							点击图片，可以修改个人图片信息 ...
						</div>
					</div>
					<div class="hr dotted"></div>
					<div>
						<div class="user-profile row" id="user-profile-1">
							<div class="col-xs-12 col-sm-3 center">
								<div>
									<!-- #section:pages/profile.picture -->
									<span class="profile-picture">
										<img class="editable img-responsive editable-click editable-empty" id="avatar" alt="Alex's Avatar" src="<%=request.getContextPath() %>/resources/ace/assets/avatars/profile-pic.jpg">Empty
									</span>

									<!-- /section:pages/profile.picture -->
									<div class="space-4"></div>

									<div class="width-80 label label-info label-xlg arrowed-in arrowed-in-right">
										<div class="inline position-relative">
											<a class="user-title-label dropdown-toggle" href="#" data-toggle="dropdown">
												<i class="ace-icon fa fa-circle light-green"></i>
												&nbsp;
												<span class="white">${user.username }</span>
											</a>

											<ul class="align-left dropdown-menu dropdown-caret dropdown-lighter">
												<li class="dropdown-header"> 修改状态 </li>

												<li>
													<a href="#">
														<i class="ace-icon fa fa-circle green"></i>

														<span class="green">在线</span>
													</a>
												</li>

												<li>
													<a href="#">
														<i class="ace-icon fa fa-circle red"></i>

														<span class="red">忙碌</span>
													</a>
												</li>

												<li>
													<a href="#">
														<i class="ace-icon fa fa-circle grey"></i>

														<span class="grey">离开</span>
													</a>
												</li>
											</ul>
										</div>
									</div>
								</div>

								<div class="space-6"></div>

								<div class="profile-contact-info">
									<div class="profile-contact-links align-left">
										<a class="btn btn-link" href="#">
											<i class="ace-icon fa fa-envelope bigger-120 pink"></i>
											发送消息
										</a>
										
										<c:if test="${isAdmin}">
											<a href="update/${user.id }" class="btn btn-link">
												<i class="ace-icon fa fa-users bigger-120 pink"></i>									
												修改个(他)人信息
											</a>
										</c:if>
										<c:if test="${not isAdmin}">
											<a href="<%=request.getContextPath()%>/admin/user/updateSelf" class="btn btn-link">
												<i class="ace-icon fa fa-users bigger-120 pink"></i>
												修改个人信息
											</a>
										</c:if>
										
										<a class="btn btn-link" href="<%=request.getContextPath()%>/admin/user/updatePwd">
											<i class="ace-icon fa 	fa-lock bigger-120 pink"></i>
											修改密码
										</a>
									</div>
								</div>

								<!-- /section:pages/profile.contact -->
								<div class="hr hr12 dotted"></div>

								<!-- #section:custom/extra.grid -->
								<div class="clearfix">
									<div class="grid2">
										<span class="bigger-175 blue">25</span>

										<br>
										我的任务
									</div>

									<div class="grid2">
										<span class="bigger-175 blue">12</span>

										<br>
										我的消息
									</div>
								</div>

								<!-- /section:custom/extra.grid -->
								<div class="hr hr16 dotted"></div>
							</div>

							<div class="col-xs-12 col-sm-9">

								<!-- #section:pages/profile.info -->
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<div class="profile-info-name"> 用户名 </div>

										<div class="profile-info-value">
											<span class="editable editable-click" id="username" style="display: inline;">${user.username }</span>
										</div>
									</div>

									<div class="profile-info-row">
										<div class="profile-info-name"> 当前位置 </div>

										<div class="profile-info-value">
											<i class="fa fa-map-marker light-orange bigger-110"></i>
											<span class="editable editable-click" id="country" style="display: inline;">中国</span>
											<span class="editable editable-click" id="city">深圳</span>
										</div>
									</div>

									<div class="profile-info-row">
										<div class="profile-info-name"> 显示名称 </div>

										<div class="profile-info-value">
											<span class="editable editable-click" id="age" style="display: inline;">${user.nickname }</span>
										</div>
									</div>

									<div class="profile-info-row">
										<div class="profile-info-name"> 联系电话 </div>

										<div class="profile-info-value">
											<span class="editable editable-click" id="signup" style="display: inline;">${user.phone}</span>
										</div>
									</div>

									<div class="profile-info-row">
										<div class="profile-info-name"> 电子邮件 </div>

										<div class="profile-info-value">
											<span class="editable editable-click" id="login" style="display: inline;">${user.email }</span>
										</div>
									</div>

									<div class="profile-info-row">
										<div class="profile-info-name"> 状态 </div>

										<div class="profile-info-value">
											<span class="editable editable-click" id="about" style="display: inline;">
											<c:if test="${user.status eq 0 }">
													<span class="emp">停用</span>
												</c:if>
												<c:if test="${user.status eq 1 }">
													<span>启用</span>
												</c:if>
											</span>
										</div>
									</div>
									
									<div class="profile-info-row">
										<div class="profile-info-name"> 请加时间 </div>

										<div class="profile-info-value">
											<span class="editable editable-click" id="login" style="display: inline;">
												<fmt:formatDate value="${user.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
											</span>
										</div>
									</div>
									
									<div class="profile-info-row">
										<div class="profile-info-name"> 拥有角色 </div>

										<div class="profile-info-value">
											<span class="editable editable-click" id="login" style="display: inline;">
												<c:forEach items="${rs }" var="r">
													<c:if test="${isAdmin}">
														<a href="<%=request.getContextPath()%>/admin/role/${r.id}" class="list_op">
														[${r.name }]
														</a>&nbsp;
													</c:if>
													<c:if test="${not isAdmin}">
														[${r.name }]
													</c:if>	
												</c:forEach>								
											</span>
										</div>
									</div>
									
									<div class="profile-info-row">
										<div class="profile-info-name"> 拥有群组</div>

										<div class="profile-info-value">
											<span class="editable editable-click" id="login" style="display: inline;">
												<c:forEach items="${gs }" var="g">
													<c:if test="${isAdmin}">
														<a href="<%=request.getContextPath()%>/admin/group/${g.id}" class="list_op">[${g.name }]</a>&nbsp;
													</c:if>
													<c:if test="${not isAdmin}">
														[${g.name }]
													</c:if>
												</c:forEach>
											</span>
										</div>
									</div>
								</div>

								<!-- /section:pages/profile.info -->
											<div class="space-20"></div>

											<div class="widget-box transparent">
												<div class="widget-header widget-header-small">
													<h4 class="widget-title blue smaller">
														<i class="ace-icon fa fa-rss orange"></i>
														Recent Activities
													</h4>

													<div class="widget-toolbar action-buttons">
														<a href="#" data-action="reload">
															<i class="ace-icon fa fa-refresh blue"></i>
														</a>
&nbsp;
														<a class="pink" href="#">
															<i class="ace-icon fa fa-trash-o"></i>
														</a>
													</div>
												</div>

												<div class="widget-body">
													<div class="widget-main padding-8">
														<!-- #section:pages/profile.feed -->
											<div class="profile-feed ace-scroll" id="profile-feed-1" style="position: relative;"><div class="scroll-track scroll-active" style="height: 200px; display: block;"><div class="scroll-bar" style="top: 0px; height: 62px;"></div></div><div class="scroll-content" style="max-height: 200px;">
												<div class="profile-activity clearfix">
													<div>
														<img class="pull-left" alt="Alex Doe's avatar" src="<%=request.getContextPath() %>/resources/ace/assets/avatars/avatar5.png">
														<a class="user" href="#"> Alex Doe </a>
														changed his profile photo.
														<a href="#">Take a look</a>

														<div class="time">
															<i class="ace-icon fa fa-clock-o bigger-110"></i>
															an hour ago
														</div>
													</div>

													<div class="tools action-buttons">
														<a class="blue" href="#">
															<i class="ace-icon fa fa-pencil bigger-125"></i>
														</a>

														<a class="red" href="#">
															<i class="ace-icon fa fa-times bigger-125"></i>
														</a>
													</div>
												</div>

												<div class="profile-activity clearfix">
													<div>
														<img class="pull-left" alt="Susan Smith's avatar" src="<%=request.getContextPath() %>/resources/ace/assets/avatars/avatar1.png">
														<a class="user" href="#"> Susan Smith </a>

														is now friends with Alex Doe.
														<div class="time">
															<i class="ace-icon fa fa-clock-o bigger-110"></i>
															2 hours ago
														</div>
													</div>

													<div class="tools action-buttons">
														<a class="blue" href="#">
															<i class="ace-icon fa fa-pencil bigger-125"></i>
														</a>

														<a class="red" href="#">
															<i class="ace-icon fa fa-times bigger-125"></i>
														</a>
													</div>
												</div>

												<div class="profile-activity clearfix">
													<div>
														<i class="pull-left thumbicon fa fa-check btn-success no-hover"></i>
														<a class="user" href="#"> Alex Doe </a>
														joined
														<a href="#">Country Music</a>

														group.
														<div class="time">
															<i class="ace-icon fa fa-clock-o bigger-110"></i>
															5 hours ago
														</div>
													</div>

													<div class="tools action-buttons">
														<a class="blue" href="#">
															<i class="ace-icon fa fa-pencil bigger-125"></i>
														</a>

														<a class="red" href="#">
															<i class="ace-icon fa fa-times bigger-125"></i>
														</a>
													</div>
												</div>

												<div class="profile-activity clearfix">
													<div>
														<i class="pull-left thumbicon fa fa-picture-o btn-info no-hover"></i>
														<a class="user" href="#"> Alex Doe </a>
														uploaded a new photo.
														<a href="#">Take a look</a>

														<div class="time">
															<i class="ace-icon fa fa-clock-o bigger-110"></i>
															5 hours ago
														</div>
													</div>

													<div class="tools action-buttons">
														<a class="blue" href="#">
															<i class="ace-icon fa fa-pencil bigger-125"></i>
														</a>

														<a class="red" href="#">
															<i class="ace-icon fa fa-times bigger-125"></i>
														</a>
													</div>
												</div>

												<div class="profile-activity clearfix">
													<div>
														<img class="pull-left" alt="David Palms's avatar" src="<%=request.getContextPath() %>/resources/ace/assets/avatars/avatar4.png">
														<a class="user" href="#"> David Palms </a>

														left a comment on Alex's wall.
														<div class="time">
															<i class="ace-icon fa fa-clock-o bigger-110"></i>
															8 hours ago
														</div>
													</div>

													<div class="tools action-buttons">
														<a class="blue" href="#">
															<i class="ace-icon fa fa-pencil bigger-125"></i>
														</a>

														<a class="red" href="#">
															<i class="ace-icon fa fa-times bigger-125"></i>
														</a>
													</div>
												</div>

												<div class="profile-activity clearfix">
													<div>
														<i class="pull-left thumbicon fa fa-pencil-square-o btn-pink no-hover"></i>
														<a class="user" href="#"> Alex Doe </a>
														published a new blog post.
														<a href="#">Read now</a>

														<div class="time">
															<i class="ace-icon fa fa-clock-o bigger-110"></i>
															11 hours ago
														</div>
													</div>

													<div class="tools action-buttons">
														<a class="blue" href="#">
															<i class="ace-icon fa fa-pencil bigger-125"></i>
														</a>

														<a class="red" href="#">
															<i class="ace-icon fa fa-times bigger-125"></i>
														</a>
													</div>
												</div>

												<div class="profile-activity clearfix">
													<div>
														<img class="pull-left" alt="Alex Doe's avatar" src="<%=request.getContextPath() %>/resources/ace/assets/avatars/avatar5.png">
														<a class="user" href="#"> Alex Doe </a>

														upgraded his skills.
														<div class="time">
															<i class="ace-icon fa fa-clock-o bigger-110"></i>
															12 hours ago
														</div>
													</div>

													<div class="tools action-buttons">
														<a class="blue" href="#">
															<i class="ace-icon fa fa-pencil bigger-125"></i>
														</a>

														<a class="red" href="#">
															<i class="ace-icon fa fa-times bigger-125"></i>
														</a>
													</div>
												</div>

												<div class="profile-activity clearfix">
													<div>
														<i class="pull-left thumbicon fa fa-key btn-info no-hover"></i>
														<a class="user" href="#"> Alex Doe </a>

														logged in.
														<div class="time">
															<i class="ace-icon fa fa-clock-o bigger-110"></i>
															12 hours ago
														</div>
													</div>

													<div class="tools action-buttons">
														<a class="blue" href="#">
															<i class="ace-icon fa fa-pencil bigger-125"></i>
														</a>

														<a class="red" href="#">
															<i class="ace-icon fa fa-times bigger-125"></i>
														</a>
													</div>
												</div>

												<div class="profile-activity clearfix">
													<div>
														<i class="pull-left thumbicon fa fa-power-off btn-inverse no-hover"></i>
														<a class="user" href="#"> Alex Doe </a>

														logged out.
														<div class="time">
															<i class="ace-icon fa fa-clock-o bigger-110"></i>
															16 hours ago
														</div>
													</div>

													<div class="tools action-buttons">
														<a class="blue" href="#">
															<i class="ace-icon fa fa-pencil bigger-125"></i>
														</a>

														<a class="red" href="#">
															<i class="ace-icon fa fa-times bigger-125"></i>
														</a>
													</div>
												</div>

												<div class="profile-activity clearfix">
													<div>
														<i class="pull-left thumbicon fa fa-key btn-info no-hover"></i>
														<a class="user" href="#"> Alex Doe </a>

														logged in.
														<div class="time">
															<i class="ace-icon fa fa-clock-o bigger-110"></i>
															16 hours ago
														</div>
													</div>

													<div class="tools action-buttons">
														<a class="blue" href="#">
															<i class="ace-icon fa fa-pencil bigger-125"></i>
														</a>

														<a class="red" href="#">
															<i class="ace-icon fa fa-times bigger-125"></i>
														</a>
													</div>
												</div>
											</div></div>

											<!-- /section:pages/profile.feed -->
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div><!-- /.col -->
			</div><!-- /.row -->
		</div>
	</div>
<%@ include file="../common/common_js.jsp" %>

<script src="<%=request.getContextPath() %>/resources/ace/assets/js/jquery.dataTables.js"></script>
<script src="<%=request.getContextPath() %>/resources/ace/assets/js/jquery.dataTables.bootstrap.js"></script>

</body>
</html>