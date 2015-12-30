<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
//清缓存
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
	<title>商品属性管理</title>
	<script type="text/javascript" src="js/jquery-1.6.4.js"></script>
	<script type="text/javascript" src="js/good/good.js"></script>
	<!-- plupload -->
	<script type="text/javascript" charset="utf-8" src="js/good/plupload-2.1.2/js/plupload.full.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="js/good/plupload-2.1.2/js/i18n/zh_CN.js"></script>
	
	<script>
		//页面加载，初始化页面
		var upImageUrl = "<%=path %>/goods/testimg";
		var s = "1";
		var scate = "1";
		var up;
		$(function (){
			//初始化文件上传插件
			up = initUploader();
			up.init();
			$("input[name='move']").change(function(){
				s = $("input:checked[name='move']").val();
				up.setOption("multipart_params", {ismove:s,cate:scate});
			});
			$("input[name='cate']").change(function(){
				scate = $("input:checked[name='cate']").val();
				up.setOption("multipart_params", {ismove:s,cate:scate});
			});
			
		});
		
		
	</script>
	<style type="text/css">
		body{
			font-size:13px
		}
		.divline {
			margin-bottom: 10px
		}
		
		.rtitle {
			font-weight: normal;
			padding-right: 10px
		}
		
		.ltitle {
			font-weight: normal;
			padding-right: 10px;
		}
		
		.bccolor {
			background-color: #F8F8F8;
			border: 1px solid #ECECEC;
		}
		
		.file-preview-frame {
			position:relative;
			display: table;
			margin: 8px;
			height: 90px;
			width: 90px;
			border: 1px solid #ddd;
			box-shadow: 1px 1px 5px 0px #a2958a;
			padding: 6px;
			float: left;
			text-align: center;
		}
		
		.file-preview {
			border-radius: 5px;
			border: 1px solid #ddd;
			padding: 3px;
			margin-bottom: 5px;
		}
	</style>
</head>
<body>
	<!-- 图片上传 -->
	<div id="imguploadpanel" class="easyui-tabs"
		style="width: 650px; height: 420px; margin-top: 20px; margin-left: 30px">
		<div style="margin-left: 20px">
			<input type="radio" name="move" id="id_move1" value="1" checked="checked"/><label for="id_move1">移动</label>
			<input type="radio" name="move" id="id_move0" value="0"/><label for="id_move0">不移动</label>
		</div>
		<div style="margin-left: 20px">
			<input type="radio" name="cate" id="id_cate1" value="1" checked="checked"/><label for="id_cate1">商品</label>
			<input type="radio" name="cate" id="id_cate5" value="5"/><label for="id_cate5">广告</label>
			<input type="radio" name="cate" id="id_cate6" value="6"/><label for="id_cate6">用户</label>
		</div>
		<div title="本地上传<font style='color:red'>（必填）</font>"
			style="padding: 10px">
			<div id="imgmain" style="width: 618px; height: 100px">
				<div id="filelist"></div>
				<label
					style="float: left; text-align: right; position: relative; margin-left: 20px">选择本地图片：</label>
				<a style="position: relative; margin-left: 10px" id="pickfiles"
					href="javascript:;" class="easyui-linkbutton">开始上传</a>
				<div style="color: #aaa; margin-left: 20px; margin-top: 15px;">
					<label style="float: left; text-align: right; width: 40px;">提示：</label>
					<div style="margin-left: 20px">
						<ol>
							<li>本地上传图片大小不能超过<strong style="color: #f60">6000kb</strong>。
							</li>
							<li>您最多可以上传<strong style="color: #f60"> 10 </strong>张图片。
						</ol>
					</div>
				</div>
			</div>
			<pre id="console"></pre>
			<div id="imglist" class="file-preview"
				style="width: 618px; height: 240px"></div>
			<input type="hidden" name="imgInfoList" id="id_imgInfoList" />
		</div>
	</div>
</body>
</html>