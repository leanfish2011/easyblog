<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/WEB-INF/view/common/context.jsp"%>
<html>
<head>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>欢迎</title>
	<%@include file="/WEB-INF/view/common/resinculde.jsp"%>
	<%@include file="/WEB-INF/view/common/adminResource.jsp"%>
</head>
<body>
	<div style="padding: 10px;">
		<span><spring:message code="beginDate"/>：</span> 
		<input id="txtStartDate" class="easyui-datebox" style="width: 100%;" data-options="formatter:myformatter,parser:myparser"></input> 
		<span><spring:message code="endDate"/>：</span> 
		<input id="txtEndDate" class="easyui-datebox" style="width: 100%;" data-options="formatter:myformatter,parser:myparser"></input>
		<script type="text/javascript">
			//设置日期返回格式
			function myformatter(date){
				var y = date.getFullYear();
				var m = date.getMonth()+1;
				var d = date.getDate();
				return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
			}
			
			function myparser(s){
				if (!s) return new Date();
				var ss = (s.split('-'));
				var y = parseInt(ss[0],10);
				var m = parseInt(ss[1],10);
				var d = parseInt(ss[2],10);
				if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
					return new Date(y,m-1,d);
				} else {
					return new Date();
				}
			}
	</script>
		<span><spring:message code="staticStyle"/>：</span> 
		<select id="styleStatistics" class="easyui-combobox" panelheight="auto"
			style="width: 100px;">
			<option value="0"><spring:message code="staticDay"/></option>
			<option value="1"><spring:message code="staticMonth"/></option>
			<option value="2"><spring:message code="staticYear"/></option>
		</select> 
		<a id="btnSum" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"><spring:message code="static"/></a>
		<div id="blogPost" style="height: 400px"></div>
	</div>
	<script src="${jsPath}/MyECharts/resource/ECharts/echarts.js" type="text/javascript"></script>
	<script src="${jsPath}/MyECharts/resource/MyECharts.js" type="text/javascript"></script>
	<script type="text/javascript">
		//生成统计图
		function getPost() {
			//作为外部页面的子页面，生成图后会刷掉时间值，这里如果值为空，则设置为默认值
			if ($('#txtStartDate').datebox('getValue') == "") {
				$("#txtStartDate").datebox("setValue", getStartDate());
			}

			if ($("#txtEndDate").datebox("getValue") == "") {
				$("#txtEndDate").datebox("setValue", getEndDate());
			}

			var param = {
				styleType : $('#styleStatistics').combobox('getValue'),
				startDate : $('#txtStartDate').datebox('getValue'),
				endDate : $('#txtEndDate').datebox('getValue')
			};

			$.ajax({
				type : 'POST',
				dataType : 'json',
				data : $.param(param),
				url : '${ctxPath}/admin/static/getBlogStatistics.do',
				success : function(data, textStatus) {
					var opt1 = MyECharts.ChartOptionTemplates.Bar('<spring:message code="blogCount"/>','<spring:message code="blogpage"/>', eval(data.data));
					MyECharts.RenderChart(opt1, 'blogPost');
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
					$.messager.alert('<spring:message code="error"/>', '<spring:message code="errorStatic"/>','error');
				}
			});
		}

		//设置默认查询时间段
		//mgetMonth(); 获取当前月份(0-11,0代表1月)，所以获取当前月份是myDate.getMonth()+1; 
		//TODO，这里有bug，算上个月有误
		function getStartDate() {
			var curr_time = new Date();
			var y = curr_time.getFullYear();
			var m = curr_time.getMonth();
			var d = curr_time.getDate();
			return y + '-' + (m < 10 ? ('0' + m) : m) + '-'+ (d < 10 ? ('0' + d) : d);
		}
		
		function getEndDate() {
			var curr_time = new Date();
			var y = curr_time.getFullYear();
			var m = curr_time.getMonth() + 1;
			var d = curr_time.getDate();
			return y + '-' + (m < 10 ? ('0' + m) : m) + '-'+ (d < 10 ? ('0' + d) : d);
		}

		$(document).ready(function() {
			$('#btnSum').live('click', function() {
				getPost();
			});

			$("#txtStartDate").datebox("setValue", getStartDate());
			$("#txtEndDate").datebox("setValue", getEndDate());

			getPost();
		});
	</script>
</body>
</html>