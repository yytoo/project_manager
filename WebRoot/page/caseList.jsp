<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix ="s" uri="/struts-tags"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'webList.jsp' starting page</title>
	<!-- <link href="css/table.css" rel="stylesheet" type="text/css"> -->
	<!--  <script src="js/j.js"></script> -->
	<link href="<%=request.getContextPath()%>/css/list.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/css/jquery-ui.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body onload="onload()">
  <form action="caseList" name="listForm" id="listForm" method="post">
 	 <div>
    		<span>类型</span>
    		<select id="tagId" name="tagId">
    			<option value="1">问题</option>
				<option value="2">需求</option>
				<option value="0" selected></option>
    		</select>
    		<span>平台</span>
    		<select id="platId" name="platId">
    			<option value="1">android</option>
				<option value="2">IOS</option>
				<option value="3">前台</option>
				<option value="4">后台</option>
				<option value="0" selected></option>
    		</select>
    		<span>时间段</span>   		
    		从<input type="text" name="timeBeg" id="timeBeg" value="">
    		到<input type="text" id="timeEnd" name="timeEnd" value="">
    		<span>进度</span>
    		<select name="schedule" id="schedule">
    			<option value="1">待确认</option>
				<option value="2">待测试</option>
				<option value="3">待修改</option>
				<option value="4">关闭</option>
				<option value="5">忽略</option>
    			<option value="0" selected></option>
    		</select>
    		<input type="button"  onclick="screenings()" value="筛选">
    </div>
    <table class="table">
 
    	<tr>
    		<th>编号</th>
    		<th>摘要</th>
    		<th>相关页面</th>
    		<th>类型</th>
    		<th>平台</th>
    		<th>状态</th>
    		<th>优先级</th>
    		<th>创建时间</th>
    	</tr>
		<s:iterator value="#request.lists" id="lists">
	    	<tr>
	    		<td class="id_td"><s:a href="casedetail?id=%{#lists.id}&pageIndex=%{#request.pageIndex}"><s:property value="#lists.id"/></s:a></td>
	    		<td class="id_td"><s:property value="#lists.summary"/></td>
	    		<td class="id_td"><s:property value="#lists.page"/></td>
	    		<td class="tag_td"><s:property value="#lists.tag"/></td>
	    		<td class="classify_td"><s:property value="#lists.platform"/></td>
	    		<td class="status_td" id="status_td"><s:property value="#lists.status"/></td>
	    		<td class="priority_td"><s:property value="#lists.priority"/></td>
	    		<td class="id_td"><s:property value="#lists.updateTime"/></td>
	    	</tr>
		</s:iterator>
    </table>
    <div class="buttons">
		<p hidden id="hiddenP">${request.pageIndex}</p>
		<input type="button" id="lastPage"  class="changePage" value="上一页" onclick="lastPage5()">&nbsp;&nbsp;
		 第<input type="text" id="pageIndex" name="pageIndex" value=${request.pageIndex}>页&nbsp;&nbsp;共<span id="pageCount">${request.pageCount}</span>页&nbsp;&nbsp;
		<input type="button" id="jump" class="changePage" onclick="pageJump()" value="跳转">
		<input type="button" id="nextPage" class="changePage" value="下一页" onclick="nextPage5()">
		<input type="button" onclick="window.location='casedetail?pageIndex=${pageIndex}'" value="新增">		
	</div>
	</form>
  </body>
  
  <script type="text/javascript">
	 	 $(function(){
	  		$('#timeBeg').datepicker({  
	  			dateFormat: "yy-mm-dd"
	  			 });
	  		$('#timeEnd').datepicker({  
	  			dateFormat: "yy-mm-dd"  
                });
	 	 });
		function onload(){
			var pageIndex=$("#hiddenP").html();
			var maxPage=$("#pageCount").html();
			if(pageIndex==1){
				$("#lastPage").removeAttr("onclick");
				$("#lastPage").css("background","#aaa");
			}
			if(pageIndex==maxPage){
				$("#nextPage").removeAttr("onclick");
				$("#nextPage").css("background","#aaa");
			}
			selected("${tagId}","tagId");
			selected("${platId}","platId");
			selected("${schedule}","schedule");
			$("#timeBeg").val("${timeBeg}");
			$("#timeEnd").val("${timeEnd}");
			$(".status_td").each(function(){
				var str1=$(this).text();
				var str2="关闭";
				var str3="忽略";
				str1=str1.replace(/^\s+|\s+$/g,"");
				if((str1 == str2) || (str1==str3)){
				$(this).parent().css("background","#d5d5d5");		
				}
			});
		}
		function lastPage5(){
			var lastIndex=parseInt($("#hiddenP").html())-1;
			 $("#pageIndex").val(lastIndex);
			 $("#listForm").submit();	
		}
		 function nextPage5(){
			var nextIndex=parseInt($("#hiddenP").html())+1;
			 $("#pageIndex").val(nextIndex);
			 $("#listForm").submit();
		}
		function pageJump(){
			$("#listForm").submit();
		} 
					    
		function screenings(){

			 $("#pageIndex").val("1");

			 $("#listForm").submit(); 
		}
		function selected(defaultSelected,id){			
			var selects = new Array();
			selects = document.getElementById(id).options;			
			for(var i=0; i<selects.length; i++){
				if(selects[i].value==defaultSelected){
					selects[i].selected=true;	
				}
			}
		}
		
  </script>
</html>
