<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/common_css.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/admin/main.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/admin/inc.js"></script>
<script type="text/javascript">
$(function(){
	$("#search").click(function(event){
		var con = $("#con").val(); var cid = $("#cid").val();
		var href = window.location.href;
		var len = href.lastIndexOf("?");
		if(len>0) {
			href = href.substr(0,len);
		}
		window.location.href=href+"?con="+con+"&cid="+cid;
	});
})
</script>
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
					<a href="#">信息发布管理</a>
				</li>
				<li class="active">文章管理</li>
			</ul><!-- /.breadcrumb -->			
		</div>
		
		<div class="page-content">
			<div class="page-header">
				<span class="input-icon">
					<input name="con" id="con" value="${con}" class="nav-search-input" id="con" type="text" placeholder="Search ..." autocomplete="off">
					<i class="ace-icon fa fa-search nav-search-icon"></i>
				</span>
				<select name="cid" id="cid">
					<option value="0">选择栏目</option>
					<c:forEach items="${cs }" var="c">
						<c:if test="${c.id  eq cid}">
						<option value="${c.id }" selected="selected">${c.name }</option>
						</c:if>
						<c:if test="${c.id  ne cid}">
						<option value="${c.id }">${c.name }</option>
						</c:if>
					</c:forEach>
				</select>
				<input type="button" id="search" value="搜索文章" class="btn btn-info fa"/>
			</div>
			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<div class="row">
						<div class="col-xs-12">
							<table id="sample-table-1" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>文章标题</th>
										<th>文章作者</th>
										<th>是否推荐</th>
										<th>所属频道</th>
										<th>文章的状态</th>
										<th>操作</th>
									</tr>
								</thead>

								<tbody>
								<c:if test="${datas.total eq 0}">
									<tr align="center"><td colspan="6"><font color="red">没有找到相关记录</font></td></tr>
								</c:if>
								<c:forEach items="${datas.datas }" var="t">
									<tr>
										<td>
											<a href="javascript:openWin('<%=request.getContextPath() %>/admin/topic/${t.id }','showTopic')" class="list_link">${t.title }</a>
										</td>
										<td>${t.author}</td>
										<td class="hidden-480">
											<c:if test="${t.recommend eq 0 }">不推荐</c:if>
											<c:if test="${t.recommend eq 1 }">推荐</c:if>
										</td>

										<td class="hidden-480">
											${t.cname }
										</td>
										<td>
											<c:if test="${t.status eq 0 }">未发布&nbsp;<a href="changeStatus/${t.id }?status=${t.status}" class="list_op delete">发布</a></c:if>
											<c:if test="${t.status eq 1 }">已发布&nbsp;<a href="changeStatus/${t.id }?status=${t.status}" class="list_op delete">取消发布</a></c:if>
										</td>

										<td>
											<a class="btn btn-xs btn-info" href="update/${user.id }" title="编辑">
												<i class="ace-icon fa fa-pencil bigger-120"></i>
											</a>

											<a class="btn btn-xs btn-danger" href="delete/${t.id }?status=${t.status}" title="删除">
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
												<a href="<%=request.getContextPath() %>/admin/topic/add" target="mainFrame" style="color:#FFF;text-decoration:none;" title="添加文章" class="btn btn-info fa">+添加</a>
												<a href="<%=request.getContextPath() %>/admin/topic/audits" style="color:#FFF;text-decoration:none;" class="btn btn-info fa fa-share" title="已发布列表">已发布</a>
												<a href="<%=request.getContextPath() %>/admin/topic/unaudits" style="color:#FFF;text-decoration:none;" class="btn btn-info fa fa-undo" title="未发布列表">未发布</a>
												<a href="<%=request.getContextPath() %>/admin/topic/audits" style="color:#FFF;text-decoration:none;" class="btn btn-info fa fa-refresh" title="刷新列表">刷新</a>
											</td>
											<td style="vertical-align: top;">
												<c:if test="${datas.total > 0}">
													<jsp:include page="/jsp/pager.jsp">
														<jsp:param value="${datas.total }" name="totalRecord"/>
														<jsp:param value="audits" name="url"/>
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
<script type="text/javascript">
$(function(){
	$("#search").click(function(event){
		alert(1);
		var con = $("#con").val(); var cid = $("#cid").val();
		var href = window.location.href;
		var len = href.lastIndexOf("?");
		if(len>0) {
			href = href.substr(0,len);
		}
		window.location.href=href+"?con="+con+"&cid="+cid;
	});
})
</script>
</body>
</html>