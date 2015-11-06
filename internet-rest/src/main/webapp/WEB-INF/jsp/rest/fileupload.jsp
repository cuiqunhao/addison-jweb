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
				<li class="active">文件上传</li>
			</ul><!-- /.breadcrumb -->			
		</div>
		
		<div class="page-content">
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<div class="profile-info-name"> 选择文件： </div>
					<div class="profile-info-value">
						<span class="editable editable-click">
							<iframe id='target_upload' name='target_upload' src='' style='display: none'></iframe>
							<form enctype="multipart/form-data" name="form" method="post" action="<%=request.getContextPath() %>/rest/fileupload/fileupload">
								<input name="importFile" type="file">
								<input id="submitButton" type="submit" value="上传" class="btn btn-large btn-primary"/>
							</form>
						</span>
					</div>
				</div>
				
				<div class="profile-info-row">
					<div class="profile-info-name"> 多线程下载  </div>

					<div class="profile-info-value">
						<span class="editable editable-click" style="color:red;">
							/**</br>
							 * 多线程文件下载</br>
							 * @param path 远程文件地址</br>
							 * @param size 线程数</br>
							 * @param localpath 文件下载目录</br>
							 * @return {"restule":"1"} 0:失败 1：成功</br>
							 * @throws IOException 异常</br>
							 * filedownload/filedownload?path=下载地址&size=线程数&localpath=文件下载目录 </br>
							 */</br>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../common/common_js.jsp"%>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-1.7.2.min.js"></script>
	
</body>
</html>