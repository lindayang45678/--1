// JavaScript Document
//点击商品下架按钮-项文龙
shelves("$('.downshelves')","209");
//点击商品上架按钮-项文龙
shelves("$('.shelves')","208");
//商品上下架
function shelves(obj,opt){
	obj.live('tap',function() {
		var goodsId = $(this).parent().parent().attr('tgoodinfoid');
		var data = {"token":userToken,"mobile":mobile,"opt":opt,"goodsIdList[0]":goodsId};
		resultJson = ajaxCommon(urlUpShelf, data);
		if(resultJson._ReturnCode === returnCodeSuccess) {
			showAlertMsg(resultJson._ReturnMsg);
		} else {
			showAlertMsg(resultJson._ReturnMsg);
		}	
	});	
}
//删除已下架商品-项文龙
$('.del').live('tap',function() {
	var goodsId = $(this).parent().parent().attr('tgoodinfoid');
	var data = {"token":userToken,"tGoodsInfoId":goodsId};
	resultJson = ajaxCommon(urlDeleteGoods, data);
	if(resultJson._ReturnCode === returnCodeSuccess) {
		$(this).parent().parent().remove();
	} else {
		showAlertMsg(msgDeleteFail);
	}	
});

function mytab(n){
	for(i=1;i<=2;i++){
		document.getElementById('tab'+i).className='';
		document.getElementById('content'+i).className='incurtab';
	}
	document.getElementById('tab'+n).className='active';
	document.getElementById('content'+n).className='curtab';
	/*一级菜单左右滚动*/
	var liWidth = null;
	$("#sorts li").each(function(){
		liWidth += parseInt($(this).width());
	});
	$("#sorts .sorts-in").css("width", liWidth + 1 + "px");
	/*二级菜单左右滚动*/
	var twoWidth = null;
	$("#menu2 li").each(function(){
		twoWidth += parseInt($(this).width());
	});
	$("#menu2-list").css("width", twoWidth + 1 + "px");
	//调用iScroll
	$(function(){
		var LiobjScroll = new iScroll("sorts", {
			hScroll : true,
			vScroll :false
		});
		var menuScroll = new iScroll("menu2", {
			hScroll : true,
			vScroll : false
		});
	});
}


/*function mtab(n){
	for(i=1;i<=2;i++){
		document.getElementById('tab'+'-'+i).className='';
		document.getElementById('content'+'-'+i).className='incurtab-1';
	}
		document.getElementById('tab'+'-'+n).className='activer';
		document.getElementById('content'+'-'+n).className='curtab-1';
}*/

/*一级菜单选中*/
$(function(){
	$('.sorts-in').find('a').on('tap',function(){
		$('.sorts-in').find('a').removeClass('color');
		$(this).addClass('color');
	})
});
/*二级菜单选中*/
$(function(){
	$('#menu2-list').find('a').on('tap',function(){
		$('#menu2-list').find('a').removeClass('color');
		$(this).addClass('color');
	});
});


//

/*$(function(){
	//根据参数类型获取不同的URL查询地址0自营1平台信息
	var data = {
			"psam":psam,
	        "channelid":channelcodePF,
	        "virtualcatid":'all',
	        "searchparm":'',
	        "type":0,
	        "page":'1',
	        "pageSize":'10'
		
	};
	var resultJson = ajaxCommon(sideshopgoodsList,data);
	if(resultJson._ReturnCode === returnCodeSuccess){
		var _ReturnData = resultJson._ReturnData;
		alert(_ReturnData.length)
		if(_ReturnData.length > 0){
			shareFunction(_ReturnData);
		}
	}
	
});*/

//共用
function shareFunction(_ReturnData){
      var container = $('.curtab-1');
      var content = "";
		for(var i = 0; i < _ReturnData.length; i++){
			content +="<div class='goods-list' tgoodinfoid='"+ _ReturnData[i].tgoodinfoid +"'><dl><dt><img src='" +urlImage+ "/" +_ReturnData[i].tgoodinfoid+ "/" +imgw600+ "/" +_ReturnData[i].goodbigpic.split(";")[i]+ "'/></dt><dd><h3>'"+_ReturnData[i].goodname+"'</h3><p class='goods-time'><span>上架时间：'"+_ReturnData[i]+"'&nbsp;&nbsp;12:12</span></p><p class='goods-num'><span>库存：'"+_ReturnData[i].store+"'</span><span>销量：'"+_Return[i].soldstore+"'</span></p><p class='goods-price'><span>价格：¥'"+_ReturnData[i].promotionPrice+"'</span><span>市场价：¥'"+_ReturnData[i].saleprice_o2o+"'</span></p>" 
			content +="</dd></dl>";
			content +="<div><a href='#' class='downshelves'>"+下架+"</a></div>";
			content +="</div>"
		}
		container.append(content);
  }
//共用平台商品
function platformShop(_ReturnData){
	var container = $('.incurtab');
	var content = "";
	   for(var i = 0; i < _ReturnData.length; i++){
		   
		   content +="<div class='goods-list'><dl><dt><img src='../../images/goods-img.jpg' alt=''/></dt><dd>";
		   content +="<h3>【生鲜】'"+_ReturnData[i].goodname+"'</h3><p class='goods-time'><span>上架时间：2014-12-12&nbsp;&nbsp;12:12</span></p>";
		   content +="<p class='goods-num'><span>库存：'"+_ReturnData[i].store+"'</span><span>销量：'"+_ReturnData[i].soldstore+"'</span></p>";
		   content +="<p class='goods-price'><span>价格：¥'"+_ReturnData[i].promotionPrice+"'</span><span>市场价：¥'"+ReturnData[i].saleprice_o2o+"'</span></p>";
		   content +="<em>预期收益：满额按10%商品销售收益共10元</em></dd> </dl></div>";
	   }
         
      container.append(content);
}

/*上架商品*/
function mtab(id){
	//上架商品1下架商品==2
	var data = {
			"psam":psam,
	        "channelid":channelcodePF,
	        "virtualcatid":'all',
	        "searchparm":'',
	        "type":0,
	        "page":'1',
	        "pageSize":'10'
			
		};
		var resultJson = ajaxCommon(sideshopgoodsList,data);
		if(resultJson._ReturnCode === returnCodeSuccess){
			var _ReturnData = resultJson._ReturnData;
			if(_ReturnData.length > 0){
				shareFunction(_ReturnData);
			}
		}
	
}

//获取商品下架列表
function shareDownFunction(_ReturnData){
	 shareFunction(_ReturnData){
	      var container = $('.curtab-1');
	      var content = "";
	      for(var i = 0; i < _ReturnData.length; i++){
	    	  content +="	<div class='goods-list' tgoodinfoid='"+ _ReturnData[i].tgoodinfoid +"'><dl><dt><img src='../../images/goods-img.jpg' alt=''/></dt><dd><h3>【生鲜】信息水果</h3><p class='goods-time'><span>上架时间：2014-12-12&nbsp;&nbsp;12:12</span></p><p class='goods-num'><span>库存：300</span><span>销量：88</span></p>";
	    	  content +="<p class='goods-price'><span>价格：¥90</span><span>市场价：¥190</span></p></dd></dl><div><a href='#' class='del'>删除</a><a href='addgoods1.html' class='edit'>编辑</a><a href='#' class='shelves'>上架</a></div></div>" ;
	      }
	      container.append(content);	
	 } 
}

