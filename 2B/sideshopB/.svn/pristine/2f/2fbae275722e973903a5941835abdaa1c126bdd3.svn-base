var page = 1;
var pageSize = 10;
var hasNextPage = true;
var zyorpt = '1';   //自营或平台类型，0-自营。1-平台
var container = $(".searchyes-list");
$("#loading").hide();
$('#searchtrue').hide();
$('#searchfalse').hide();
var searchparm = '';
var myScroll,
	pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;

//初始化iScroll控件 
function loaded() {
	pullUpEl = document.getElementById('loading');
    pullUpOffset = pullUpEl.offsetHeight;
	myScroll = new iScroll('search-srocll', {//iScroll的对象
		useTransition: false, 
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
			}
		},
		onScrollMove: function () {
			if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'loading';	
				getgoodlist();	// 执行加载后的自定义功能	
			}
		}
	});
	setTimeout(function () { document.getElementById('search-srocll').style.left = '0'; }, 800);
}

document.addEventListener('touchmove', function(e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', function() { setTimeout(loaded, 200); }, false);

$(function() {

	$("#search-srocll").css("height",windowHeight-160);
	
	$("#searchbtn").live("tap", function() {
		searchparm = '';
		searchparm = $('#searchparm').val();
		page = 1;
		//进行商品搜索
		getgoodlist();
	});
	
});


//取批发订单下各状态的订单列表
function getgoodlist(){
	$("#loading").show();
	var content ="";
	setTimeout(function () {
		if(hasNextPage){
			var data = {
					"psam":psam,
					"virtualcatid":'all',
					"channelid":channelcodePF,
					"searchparm":searchparm,
					"type":zyorpt,
					"page": page,
					"pageSize": 10
				};
			var resultJson = ajaxCommon(sideshopgoodsList,data);
			resultJson = convertNullToEmpty(resultJson);
			if(resultJson._ReturnCode === returnCodeSuccess){
				var _ReturnData = resultJson._ReturnData;
				var	list = _ReturnData.list;
				if (list.length>0) {
					var cnt = _ReturnData.count;
					for (var i = 0; i < list.length; i++) {
						//反馈搜索结果
						if(i == 0 && page == 1){
							
							$('#searchtrue').show();
							$('#searchfalse').hide();
							$('#searchtrueback').empty();
							$('#searchtrueback').append('搜索到和【'+searchparm+'】相关的商品，共<span class="color">'+cnt+'</span>个');
							$('#search-srocll').show();
						}
						$('#goods-search').attr('class','goods-search yesresult');
						
						
					    var tgoodinfoid = list[i].tgoodinfoid;
					    var goodbigpic = list[i].goodbigpic;
					    
						content += "            <dl> ";
						content += "            	<dt><img src='" + urlImage + "/" + tgoodinfoid + "/" + imgw100 + "/" + goodbigpic + "'></dt> ";
						content += "                <dd> ";
						content += "                	<h3>【<span class='color'>生鲜</span>】"+list[i].goodname+"</h3> ";
						content += "                    <p class='f-16'>销售价：<span class='color f-16'>¥"+list[i].saleprice_o2o+"</span></p> ";
						content += "                    <del>批发市场价："+list[i].marketprice+"</del> ";
						content += "                </dd> ";
						content += "            </dl> ";
					}
					container.append(content);
					myScroll.refresh();		
				}else if(page=1){
					//反馈搜索结果
					$("#loading").hide();	
					$('#searchtrue').hide();
					$('#searchfalse').show();
					//反馈搜索结果
					$('#searchfalseback').empty();
					$('#searchfalseback').append('没有搜索到和【'+searchparm+'】相关的商品');
					
					$('#search-srocll').hide();
					$('#goods-search').attr('class','goods-search noresult');
					
				}
				
			} else {
				hasNextPage = false;
				//showAlertMsg(resultJson._ReturnMsg);
				$("#loading").hide();	
				$('#searchtrue').hide();
				$('#searchfalse').show();
				//反馈搜索结果
				$('#searchfalseback').empty();
				$('#searchfalseback').append('没有搜索到和【'+searchparm+'】相关的商品');
				
				$('#search-srocll').hide();
				$('#goods-search').attr('class','goods-search noresult');
			}
			page ++;
		}
	},1000);	
	$("#loading").hide();	
}