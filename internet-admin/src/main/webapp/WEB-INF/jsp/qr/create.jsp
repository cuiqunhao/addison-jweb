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
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="#">二维码生成</a>
				</li>
			</ul><!-- /.breadcrumb -->			
		</div>
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
					<!-- #section:elements.form -->
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">扫描的url: </label>
						<div class="col-sm-9">
							<input type="text" name="url" id="url" value="http://43.249.81.29:8080/index.html" size="50">
						</div>
					</div>
				</div>
				<div class="col-xs-12">	
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">二维码宽度: </label>
						<div class="col-sm-9">
							<input type="text" name="width" id="width" value="300" size="50">
						</div>
					</div>
				</div>
				<div class="col-xs-12">
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">二维码高度: </label>
						<div class="col-sm-9">
							<input type="text" name="height" id="height" value="300" size="50">
						</div>
					</div>
				</div>
				<div class="col-xs-12">	
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">二维码格式: </label>
						<div class="col-sm-9">
							<input type="text" name="suffix" id="suffix" size="50" value="gif">
						</div>
					</div>
				</div>
				<div class="col-xs-12">	
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">服务器返回的信息: </label>
						<div class="col-sm-9" id="result">
							
						</div>
					</div>
				</div>
				<div class="col-xs-12">	
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="button" onclick="createQR()">
								<i class="ace-icon fa fa-check bigger-110"></i>
								创建
							</button>

							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset">
								<i class="ace-icon fa fa-undo bigger-110"></i>
								重置
							</button>
						</div>
					</div>
				</div>
			</div><!-- /.row -->
		</div>
	</div>
<%@ include file="../common/common_js.jsp"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/json2.js"></script>
<script type="text/javascript">
$(function(){});

function createQR(){
	var url = $("#url").val();
	var width = $("#width").val();
	var height = $("#height").val();
	var suffix = $("#suffix").val();
	var json = {};
	json.url = url;
	json.width = width;
	json.height = height;
	json.suffix = suffix;
	json = JSON.stringify(json);
	$.ajax({ 
		type: "POST", 
		url: "http://localhost:8080/internet-rest/rest/qr/create", 
		contentType: "application/json; charset=utf-8", 
		dataType: "json", 
		data:  json,
		success: function(data){
			$("#result").html("扫描的URL：" + data.url + "</br>二维码宽度：" + data.weight + "</br>二维码高度：" + data.height + "</br>二维码格式：" + data.suffix + "</br><img src='http://localhost:8080/internet-rest/resources/upload/"+data.name+"'>");
		}
	}); 
}

</script>

</body>
</html>