<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath() %>/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet" />

<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/common/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/common/css/ace.min.css" />

<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/resources/bootstrap/js/bootstrap.min.js"></script>





<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/base/jquery.ui.all.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/validate/main.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/css/zTree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resources/js/core/jquery.cms.keywordinput.css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.keywordinput.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/core/jquery.cms.validate.js"></script>
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
<body>
<input type="hidden" id="sid" value="<%=session.getId()%>"/>
<div id="menuContent" class="menuContent" style="display:none; position: absolute;background:#eee;z-index: 999;border:1px solid #999">
	<ul id="mytree" class="ztree" style="margin-top:0;"></ul>
</div>
<div class="page-content" style="height:1300px;">
	<div class="row">
		<div class="span12">
			<div class="table-responsive">
				<input type="hidden" id="ctx" value="<%=request.getContextPath() %>"/>
				<sf:form method="post" modelAttribute="topicDto" id="addForm">
				<table width="100%" cellspacing="0" cellPadding="0" id="addTable" class="table table-bordered">
					<thead><tr><th colspan="2">编辑文章</th></tr></thead>
					<tr>
						<td width="15%">文章标题:</td>
						<td>
						<sf:input path="title" size="80"/><sf:errors cssClass="errorContainer" path="title"/></td>
					</tr>
					<tr>
						<td>文章摘要:</td>
						<td>
							<sf:textarea path="summary" rows="3" style="width:430px;" />
						</td>
					</tr>
					<tr>
						<td>文章栏目:</td>
						<td>
							<input type="text" readonly="readonly" name="cname" id="cname" value="${cname}"/>
							<input type="text" readonly="readonly" id="cid" name="cid" value="${topicDto.cid }"/><sf:errors cssClass="errorContainer" path="cid"/>
						</td>
					</tr>
					<c:choose>
						<c:when test="${isAudit||isAdmin }">
						<tr>
							<td>文章状态:</td>
							<td>
								<sf:radiobutton path="status" value="0"/>未发布
								<sf:radiobutton path="status" value="1"/>已发布
							</td>
						</tr>
						</c:when>
						<c:otherwise>
						<sf:hidden path="status"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<td>是否推荐该文章:</td>
						<td>
							<sf:radiobutton path="recommend" value="0"/>不推荐
							<sf:radiobutton path="recommend" value="1"/>推荐
						</td>
					</tr>
					<tr>
						<td>发布时间:</td>
						<td>
							<sf:input path="publishDate"/>
						</td>
					</tr>
					<tr>
						<td>文章关键字:</td>
						<td>
							<div id="keyword-exists">
								<c:forEach items="${keywords }" var="k">
									<span>${k }</span>
								</c:forEach>
							</div>
							<sf:input path="keyword"/>
						</td>
					</tr>
					<tr>
						<td>文章附件</td>
						<td>
							<div id="attachs"></div>
							<input type="file" id="attach" name="attach"/>
							<input type="button" id="uploadFile" value="上传文件"/>
						</td>
					</tr>
					<tr>
					<td colspan="2">已传附件</td>
					</tr>
					<tr>
					<td colspan="2">
					<table id="ok_attach" width="990" cellspacing="0" cellPadding="0" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
							<th>文件名缩略图</th>
							<th width="180">文件名</th>
							<th>文件大小</th>
							<th>主页图片</th>
							<th>栏目图片</th>
							<th>附件信息</th>
							<th width="160">操作</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${atts }" var="att">
							<tr>
								<td>
									<c:if test="${att.isImg eq 1 }">
										<img src="<%=request.getContextPath() %>/resources/upload/thumbnail/${att.newName}"/>
									</c:if>
									<c:if test="${att.isImg ne 1 }">
										普通类型附件
									</c:if>
								</td>
								<td>
									${att.oldName }
								</td>
								<td>
									${att.size/1024}K
								</td>
								<c:if test="${att.isImg eq 1 }">
									<td><input type='checkbox' value="${att.id }" name='indexPic' class='indexPic' <c:if test="${att.isIndexPic eq 1 }">checked="checked"</c:if>></td>
									<td><input type='radio' value="${att.id }" name='channelPicId' <c:if test="${att.id eq topicDto.channelPicId }"> checked="checked"</c:if>></td>
								</c:if>
								<c:if test="${att.isImg ne 1 }">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</c:if>
								<td><input type='checkbox' value="${att.id }" name='isAttach' class='isAttach' <c:if test="${att.isAttach eq 1 }">checked="checked"</c:if>></td>
								<td><a href='#' class='list_op insertAttach' title='${att.id}' isImg="${att.isImg }" 
									name="${att.newName }" oldName="${att.oldName }">插入附件</a>&nbsp;<a href='#' title="${att.id }" class='list_op deleteAttach delete'>删除附件</a></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					</td>
					</tr>
					<tr>
						<td colspan="2">文章内容</td>
					</tr>
					<tr>
						<td colspan="2">
						<sf:textarea path="content" rows="25" cols="110"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="centerTd">
							<input type="button" id="addBtn" value="更新文章" class="btn btn-primary"/>
							<input type="reset" class="btn btn-warning"/>
						</td>
					</tr>
				</table>
				</sf:form>
			</div>
		</div>
	</div>
</div>
</body>
</html>