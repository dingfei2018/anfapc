fnResize()
window.onresize = function() {
	fnResize()
}

function fnResize() {
	var deviceWidth = document.documentElement.clientWidth || window.innerWidth
	if(deviceWidth >= 1200) {
		deviceWidth = 1200
	}
	if(deviceWidth <= 320) {
		deviceWidth = 320
	}
	document.documentElement.style.fontSize = (deviceWidth / 12) + 'px'
}

              //调用上传的js代码
            