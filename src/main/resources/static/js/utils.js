var Global_Tag_Position = false;
function showBigPic(uri) {
  //alert(uri)

}
//地理位置获取
function getLocation(){ 
   if(Global_Tag_Position) return;
   Global_Tag_Position = true;
   $("#localpic")[0].src = "/img/pload.gif";
   var geolocation = new BMap.Geolocation();
	geolocation.getCurrentPosition(function(r){
		if(this.getStatus() == BMAP_STATUS_SUCCESS){
			var geoc = new BMap.Geocoder();    
			//alert('您的位置：'+r.point.lng+','+r.point.lat);
			Global_Tag_Position = false;
			$("#lng")[0].value = r.point.lng;
			$("#lat")[0].value = r.point.lat;
			geoc.getLocation(r.point, function(rs){
				$("#address")[0].value = rs.address;
  				$("#localpic")[0].src = "/img/local1.jpg";
		});
		}
		else {
			alert('获取位置失败：'+this.getStatus());
			Global_Tag_Position = false;
			$("#localpic")[0].src = "/img/local1.jpg";
		}        
	},{enableHighAccuracy: true})
}
 
 //根据地名获取坐标
 function getPosition(obj) {
 	// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	// 将地址解析结果显示在地图上,并调整地图视野
	myGeo.getPoint(obj.value, function(point){
		if (point) {
			$("#lng")[0].value = point.lng;
			$("#lat")[0].value = point.lat; 
		}else{
		    alert('您填写的地名没有解析到对应的位置！');
			$("#lng")[0].value = 0;
			$("#lat")[0].value = 0;
		}
	}, "鄂尔多斯市");
 }
 
function ImgZoomIn(value) {
	var bgstr = '<div id="ImgZoomInBG" style=" background:#000000; filter:Alpha(Opacity=70); opacity:0.7; position:fixed; left:0; top:0; z-index:10000; width:100%; height:100%; display:none;"><iframe src="about:blank" frameborder="5px" scrolling="yes" style="width:100%; height:100%;"></iframe></div>';
	//alert($(this).attr('src'));
	imgstr = '<img id="ImgZoomInImage" src="' + value +'" onclick=$(\'#ImgZoomInImage\').hide();$(\'#ImgZoomInBG\').hide(); style="cursor:pointer; display:none; position:absolute; z-index:10001;" />';
	if ($('#ImgZoomInBG').length < 1) {
		$('body').append(bgstr);
	}
	if ($('#ImgZoomInImage').length < 1) {
		$('body').append(imgstr);
	}
	else {
		$('#ImgZoomInImage').attr('src',  value);
	}
	window.setTimeout(function(){
		//alert($(window).scrollLeft());
		//alert( $(window).scrollTop());
		//alert($(window).innerHeight());
		//alert($('#ImgZoomInImage').height())
		$('#ImgZoomInImage').css('left', $(window).scrollLeft() + ($(window).width() - $('#ImgZoomInImage').width()) / 2);
		//$('#ImgZoomInImage').css('left',0);
		$('#ImgZoomInImage').css('top', $(window).scrollTop() + ($(window).height() - $('#ImgZoomInImage').height()) / 2);
		$('#ImgZoomInBG').show();
		$('#ImgZoomInImage').show();
	},200);
};

//图片选择器
function picSelect(evt) {
	// 如果浏览器不支持FileReader
	if (!window.FileReader) return;
	var files = evt.target.files;
	for (var i = 0, f; f = files[i]; i++) {
		if (!f.type.match('image.*')) {
			continue;
		}
		var reader = new FileReader();
		reader.onload = (function(theFile) {
			return function(e) {
				// img 元素
				document.getElementById('previewImage').src = e.target.result;
				document.getElementById('previewImage').width = 300;
				document.getElementById('previewImage').height = 300;
			};

		})(f);
		reader.readAsDataURL(f);
	}
}


/** 
 * 给表单赋值
 * 必须是json格式的数据
 */
/*
 * jquery 初始化form插件，传入一个json对象，为form赋值 
 * version: 1.0.0-2013.06.24
 * @requires jQuery v1.5 or later
 * Copyright (c) 2013
 * note:  1、此方法能赋值一般所有表单，但考虑到checkbox的赋值难度，以及表单中很少用checkbox，这里不对checkbox赋值
 *		  2、此插件现在只接收json赋值，不考虑到其他的来源数据
 *		  3、对于特殊的textarea，比如CKEditor,kindeditor...，他们的赋值有提供不同的自带方法，这里不做统一，如果项目中有用到，不能正确赋值，请单独赋值
 */	
(function($){
	$.fn.extend({
		initForm:function(options){
			//默认参数
			var defaults = {
				jsonValue:"",
				isDebug:false	//是否需要调试，这个用于开发阶段，发布阶段请将设置为false，默认为false,true将会把name value打印出来
			}
			//设置参数
			var setting = $.extend({}, defaults, options);
			var form = this;
			jsonValue = setting.jsonValue;
			//如果传入的json字符串，将转为json对象
			if($.type(setting.jsonValue) === "string"){
				jsonValue = $.parseJSON(jsonValue);
			}
			//如果传入的json对象为空，则不做任何操作
			if(!$.isEmptyObject(jsonValue)){
				var debugInfo = "";
				$.each(jsonValue,function(key,value){
					//是否开启调试，开启将会把name value打印出来
					if(setting.isDebug){
						alert("name:"+key+"; value:"+value);
						debugInfo += "name:"+key+"; value:"+value+" || ";
					}
					var formField = form.find("[name='"+key+"']");
					if($.type(formField[0]) === "undefined"){
						if(setting.isDebug){
							alert("can not find name:["+key+"] in form!!!");	//没找到指定name的表单
						}
					} else {
						var fieldTagName = formField[0].tagName.toLowerCase();
						if(fieldTagName == "input"){
							if(formField.attr("type") == "radio"){
								$("input:radio[name='"+key+"'][value='"+value+"']").attr("checked","checked");
							} else {
								formField.val(value);
							}
						} else if(fieldTagName == "select"){
							//do something special
							formField.val(value);
						} else if(fieldTagName == "textarea"){
							//do something special
							formField.val(value);
						} else {
							formField.val(value);
						}
					}
				})
				if(setting.isDebug){
					alert(debugInfo);
				}
			}
			return form;	//返回对象，提供链式操作
		}
	});
})(jQuery)