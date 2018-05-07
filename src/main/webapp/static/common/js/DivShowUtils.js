var DivUtils = {
	ShowDiv: function(show_div, title_div) {
		$("#"+show_div).find(".white_contents").remove();
		var html ="<div class='white_contents'>"+title_div+"<span class='white_contents-span' onclick=\"DivUtils.CloseDiv('"+show_div+"')\"></span></div>";	
		$("#" + show_div).prepend(html).before("<div id=\"fade\" class=\"black_overlay\"></div>");
		
		document.getElementById(show_div).style.display = 'block';
		document.getElementById("fade").style.display = 'block';
		var bgdiv = document.getElementById("fade");
		bgdiv.style.width = document.body.scrollWidth;
		$("#fade").height($(document).height());
	},
	CloseDiv: function(show_div) {
		document.getElementById(show_div).style.display = 'none';
		document.getElementById("fade").style.display = 'none';
	}

};
