<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/common_css.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/base/jquery.ui.all.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/admin/main.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/ui/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/ui/jquery.ui.sortable.js"></script>

<script type="text/javascript">
$(function(){
	if($("#refresh").val()=="1") {
		parent.refreshTree();
	}
	$(".listTable").mysorttable();
});
</script>
</head>
<body class="no-skin">
	<div class="main-content-inner">
		<div class="page-content">
			<div class="page-header">
				当前栏目
				<span class="label label-info arrowed-in arrowed">${pc.name }[${pc.id }]&nbsp;</span>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12">
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>栏目名称</th>
										<th>栏目类型</th>
										<th>是否推荐</th>
										<th>主页栏目</th>
										<th>栏目状态</th>
										<th>操作</th>
									</tr>
								</thead>

								<tbody>
								<c:if test="${datas.total eq 0}">
									<tr align="center"><td colspan="6"><font color="red">没有找到相关记录</font></td></tr>
								</c:if>
								<c:forEach items="${datas.datas }" var="c">
									<tr id="id_${c.id }">
										<td>
											<a href="<%=request.getContextPath() %>/admin/channel/channels/${c.id }" target="testIframe">${c.name }</a>
										</td>
										<td class="ctype">${c.type.name }</td>
										<td class="hidden-480">
											<c:if test="${c.recommend eq 0 }"><span class="emp">不推荐</span></c:if>
											<c:if test="${c.recommend eq 1 }">推荐</c:if>
										</td>
										<td class="hidden-480">
											<c:if test="${c.isIndex eq 0 }"><span class="emp">不是</span></c:if>
											<c:if test="${c.isIndex eq 1 }">是</c:if>
										</td>
										<td class="hidden-480">
											<c:if test="${c.status eq 0 }">启用</c:if>
											<c:if test="${c.status eq 1 }"><span class="emp">停用</span></c:if>
										</td>
										<td>
											
												<a class="btn btn-xs btn-info" href="<%=request.getContextPath() %>/admin/channel/update/${c.id}" title="编辑">
													<i class="ace-icon fa fa-pencil bigger-120"></i>
												</a>

												<a class="btn btn-xs btn-danger" href="<%=request.getContextPath() %>/admin/channel/delete/${pid}/${c.id}" title="删除">
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
												<a href="<%=request.getContextPath() %>/admin/channel/add/${pid}" style="color:#FFF;text-decoration:none;" class="btn btn-info fa">+添加子栏目</a>
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