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
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<sf:form method="post" modelAttribute="channel" id="addForm" cssClass="form-horizontal" role="form">
					<sf:hidden path="id"/>
					更新[${channel.name}]子栏目功能
					<!-- #section:elements.form -->
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 栏目名称: </label>
						<div class="col-sm-9">
							<sf:input path="name" size="50"/>
							<sf:errors cssClass="errorContainer" path="name"/>
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否指定链接: </label>
						<div class="col-sm-9" style="padding-top:10px;">
							<sf:radiobutton path="customLink" value="0"/>不指定
							<sf:radiobutton path="customLink" value="1"/>指定
						</div>
					</div>

					<!-- /section:elements.form -->
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-2">链接地址: </label>

						<div class="col-sm-9">
							<sf:input path="customLinkUrl" size="50"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-2">栏目类型: </label>

						<div class="col-sm-9" style="padding-top:5px;">
							<sf:select path="type">
								<sf:options items="${types}"/>
							</sf:select>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否在主页显示: </label>
						<div class="col-sm-9" style="padding-top:10px;">
							<sf:radiobutton path="isIndex" value="0"/>不显示
							<sf:radiobutton path="isIndex" value="1"/>显示
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 导航顶部栏目: </label>
						<div class="col-sm-9" style="padding-top:10px;">
							<sf:radiobutton path="isTopNav" value="0"/>不是
							<sf:radiobutton path="isTopNav" value="1"/>是
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 是否是推荐栏目: </label>
						<div class="col-sm-9" style="padding-top:10px;">
							<sf:radiobutton path="recommend" value="0"/>不是
							<sf:radiobutton path="recommend" value="1"/>是
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 状态: </label>
						<div class="col-sm-9" style="padding-top:10px;">
							<sf:radiobutton path="status" value="0"/>启用
							<sf:radiobutton path="status" value="1"/>停用
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 导航序号: </label>
						<div class="col-sm-9">
							<sf:input path="navOrder" size="50" cssClass="col-xs-10 col-sm-5"/>
						</div>
					</div>
					
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit">
								<i class="ace-icon fa fa-check bigger-110"></i>
								Submit
							</button>

							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset">
								<i class="ace-icon fa fa-undo bigger-110"></i>
								Reset
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
	$("#addForm").cmsvalidate();
});
</script>

</body>
</html>