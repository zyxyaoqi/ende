var Global_species = { 
    1: {"id": 1, "text": "绵羊", "mgltext": " "},
	2: {"id": 2, "text": "牛", "mgltext": " "},
	3 : {"id": 3, "text": "山羊", "mgltext": ""},
	4 : {"id": 4, "text": "猪", "mgltext": ""},
	5 : {"id": 5, "text": "鸡", "mgltext": " "}
}

var Global_milks = {
 	1: {"id": 1, "text": "白酥油", "mgltext": "   "},
	2: {"id": 2, "text": "查嘎", "mgltext": " "},
	3 : {"id": 3, "text": "达希玛", "mgltext": " "},
	4 : {"id": 4, "text": "马奶", "mgltext": " "},
	5 : {"id": 5, "text": "奶酪", "mgltext": " "},
	6: {"id": 6, "text": "奶皮", "mgltext": " "},
	7: {"id": 7, "text": "奶渣", "mgltext": " "},
	8 : {"id": 8, "text": "沙日素", "mgltext": " "},
	9 : {"id": 9, "text": "酥油", "mgltext": "   "},
	10 : {"id": 10, "text": "酸奶", "mgltext": " "},
	11 : {"id": 11, "text": "鲜奶", "mgltext": " "}


}
var Global_breed = [	
	  [{"id":1,"text":"细毛羊","mgltext":"  "},{"id":2,"text":"小尾寒羊","mgltext":" "},{"id":3,"text":"其他","mgltext":""}],
	  [{"id":1,"text":"土牛","mgltext":"  "},{"id":2,"text":"西门塔尔","mgltext":"   "},{"id":3,"text":"安格斯","mgltext":""},{"id":4,"text":"其他","mgltext":""}],
	  [{"id":1,"text":"绒山羊","mgltext":"  "},{"id":2,"text":"奶羊","mgltext":""},{"id":3,"text":"其他","mgltext":""}],
	  [{"id":1,"text":"猪","mgltext":""}],
	  [{"id":1,"text":"土鸡","mgltext":" "}]
	  ]
	  
var Global_type = [
		[{"id":1,"text":"羊羔","mgltext":""},{"id":2,"text":"公羊","mgltext":" 	"},{"id":3,"text":"母羊","mgltext":" "},{"id":4,"text":"淘汰羊","mgltext":" "},{"id":5,"text":"种羊","mgltext":""},{"id":6,"text":"其他","mgltext":""}],
		[{"id":1,"text":"牛犊","mgltext":""},{"id":2,"text":"公牛","mgltext":" "},{"id":3,"text":"母牛","mgltext":" "},{"id":4,"text":"奶牛","mgltext":""},{"id":5,"text":"种牛","mgltext":""},{"id":6,"text":"其他","mgltext":""}],
		[{"id":1,"text":"山羊羔","mgltext":""},{"id":2,"text":"公山羊","mgltext":" "},{"id":3,"text":"母山羊","mgltext":" "},{"id":4,"text":"淘汰山羊","mgltext":" "},{"id":5,"text":"种山羊","mgltext":" "},{"id":6,"text":"其他","mgltext":""}],
		[{"id":1,"text":"猪仔","mgltext":""},{"id":2,"text":"肉猪","mgltext":" "},{"id":3,"text":"其他","mgltext":""}],
		[{"id":1,"text":"鸡蛋","mgltext":" "},{"id":2,"text":"鸡肉","mgltext":""},{"id":3,"text":"其他","mgltext":""}]
	]  
	
var Global_feedtype = { 1: {id:"1", text:"散养", "mgltext":""},2: {id:"2", text:"圈养", "mgltext":""}}	
	
var Global_sourcetype = {
    1: {"id":"1", "text":"牛", "mgltext":""},
    2: {"id":"2", "text":"羊", "mgltext":""},
    3: {"id":"3", "text":"山羊", "mgltext":""},
    4: {"id":"4", "text":"其他","mgltext":""}
}
	
