<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/view/common/context.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><spring:message code="blogworld"/>-<spring:message code="management"/></title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
	<script type="text/javascript">
		//点击菜单，打开页面，以tab形式打开。如果已经打开，则选中该tab
		function openMenu(url, title) {
			//链接开始加上当前上下文
			url = getContextPath() + url;
			
			if ($('#tabContainer').tabs('exists', title)) {
				$('#tabContainer').tabs('select', title);//选中页面
				//刷新页面
				var currTab = $('#tabContainer').tabs('getSelected');
				var url = $(currTab.panel('options').content).attr('src');
				if(url != undefined) {
					$('#tabContainer').tabs('update',{
						tab : currTab,
						options : {
							content : createFrame(title,url)
						}
					})
				}
			} else { 
				//注：使用iframe即可防止同一个页面出现js和css冲突的问题 
				$('#tabContainer').tabs('add',{
						title : title,
						closable : true,
						cache : false,
						content : createFrame(title,url)
					});
			}
			tabClose();
		}
		
		//判断前后的tab数，来隐藏或者显示tab右键选项
		function showTabMemu() {
			$("#mm-tabcloseother").show();
			$('#mm-tabcloseleft').show();
			$('#mm-tabcloseright').show();
			
			var prevall = $('.tabs-selected').prevAll();//之前的tab，需要排除欢迎页，该页面不关闭
			var nextall = $('.tabs-selected').nextAll();//之后的tab
			if (prevall.length-1 == 0 && nextall.length == 0) {
				$("#mm-tabcloseother").hide();//隐藏关闭其他选项
			}
			
			if (prevall.length-1 == 0) {
				$('#mm-tabcloseleft').hide();
			}
			
			if (nextall.length == 0) {
				$('#mm-tabcloseright').hide();
			}
		}
		
		//创建内嵌页面
		function createFrame(title,url) {
			var s = '<iframe name="'
				+ title
				+ '" id="'
				+ title
				+ '" src="'
				+ url
				+ '"width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>';
				
			return s;
		}
		
		//tab关闭
		function tabClose() {
			/*双击关闭TAB选项卡*/
			$(".tabs-inner").dblclick(function(){
				var subtitle = $(this).children(".tabs-closable").text();
				$('#tabContainer').tabs('close',subtitle);
			})
			
			/*为选项卡绑定右键*/
			$(".tabs-inner").bind('contextmenu',function(e){
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});

				var subtitle =$(this).children(".tabs-closable").text();

				$('#mm').data("currtab",subtitle);
				$('#tabContainer').tabs('select',subtitle);
				
				//在鼠标右键时，判断是否显示隐藏关闭左边、右边等按钮
				showTabMemu();
				
				return false;
			});
		}
		
		//绑定右键菜单事件
		function tabCloseEvent() {
			//刷新
			$('#mm-tabupdate').click(function(){
				var currTab = $('#tabContainer').tabs('getSelected');
				var url = $(currTab.panel('options').content).attr('src');
				var title = $(currTab.panel('options').content).attr('id');
				if(url != undefined) {
					$('#tabContainer').tabs('update',{
						tab : currTab,
						options : {
							content : createFrame(title,url)
						}
					})
				}
			})
			
			//关闭当前
			$('#mm-tabclose').click(function(){
				var currtab_title = $('#mm').data("currtab");
				$('#tabContainer').tabs('close',currtab_title);
			})
			
			//全部关闭
			$('#mm-tabcloseall').click(function(){
				$('.tabs-inner span').each(function(i,n){
					var t = $(n).text();
					if(t != '<spring:message code="welcome"/>') {
						$('#tabContainer').tabs('close',t);
					}
				});
			});
			
			//关闭除当前之外的TAB
			$('#mm-tabcloseother').click(function(){
				var prevall = $('.tabs-selected').prevAll();
				var nextall = $('.tabs-selected').nextAll();		
				if(prevall.length>0){
					prevall.each(function(i,n){
						var t=$('a:eq(0) span',$(n)).text();
						if(t != '<spring:message code="welcome"/>') {
							$('#tabContainer').tabs('close',t);
						}
					});
				}
				
				if(nextall.length>0) {
					nextall.each(function(i,n){
						var t=$('a:eq(0) span',$(n)).text();
						if(t != '<spring:message code="welcome"/>') {
							$('#tabContainer').tabs('close',t);
						}
					});
				}
				return false;
			});
			
			//关闭当前右侧的TAB
			$('#mm-tabcloseright').click(function(){
				var nextall = $('.tabs-selected').nextAll();
				if(nextall.length==0){
					alert('<spring:message code="theend"/>');
					return false;
				}
				nextall.each(function(i,n){
					var t=$('a:eq(0) span',$(n)).text();
					if(t != '<spring:message code="welcome"/>') {
						$('#tabContainer').tabs('close',t);
					}
				});
				return false;
			});
			
			//关闭当前左侧的TAB
			$('#mm-tabcloseleft').click(function(){
				var prevall = $('.tabs-selected').prevAll();
				if(prevall.length==0){
					alert('<spring:message code="theend"/>');
					return false;
				}
				prevall.each(function(i,n){
					var t=$('a:eq(0) span',$(n)).text();
					if(t != '<spring:message code="welcome"/>') {
						$('#tabContainer').tabs('close',t);
					}
				});
				return false;
			});
		}
		
	    //语言切换
	    //调用控制器，修改session中存放的语种，重新刷新页面，回到起始页
	    //各个页面加载语言根据session中记录语种
		function changeLang(lang) {
 			location.href = "${ctxPath}/Global/changeLang.do?from=admin&langType="+lang;
 			$.removeCookie('easyuiTheme');
		}
		
		$(document).ready(function() {
			//启动后，加载欢迎页面
			openMenu('/admin/static/index.do','<spring:message code="welcome"/>')
			
			tabCloseEvent();
			
			//切换皮肤
			var themes = {
					'metro-blue' : '${jsPath}/jquery-easyui/themes/metro-blue/easyui.css',
					'metro-green' : '${jsPath}/jquery-easyui/themes/metro-green/easyui.css',
					'metro-red' : '${jsPath}/jquery-easyui/themes/metro-red/easyui.css'
				};

			$('#uiSkinNav').combobox({
				onSelect: function(rec){
					$.removeCookie('easyuiTheme');
					var theme = rec.value;
					$.cookie('easyuiTheme',theme);//主题记录在cookie中，其他新增加的页面读取cookie，加载样式。加载时判断为null,则加载默认为metro-blue
					
					$('#swicth-style').attr('href', themes[theme]);//当前页面换皮肤
					$('#swicth-style',$("iframe").contents()).attr('href', themes[theme]);//设置当前其他子页面主题
				}
			});
		});
	</script>
