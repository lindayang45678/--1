/**
 * ============================================================================================================
 * 商品页面图片上传
 */
//初始化文件上传插件,依赖Jquery、easyui框架
var uploadedFiles = new Array();
var uploadingFilesCount = 0;
var closeFlag = true;//标记弹出框有没关闭, 默认关闭
function initUploader(){
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : $("#pickfiles")[0],
		container: 'imgmain', 
		url : upImageUrl,//上传请求
		max_file_size:'6000kb',//文件大小限制
		multi_selection:true,//是否可以多选
		max_file_count:10,//每次最多可上传文件数量
		multipart_params: {ismove: s,cate:scate},
		filters : {//过滤器
				mime_types : [ {
					title : "Image files",
					extensions : "jpg,gif,png"
				} ]
		},
		init: {
			PostInit: function() {
				//初始化图片列表
				if(uploadedFiles.length == 0){
					init();
				}
			},

			FilesAdded: function(up, files) {
				//获取待上传的文件数量,已上传文件之和
				uploadingFilesCount = files.length + uploadedFiles.length;
				
				//每个商品最多上传10个商品图片
				if(uploadedFiles.length >= 10 || files.length > 10 || (files.length + uploadedFiles.length) > 10){
					$.messager.alert("信息","您最多能上传10张商品图！","info"); 
					//清空当前缓存的文件
					$.each(files,function(i,file){
						uploader.removeFile(file);
					});

					return;
				}
				uploader.start();
//				return false;
			},

			UploadProgress: function(up, file) {
				//document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
			},
			
			FileUploaded:function(up, file, res){
				//解析后台返回值(图片id_图片URL_图片排序)，将其转换成json对象
				var jsonRes = $.parseJSON(res.response);
				
				//存放在缓存数组中
				$.each(jsonRes.SUCCESS,function (i, retData){
					uploadedFiles.push(retData + "_" + (uploadedFiles.length + 1));//初始化图片排序
				});
				//展示在页面上，隐藏上传的图片路径
				showPage();
			},

			Error: function(up, err) {
				
//				$.messager.alert("信息",err.code + ": " + err.message,"error"); 
				if(err.code == "-600" && closeFlag){
					closeFlag = false;
					$.messager.alert("信息","图片大小限50k以内","error",function(){
						closeFlag = true;
					}); 
				}
				if(err.code == "-601" && closeFlag){
					closeFlag = false;
					$.messager.alert("信息","请选择图片文件（jpg、gif、png）","error",function(){
						closeFlag = true;
					}); 
				}
				
			}
		}
	});
	return uploader;
}

//上传成功的图片，展示在页面上，隐藏上传的图片路径
function showPage(){
	//展示前，初始化容器
	init();
	var hiddenImgInfo = "";
	$.each(uploadedFiles,function (i, imgData){
		//解析返回的值
		var temp = imgData.split("_");
		//上传成功的图片展示在页面上
		$("#cc" + (i + 1) + " > a > img").attr("src", temp[1]);
		$("#cc" + (i + 1) + " > a").attr("href", temp[1]);
		//整理隐藏图片信息：图片id_图片排序;图片id_图片排序;...
		if(i == uploadedFiles.length - 1){
			hiddenImgInfo += (temp[0] + "_" + temp[2]);
		}else{
			hiddenImgInfo += (temp[0] + "_" + temp[2] + ";");
		}
	});
	//隐藏页面信息
	$("#id_imgInfoList").val(hiddenImgInfo);
}

//初始化
function init(){
	//初始化前，清空容器
	$("#imglist").empty();
	var imgList = "";
	for ( var i = 1; i <= 10; i++) {
				imgList += "<div id='cc" + i + "' class='file-preview-frame' onmouseout='toolHide(\"" + i + "\")' onmouseover='toolShow(\"" + i + "\")'>"
						+  "<a href='javascript:void(0)' target='_black'><img src='http://p1.vikecn.com/myfile/2012-2/25/s/135932961_2369018.jpg' onload='DrawImage(this,90,90)' /></a></div>";
	}
	$("#imglist").append(imgList);
}
	
//更新后台图片排序
function updateSort(a,b){
	var temp1 = uploadedFiles[a].split("_");
	var temp2 = uploadedFiles[b].split("_");
	uploadedFiles[a] = temp1[0] + "_" + temp1[1] + "_" + temp2[2];
	uploadedFiles[b] = temp2[0] + "_" + temp2[1] + "_" + temp1[2];
}

//显示工具栏
function toolShow(i){
	$("#tool" + i).css("display","block");
}
//隐藏工具栏
function toolHide(i){
	$("#tool" + i).css("display","none");
}

/**
 * 等比例缩放图片，参数(图片,允许的宽度,允许的高度)
 */
var flag=false;
function DrawImage(ImgD,iwidth,iheight){
    var image=new Image();
    image.src=ImgD.src;
    if(image.width>0 && image.height>0){
	    flag=true;
	    if(image.width/image.height>= iwidth/iheight){
	        if(image.width>iwidth){  
		        ImgD.width=iwidth;
		        ImgD.height=(image.height*iwidth)/image.width;
	        }else{
		        ImgD.width=image.width;  
		        ImgD.height=image.height;
	        }
	        ImgD.alt=image.width+"×"+image.height;
	    } else{
	        if(image.height>iheight){  
		        ImgD.height=iheight;
		        ImgD.width=(image.width*iheight)/image.height;        
	        }else{
		        ImgD.width=image.width;  
		        ImgD.height=image.height;
	        }
	        ImgD.alt=image.width+"×"+image.height;
	    }
	}
} 

