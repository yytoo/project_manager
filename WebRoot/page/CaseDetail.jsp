<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix ="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'detail.jsp' starting page</title>
    
   <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ajaxfileupload.js"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/myjs.js"></script>
    <link href="css/detail.css" rel="stylesheet" type="text/css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<%-- 	<script type="text/javascript">
	$(document).ready(function(){
    //解决file的change事件只能执行一次的问题
	    $(document).on('change','#upload',function(){
	        fileUpload();
	        
	    });


	</script> 
 --%>
  </head>
  
  <body onload="detailOnload()">
    <div>
	<input type="hidden" value = "<%=basePath%>" id = "basePath"/>
 	 <p class="head2">CaseDtail</p>
 		 <form action="updateCase" name="listForm" id="listForm" method="post">
 		  	<p>	 <input type="hidden" id="pageIndex" name="pageIndex"  value="${pageIndex}">
 		     <input type="hidden" id="hiddenId" name="id"  value="${caseItem.id}"></p>
	    	<table class="table detailtable" >
	    		<tbody>
	    			<tr>
	    				<th class="detalith">摘要</th>
	    				<td class="detailtd"><input type="text" name="summary" value="${caseItem.summary}" style="width:50%"> </td>    				
	    				<th class="detalith">类型</th>	   
	    				<td class="detailtd">
	    					<select  id="tagId" name="tagId">
				    			<option value="1">问题</option>
					    		<option value="2">需求</option>
		    				</select>  					
	    				</td>	
	    			</tr>
	    			<tr>
	    				<th>状态</th>
	    				<td>
	    					<select  id="statusId" name="statusId">
				    			<option value="1">待确认</option>
				    			<option value="2">待测试</option>
					    		<option value="3">待修改</option>
					    		<option value="4">关闭</option>
								<option value="5">忽略</option>
		    				</select>
							<%-- <s:if test="#request.caseItem.statusId==1">待确认</s:if>
	   			 	 		<s:elseif test="#request.caseItem.statusId==2">待测试</s:elseif> 
	   			 	 		<s:elseif test="#request.caseItem.statusId==3">待修改</s:elseif> 
	   			 	 		<s:elseif test="#request.caseItem.statusId==4">关闭</s:elseif>
	   			 	 		<s:elseif test="#request.caseItem.statusId==5">忽略</s:elseif>   --%>
						</td>
	    				<th>优先级</th>
	    				<td>
	    					<select  id="prioId" name="prioId">
				    			<option value="1">高</option>
					    		<option value="2">中</option>
					    		<option value="3">低</option>
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
	    				<td><input type="text" name="page" value="${caseItem.page}" style="width:50%"></td>							
	    			</tr>
	    			<tr>
	    				<th>描述</th>
	    				<td  colspan=3><textarea rows="5" cols="100" name="detail" style="width:100%">${caseItem.detail}</textarea></td>
	    			<tr>
	    				<th>备注</th>
	    				<td colspan=3><textarea rows="2" cols="100" name="about" style="width:100%">${caseItem.comment}</textarea></td>
	    			</tr>
	    			<tr>
	    				<th>测试环境</th>
	    				<td colspan=3><textarea rows="2" cols="100" name="environment" style="width:100%">${caseItem.environment}</textarea></td>
	    			</tr>
	    			<tr>
	    				<th>添加时间</th>
	    				<td>${caseItem.updateTime}</td>
	    				<th>人员</th>
	    				<td>
		    				${caseItem.userId}    					    				
	    				</td>    				
	    			</tr>	
	    			<tr>
	    			<td colspan="4">
	    					<%-- <s:iterator value="#request.picName" id="picNames">	    				
	    						<img id="img" alt="" src="<%=basePath%>PicFile/${picNames }" height="100px" weight="100px">
	    					</s:iterator> --%>
	    					<img id="img" alt="" src="<%=basePath%>PicFile/${picName}" height="100px" >
	    					<input name="pic" id="pic" value="${picName}" hidden/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<th>上传图片</th>
	    				
	    				<td colspan="3">    
	    					<input type="file" id="upload" name="upload" onchange="fileUpload('<%=basePath %>','1');">
	    				</td>
	    			</tr>
	    		</tbody>	
	    	
	    	</table>
	    	<input type="button" value="后退" onclick="back()">
    		<input type="submit" value="提交">
    	</form>

 	 </div>
 	 
 	<script type="text/javascript">
	    function back(){   
	    	window.history.back();
	    	//window.location.href("index.jsp");
	    	window.event.returnValue=false;
	    }
	   		function detailOnload(){
			//选择默认的tag 
			selected("${caseItem.tagId}","tagId");
			selected("${caseItem.statusId}","statusId");
			selected("${caseItem.prioId}","prioId");
			selected("${caseItem.platId}","platId");
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
