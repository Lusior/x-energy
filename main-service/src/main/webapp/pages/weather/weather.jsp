<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
    <link href="${pageContext.request.contextPath}/css/weather/weather7-1.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/weather/weather7-2.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/weather/weather.css" rel="stylesheet">
    <style type="text/css">
        .weather_td {
            border: 1px solid #3466bb;
            text-align: center;
        }

        .today .t .sk .h {
            margin: 10px;
        }

        .today .t .sk .topic {
            padding: 13px 10px 10px 10px;
            color: #7e7e7e;
            font-size: 13px;
        }

        .table thead > tr > th, .table tbody > tr > th, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td {
            height: 30px;
            line-height: 30px;
            padding: 3px;
        }

        #chart {
            width: 250px;
        }

        #chart p.h {
            margin: 10px 15px 0 10px;
        }

        #chart p.topic {
            margin: 10px 0 12px 10px;
        }

        #chart p.topic span {
            font-size: 16px;
            color: #7e7e7e;
            margin: 10px 0 15px 10px;
        }
    </style>
</head>
<body>

<section class="wrapper" style='padding-right: 0;'>
    <section class="panel panel-info">
        <header class="panel-heading" style="margin-right:15px;">
            <span><b>总览</b></span>
        </header>
        <div class="ctop clearfix" style="margin-left:15px;">
            <div class="crumbs fl">
                <a href="" target="_blank">黑龙江</a>
                <span>&gt;</span>
                <jsp:useBean id="now" class="java.util.Date" scope="page"/>
                <a href="" target="_blank">哈尔滨 (<a href=""><fmt:formatDate value="${now}"
                                                                           pattern="yyyy年MM月dd日"/></a>)</a>
            </div>
        </div>
        <div class="today clearfix" id="today" style="margin-left:15px;">
            <div class="t" style="overflow:hidden;">
                <div class="sk" style="float:left;">
                    <p class="time">
                        <span>${shikuang} 实况</span>
                    </p>
                    <div class="zs h" style="margin-top:25px;"><i></i>
                        <span>相对湿度</span> <em>${weatherDataStal.rhu}%</em>
                    </div>
                    <div class="zs w" style="top:238px;">
                        <span>${winName}</span> <em>${winLevl}级</em>
                    </div>
                    <div class="tem">
                        <span>${wendu}</span>
                        <em>℃</em>
                    </div>
                    <p></p>
                    <div class="therm" style="background-size:350%; height:160px;">
                        <p style="bottom:40px; left:44px;">
                            <i class="t"></i>
                            <i class="c" style="height:${xiangsu}px"></i>
                        </p>
                    </div>
                </div>
                <ul style="float:left; overflow:hidden;">
                    <li style="width:200px;">
                        <h1>${dt}日白天</h1>
                        <big class="jpg80 ${weatherData.teI1}"></big>
                        <p class="wea" title="">${weatherData.teC1}</p>
                        <p class="tem">
                            <span><fmt:formatNumber value="${weatherData.teH}" pattern="#"/></span>
                            <em>°C</em>
                        </p>
                        <p class="win" style="margin-top:14px;">
                            <span class=""
                                  title="${weatherData.teWd1}">${weatherData.teWe1} ${weatherData.teWd2}</span>
                        </p>
                    </li>
                    <li style="width:200px;">
                        <h1>${dt}日夜间</h1>
                        <big class="jpg80 ${weatherData.teI2}"></big>
                        <p class="wea" title="多云">${weatherData.teC2}</p>
                        <p class="tem">
                            <span><fmt:formatNumber value="${weatherData.teL}" pattern="#"/></span>
                            <em>°C</em>
                        </p>
                        <p class="win" style="margin-top:14px;">
                            <span class=""
                                  title="${weatherData.teWd2}">${weatherData.teWe2} ${weatherData.teWd2}</span>
                        </p>
                    </li>
                </ul>
                <div id="chart" style="border:1px solid #d4d4d4;height:295px; float:left; margin-left:5px;">

                    <p class="topic"><i></i>
                        <span>实 时 气 象 要 素:</span>
                    </p>
                    <p class="h">
                        <span style="margin:10px">气压: <font
                                color="#3466bb"><b>${weatherDataStal.prs}</b></font> (百帕)</span>
                    </p>
                    <p class="h">
                        <span style="margin:10px">最高气压: <font color="#3466bb"><b>${weatherDataStal.prsMax }</b></font> (百帕)</span>
                    </p>
                    <p class="h">
                        <span style="margin:10px">最低气压: <font color="#3466bb"><b>${weatherDataStal.prsMin }</b></font> (百帕)</span>
                    </p>
                    <p class="h">
                        <span style="margin:10px">水汽压: <font color="#3466bb"><b>${weatherDataStal.vap }</b></font> (百帕)</span>
                    </p>
                    <p class="h">
                        <span style="margin:10px">最大风速: <font color="#3466bb"><b>${weatherDataStal.winSMax }</b></font> (米/秒)</span>
                    </p>
                    <p class="h">
                        <span style="margin:10px">10分钟平均风速: <font color="#3466bb"><b>${weatherDataStal.winSAvg10mi}</b></font> (米/秒)</span>
                    </p>
                    <p class="h">
                        <span style="margin:10px">极大风速: <font
                                color="#3466bb"><b>${weatherDataStal.winSInstMax}</b></font> (米/秒)</span>
                    </p>

                </div>

            </div>
        </div>

        <div class="row state-overview col-lg-12" style='padding: 0;'>
            <div class="wrap-all">
                <div class="wrap-day chart-flow" id="morris" style="margin-left:15px">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <section class="panel panel-info" style="margin-left:15px;">
                                <header class="panel-heading">
                                    <span><b>天气预报(${shikuang } 实时发布)</b></span>
                                </header>
                                <table id="table" class="table"
                                       style="width:100%;border:1px solid #3466bb;margin-left:0px;margin-right:0px"
                                       cellspacing="0" cellpadding="0" class="main">
                                    <tr style="background-color:#F5F5F5">
                                        <th colspan="2" class="weather_td" style="background-color:#F5F5F5">日期</th>
                                        <th colspan="2" class="weather_td" style="background-color:#F5F5F5">天气现象
                                        <th class="weather_td">气温</th>
                                        <th class="weather_td">风向</th>
                                        <th class="weather_td">风力</th>
                                    </tr>
                                    <c:forEach var="weatherDate" items="${data7}" varStatus="status">
                                        <tr <c:if test="${status.count%2==0}"> style="background-color:#F5F5F5" </c:if>
                                                align="left">
                                            <td class="weather_td" rowspan="2" style="vertical-align: middle;">
                                                <fmt:parseDate value="${weatherDate.day}" pattern="yyyyMMdd" var="dd"/>
                                                <fmt:formatDate value="${dd}" pattern="MM月dd日(EEEE)"/>
                                            </td>
                                            <td class="weather_td">白天</td>
                                            <td class="weather_td"><big class="png40 ${weatherDate.teI1 }"></big></td>
                                            <td class="weather_td">${weatherDate.teC1}</td>
                                            <td class="weather_td"><fmt:formatNumber value="${weatherDate.teH}"
                                                                                     pattern="#"/>°C
                                            </td>
                                            <td class="weather_td">${weatherDate.teWd1}</td>
                                            <td class="weather_td">${weatherDate.teWe1}</td>
                                        </tr>
                                        <tr <c:if test="${status.count%2==0}"> style="background-color:#F5F5F5" </c:if>>
                                            <td class="weather_td">夜间</td>
                                            <td class="weather_td"><big class="png40 ${weatherDate.teI2 }"></big></td>
                                            <td class="weather_td">${weatherDate.teC2}</td>
                                            <td class="weather_td"><fmt:formatNumber value="${weatherDate.teL}"
                                                                                     pattern="#"/>°C
                                            </td>
                                            <td class="weather_td">${weatherDate.teWd2}</td>
                                            <td class="weather_td">${weatherDate.teWe2}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </section>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>

<script type="text/javascript" charset="utf-8">
    $(function () {
        $("#weather").addClass("active");
        $("#weather_list").addClass("active");
        $("#weather_list").parents(".sub").show();
    });

</script>
</body>