</head>
<body class="easyui-layout" style="text-align: left">
	<div region="north" border="false" style="background: #5590E8; text-align: center">
		<div id="header-inner">
			<table cellpadding="0" cellspacing="0" style="width: 100%;">
				<tr>
					<td style="height: 35px;width: 25%;">
						<span style="color: #EFD33E; font-size: 20px; font-weight: bold; text-decoration: none"><spring:message code="blogworld"/>-<spring:message code="management"/></span> 
						<span style="color: #fff; font-size: 16px;"><spring:message code="technology"/></span>
					</td>
					<td style="height: 35px;width: 30%;"></td>
					<td style="font-size: 14px;width: 60%;">
						<span style="color: #E6EAD5;"  id="currentuser"><spring:message code="welcomeyou"/>：${Current_User.userName}</span> 
						<a style="color: #fff; text-decoration: none" href="${ctxPath}/main/index.do"><spring:message code="index"/></a> 
						<a style="color: #fff; text-decoration: none" href="${ctxPath}/Login/logout.do"><spring:message code="logout"/></a>
						<span style="color: #E6EAD5;"><spring:message code="changeTheme"/>：</span>
						<select id="uiSkinNav" class="easyui-combobox" style="width:100px;">
        					<option value="metro-blue"><spring:message code="blue"/></option>
       				 		<option value="metro-green"><spring:message code="green"/></option>
        					<option value="metro-red"><spring:message code="red"/></option>
    					</select>
    				    <a style="color: #fff; text-decoration: none" href="#" onclick="changeLang('zh')" >简体中文</a> 
                        <a style="color: #fff; text-decoration: none" href="#" onclick="changeLang('en')" >English</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div  region="west" split="true" title="<spring:message code="menu"/>" style="width: 150px; padding: 5px;">
		<ul id="menus" class="easyui-tree">
			<c:forEach items="${Current_MenuList}" var="menu">
				<li iconCls="icon-base"><span>${menu.root.menuName}</span>
					<c:if test="${!(empty menu.children)}">
						<ul>
							<c:forEach items="${menu.children}" var="submenu">
					      		<li iconCls="icon-gears"><a class="e-link" href="#" onclick="openMenu('${submenu.root.uRL}','${submenu.root.menuName}')">${submenu.root.menuName}</a></li>
					        </c:forEach>
					    </ul>
				    </c:if>
			    </li>
			</c:forEach>
		</ul>
	</div>
	<div region="center">
		<div id="tabContainer" class="easyui-tabs" fit="true" border="false" plain="true">
			<!-- 这里是tab页签 -->
		</div>
	</div>
	<div region="south" style="overflow: hidden;">
		<table style="font-size: 12px; width: 100%;">
			<tr>
				<td style="width: 50%;"><span id="status"><spring:message code="prepared"/></span></td>
				<td style="width: 50%;"><span style="width: 100px; margin: 0 auto 0 auto;"><spring:message code="rightAll"/>：Tim</span></td>
			</tr>
		</table>
	</div>
	
	<div id="mm" class="easyui-menu cs-tab-menu">
		<div id="mm-tabupdate"><spring:message code="refresh"/></div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose"><spring:message code="close"/></div>
		<div id="mm-tabcloseother"><spring:message code="closeOthers"/></div>
		<div id="mm-tabcloseall"><spring:message code="closeAll"/></div>
		<div id="mm-tabcloseleft"><spring:message code="closeLeft"/></div>
		<div id="mm-tabcloseright"><spring:message code="closeRight"/></div>
	</div>
</body>
</html>