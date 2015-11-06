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
			<div class="breadcrumbs" id="breadcrumbs">
				<ul class="breadcrumb">
					<li>
						<i class="ace-icon fa fa-home home-icon"></i>
						<a href="#">定时调度平台</a>
					</li>
					<li class="active">添加任务</li>
				</ul><!-- /.breadcrumb -->			
			</div>
			<div class="row">
				<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<sf:form method="post" modelAttribute="job" id="addForm" cssClass="form-horizontal" role="form">
					<!-- #section:elements.form -->
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">任务类型: </label>
						<div class="col-sm-9">
							<sf:select path="task_type" tbindex="1">
								<sf:option value="1">首页静态化</sf:option>
								<sf:option value="2">生成窗体</sf:option>
							</sf:select>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">任务名称: </label>
						<div class="col-sm-9">
							<sf:input path="task_name" size="30" cssClass="col-xs-10 col-sm-5" placeholder="任务名称" value="首页静态化"/>
							<sf:errors cssClass="errorContainer" path="task_name"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">job_class: </label>
						<div class="col-sm-9">
							<sf:input path="job_class" size="30" cssClass="col-xs-10 col-sm-5" placeholder="job_class" value="com.internet.cms.service.job.task.Index"/>
							<sf:errors cssClass="errorContainer" path="job_class"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">执行方式: </label>
						<div class="col-sm-9" >
							<sf:radiobutton path="execycle" value="1" onclick="selectExecycle(this)" cssStyle="padding-top:10px;" checked="checked" /> 执行周期
							<sf:radiobutton path="execycle" value="2" onclick="selectExecycle(this)" cssStyle="padding-top:10px;"/> cron表达式 
						</div>
					</div>
					
					<span id="intervalUnit" style="dispaly:block;"> 
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1">选择执行周期: </label>
							<div class="col-sm-9" >
								<span>
								 	<sf:select path="task_interval_unit" tbindex="1" onchange="selectUnit()">
										<sf:option value="1">分</sf:option>
										<sf:option value="2">时</sf:option>
										<sf:option value="3">日</sf:option>
										<sf:option value="4">周</sf:option>
										<sf:option value="5">月</sf:option>
									</sf:select>
								</span>
								
								<span id="intervalMinuteSpan" style="display:none;">
									<sf:input path="interval_minute" id="INTERVAL_MINUTE" style="width:35px;"/> 分
								</span>
								<span id="intervalHourSpan" style="display:none;">
									<sf:input path="interval_hour" id="INTERVAL_HOUR" style="width:35px;"/> 时
								</span>
								<span id="dayOfMonthSpan" style="display:none;">
									<sf:input path="day_of_month" id="DAY_OF_MONTH" style="width:35px;"/> 月
								</span>
								<span id="dayOfWeekSpan" style="display:none;">
									<sf:input path="day_of_week" id="DAY_OF_WEEK" style="width:35px;"/> 周
								</span>
								<span id="hourSpan" style="display:none;">
									<sf:input path="hour" id="HOUR" style="width:35px;"/> 时
								</span>
								<span id="minuteSpan" style="display:none;">
									<sf:input path="minute" id="MINUTE" style="width:35px;"/> 分
								</span>
							</div>
						</div>
					</span>
					
					<span id="cronExpressionSpan" style="display:none;">
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">表达式: </label>
						<div class="col-sm-9" >
							<span>
								<sf:input path="cron_expression"  id="CRON_EXPRESSION"/>
							</span>
						</div>
					</div>
					</span>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">请选择状态: </label>
						<div class="col-sm-9">
							<sf:select path="is_enable" tbindex="1">
								<sf:option value="1">启用</sf:option>
								<sf:option value="2">禁用</sf:option>
							</sf:select>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">备注: </label>
						<div class="col-sm-9" >
							<span>
								<sf:textarea path="task_remark" id="task_remark" cols="45" rows="4" style="overflow: hidden; overflow-y: hidden; word-wrap: break-word;"/>
								<sf:errors cssClass="errorContainer" path="task_remark"/>
							</span>
						</div>
					</div>
					
					
					
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit">
								<i class="ace-icon fa fa-check bigger-110"></i>
								提交
							</button>

							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset">
								<i class="ace-icon fa fa-undo bigger-110"></i>
								重置
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


	function selectUnit(){
		var intervalUnitValue=$("#task_interval_unit").val();
		controlInput(intervalUnitValue);
	}
	function controlInput(intervalUnitValue){
		$("span[id$='Span']").each(function(){
			$(this).hide();
		});
		$("input[id$='Input']").each(function(){
			$(this).prop("disabled","disabled");
		});
		if(intervalUnitValue==1){
			$("#intervalMinuteSpan").show();
			$("#intervalMinuteInput").prop("disabled","");
		}else if(intervalUnitValue==2){
			$("#intervalHourSpan").show();
			$("#intervalHourInput").prop("disabled","");
		}else if(intervalUnitValue==3){
			$("#hourSpan").show();
			$("#minuteSpan").show();
			$("#hourInput").prop("disabled","");
			$("#minuteInput").prop("disabled","");
		}else if(intervalUnitValue==4){
			$("#dayOfWeekSpan").show();
			$("#hourSpan").show();
			$("#minuteSpan").show();
			$("#dayOfWeekInput").prop("disabled","");
			$("#hourInput").prop("disabled","");
			$("#minuteInput").prop("disabled","");
		}else if(intervalUnitValue==5){
			$("#dayOfMonthSpan").show();
			$("#hourSpan").show();
			$("#minuteSpan").show();
			$("#dayOfMonthInput").prop("disabled","");
			$("#hourInput").prop("disabled","");
			$("#minuteInput").prop("disabled","");
		}
	}

	function selectExecycle(obj){
		
		var execycleValue = $(obj).val();
		if(execycleValue == 1){
			$("span[id$='Span']").each(function(){
				$(this).val("");
			});
			$("#intervalUnit").show();
			$("#intervalUnit").find("option[value='']").attr("selected",true);
			$("#cronExpressionSpan").hide();
			
		}else if(execycleValue == 2){
			$("span[id$='Span']").each(function(){
				$(this).hide();
				$(this).val("");
			});
			$("#intervalUnit").hide();
			$("#cronExpressionSpan").show();
		}
		
	}
</script>

</body>
</html>