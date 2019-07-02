<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="wrapper" style='padding-right: 0'>
    <div class="row state-overview col-lg-12" style='padding-right: 0'>
    </div>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>历史曲线</b></span>
                </header>
                <div class="panel-body" style="margin-left:33px;">
                    <form id="post-form" class="pull-left pull-top">
                        <div class="form-group">
                            <label for="name">选择机组:</label>
                            <select id="standSelect" name="standSelect" class="form-control">
                                <c:forEach var="standData" items="${standDataList}" varStatus="status">
                                    <c:forEach var="stationData" items="${stationDataList}" varStatus="status1">
                                        <c:choose>
                                            <c:when test="${standData.stationId == stationData.stationId}">
                                                <c:choose>
                                                    <c:when test="${status.index == 0}">
                                                        <option selected="selected" value="${standData.standId}">${standData.standName}(${stationData.stationName})</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${standData.standId}">${standData.standName}(${stationData.stationName})</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                </c:forEach>
                            </select>
                            <label for="name">选择曲线:</label>
                            <select id="curveSelect" class="form-control" onchange="loadData()">
                                <option selected="selected" value="1">一网压力</option>
                                <option value="2">二网压力</option>
                                <option value="3">一网温度</option>
                                <option value="4">二网温度</option>
                                <option value="5">瞬时流量</option>
                                <option value="6">瞬热、阀位、与二供温度对比</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="name">起始时间:</label>
                            <input id="tTime" name="tTime" type="text" data-date-format="yyyy-mm-dd hh:ii:ss"
                                   class="form-control default-date-picker dpd1 dealtime1" readonly value=""/>
                            <label for="name">时间范围:</label>
                            <input id="timeRange" name="timeRange" value="100"
                                   onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,'');}).call(this)"
                                   type="text" class="form-control"/>
                            <select id="timeType" name="timeType" style="width:40px;height:35px;margin-left:-58px;">
                                <option value="minute">分</option>
                                <option value="hour">时</option>
                                <option value="day">日</option>
                            </select>
                            <button type="button" class="btn btn-sm btn-primary" id="search" onclick="loadData()">查询
                            </button>
                        </div>
                    </form>
                </div>
            </section>
            <section class="panel panel-info">
                <!-- 							<header class="panel-heading"> -->
                <!-- 								<span><b>历史曲线</b></span> -->
                <!-- 							</header> -->
                <div class="panel-body" style="width: 100%;height:600px;" id="chart">

                </div>
            </section>
        </div>
    </div>
</section>

