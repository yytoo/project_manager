function fileUpload(aBasePath,aflag){
		var basePath=$('#basePath').attr("value");	
		var formId=	$('#hiddenId').attr("value");
			$.ajaxFileUpload({
				url:"uploadCaseImg",
				secureuri:"false",
				fileElementId:"upload",
				dataType:"json",
				data:{
                        flag:aflag,
                        formId:formId,
                        },
				success:function(data){				
				 	$('#img').attr('src',aBasePath+"PicFile/"+data.sPicPath); 
				 	$('#pic').val(data.sPicPath); 
				}
			});
		}