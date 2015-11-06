<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/common_css.jsp"%>
</head>
<body class="no-skin">
	<div class="main-content-inner">
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<!-- #section:pages/error -->
					<div class="error-container">
						<div class="well">
							<h1 class="grey lighter smaller">
								<span class="blue bigger-125">
									<i class="ace-icon fa fa-random"></i>
									提示：
								</span>
								发生了运行错误
							</h1>

							<hr />
							<h3 class="lighter smaller">
								但是网站
								<i class="ace-icon fa fa-wrench icon-animated-wrench bigger-125"></i>
								运行正常！
							</h3>

							<div class="space"></div>

							<div>
								<h4 class="lighter smaller">可能因为以下原因引起:</h4>

								<ul class="list-unstyled spaced inline bigger-110 margin-15">
									<li>
										<i class="ace-icon fa fa-hand-o-right blue"></i>
										<span style="color:red;">${exception.message }</span>
									</li>
								</ul>
							</div>

							<hr />
							<div class="space"></div>

							<div class="center">
								<a href="javascript:history.go(-1)" class="btn btn-grey">
									<i class="ace-icon fa fa-arrow-left"></i>
									返回上一页
								</a>

								<a href="#" class="btn btn-primary">
									<i class="ace-icon fa fa-desktop"></i>
									查看首页
								</a>
							</div>
						</div>
					</div>
				</div><!-- /.col -->
			</div><!-- /.row -->
		</div>
	</div>
<%@ include file="common/common_js.jsp"%>


</body>
</html>