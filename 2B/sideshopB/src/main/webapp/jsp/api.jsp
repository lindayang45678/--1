<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="../js/jquery-1.6.4.js"></script>
	<script src="../js/json.js"></script>
	<title>接口测试页面</title>
</head>
<body>

<div style="font-weight:600; font-size:15px; color:red;margin-top:20px;width:500px;position:absolute;">
    接口地址：<input id="url" value="/user/login" style="height:35px;width:350px;" /><br/><br/>
	接口参数：<textarea id="json" style="width:350px;height:300px;">{mobile:'12412312',pwd:'23233'}</textarea>
	<br/><br/>
	<p>
	   <center>
				<input type="button" onclick="test()" value="测  试" style="height:35px;width:80px;"/>
	   </center>
	</p>
</div>
<div id="returnMsg" style="word-wrap: break-word;width:480px;height:500px;margin-left:500px;margin-top:10px;position:absolute;border:1px solid #eee;background-color:#f7f7f7;overflow:auto;">
</div>

    
</body>
</html>
<script>
    function test(){
		var _url = $('#url').val();
		var _data = $('#json').val();
	    if(_url == ''){
		       return;
		}
		if(_data == ''){
			return;
		}
	    $.post(_url,eval("("+_data+")"),function(d,s){
			 if(s == 'success'){
			  //console.log(d);
		         $('#returnMsg').html(format(JSON.stringify(d,null,'\t')));
			 }else{
			     alert('请检查接口和参数是否正确！');
			 }
		});
	}

function format(txt,compress/*是否为压缩模式*/){/* 格式化JSON源码(对象转换为JSON文本) */
        var indentChar = '&nbsp;&nbsp;';   
        if(/^\s*$/.test(txt)){   
            alert('数据为空,无法格式化! ');   
            return;   
        }   
        try{var data=eval('('+txt+')');}   
        catch(e){   
            alert('数据源语法错误,格式化失败! 错误信息: '+e.description,'err');   
            return;   
        };   
        var draw=[],last=false,This=this,line=compress?'':'<br/>',nodeCount=0,maxDepth=0;   
           
        var notify=function(name,value,isLast,indent/*缩进*/,formObj){   
            nodeCount++;/*节点计数*/  
            for (var i=0,tab='&nbsp;';i<indent;i++ )tab+=indentChar;/* 缩进HTML */  
            tab=compress?'':tab;/*压缩模式忽略缩进*/  
            maxDepth=++indent;/*缩进递增并记录*/  
            if(value&&value.constructor==Array){/*处理数组*/  
                draw.push(tab+(formObj?('"'+name+'":'):'')+'['+line);/*缩进'[' 然后换行*/  
                for (var i=0;i<value.length;i++)   
                    notify(i,value[i],i==value.length-1,indent,false);   
                draw.push(tab+']'+(isLast?line:(','+line)));/*缩进']'换行,若非尾元素则添加逗号*/  
            }else   if(value&&typeof value=='object'){/*处理对象*/  
                    draw.push(tab+(formObj?('"'+name+'":'):'')+'{'+line);/*缩进'{' 然后换行*/  
                    var len=0,i=0;   
                    for(var key in value)len++;   
                    for(var key in value)notify(key,value[key],++i==len,indent,true);   
                    draw.push(tab+'}'+(isLast?line:(','+line)));/*缩进'}'换行,若非尾元素则添加逗号*/  
                }else{   
                        if(typeof value=='string')value='"'+value+'"';   
                        draw.push(tab+(formObj?('"'+name+'":'):'')+value+(isLast?'':',')+line);   
                };   
        };   
        var isLast=true,indent=0;   
        notify('',data,isLast,indent,false);   
        return draw.join(' ');   
    }  
</script>