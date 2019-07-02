<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<head>
    <link href="${pageContext.request.contextPath}/css/weather/weather7-1.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/weather/weather7-2.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/weather/weather.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dcjqaccordion.2.7.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/macarons.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/infographic.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/vintage.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/roma.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/shine.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/dark.js"></script>
    <style type="text/css">
        .weatherFc {
            width: 900px;
            overflow: hidden;
            color: #bebebe;
        }

        .detail_time {
            width: 100%;
            overflow: hidden;
            padding: 0 0 5px 0;
        }

        .detail_time p {
            color: #999;
        }

        .wea_weather {
            width: 40%;
            overflow: hidden;
            float: left;
            text-align: center;
            border-right: 1px solid #d1d1d1;
            margin: 0 0 0 -1px;
            background: #f2f2f2;
        }

        .wea_weather:hover {
            cursor: pointer;
            transition: background-color 0.3s linear;
            background-color: #eee;
            box-shadow: 0 0 8px #888;
        }

        .wea_top {
            height: 120px;
        }

        .wea_weather .wea_top em {
            position: relative;
            font-family: Arial, serif;
            font-size: 48px;
            line-height: 1;
            color: #999;
        }

        .wea_weather .wea_top em::before {
            position: absolute;

            right: -20px;
            font-size: 48px;
            content: "°";
            color: #999;
        }

        .wea_weather span.img {
            display: block;
        }

        .wea_weather span.img img {
            width: 50px;
            height: 50px;
        }

        .wea_weather .wea_top ul {
            width: 100%;
            border-right: 1px solid #d1d1d1;
            margin: 0 0 0;
        }

        .detail_future {
            overflow: hidden;
            width: 900px;
        }

        .wea_list {
            margin: 0 auto;
            overflow: hidden;
            border: 1px solid #d1d1d1;
        }

        .wea_list ul.wea_fu {
            width: 20%;
            line-height: 40px;
            overflow: hidden;
            float: left;
            border-right: 1px solid #d1d1d1;
            margin: 0 0 0 -1px;
        }

        .wea_list ul.wea_fu:hover {
            cursor: pointer;
            transition: background-color 0.3s linear;
            background-color: #eee;
            box-shadow: 0 0 8px #888;
        }

        .wea_list ul li {
            width: 100%;
            height: 40px;
            line-height: 40px;
            float: left;
            text-align: center;
        }

        .wea_list ul.wea_fu li span {
            color: #666;
        }

        .wea_list .week, .wea_list .wea {
            width: 60px;
            height: 22px;
            line-height: 22px;
            text-align: center;
            font-size: 14px;
            color: #666;
        }

        .weai {
            height: 59px;
            text-align: center;
        }

        .weai img {
            width: 30px;
            height: 30px;
            margin: -9px auto;
            display: inline-block;
        }

        .wendu {
            margin: 10px 0 0;
            width: 900px;
        }
    </style>
    <title></title>
</head>

<body style="width: 900px;">

