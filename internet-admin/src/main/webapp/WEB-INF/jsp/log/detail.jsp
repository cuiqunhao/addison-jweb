<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="java.io.File" %>
<%@ page import="org.apache.commons.io.FileUtils" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
<div>
 <%
   String logPath = (String)request.getAttribute("logPath");
   if(StringUtils.isNotEmpty(logPath)){
	    File file = new File(logPath);
	    List<String> list = FileUtils.readLines(file);
	    int i=1;
		for(String s : list){
			if(s!=null && !"".equals(s.trim())){
				out.print(i+"、   "+s+"<br/>");
				i++;
			}else{
				out.print("<br/>");
			}
		}
   }
   
   %>

</div>
</body>
</html>