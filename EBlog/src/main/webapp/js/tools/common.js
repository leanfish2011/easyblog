/**
 * 公共脚本
 */

/**
 * 获取当前上下文路径
 */
function getContextPath(){
	var localObj=window.location;
	var contextPath=localObj.pathname.split("/")[1];
	var basePath=localObj.protocol+"//"+localObj.host+"/"+contextPath;
	
	return basePath;
}