<section class="wrapper">
    <div class="weatherFc">
        <div class="detail_time">
            <a href="" target="_blank">黑龙江</a>
            <span>&gt;</span>
            <a href="" target="_blank">哈尔滨 </a>
        </div>
        <div class="detail_future">
            <div class="wea_list">
                <div class="wea_weather">
                    <span style="float:left;margin:2px;padding:5px;font-size:20px">今日</span>
                    <div class="wea_top">
                        <span class="img"><big class="png80 ${wd0.teI1 }"></big></span>
                        <em>13</em>
                    </div>
                    <ul id="chars_h0">
                        <li><span class="wea"><fmt:formatNumber value="${wd0.teH }" pattern="#"/>℃／<fmt:formatNumber
                                value="${wd0.teL }" pattern="#"/>℃</span></li>
                        <li><span class="week">${wd0.teC1}<c:if
                                test="${wd0.teC1!=wd0.teC2 }">转${wd0.teC2 }</c:if></span></li>
                        <li><span class="week">${wd0.teWd1}
                        <c:if test="${wd0.teWd1!=wd0.teWd2 }">转${wd0.teWd2 }</c:if> ${wd0.teWe1}</span></li>
                    </ul>
                </div>
                <ul class="wea_fu" id="chars_h1">
                    <li><span class="week"><fmt:parseDate value="${wd1.day}" pattern="yyyyMMdd" var="dd"/>
									   <fmt:formatDate value="${dd}" pattern="MM月dd日"/></span></li>
                    <li>
                    <span class="weai">
                        <big class="png40 ${wd1.teI1 }"></big>
                    </span>
                    </li>
                    <li>
                        <span class="wea"><fmt:formatNumber value="${(wd1.teH+wd1.teL)/2 }" pattern="#"/>℃</span>
                    </li>
                    <li>
                        <span class="wea"><fmt:formatNumber value="${wd1.teH }" pattern="#"/>℃／<fmt:formatNumber
                                value="${wd1.teL }"/> ℃</span>
                    </li>
                    <li><span class="week">${wd1.teC1}<c:if test="${wd1.teC1!=wd1.teC2 }">转${wd1.teC2 }</c:if></span>
                    </li>
                    <li><span class="week">${wd1.teWd1}
                    <c:if test="${wd1.teWd1!=wd1.teWd2 }">转${wd1.teWd2 }</c:if> ${wd1.teWe1}</span></li>
                </ul>
                <ul class="wea_fu" id="chars_h2">
                    <li><span class="week"><fmt:parseDate value="${wd2.day}" pattern="yyyyMMdd" var="dd"/>
									   <fmt:formatDate value="${dd}" pattern="MM月dd日"/></span></li>
                    <li>
                    <span class="weai">
                        <big class="png40 ${wd2.teI1 }"></big>
                    </span>
                    </li>
                    <li>
                        <span class="wea"><fmt:formatNumber value="${(wd2.teH+wd2.teL)/2 }" pattern="#"/>℃</span></li>
                    <li>
                        <span class="wea"><fmt:formatNumber value="${wd2.teH }" pattern="#"/>℃／<fmt:formatNumber
                                value="${wd2.teL }"/> ℃</span>
                    </li>
                    <li><span class="week">${wd2.teC1}<c:if test="${wd2.teC1!=wd1.teC2 }">转${wd2.teC2 }</c:if></span>
                    </li>
                    <li><span class="week">${wd2.teWd1}
                    <c:if test="${wd2.teWd1!=wd1.teWd2 }">转${wd2.teWd2 }</c:if> ${wd2.teWe1}</span></li>
                </ul>
                <ul class="wea_fu" style="border-right:0" id="chars_h3">
                    <li><span class="week"><fmt:parseDate value="${wd3.day}" pattern="yyyyMMdd" var="dd"/>
									   <fmt:formatDate value="${dd}" pattern="MM月dd日"/></span></li>
                    <li>
                    <span class="weai">
                        <big class="png40 ${wd3.teI1 }"></big>
                    </span>
                    </li>
                    <li>
                        <span class="wea"><fmt:formatNumber value="${(wd3.teH+wd3.teL)/2 }" pattern="#"/>℃</span></li>
                    <li>
                        <span class="wea"><fmt:formatNumber value="${wd3.teH }" pattern="#"/>℃／<fmt:formatNumber
                                value="${wd3.teL }"/> ℃</span>
                    </li>
                    <li><span class="week">${wd3.teC1}<c:if test="${wd3.teC1!=wd3.teC2 }">转${wd3.teC2 }</c:if></span>
                    </li>
                    <li><span class="week">${wd3.teWd1}
                    <c:if test="${wd3.teWd1!=wd3.teWd2 }">转${wd3.teWd2 }</c:if> ${wd3.teWe1}</span></li>
                </ul>
            </div>
        </div>

    </div>
    <div class="wrap-day chart-flow" id="morris">

        <header class="panel-heading">
            <span>24小时气温变化</span>
        </header>
        <div class="panel-body">
            <div id='char1' style="width: 980px;height:400px;"></div>
        </div>

    </div>
</section>

<script type="text/javascript">
    var myChart1;
    var option;
    var chargeChars = function (f) {
        if (f === "h0") {
            myChart1.setOption({
                xAxis: {
                    data: [${h0_d}]
                },
                series: {
                    data: [${h0_w}]
                },
                title: {
                    text: '今日'
                }
            });
        } else if (f === "h1") {
            myChart1.setOption({
                xAxis: {
                    data: [${h1_d}]
                },
                series: {
                    data: [${h1_w}]
                }
                ,
                title: {
                    text: '<fmt:parseDate value="${wd1.day}" pattern="yyyyMMdd" var="dd"/> <fmt:formatDate value="${dd}" pattern="yyyy年MM月dd日"/>'
                }
            });
        } else if (f === "h2") {
            myChart1.setOption({
                xAxis: {
                    data: [${h2_d}]
                },
                series: {
                    data: [${h2_w}]
                }
                ,
                title: {
                    text: '<fmt:parseDate value="${wd2.day}" pattern="yyyyMMdd" var="dd"/> <fmt:formatDate value="${dd}" pattern="yyyy年MM月dd日"/>'
                }
            });
        } else if (f === "h3") {
            myChart1.setOption({
                xAxis: {
                    data: [${h3_d}]
                },
                series: {
                    data: [${h3_w}]
                }
                ,
                title: {
                    text: '<fmt:parseDate value="${wd3.day}" pattern="yyyyMMdd" var="dd"/> <fmt:formatDate value="${dd}" pattern="yyyy年MM月dd日"/>'
                }
            });
        }

    };

    $(function () {
        $("#weather").addClass("active");
        $("#weather_detail").addClass("active");
        $("#weather_detail").parents(".sub").show();
        $("#chars_h0").mouseover(function () {
            chargeChars("h0");
        });
        $("#chars_h1").mouseover(function () {
            chargeChars("h1");
        }).mouseout(function () {
            chargeChars("h0");
        });
        $("#chars_h2").mouseover(function () {
            chargeChars("h2");
        }).mouseout(function () {
            chargeChars("h0");
        });
        $("#chars_h3").mouseover(function () {
            chargeChars("h3");
        }).mouseout(function () {
            chargeChars("h0");
        });
        // 基于准备好的dom，初始化echarts实例
        myChart1 = echarts.init(document.getElementById('char1'), 'shine');

        option = {
            title: {
                text: '今日',
                subtext: ''
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['气温']
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: [${h0_d}]
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} °C'
                    }
                }
            ],
            series: [
                {
                    name: '最高气温',
                    type: 'line',
                    data: [${h0_w}],
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    }
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart1.setOption(option);
    });

</script>
</body>

