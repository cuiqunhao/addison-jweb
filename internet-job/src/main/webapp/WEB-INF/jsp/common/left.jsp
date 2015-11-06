<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar                  responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<ul class="nav nav-list">
					
					<c:if test="${isAdmin }">
					
					
						<li class="">
							<a href="#" class="dropdown-toggle">
								<i class="menu-icon fa 	fa-bolt"></i>
								<span class="menu-text">定时调度平台 </span>
	
								<b class="arrow fa fa-angle-down"></b>
							</a>
	
							<b class="arrow"></b>
	
							<ul class="submenu">
								<li class="">
							<a href="#" class="dropdown-toggle">
								<i class="menu-icon fa glyphicon-user fa-bell"></i>
								<span class="menu-text">
									定时调度
								</span>
	
								<b class="arrow fa fa-angle-down"></b>
							</a>
	
							<b class="arrow"></b>
	
							<ul class="submenu">
								<li class="">
									<a href="<%=request.getContextPath() %>/admin/job/jobs" target="mainFrame">
										<i class="menu-icon fa fa-caret-right"></i>
											任务管理
										<b class="arrow fa fa-angle-down"></b>
									</a>
								</li>
							</ul>
						</li>
					</c:if>
				</ul><!-- /.nav-list -->

				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>