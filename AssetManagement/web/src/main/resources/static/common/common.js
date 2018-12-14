/*!
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 * 项目自定义的公共JavaScript，可覆盖jeesite.js里的方法
 */


var L = {}
// 判断浏览器类型
L.myBrowser = function(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("OPR") > -1; if (isOpera) { return "Opera" }; //判断是否Opera浏览器 OPR/43.0.2442.991

    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) { return "IE"; }; //判断是否IE浏览器 
    if (userAgent.indexOf("Firefox") > -1) { return "FF"; } //判断是否Firefox浏览器  Firefox/51.0
    if (userAgent.indexOf("Trident") > -1) { return "IE"; } //判断是否IE浏览器  Trident/7.0; rv:11.0
    if (userAgent.indexOf("Edge") > -1) { return "Edge"; } //判断是否Edge浏览器  Edge/14.14393
    if (userAgent.indexOf("Chrome") > -1) { return "Chrome"; }// Chrome/56.0.2924.87
    if (userAgent.indexOf("Safari") > -1) { return "Safari"; } //判断是否Safari浏览器 AppleWebKit/534.57.2 Version/5.1.7 Safari/534.57.2
}