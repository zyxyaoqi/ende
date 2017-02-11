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