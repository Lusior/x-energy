<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<section class="wrapper" style='padding-right: 0'>
	<div class="row state-overview col-lg-12" style='padding-right: 0'>
		<div class="wrap-all">
			<div class="col-lg-3 col-sm-6">
				<section class="panel">
					<div class="value">
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;换热站数量：<font color="#445CBB"><b>${stationSize}</b></font> 座</p></br>
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;换热站面积：<font color="#445CBB"><b>${standArea}</b></font> m<sup>2</sup></p></br>
						<p>&nbsp;</p></br>
						<h1 class="totalAmtYes"><b>换热站相关</b></h1>
					</div>
				</section>
			</div>
			<div class="col-lg-3 col-sm-6">
				<section class="panel">
					<div class="value">
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;换热站补水量：<font color="#445CBB"><b>${yesFt3q}</b></font> t</p></br>
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;换热站供电量：<font color="#445CBB"><b>${yesJqi}</b></font> kWh</p></br>
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;换热站供热量：<font color="#445CBB"><b>${yesQqi}</b></font> GJ</p></br>
						<h1 class="totalAmtYes"><b>昨日能耗量</b></h1>
					</div>
				</section>
			</div>
			<div class="col-lg-3 col-sm-6">
				<section class="panel">
					<div class="value">
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;换热站总水量：<font color="#445CBB"><b>${ft3q}</b></font> t</p></br>
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;换热站总电量：<font color="#445CBB"><b>${jqi}</b></font> kWh</p></br>
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;换热站总热量：<font color="#445CBB"><b>${qqi}</b></font> GJ</p></br>
						<h1 class="totalAmtYes"><b>累计耗能量</b></h1>
					</div>
				</section>
			</div>
			<div class="col-lg-3 col-sm-6">
				<section class="panel">
					<div class="value">
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;水单耗：<font color="#445CBB"><b><fmt:formatNumber type="number" value="${ft3q/1000/standArea}" pattern="0.0000" maxFractionDigits="4"/></b></font> KG/h<sup>2</sup></p></br>
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;电单耗：<font color="#445CBB"><b><fmt:formatNumber type="number" value="${jqi/standArea}" pattern="0.0000" maxFractionDigits="4"/></b></font> kWh/m<sup>2</sup></p></br>
						<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;热单耗：<font color="#445CBB"><b><fmt:formatNumber type="number" value="${qqi/36000000/standArea}" pattern="0.0000" maxFractionDigits="4"/></b></font> W/h<sup>2</sup></p></br><!-- 1GJ=3.6MW 把多少W算出来，除以面积就是热单耗了 -->
						<h1 class="totalAmtYes"><b>水电热单耗</b></h1>
					</div>
				</section>
			</div>
		</div>
		<div class="wrap-day chart-flow"  id="morris">
			<div class="col-lg-12">
				<section class="panel panel-info">
					<header class="panel-heading">
						<span><b>总览</b></span>
						<span style="float:right;">&nbsp;热&nbsp;</span>
						<span class="roundElement" style="background:red;float:right;width:30px;height:12px;margin-top:5px;">&nbsp;</span>
						<span style="float:right;">&nbsp;电&nbsp;</span>
						<span class="roundElement" style="background:#32CD32;float:right;width:30px;height:12px;margin-top:5px;">&nbsp;</span>
						<span style="float:right;">&nbsp;水&nbsp;</span>
						<span class="roundElement" style="background:blue;float:right;width:30px;height:12px;margin-top:5px;">&nbsp;</span>
					</header>
					<div class="panel-body">
						<div id="pieChart" style="width: 100%; height: 500px; margin: 0 auto"></div>
						<!--                         	<div id="legenddiv" style="border: 2px dotted #3f3; margin: 5px 0 20px 0;position: relative;"></div> -->
					</div>
				</section>
			</div>
		</div>
		<div class="wrap-day chart-flow"  id="morris">
			<div class="col-lg-12">
				<section class="panel panel-info">
					<header class="panel-heading">
						<span><b>三耗占比</b></span>
						<span style="float:right;">&nbsp;热(GJ)&nbsp;</span>
						<span class="roundElement" style="background:red;float:right;width:30px;height:12px;margin-top:5px;">&nbsp;</span>
						<span style="float:right;">&nbsp;电(kWh)&nbsp;</span>
						<span class="roundElement" style="background:#32CD32;float:right;width:30px;height:12px;margin-top:5px;">&nbsp;</span>
						<span style="float:right;">&nbsp;水(t)&nbsp;</span>
						<span class="roundElement" style="background:blue;float:right;width:30px;height:12px;margin-top:5px;">&nbsp;</span>
					</header>
					<div class="panel-body"><!-- 这里显示的是那三个水、电、热的图形 -->
						<div class="chart"></div><div class="chart"></div><div class="chart"></div>
						<table id="table" class="table" style="width:98%;border:1px solid #3466bb;margin-left:15px;margin-right:15px"
							   cellspacing="0" cellpadding="0" class="main">
							<tr><td colspan="7" style="background-color: #3466bb;color:white" >供热信息</td></tr>
							<tr style="background-color:#F5F5F5">
								<th colspan="2" class="weather_td" style="background-color:#F5F5F5">水</th>
								<th colspan="2" class="weather_td" style="background-color:#F5F5F5">电</th>
								<th colspan="2" class="weather_td" >热</th>
								<th class="weather_td" >总花费</th>
							</tr>
							<tr style="background-color:#F5F5F5">
								<th class="weather_td" style="background-color:#F5F5F5">数值(t)</th>
								<th class="weather_td" style="background-color:#F5F5F5">单价(元)</th>
								<th class="weather_td" style="background-color:#F5F5F5">数值(kWh)</th>
								<th class="weather_td" style="background-color:#F5F5F5">单价(元)</th>
								<th class="weather_td" style="background-color:#F5F5F5">数值(GJ)</th>
								<th class="weather_td" style="background-color:#F5F5F5">单价(元)</th>
								<th class="weather_td" >总花费(元)</th>
							</tr>
							<tr align="left">
								<td class="weather_td">${ft3q}</td>
								<td class="weather_td">${priceData.ft3qPrice}</td>
								<td class="weather_td">${jqi}</td>
								<td class="weather_td">${priceData.jqiPrice}</td>
								<td class="weather_td">${qqi}</td>
								<td class="weather_td">${priceData.qqiPrice}</td>
								<td class="weather_td">
									<fmt:formatNumber type="number" value="${ft3q * priceData.ft3qPrice + jqi * priceData.jqiPrice + qqi * priceData.qqiPrice}" pattern="0.00" maxFractionDigits="2"/>
								</td>
							</tr>
						</table>
					</div>
				</section>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript" charset="utf-8">
	var ft3pPerAmount = (${ft3q * priceData.ft3qPrice}).toFixed(2);
	var jqiPerAmount = (${jqi * priceData.jqiPrice}).toFixed(2);
	var qqiPerAmount = (${qqi * priceData.qqiPrice}).toFixed(2);

	var totalAmount = (${ft3q * priceData.ft3qPrice} + ${jqi * priceData.jqiPrice} + ${qqi * priceData.qqiPrice});
	var ft3pPer = (ft3pPerAmount / totalAmount).toFixed(2);
	var jqiPer = (jqiPerAmount / totalAmount).toFixed(2);
	var qqiPer = 1 - ft3pPer - jqiPer;
	var _hmt = _hmt || [];
	(function() {
		$("#home").addClass("active");

		var hm = document.createElement('script');

		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(hm, s);
	})();


	var containers = document.getElementsByClassName('chart');
	var options = [{
		title:{
			text:'',
			subtext:'',
			x:'left',
			y:'top',
			textAlign:'left'
		},
		series: [{
			type: 'liquidFill',
			radius: '80%',
			//shape: 'path://M367.855,428.202c-3.674-1.385-7.452-1.966-11.146-1.794c0.659-2.922,0.844-5.85,0.58-8.719 c-0.937-10.407-7.663-19.864-18.063-23.834c-10.697-4.043-22.298-1.168-29.902,6.403c3.015,0.026,6.074,0.594,9.035,1.728 c13.626,5.151,20.465,20.379,15.32,34.004c-1.905,5.02-5.177,9.115-9.22,12.05c-6.951,4.992-16.19,6.536-24.777,3.271 c-13.625-5.137-20.471-20.371-15.32-34.004c0.673-1.768,1.523-3.423,2.526-4.992h-0.014c0,0,0,0,0,0.014 c4.386-6.853,8.145-14.279,11.146-22.187c23.294-61.505-7.689-130.278-69.215-153.579c-61.532-23.293-130.279,7.69-153.579,69.202 c-6.371,16.785-8.679,34.097-7.426,50.901c0.026,0.554,0.079,1.121,0.132,1.688c4.973,57.107,41.767,109.148,98.945,130.793 c58.162,22.008,121.303,6.529,162.839-34.465c7.103-6.893,17.826-9.444,27.679-5.719c11.858,4.491,18.565,16.6,16.719,28.643 c4.438-3.126,8.033-7.564,10.117-13.045C389.751,449.992,382.411,433.709,367.855,428.202z',
			shape: 'circle',
			outline: {
				show: true,
				borderDistance: 8,
				itemStyle: {
					color: 'none',
					borderColor: '#0000FF',
					borderWidth: 8,
					shadowBlur: 20,
					shadowColor: 'rgba(0, 0, 0, 0.25)'
				}
			},
			data: [{
				value: ft3pPer,
				itemStyle: {
					normal: {
						color: '#0000FF'
					}
				}
			}]
		}]
	},{
		series: [{
			type: 'liquidFill',
			radius: '80%',
			shape: 'circle',
			outline: {
				show: true,
				borderDistance: 8,
				itemStyle: {
					color: 'none',
					borderColor: '#32CD32',
					borderWidth: 8,
					shadowBlur: 20,
					shadowColor: 'rgba(0, 0, 0, 0.25)'
				}
			},
			data: [{
				value: jqiPer,
				itemStyle: {
					normal: {
						color: '#32CD32'
					}
				}
			}]
		}]
	},{
		series: [{
			type: 'liquidFill',
			radius: '80%',
			shape: 'circle',
			outline: {
				show: true,
				borderDistance: 8,
				itemStyle: {
					color: 'none',
					borderColor: '#FF0000',
					borderWidth: 8,
					shadowBlur: 20,
					shadowColor: 'rgba(0, 0, 0, 0.25)'
				}
			},
			data: [{
				value: qqiPer,
				itemStyle: {
					normal: {
						color: '#FF0000'
					}
				}
			}]
		}]
	}];

	var charts = [];
	for (var i = 0; i < options.length; ++i) {
		var chart = echarts.init(containers[i]);

		if (i > 0) {
			//options[i].series[0].silent = true;
		}
		chart.setOption(options[i]);
		chart.setOption({
			baseOption: options[i],
			media: [{
				query: {
					maxWidth: 300
				},
				option: {
					series: [{
						label: {
							normal: {
								textStyle: {
									fontSize: 26
								}
							}
						}
					}]
				}
			}]
		});

		charts.push(chart);
	}

	window.onresize = function () {
		for (var i = 0; i < charts.length; ++i) {
			charts[i].resize();
		}
	};

	//setInterval(update, 3000);

	function update() {
		var data = [];
		var last = 1;
		for (var i = 0; i < 4; ++i) {
			last = Math.floor(last * (Math.random() * 0.5 + 0.5)
					* 100) / 100;
			data.push(last);
		}
		charts[1].setOption({
			series: [{
				data: data
			}]
		});
	}

	//饼图

	var chart;
	var chartData = [
		{
			category: "水",
			count: ft3pPerAmount,
			color:"#0000FF",
		}, {
			category: "电",
			count: jqiPerAmount,
			color:"#32CD32"
		}, {
			category: "热",
			count: qqiPerAmount,
			color:"#FF0000"
		}
	];


	// 创建饼图
	chart = new AmCharts.AmPieChart();
	// 饼图标题  文字大小
	chart.addTitle("金额总览分析", 30);
	// 数据提供方
	chart.dataProvider = chartData;
	// 切片属性
	chart.titleField = "category";
	// 切片值
	chart.valueField = "count";
	// 切片颜色
	chart.colorField = "color";
	// 动画
	chart.sequencedAnimation = true;
	// 动画效果  ">", "<", "elastic" and "bounce".
	//chart.startEffect = "elastic";  //这个眼晕的很
	// 内半径
	chart.innerRadius = "30%";
	// 动画持续时间
	chart.startDuration = 1;
	// 文字与饼图之间的距离
	chart.labelRadius = 45;
	// 饼图深度
	chart.depth3D = 25;
	// 饼图倾斜角度
	chart.angle = 35;
	// 字体大小
	chart.fontSize = 20;
	// 字体类型
	chart.fontFamily = "微软雅黑";
	//外边那圈线
	chart.outlineAlpha = 2.4;

	// 显示各个分类

	legend = new AmCharts.AmLegend();
	// 对齐方式 有三种："left", "center", "right"
	legend.align = "center";
	legend.autoMargins = false;
	//legend.position = "center";
	// 分类标记图 这里设置成方块形状
	legend.markerType = "square";
	legend.labelText = '';
	legend.valueWidth = 130;
	chart.addLegend(legend);
	//     chart.legends(0).Position.Auto = False
	//     chart.legends(0).Position.X = 10
	//     chart.legends(0).Position.Y = 10
	//     chart.legends(0).Position.Width = 35
	//     chart.legends(0).Position.Height = 10
	// WRITE
	chart.write("pieChart");
	//饼图结束
</script>