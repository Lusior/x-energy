<%@ page contentType="text/html;charset=UTF-8" %>

<style>
    .fa-cloud-download:before, .fa-search:before {
        margin: 0 5px 0 0;
        float: left;
    }
</style>

<section class="wrapper" style='padding-right: 0'>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>天气情况查询</b></span>
                </header>
                <div class="panel-body">
                    <div id="toolbar" class="navbar-btn">
                        <form action="" class="form-inline pull-left pull-top"
                              id="weather_form" name="weather_form">

                            <span>日期段:</span> <input id="beginTime" type="text"
                                                     class="form-control default-date-picker dpd1 dealtime1"
                                                     name="beginTime" readonly> <span>-</span> <input
                                id="endTime" type="text"
                                class="form-control default-date-picker dpd2 dealtime2"
                                name="endTime" readonly>
                            <button type="button" class="btn btn-sm btn-primary"
                                    id="search">
                                <span class="fa fa-search" aria-hidden="true">查询</span>
                            </button>
                            <button type="button" class="btn btn-sm btn-primary"
                                    id="btn_download">
                                <span class="fa fa-cloud-download" aria-hidden="true">导出</span>
                            </button>
                            <button type="button" class="btn btn-sm btn-primary"
                                    id="btn_tubiao" onclick="show_change('chart')">
                                <span class="fa fa-adjust" aria-hidden="true">&nbsp;&nbsp;图形</span>
                            </button>
                            <button type="button" class="btn btn-sm btn-primary"
                                    style="display: none" id="btn_shuju"
                                    onclick="show_change('data')">
                                <span class="fa fa-asterisk" aria-hidden="true">&nbsp;&nbsp;数据</span>
                            </button>
                        </form>
                    </div>
                    <div class="row row-lg" id="shuju">
                        <div class="col-sm-12" style="border-bottom: 1px solid #eee;">
                            <table id="table" class="table"></table>
                        </div>
                    </div>

                    <div class="row row-lg" id="tubiao" style="display: none;">
                        <div class="wrap-day chart-flow" id="morris">
                            <div class="panel-body">
                                <div id='char1' style="width: 1200px; height: 400px;"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</section>

