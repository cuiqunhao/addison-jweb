<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<%@ include file="../common/common_css.jsp"%>
</head>
<body class="no-skin">

	<div class="main-content-inner">
		<div class="breadcrumbs" id="breadcrumbs">
			<ul class="breadcrumb">
				<li>
					<i class="ace-icon fa fa-home home-icon"></i>
					<a href="#">首页</a>
				</li>
				<li class="active">平台介绍</li>
			</ul><!-- /.breadcrumb -->
		</div>

		<!-- /section:basics/content.breadcrumbs -->
		<div class="page-content">

			<div class="row">
				<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
					<div class="alert alert-block alert-success">
						<button type="button" class="close" data-dismiss="alert">
							<i class="ace-icon fa fa-times"></i>
						</button>

						<i class="ace-icon fa fa-check green"></i>

						欢迎使用
						<strong class="green">
							66 Internet
							<small>(v1.1)</small>
						</strong>,Rest服务架构平台
					</div>
					
					<div class="alert alert-block alert-success">
						<div><strong class="green">1.1、	REST介绍</strong></div>
						<div>Representational State Transfer (REST) 是一种架构原则，其中将 web 服务视为资源，可以由其 URL 唯一标识。
							RESTful Web 服务的关键特点是明确使用 HTTP 方法来表示不同的操作的调用。
						</div>
						<div>REST 的基本设计原则对典型 CRUD 操作使用 HTTP 协议方法：</div>
						<div>POST - 创建资源</div>
						<div>GET - 检索资源</div>
						<div>PUT – 更新资源</div>
						<div>DELETE - 删除资源</div>
						<div>REST 服务的主要优势在于：</div>
						<div>它们是跨平台 (Java、.net、PHP 等）高度可重用的，因为它们都依赖基本 HTTP 协议。</div>
						<div>它们使用基本的 XML，而不是复杂的 SOAP XML，使用非常方便。</div>
						<div>基于 REST 的 web 服务日益成为后端企业服务集成的首选方法。与基于 SOAP 的 web 服务相比，它的编程模型简单，而本机 XML
						（而不是 SOAP ）的使用减少了序列化和反序列化过程的复杂性，并且不再需要其他作用相同的第三方库。
						</div>

						<div><strong class="green">1.2、	编写目的</strong></div>
						<div>编写本文的目的是为了将系统功能进行模块化、服务化，将用户的操作以服务的方式提供。系统与系统之间遵循服务规范，将系统与系统之间
							的交互转为定制化服务交互，以实现系统与系统之间的集成。
						</div>
						<div><strong class="green">1.3、	编写原则</strong></div>
						<div>可寻址性（Addressability）REST 中的所有东西都基于资源 的概念。资源与 OOP 中的对象或其他名词不同，它是一种抽象，必须
							可以通过 URI 寻址或访问。 
						</div>
						<div>接口一致性（Interface uniformity）与 SOAP 或其他标准不同，REST 要求用来操纵资源的方法或动词不是任意的。这意味着 
							RESTful 服务的开发人员只能使用 HTTP 支持的方法，比如 GET、PUT、POST、DELETE 等等。因此不需要使用 WSDL 等服务描述语言。
						</div> 
						<div>无状态（Statelessness）为了增强可伸缩性，服务器端不存储客户机的状态信息。这使服务器不与特定的客户机相绑定，负载平衡变得简单多
							了。这还让服务器更容易监视、更可靠。 
						</div>
						<div>具象（Representational）客户机总是与资源的某种具象交互，绝不会直接与资源本身交互。同一资源还可以有多个具象。理论上说，持有资
							源的具象的任何客户机应该有操纵底层资源的足够信息。 
						</div>
						<div>连通性（Connectedness）任何基于 REST 的系统都应该预见到客户机需要访问相关的资源，应该在返回的资源具象中包含这些资源。例如，可
							以以超链接的形式包含特定 RESTful 服务的操作序列中的相关步骤，让客户机可以根据需要访问它们。
						</div>
					</div>
					<!-- PAGE CONTENT ENDS -->
				</div><!-- /.col -->
			</div><!-- /.row -->
		</div><!-- /.page-content -->
	</div>
<!-- /.main-content -->
<%@ include file="../common/common_js.jsp"%>
</body>
</html>
