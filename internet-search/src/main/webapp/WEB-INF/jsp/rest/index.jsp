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
		<!-- #section:basics/content.breadcrumbs -->
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="#">定制化服务</a>
				</li>
				<li class="active">REST服务测试</li>
			</ul><!-- /.breadcrumb -->			
		</div>
		
		<div class="page-content">
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<div class="profile-info-name"> Type </div>
					<div class="profile-info-value">
						<span class="editable editable-click">
							<select style="width:200px;" id="type">
					   			<option value="GET">GET</option>
								<option value="POST">POST</option>
								<option value="PUT">PUT</option>
								<option value="DELETE">DELETE</option>
							</select>
						</span>
					</div>
				</div>
				
				<div class="profile-info-row" id="json">
					<div class="profile-info-name"> Rest json </div>
					<div class="profile-info-value">
						<span class="editable editable-click">
							<textarea name="test" rows="7" id="restJson" style="width:300px;"></textarea>
						</span>
						<br>
						<span id="params">
							调用实例：</br>
							var com = {};</br>
							rest.ti = com;
						</span>
						
					</div>
					
				</div>
				
				<div class="profile-info-row">
					<div class="profile-info-name"> Rest path </div>
					<div class="profile-info-value">
						<span class="editable editable-click">
							<input id="restPath" class="input-xlarge" type="text">
					</div>
				</div>
				
				<div class="profile-info-row">
					<div class="profile-info-name">  </div>
					<div class="profile-info-value">
						<span class="editable editable-click">
							<input type="button" value="提交" id="rest" onclick="reqService()" class="btn btn-large btn-primary"/>
				  					<input type="button" value="清除" id="rest" onclick="clearResult()" class="btn btn-large"/>
						</span>
					</div>
				</div>
				
				<div class="profile-info-row">
					<div class="profile-info-name"> Result </div>
					<div class="profile-info-value">
						<span class="editable editable-click">
							<textarea name="test" cols="200" rows="18" id="restResult" style="width:700px;"></textarea>
						</span>
					</div>
				</div>

			</div>
		</div>
	</div>
	<%@ include file="../common/common_js.jsp"%>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/rest/resthelp.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/rest/json2.js"></script>
	<script type="text/javascript">
		
		$(function(){
			var type = ${type};
			if(type == 1){
				$("#restPath").val("commonlink/list/commonlinktype");
			}
			
			$("#json").hide();
			$("#type").change(function(){
				var selectVal = $(this).val();
				if('GET' != selectVal){
					$("#json").show();
					$("#restPath").val("");
				}else{
					$("#json").hide();
				}
			});
			
		});
		
		function reqService(){
			var restPath = $("#restPath").val()+"?data=" + new Date().getTime();
			var rest = new Rest(getRoot(restPath));
			var type = $("#type").val();
			var restJson = $("#restJson").val();
			eval(restJson);
			
			rest.request(type,rest.root,rest.ti);
		}
		
		function getRoot(restPath){
			return '<%=request.getContextPath() %>/rest/' + restPath;
		}
		
		function clearResult(){
			$("#restResult").val("");
		}
	</script>
</body>
</html>