<script>
    function show_change(flag) {
        if (flag === 'chart') {
            $("#tubiao").show();
            $("#shuju .fixed-table-container").hide();
            $("#btn_shuju").show();
            $("#btn_tubiao").hide();
        } else {
            $("#tubiao").hide();
            $("#shuju .fixed-table-container").show();
            $("#btn_shuju").hide();
            $("#btn_tubiao").show();
        }

    }

    option1 = {
        title: {
            text: '',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['最高气温', '最低气温', '平均温度']
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
        xAxis:
            {
                type: 'category',
                boundaryGap: false,
                data: []
            }
        ,
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
                data: [],
                markPoint: {
                    data: []
                }
            },
            {
                name: '最低气温',
                type: 'line',
                data: [],
                markPoint: {
                    data: []
                }
            },
            {
                name: '平均温度',
                type: 'line',
                data: [],
                markPoint: {
                    data: []
                }
            }
        ]
    };
    option2 = {
        title: {
            text: '',
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['实时温度']
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
        xAxis:
            {
                type: 'category',
                boundaryGap: false,
                data: []
            }
        ,
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    formatter: '{value} °C'
                }
            }
        ],
        series:
            {
                name: '温度',
                type: 'line',
                data: [],
                markPoint: {
                    data: []
                }
            }
    };
    $(function () {
        $("#weather").addClass("active");
        $("#weather_situation").addClass("active");
        $("#weather_situation").parents(".sub").show();
        var etime = getBeforeDate(0);
        $("#endTime").val(etime);

        var btime = getBeforeDate(6);
        $("#beginTime").val(btime);

        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();
        // 基于准备好的dom，初始化echarts实例
        myChart1 = echarts.init(document.getElementById('char1'), 'qqhr-Thrme');
        // 使用刚指定的配置项和数据显示图表。
        //myChart1.setOption(option1);

        $("#selectGroup").change(function () {
            var p1 = $(this).val();
            if (p1 === '按小时显示') {
                $("#showday").show();
            } else if (p1 === '按日期显示') {
                $("#showday").hide();
                $('#cBeginTime').val("");
                tbsx_day();
            }
        });
    });

    var TableInit = function () {
        var oTableInit = {};
        //初始化Table
        oTableInit.Init = function () {
            $('#table').bootstrapTable({
                url: '${pageContext.request.contextPath}/weather/situation/list',
                pagination: true,
                height: 555,
                striped: true,
                method: 'post',

                mobileResponsive: true,
                toolbar: '#toolbar',
                clickToSelect: true,
                cache: false,
                queryParams: oTableInit.queryParams,
                sidePagination: 'server',
                pageSize: 10,
                pageNumber: 1,
                showRefresh: true,
                pageList: [10, 20, 50],
                silent: true,  //刷新事件必须设置
                formatLoadingMessage: function () {
                    return "";
                },
                formatNoMatches: function () {  //没有匹配的结果
                    return '';
                },
                onLoadError: function (data) {
                    $('#table').bootstrapTable('removeAll');
                },
                onLoadSuccess: function (data) {
                    tbsx_day();
                },
                columns: [
                    {
                        title: '序号',
                        field: 'num',
                        align: 'center',
                        bgcolor: 'blue',
                        formatter: function (value, row, index) {
                            return index + 1;
                        }
                    },
                    {
                        title: '预报日期', field: 'day', align: 'center', formatter: function (value, row, index) {
                            return value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6);
                        }
                    },
                    {title: '白天最高温°C', field: 'teH', align: 'center'},
                    {title: '夜晚最低温°C', field: 'teL', align: 'center'},
                    {
                        title: '平均温度°C', field: 'teA', align: 'center', formatter: function (value, row, index) {
                            return parseInt(value);
                        }
                    },
                    {title: '白天天气状况', field: 'teC1', align: 'center'},
                    {title: '夜晚天气状况', field: 'teC2', align: 'center'},
                    {
                        title: '白天风力风向', field: 'teWd1', align: 'center', formatter: function (value, row, index) {
                            return row.teWd1 + " " + row.teWe1;
                        }
                    },
                    {
                        title: '夜晚风力风向', field: 'teWd2', align: 'center', formatter: function (value, row, index) {
                            return row.teWd2 + " " + row.teWe2;
                        }
                    }
                ]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            console.log(params);
            return {
                limit: params.limit,
                offset: params.offset,
                beginTime: $('#beginTime').val().trim(),
                endTime: $('#endTime').val().trim(),
            };
        };
        return oTableInit;
    };
    var ButtonInit = function () {
        var oInit = {};
        //初始化页面上面的按钮事件
        oInit.Init = function () {
            $('#search').click(function () {
                $('#table').bootstrapTable('refresh');
            });
            $('#btn_download').click(function () {
                $('#weather_form').attr("action", "${pageContext.request.contextPath}/weather/situation/list/export");
                $('#weather_form').submit();
            });
        };
        return oInit;
    };

    $('#beginTime').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        autoclose: true, //选择日期后自动关闭
        todayBtn: 1
    });
    $('#endTime').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        autoclose: true, //选择日期后自动关闭
        todayBtn: 1
    });
    $('#cBeginTime').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        autoclose: true, //选择日期后自动关闭
        todayBtn: 1,
        endDate: new Date()
    }).on('changeDate', function (ev) {
        tbsx_hour($("#cBeginTime").val());
    });
    var tbsx_day = function () {
        $("#showday").hide();
        var days = [], teHs = [], teLs = [], avgs = [];
        var jsonData = $('#table').bootstrapTable('getData');
        for (var i = 0; i < jsonData.length; i++) {
            var dayStr = jsonData[i].day;
            days[i] = dayStr.substring(4, 6) + "-" + dayStr.substring(6);
            teHs[i] = parseInt(jsonData[i].teH);
            teLs[i] = parseInt(jsonData[i].teL);
            avgs[i] = parseInt((teHs[i] + teLs[i]) / 2);
        }
        option1.xAxis.data = days;
        option1.series[0].data = teHs;
        option1.series[1].data = teLs;
        option1.series[2].data = avgs;
        option1.title.text = "按日期显示";
        myChart1.clear();
        myChart1.setOption(option1);
    };
    var tbsx_hour = function (day) {
        var daystr = day;
        day = day.replace(new RegExp("-", "gm"), "");
        $.ajax({
            url: '${pageContext.request.contextPath}/weather/situation/day/hour/weather?day=' + day,
            type: 'get',
            success: function (data) {
                var hours = [], tems = [];
                for (var i = 0; i < data.length; i++) {
                    tems[i] = parseInt(data[i].tem);
                    var tm = data[i].crt_tm;
                    hours[i] = tm.substring(0, 2) + ":" + tm.substring(2, 4);
                }
                option2.xAxis.data = hours;
                option2.series.data = tems;
                option2.legend.data = "实时温度";
                option2.title.text = daystr + " 实时温度";
                myChart1.clear();
                myChart1.setOption(option2);
            }
        })
    }
</script>
