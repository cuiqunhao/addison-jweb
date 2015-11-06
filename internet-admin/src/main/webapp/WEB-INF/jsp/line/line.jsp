<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="<%=request.getContextPath() %>/resources/ace/assets/js/jquery.js"></script>
<style>
body{font-size:14px;}
h5{line-height:3em;padding:0;margin:0;}
a{color:#EE3399;}
a:hover{color:#99AA66;}
.txt{border:1px solid #ccc;background:none;padding:1px;}
.f-l{float:left;}
.t-c{text-align:center;}
.clear{clear:both;}
.hidden{display:none;}
.searchbox{border:4px solid #e5ecf9;height:40px;float:left;line-height:40px;padding:0 20px;margin:0 0 0 50px;}
.mainbox{margin:20px 0 0;}
.boxmap{width:720px;height:600px;border:1px solid gray;float:left;}
.boxpanel{width:250px;float:left;margin:0 0 0 10px;border:1px solid gray;padding:0 10px 10px;}
#startPanel,#endPanel{border:1px solid #FA8722;font-size:12px;}
#startPanel div,#endPanel div{padding:5px;}
#startPanel p,#endPanel p{margin:0;paddin:0;line-height:1.2em;}
#drivingPanel{border:1px solid #6688EE;}
</style>
<script src="http://api.map.baidu.com/api?v=1.4" type="text/javascript"></script>
</head>
<body>
	<div class="bodybox">
	    <div class="psearchhead">
	        从<input class="txt" type="text" value="北京" id="startInput"/>到<input class="txt" type="text" value="山东" id="endInput"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="驾车" onclick="mDriving()"/>
	    </div>
	</div>
	<div class="clear"></div>
	<div class="class="psearchhead">
	    <div class="boxmap" id="container"></div>
	    <div class="boxpanel hidden" id="box">
	        <h5>起点选择&nbsp;<a href="#" onclick="document.getElementById('startPanel').style.display='block';">(展开)</a></h5>
	        <div id="startPanel"></div>
	        <h5>终点选择&nbsp;<a href="#" onclick="document.getElementById('endPanel').style.display='block';">(展开)</a></h5>
	        <div id="endPanel"></div>
	        <h5>驾车导航</h5>
	        <div id="drivingPanel"></div>
	    </div>
	</div>


</body>
</html>
<script type="text/javascript">
var map =new BMap.Map("container");            // 创建Map实例
var point = new BMap.Point(120.2,30.25);    //创建点坐标
map.centerAndZoom(point,15);                     // 初始化地图,设置中心点坐标和地图级别。
debugger;
var startInfowin =new BMap.InfoWindow("<p class='t-c'><input value='选为起点' type='button' onclick='startDeter();' /></p>");
var endInfowin =new BMap.InfoWindow("<p class='t-c'><input value='选为终点' type='button' onclick='endDeter();' /></p>");

var startResults =null;
var endResults =null;

var startPoint;
var endPoint;

var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true, panel:'drivingPanel'}});



var startOption = {
  onSearchComplete: function(results){
    // 判断状态是否正确
if (startSearch.getStatus() == BMAP_STATUS_SUCCESS){
      startResults = results;
      var s = [];
      for (var i =0; i < results.getCurrentNumPois(); i ++){
        s.push("<div><p><a onmouseover='map.openInfoWindow(startInfowin,startResults.getPoi("+ i +").point);' href='#'>");
        s.push(results.getPoi(i).title);
        s.push("</a></p><p>");
        s.push(results.getPoi(i).address);
        s.push("</p></div>");
      }
      document.getElementById("startPanel").innerHTML = s.join("");
    }else{startResults =null;}
  }
};
var endOption = {
  onSearchComplete: function(results){
    // 判断状态是否正确
if (endSearch.getStatus() == BMAP_STATUS_SUCCESS){
      endResults = results;
      var s = [];
      for (var i =0; i < results.getCurrentNumPois(); i ++){
        s.push("<div><p><a href='#' onmouseover='map.openInfoWindow(endInfowin,endResults.getPoi("+ i +").point);'>");
        s.push(results.getPoi(i).title);
        s.push("</a></p><p>");
        s.push(results.getPoi(i).address);
        s.push("</p></div>");
      }
      document.getElementById("endPanel").innerHTML = s.join("");
    }else{endResults =null;}
  }
};
//创建2个搜索实例
var startSearch =new BMap.LocalSearch(map,startOption);
var endSearch =new BMap.LocalSearch(map,endOption);

function mDriving(){
    var startPlace = document.getElementById("startInput").value;
    var endPlace = document.getElementById("endInput").value;
    debugger;
    startSearch.search(startPlace);
    endSearch.search(endPlace);
    document.getElementById("box").style.display="block";
}

function startDeter(){
    map.clearOverlays();
    startPoint = startInfowin.getPosition();
    var marker =new BMap.Marker(startPoint);
    map.addOverlay(marker);
    document.getElementById("startPanel").style.display="none";
}
function endDeter(){    
    if(startPoint==null){alert("请先选择起点！");}
    else{
        endPoint = endInfowin.getPosition();        
        driving.search(startPoint,endPoint);
        document.getElementById("endPanel").style.display="none";
    }
}
</script>