<script type="text/javascript">
    $(function () {
        $("#heat_station").addClass("active");
        $("#heat_station_history_curve").addClass("active");
        $("#heat_station_history_curve").parents(".sub").show();
        var now = new Date();
        $("#tTime").val(now.getFullYear() + "-" + (parseInt(now.getMonth()) + 1) + "-" + (now.getDate()) + " " + (now.getHours()) + ":" + (now.getMinutes()));
        loadData();
    });

    $('#tTime').datetimepicker({
        //minView: "month", //选择日期后，不会再跳转去选择时分秒 ;''
        format: "yyyy-mm-dd hh:ii",
        language: 'zh-CN', //汉化
        autoclose: true, //选择日期后自动关闭
        todayBtn: 1,
        endDate: new Date()
    });

    //查询
    var jsonData;

    function loadData() {
        var formData = $("#post-form").serialize();
        $.ajax({
            type: "POST",
            url: '${pageContext.request.contextPath}/basic/station/historycurveAjax',
            processData: true,
            data: formData,
            beforeSend: function () {
            },
            success: function (data) {
                //var jsonDataStr = '{'+ '"data"' + ':' + JSON.stringify(data) + '}';
                var jsonDataStr = JSON.stringify(data);
                var jsonData = JSON.parse(jsonDataStr);

                var titleText = $("#standSelect option:selected").text() + "--" + $("#curveSelect option:selected").text(); //标题
                var myChart = echarts.init(document.getElementById('chart'), 'qqhr-Thrme');
                var xAxisData = new Array();
                var legendData = new Array();
                var pt1Data = new Array();//一网供水压力
                var pt2Data = new Array();//一网回水压力
                var pt3Data = new Array();//二网供水压力
                var pt4Data = new Array();//二网回水压力
                var te1Data = new Array();//一网供水温度
                var te2Data = new Array();//一网回水温度
                var te3Data = new Array();//二网供水温度
                var te4Data = new Array();//二网回水温度
                var ft1Data = new Array();//一网瞬时流量
                var qiData = new Array();//一网瞬时热量
                var cvi1Data = new Array();//调节阀开度

                for (var i = 0; i < jsonData.length; i++) {
                    xAxisData[i] = jsonData[i].opTimeStr;
                    pt1Data[i] = jsonData[i].pt1;
                    pt2Data[i] = jsonData[i].pt2;
                    pt3Data[i] = jsonData[i].pt3;
                    pt4Data[i] = jsonData[i].pt4;
                    te1Data[i] = jsonData[i].te1;
                    te2Data[i] = jsonData[i].te2;
                    te3Data[i] = jsonData[i].te3;
                    te4Data[i] = jsonData[i].te4;
                    ft1Data[i] = jsonData[i].ft1;
                    qiData[i] = jsonData[i].qi;
                    cvi1Data[i] = jsonData[i].cvi1;
                }

                if ($("#curveSelect option:selected").val() == "1") {//一网压力
                    legendData[0] = '一网供水压力';
                    legendData[1] = '一网回水压力';
                    option.xAxis.data = xAxisData;
                    option.title.text = '一网压力';
                    option.legend.data = legendData;
                    option.series[0].name = legendData[0];
                    option.series[0].data = pt1Data;
                    option.series[1].name = legendData[1];
                    option.series[1].data = pt2Data;
                    myChart.clear();
                    myChart.setOption(option);
                } else if ($("#curveSelect option:selected").val() == "2") {//二网压力
                    legendData[0] = '二网供水压力';
                    legendData[1] = '二网回水压力';
                    option.xAxis.data = xAxisData;
                    option.title.text = '二网压力';
                    option.legend.data = legendData;
                    option.series[0].name = legendData[0];
                    option.series[0].data = pt3Data;
                    option.series[1].name = legendData[1];
                    option.series[1].data = pt4Data;
                    myChart.clear();
                    myChart.setOption(option);
                } else if ($("#curveSelect option:selected").val() == "3") {//一网温度
                    legendData[0] = '一网供水温度';
                    legendData[1] = '一网回水温度';
                    option.xAxis.data = xAxisData;
                    option.title.text = '一网温度';
                    option.legend.data = legendData;
                    option.series[0].name = legendData[0];
                    option.series[0].data = te1Data;
                    option.series[1].name = legendData[1];
                    option.series[1].data = te2Data;
                    myChart.clear();
                    myChart.setOption(option);
                } else if ($("#curveSelect option:selected").val() == "4") {//二网温度
                    legendData[0] = '二网供水温度';
                    legendData[1] = '二网回水温度';
                    option.xAxis.data = xAxisData;
                    option.title.text = '二网温度';
                    option.legend.data = legendData;
                    option.series[0].name = legendData[0];
                    option.series[0].data = te3Data;
                    option.series[1].name = legendData[1];
                    option.series[1].data = te4Data;
                    myChart.clear();
                    myChart.setOption(option);
                } else if ($("#curveSelect option:selected").val() == "5") {//瞬时流量
                    legendData[0] = '一网瞬时流量';
                    option1.xAxis.data = xAxisData;
                    option1.title.text = '瞬时流量';
                    option1.legend.data = legendData;
                    option1.series.name = legendData[0];
                    option1.series.data = ft1Data;
                    myChart.clear();
                    myChart.setOption(option1);
                } else if ($("#curveSelect option:selected").val() == "6") {//瞬热、阀位、与二供温度对比
                    legendData[0] = '一网瞬时热量';
                    legendData[1] = '二网供水温度';
                    legendData[2] = '调节阀开度';
                    option.xAxis.data = xAxisData;
                    option.title.text = '瞬热、阀位、与二供温度对比';
                    option.legend.data = legendData;
                    option.series[0].name = legendData[0];
                    option.series[0].data = qiData;
                    option.series[1].name = legendData[1];
                    option.series[1].data = te3Data;
                    option.series[2].name = legendData[2];
                    option.series[2].data = cvi1Data;
                    myChart.clear();
                    myChart.setOption(option);
                }
            },
            complete: function () {
            }
        });
    }

    option = {
        title: {
            text: '',
        },
        tooltip: {
            trigger: 'axis',
            showDelay: 0, // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：‘line‘ | ‘shadow‘
            }
        },
        legend: {
            data: []
        },
        grid: { // 控制图的大小，调整下面这些值就可以，
            x: 40,
            x2: 10,
            y2: 120,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
        },
        toolbox: {
            show: true,
            orient: 'horizontal',      // 布局方式，默认为水平布局，可选为：
                                       // 'horizontal' ¦ 'vertical'
            x: 'right',                // 水平安放位置，默认为全图右对齐，可选为：
                                       // 'center' ¦ 'left' ¦ 'right'
                                       // ¦ {number}（x坐标，单位px）
            y: 'top',                  // 垂直安放位置，默认为全图顶端，可选为：
                                       // 'top' ¦ 'bottom' ¦ 'center'
                                       // ¦ {number}（y坐标，单位px）
            color: ['#1e90ff', '#22bb22', '#4b0082', '#d2691e'],
            backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
            borderColor: '#ccc',       // 工具箱边框颜色
            borderWidth: 0,            // 工具箱边框线宽，单位px，默认为0（无边框）
            padding: 5,                // 工具箱内边距，单位px，默认各方向内边距为5，
            showTitle: true,
            feature: {
                dataZoom: {//不可以覆盖
                    show: true,
                    title: {
                        dataZoom: '区域缩放',
                        dataZoomReset: '区域缩放-后退'
                    }
                },
                restore: {//不可以覆盖
                    show: true,
                    title: '还原',
                    color: 'black'
                }
            },
        },
        dataZoom: {
            show: true,
            realtime: true,
            start: 20,
            end: 80
        },
        calculable: true,
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: [],
            type: 'category',
            axisLabel: {//覆盖不了lzq
                interval: 0,
                rotate: 30,
                margin: 22,
                textStyle: {
                    color: "#222"
                }
            }
        },
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    formatter: '{value}'
                }
            }
        ],
        series: [
            {
                name: '',
                type: 'line',
                data: [],
                markPoint: {
                    data: []
                }
            },
            {
                name: '',
                type: 'line',
                data: []
            },
            {
                name: '',
                type: 'line',
                data: []
            }
        ]
    };

</script>

