/*
 *	分页
 */
//上一页
function previous() {
	var toRegionCode = '${toRegionCode}';
	var corpname = "${corpname}";
	var pageNumber = "${page.pageNumber-1}";
	window.location.href = "${BASE_PATH }front/line/listpage?pageNumber="
			+ pageNumber + "&toRegionCode=" + toRegionCode + "&corpname="
			+ encodeURI(encodeURI(corpname));
}
// 页码分页
function numbernext(index) {
	var toRegionCode = '${toRegionCode}';
	var corpname = "${corpname}";
	alert(index);
	window.location.href = "${BASE_PATH }front/line/listpage?pageNumber="
			+ index + "&toRegionCode=" + toRegionCode + "&corpname="
			+ encodeURI(encodeURI(corpname));
}
// 下一页
function next() {
	var toRegionCode = '${toRegionCode}';
	var corpname = "${corpname}";
	var pageNumber = "${page.pageNumber+1}";
	window.location.href = "${BASE_PATH }front/line/listpage?pageNumber="
			+ pageNumber + "&toRegionCode=" + toRegionCode + "&corpname="
			+ encodeURI(encodeURI(corpname));
}
// 输入页码跳转
function hrefnext() {
	var toRegionCode = '${toRegionCode}';
	var corpname = "${corpname}";
	var pageNumber = $("#num").val();
	window.location.href = "${BASE_PATH }front/line/listpage?pageNumber="
			+ pageNumber + "&toRegionCode=" + toRegionCode + "&corpname="
			+ encodeURI(encodeURI(corpname));
}
