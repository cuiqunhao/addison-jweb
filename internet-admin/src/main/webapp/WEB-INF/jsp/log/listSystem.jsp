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
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="#">日志管理</a>
				</li>
				<li class="active">系统操作日志</li>
			</ul><!-- /.breadcrumb -->			
		</div>
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12">
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>日期</th>
										<th>日志文件路径</th>
										<th>操作</th>
									</tr>
								</thead>

								<tbody>
								<c:if test="${empty logFileMap}">
									<tr align="center"><td colspan="6"><font color="red">没有找到相关记录</font></td></tr>
								</c:if>
								<c:forEach items="${logFileMap}" var="entry" varStatus="status">
									<tr>
										<td>${entry.value}</td>
										<td>${entry.key}</td>
										<td align="center">
										   <a href="<%=request.getContextPath() %>/admin/log/system/detail?logPath=${entry.key}" target="_blank">查看</a> | 
										   <a href="<%=request.getContextPath() %>/admin/log/system/download?logPath=${entry.key}">下载</a>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							
							<div class="page-header position-relative">
								<%-- <table style="width: 100%;">
									<tbody>
										<tr>
											<td style="vertical-align: top;">
												<a href="<%=request.getContextPath() %>/admin/dept/add/${pid}" target="mainFrame" style="color:#FFF;text-decoration:none;" title="添加用户" class="btn btn-info fa">+</a>
											</td>
											<td style="vertical-align: top;">
												<c:if test="${datas.total > 0}">
													<jsp:include page="/jsp/pager.jsp">
														<jsp:param value="${datas.total }" name="totalRecord"/>
														<jsp:param value="" name="url"/>
													</jsp:include>
												</c:if>
											</td>
										</tr>
									</tbody>
								</table> --%>
							</div>
						</div><!-- /.span -->
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