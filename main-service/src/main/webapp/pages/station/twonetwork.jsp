<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="wrapper" style='padding-right: 0'>
    <div class="row state-overview col-lg-12" style='padding-right: 0'>
    </div>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>二网排序</b></span>
                </header>
                <form id="priceForm" class="form-inline" style="margin-left:18px;margin-top:5px;">
                    <input id="tTime" type="text" data-date-format="yyyy-mm-dd hh:ii:ss"
                           class="form-control default-date-picker dpd1 dealtime1" readonly value="">
                    <select id="curveSelect" name="curveSelect" class="form-control" onchange="loadData()">
                        <option selected="selected" value="3">二网压力</option>
                        <option value="4">二网温度</option>
                    </select>
                    <button type="button" class="btn btn-sm btn-primary" id="search">
                        <span class="fa fa-search" aria-hidden="true">查询</span>
                    </button>
                </form>
                <div class="panel-body">
                    <div class="row row-lg" style="display:inline">
                        <div class="col-sm-12" style="width:40%">
                            <table class="table1" id="twoNetWorkTable"></table>
                        </div>
                        <div class="col-sm-12" id="chart" style="width:60%;height:550px;">
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</section>

<script type="text/javascript">
    $(function () {
        $("#heat_station").addClass("active");
        $("#heat_station_two_network").addClass("active");
        $("#heat_station_two_network").parents(".sub").show();

        var now = new Date();
        $("#tTime").val(now.getFullYear() + "-" + (parseInt(now.getMonth()) + 1) + "-" + (now.getDate()) + " " + (now.getHours()) + ":" + (now.getMinutes()));
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();
    });

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#twoNetWorkTable').bootstrapTable({
                url: '${pageContext.request.contextPath}/basic/station/netWorkAjax',
                pagination: true,
                striped: true,
                method: 'get',
                //showColumns : true,
                //showRefresh : true,
                mobileResponsive: true,
                toolbar: '#toolbar',
                cache: false,
                queryParams: oTableInit.queryParams,
                sidePagination: 'client',
                pageSize: 10,
                pageNumber: 1,
                pageList: [10],
                clickToSelect: true,
                silent: true, //刷新事件必须设置
                formatLoadingMessage: function () {
                    return "";
                },
                formatNoMatches: function () { //没有匹配的结果
                    return '';
                },
                onLoadError: function (data) {
                    $('#twoNetWorkTable').bootstrapTable(
                        'removeAll');
                },
                onClickRow: function (item, $element) {
                    //showChart(item);
                    return false;
                },
                onDblClickRow: function (item, $element) {
                    return false;
                },
                columns: [
                    {
                        field: 'standName',
                        title: '机组名称',
                        align: 'center',
                        valign: 'middle',
                        width: 150
                    }, {
                        field: 'pt3',
                        title: '供压(Mpa)',
                        valign: 'middle',
                        width: 50,
                        sortable: true

                    }, {
                        field: 'pt4',
                        title: '回压(Mpa)',
                        valign: 'middle',
                        width: 50,
                        sortable: true
                    }, {
                        field: 'pt3_pt4',
                        title: '压差(Mpa)',
                        valign: 'middle',
                        width: 50,
                        sortable: true
                    }, {
                        field: 'te3',
                        title: '供温(℃)',
                        valign: 'middle',
                        width: 50,
                        sortable: true
                    }, {
                        field: 'te4',
                        title: '回温(℃)',
                        valign: 'middle',
                        width: 50,
                        sortable: true
                    }, {
                        field: 'te3_te4',
                        title: '温差(℃)',
                        valign: 'middle',
                        width: 50,
                        sortable: true
                    }]
            });
        }

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {
                limit: params.limit,
                offset: params.offset,
                tTime: $('#tTime').val().trim(),
                curveSelect: $("#curveSelect option:selected").val(),
            };
            return temp;
        };
        return oTableInit;
    };

    var ButtonInit = function () {
        var oInit = new Object();
        var postdata = {limit: 10, offset: 0};
        //初始化页面上面的按钮事件
        oInit.Init = function () {
            $('#search').click(function () {
                $('#twoNetWorkTable').bootstrapTable('refresh');
            });
        };
        return oInit;
    };

    $('#tTime').datetimepicker({
        //minView: "month", //选择日期后，不会再跳转去选择时分秒 ;''
        format: "yyyy-mm-dd hh:ii",
        language: 'zh-CN', //汉化
        autoclose: true, //选择日期后自动关闭
        todayBtn: 1,
        endDate: new Date()
    });

    $('#twoNetWorkTable').on('load-success.bs.table', function (data) {
        fixTabkeColumn();
        var oneNetWorkData = JSON.stringify($('#twoNetWorkTable').bootstrapTable('getData'));
        var oneNetWorkDataJson = JSON.parse(oneNetWorkData);
        showChart(oneNetWorkDataJson);
    });

    //显示隐藏一些列
    function fixTabkeColumn() {
        if ($("#curveSelect option:selected").val() == "3") {//二网压力
            $('#twoNetWorkTable').bootstrapTable('showColumn', 'pt3');
            $('#twoNetWorkTable').bootstrapTable('showColumn', 'pt4');
            $('#twoNetWorkTable').bootstrapTable('showColumn', 'pt3_pt4');
            $('#twoNetWorkTable').bootstrapTable('hideColumn', 'te3');
            $('#twoNetWorkTable').bootstrapTable('hideColumn', 'te4');
            $('#twoNetWorkTable').bootstrapTable('hideColumn', 'te3_te4');
        } else if ($("#curveSelect option:selected").val() == "4") {
            $('#twoNetWorkTable').bootstrapTable('hideColumn', 'pt3');
            $('#twoNetWorkTable').bootstrapTable('hideColumn', 'pt4');
            $('#twoNetWorkTable').bootstrapTable('hideColumn', 'pt3_pt4');
            $('#twoNetWorkTable').bootstrapTable('showColumn', 'te3');
            $('#twoNetWorkTable').bootstrapTable('showColumn', 'te4');
            $('#twoNetWorkTable').bootstrapTable('showColumn', 'te3_te4');
        }
    };


    function loadData() {
        $('#twoNetWorkTable').bootstrapTable('refresh');
    }

    //显示chart

    function showChart(jsonData) {

        var myChart = echarts.init(document.getElementById('chart'), 'qqhr-Thrme');
        var xAxisData = new Array();
        var legendData = new Array();

        var pt3Data = new Array();//二网供水压力
        var pt4Data = new Array();//二网回水压力
        var pt3_pt4Data = new Array();//压差
        var te3Data = new Array();//二网供水温度
        var te4Data = new Array();//二网回水温度
        var te3_te4Data = new Array();//温差

        for (var i = 0; i < jsonData.length; i++) {
            xAxisData[i] = jsonData[i].standName;
            pt3Data[i] = jsonData[i].pt3;
            pt4Data[i] = jsonData[i].pt4;
            pt3_pt4Data[i] = jsonData[i].pt3_pt4;
            te3Data[i] = jsonData[i].te3;
            te4Data[i] = jsonData[i].te4;
            te3_te4Data = jsonData[i].te43_te4;
        }

        if ($("#curveSelect option:selected").val() == "3") {//二网温度
            legendData[0] = '二网供水压力';
            legendData[1] = '二网回水压力';
            legendData[2] = '压差';
            option.xAxis.data = xAxisData;
            option.title.text = '二网压力';
            option.legend.data = legendData;
            option.series[0].name = legendData[0];
            option.series[0].data = pt3Data;
            option.series[1].name = legendData[1];
            option.series[1].data = pt4Data;
            option.series[2].name = legendData[2];
            option.series[2].data = pt3_pt4Data;
            myChart.clear();
            myChart.setOption(option);
        } else if ($("#curveSelect option:selected").val() == "4") {//二网温度
            legendData[0] = '二网供水温度';
            legendData[1] = '二网回水温度';
            legendData[2] = '温差';
            option.xAxis.data = xAxisData;
            option.title.text = '二网温度';
            option.legend.data = legendData;
            option.series[0].name = legendData[0];
            option.series[0].data = te3Data;
            option.series[1].name = legendData[1];
            option.series[1].data = te4Data;
            option.series[2].name = legendData[2];
            option.series[2].data = te3_te4Data;
            myChart.clear();
            myChart.setOption(option);
        }
    };

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
                data: []
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

