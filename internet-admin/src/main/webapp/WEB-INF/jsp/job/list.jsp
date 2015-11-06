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
					<a href="#">定时调度平台</a>
				</li>
				<li class="active">任务列表</li>
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
										<th>任务id</th>
										<th>任务code</th>
										<th>任务类型</th>
										<th>任务名称</th>
										<th>任务CLASS</th>
										<th>任务状态</th>
										<th>备注信息</th>
										<th>创建时间</th>
										<th>修改时间</th>
										<th>操作</th>
									</tr>
								</thead>

								<tbody>
								<c:if test="${datas.total eq 0}">
									<tr align="center"><td colspan="10"><font color="red">没有找到相关记录</font></td></tr>
								</c:if>
								<c:forEach items="${datas.datas }" var="d">
									<tr id="id_${d.id }">
										<td>
											${d.id}
										</td>
										<td>
											${d.task_code}
										</td>
										<td>
											${d.task_type}
										</td>
										<td>
											${d.task_name}
										<td>
											${d.job_class }
										</td>
										<td class="hidden-480">
											<c:if test="${d.is_enable eq 1 }">启用</c:if>
											<c:if test="${d.is_enable eq 2 }"><span class="emp">停用</span></c:if>
										</td>
										<td>
											${d.task_remark }
										</td>
										<td>
											${d.createtime }
										</td>
										<td>
											${d.lastupdatetime }
										</td>
										<td>
											<a class="btn btn-xs btn-info" href="<%=request.getContextPath() %>/admin/job/update/${d.id}" title="编辑">
												<i class="ace-icon fa fa-pencil bigger-120"></i>
											</a>

											<a class="btn btn-xs btn-danger" href="<%=request.getContextPath() %>/admin/job/delete/${d.id}" title="删除">
												<i class="ace-icon fa fa-trash-o bigger-120"></i>
											</a>
										
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
							
							<div class="page-header position-relative">
								<table style="width: 100%;">
									<tbody>
										<tr>
											<td style="vertical-align: top;">
												<a href="<%=request.getContextPath() %>/admin/job/add" target="mainFrame" style="color:#FFF;text-decoration:none;" title="添加任务" class="btn btn-info fa">+</a>
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
								</table>
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