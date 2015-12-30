// JavaScript Document
$(function(){
	/*删除规格*/
	$('.del').on('tap',function(){
		$('.nonstock-del').hide();
		});
	/*添加规格*/
	$('.add').on('tap',function(){
		var addcontent=""
		addcontent+="<div class='nonstock-part3'>";
		addcontent+="<div class='item clearfix'>";
		addcontent+="<div class='half'>";
		addcontent+="<span>颜色：</span>";
		addcontent+="<div><em>黑色</em><ul><li>红色</li><li>蓝色</li></ul></div>";
		addcontent+="</div>";
		addcontent+="<div class='half'>";
		addcontent+="<span>尺码：</span>";
		addcontent+="<div><em>32码</em><ul><li>31码</li><li>32码</li></ul></div>";
		addcontent+="</div>";
		addcontent+="</div>";
		addcontent+="<div class='item clearfix'>";
		addcontent+="<div class='half'><span>销售价：</span><input type='number' value='13'/>元</div>";
		addcontent+="<div class='half'><span>市场价：</span><input type='number' value='33'>元</div>";
		addcontent+="</div>";
		addcontent+="<div class='item clearfix'>";
		addcontent+="<div class='half'><span>库存：</span><input type='number' value='12'/></div>";
		addcontent+="<div class='half'><a href='#' class='del'>删除规格</a></div>";
		addcontent+="</div>";
		addcontent+="</div>";
		$('.nonstock-part3').after(addcontent);
		});
		/*选择颜色*/
		$('.select-color').on('tap',function(){
			if($(this).find('ul').hasClass('hidden')){
				$(this).find('ul').removeClass('hidden');}
			else{
				  $(this).find('ul').addClass('hidden');
				}
				
			});
			$('.select-color').find('li').on('tap',function(){
					$(this).parent().siblings('em').html($(this).html());
					$(this).find('ul').addClass('hidden');
			});
			
			/*选择尺寸*/
			$('.select-size').on('tap',function(){
			if($(this).find('ul').hasClass('hidden')){
				$(this).find('ul').removeClass('hidden')}
			else{
				  $(this).find('ul').addClass('hidden');
				}
			});
			$('.select-size').find('li').on('tap',function(){
				$(this).parent().siblings('em').html($(this).html());
				$(this).find('ul').addClass('hidden');
				});
		/*点击编辑*/
		$('.edit').on('tap',function(){
			$(this).siblings('div').attr('contentEditable',true);
			});
	});
	