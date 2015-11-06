<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@ include file="../common/common_css.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/admin/main.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/zTree/zTreeStyle.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/tree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/tree/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/dwrService.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/admin/setChannel.js"></script>

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
				<li class="active">用户组管理</li>
				<li class="active">设置管理栏目</li>
				<li>当前组(<font color="red">${group.name }</font>)</li>
			</ul>
		</div>
		
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<!-- #section:plugins/fuelux.treeview -->
					<div class="row">
						<div class="col-sm-6">
							<div class="widget-box widget-color-blue2">
								<div class="widget-header">
									<h4 class="widget-title lighter smaller">栏目树</h4>
								</div>

								<div class="widget-body">
									<div class="widget-main padding-8">
										<c:forEach items="${cids }" var="cid">
											<input type="hidden" name="cids" value="${cid }">
										</c:forEach>
										<input type="hidden" id="gid" value="${group.id }"/>
										<input type="hidden" id="treePath" value="<%=request.getContextPath()%>/admin/channel/treeAll"/>
										<div id="tree" class="ztree"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div><!-- /.col -->
			</div>
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