function initMeatSelectDom(breeDom, typeDom, sepeics) {
	for(var i =0 ; i< Global_breed[species-1].length; i++){
	    var e = Global_breed[species-1][i];	
		breeDom.append("<option value=" + e.id + " mgltext='" + e.mgltext +  "'>" + e.text+ "</option>"); 
	}
		
	for(var i =0 ; i< Global_type[species-1].length; i++){
	    var e = Global_type[species-1][i];	
		typeDom.append("<option value=" + e.id + " mgltext='" + e.mgltext +  "'>" +  e.text + "</option>"); 
	}
	
}

function initTitleDom(textDom, mglTextDom, id , meatOrMilk) {
    var txt;
    var mgltxt;
    if(meatOrMilk == 1){
    	txt = Global_species[id].text;
    	mgltxt = Global_species[id].mgltext;
    }
    else {
        txt = Global_milks[id].text;
    	mgltxt = Global_milks[id].mgltext;
    }   
	textDom[0].innerHTML = txt;
	mglTextDom[0].innerHTML = mgltxt;
}


function renderMeatList() {
    $('#meatdata').find(".meat_item_content").each(function(i){
	   var data =  $(this).get(0).innerHTML.split(",") 	;
	   var str = [];
	   //str.push(Global_species[data[0]].text);
	   str.push(Global_breed[data[0]-1][data[1]-1].text);
	   str.push(Global_type[data[0]-1][data[2]-1].text);
	   str.push(Global_feedtype[data[3]].text);
	   str.push("数量为" + data[4]);
	    $(this).get(0).innerHTML = str.join(",");		   
	});
	
	 $('#meatdata').find(".meat_item_content_mgl").each(function(i){
	   var data =  $(this).get(0).innerHTML.split(",") 	;
	   var str = [];
	   //str.push(Global_species[data[0]].mgltext);
	   str.push(Global_breed[data[0]-1][data[1]-1].mgltext);
	   str.push(Global_type[data[0]-1][data[2]-1].mgltext);
	   str.push(Global_feedtype[data[3]].mgltext);
	   str.push("" + data[4]);
	    $(this).get(0).innerHTML = str.join("<br>");		   
	});
}

function renderMilkList() {
    $('#milkdata').find('.milk_item_content').each(function(i){
	   var data =  $(this).get(0).innerHTML.split(",") 	;
	   var str = [];
	   str.push(Global_milks[data[0]].text);
	   str.push(Global_sourcetype[data[1]].text);
	   str.push("价格" + data[2]);
	   str.push("数量" + data[3]);
	    $(this).get(0).innerHTML = str.join(",");		   
	});
	
	 $('#milkdata').find('.milk_item_content_mgl').each(function(i){
	   var data =  $(this).get(0).innerHTML.split(",") 	;
	   var str = [];
	   str.push(Global_milks[data[0]].mgltext);
	   str.push(Global_sourcetype[data[1]].mgltext);
	   str.push("" + data[2]);
	   str.push("" + data[3]);
	   $(this).get(0).innerHTML = str.join("<br>");		   
	});
}

function renderSerchField(data, index, domname, inputName) {
	var str = [];	
	for(var i in data[index]){
		str.push("<input type='checkbox' name='"+ inputName +"' value='");
		str.push(data[index][i].id);
		str.push("'>");
		str.push("<label>");
		str.push(data[index][i].text);
		//str.push("<span class='mgl label-mgl'>");
		//str.push(data[index][i].mgltext);
		//str.push("</span>");
		str.push("</label>");
	}
	var dom1 = $("#medium-form").find("#"+domname)[0];
	var dom2 = $("#small-form").find("#"+domname)[0];
	
	dom1.innerHTML= dom1.innerHTML + str.join("");
	dom2.innerHTML= dom2.innerHTML + str.join("");
	
}
		
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
 
function resizeHeight() {
	if($("#off-canvas-content")[0].clientHeight < $("#rightMenu")[0].clientHeight){
	 $("#off-canvas-content")[0].style.height=$("#rightMenu")[0].offsetHeight+"px";
	}
	else{
	 //document.getElementById("mm1").style.height=document.getElementById("mm2").offsetHeight+"px";
	}
}	