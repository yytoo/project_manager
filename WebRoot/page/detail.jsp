<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'detail.jsp' starting page</title>
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
       <script type="text/javascript" src="<%=request.getContextPath()%>/js/myjs.js"></script>
    <link href="<%=request.getContextPath()%>/css/detail.css" rel="stylesheet" type="text/css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body onload="detailOnload()">
    <div>

 	 <p class="head2">detail</p>
 		 <form action="updateProject" name="listForm" id="listForm" method="post">
			<input type="hidden" id="pageIndex" name="pageIndex"  value="${pageIndex}">
 		     <input type="text" id="hiddenId" name="id" hidden value="${item.function_id}"></p>
	    	<table class="table detailtable" >
	    		<tbody>
	    			<tr>
	    				<th class="detalith">功能名</th>
	    				<td class="detailtd"><input type="text" name="funcName" value="${item.function_name}" style="width:50%"> </td>    				
	    				<th class="detalith">类型</th>	    
	    				<td class="detailtd">
	    					<select  id="typeId" name="typeId">
				    			<option value="1">新增</option>
					    		<option value="2">修改</option>
					    		<option value="3">删除</option>

		    				</select>	    				
	    				</td>	
	    			</tr>
	    			<tr>			
	    				<th>平台</th>
	    				<td>
	    					<select  id="platId" name="platId">
				    			<option value="1">android</option>
					    		<option value="2">IOS</option>
					    		<option value="3">前台</option>
								<option value="4">后台</option>
		    				</select>	    				
	    				</td>
	    				<th>相关页面</th>	 
	    				<td><input type="text" name="page" value="${item.page}" style="width:50%"></td> 				
	    			</tr>
	    		
	    			<tr>
	    				<th>需求讨论进度</th>
	    				<td><input type="text" name="demSch" value="${item.demand_schedule}">%</td>
	    				<th>设计进度</th>
	    				<td><input type="text" name="desSch" value="${item.design_schedule}">%</td>
	    			</tr>
	    			<tr>
	    				<th>实现进度</th>
	    				<td><input type="text" name="achSch" value="${item.achieve_schedule}">%</td>
	    				<th>测试修改进度</th>
	    				<td><input type="text" name="testSch" value="${item.test_schedule}">%</td>
	    			</tr>

	    			<tr>
	    				<th>功能描述</th>
	    				<td  colspan=3><textarea rows="5" cols="100" name="funcDes" style="width:100%">${item.function_describe}</textarea></td>
	    			<tr>
	    				<th>备注</th>
	    				<td colspan=3><textarea rows="2" cols="100" name="note" style="width:100%">${item.note}</textarea></td>
	    			</tr>
	    			<tr>
	    				<th>相关文档</th>
	    				<td><textarea rows="1" cols="100" name="about" style="width:100%">${item.about}</textarea></td>
	    				<th>进度</th>
	    				<td>
	    					<select  id="schedule" name="schedule">
				    			<option value="1">未开始</option>
	    						<option value="2">进行中</option>
	    						<option value="3">已结束</option>
		    				</select>	    				
	    				</td>
	    			</tr>
	    			<tr>
	    				<th>添加时间</th>
	    				<td>${item.date}</td>
	    				<th>优先级</th>
	    				<td>
		    				<select  id="prioId" name="prioId">
				    			<option value="1">高</option>
					    		<option value="2">中</option>
					    		<option value="3">低</option>
		    				</select>	    				
	    				</td>    				
	    			</tr>	
	    		</tbody>
	    		    			<tr>
	    				<td colspan="4" >
	    					<%-- <s:iterator value="#request.picName" id="picNames">	    				
	    						<img id="img" alt="" src="<%=basePath%>PicFile/${picNames }" height="100px" weight="100px">
	    					</s:iterator> --%>
	    					<img id="img" alt="" src="<%=basePath%>PicFile/${picName}" height="100px" weight="100px">
	    					<input name="pic" id="pic" value="${picName}" hidden>
	    				</td>
	    			</tr>
	    			<tr>
	    				<th>上传图片</th>
	    				
	    				<td colspan="3">    
	    					<input type="file" id="upload" name="upload" onchange="fileUpload('<%=basePath %>','2');">
	    				</td>
	    			</tr>
	    	</table>
	    	<input type="button" value="后退" onclick="back()">
    		<input type="submit" value="提交">
    	</form>
    	
 	 </div>
 	 
 	<script type="text/javascript">
 		function submitImg(){
 			
 		}
	    function back(){   
	    	window.history.back();
	    	//window.location.href("index.jsp");
	    	window.event.returnValue=false;
	    }
	    
		function detailOnload(){
			//选择默认的tag 
			selected("${item.prioId}","prioId");
			selected("${item.typeId}","typeId");
			selected("${item.platId}","platId");
			selected("${item.schedule}","schedule");
		}
	
		function selected(defaultSelected,id){
			
			var selects = new Array();
			selects = document.getElementById(id).options;
			
			for(var i=0; i<selects.length; i++){
		//		alert(defaultSelected);
				if(selects[i].value==defaultSelected){
					selects[i].selected=true;
					
				}
			}
		}
	</script>
  </body>
</html>
