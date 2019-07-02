<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="wrapper" style='padding-right: 0'>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>电耗分析</b></span>
                </header>
                <div class="panel-body">
                    <div id="toolbar" class="navbar-btn">
                        <form action="" class="form-inline pull-left pull-top"
                              id="electricity_form" name="electricity_form">
										<span
                                                style="display: block; width: 50px; float: left; color: #999; line-height: 34px;">日期段:</span>
                            <input id="beginTime" type="text"
                                   class="form-control default-date-picker dpd1 dealtime1"
                                   name="beginTime" readonly> <span
                                style="float: left; display: inline-block; line-height: 30px;">-</span>
                            <input id="endTime" type="text"
                                   class="form-control default-date-picker dpd2 dealtime2"
                                   name="endTime" readonly>
                            <button type="button" class="btn btn-sm btn-primary"
                                    id="search">
                                <span class="fa fa-search" aria-hidden="true">查询</span>
                            </button>
                            <%--<button type="button" class="btn btn-sm btn-primary"
                                    id="btn_download">
                                <span class="fa fa-cloud-download" aria-hidden="true">导出</span>
                            </button>--%>
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
        if (flag == 'chart') {
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

    var option = {
        title: {
            text: '电耗分析',
            subtext: '棒图'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['电耗']
        },
        toolbox: {
            show: true,
            orient: 'vertical',
            feature: {
                mark: {
                    show: true
                },
                show: true,
                magicType: {
                    show: true,
                    type: ['line', 'bar'],
                    title: {
                        line: "折线",
                        bar: "柱形"
                    }
                },
                restore: {
                    show: true,
                    title: "还原"
                },
                saveAsImage: {
                    show: true,
                    title: "导出"
                }
            }
        },
        calculable: true,
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: [{
            type: 'value'
        }],
        series: {
            name: '电耗',
            type: 'bar',
            itemStyle: {
                normal: {
                    // color: '#FFE4C4',
                    label: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            fontSize: 18
                        }
                    }
                }
            },
            data: []
        }
    };
    $(function () {
        $("#analysis").addClass("active");
        $("#analysis_electricity_analysis").addClass("active");
        $("#analysis_electricity_analysis").parents(".sub").show();
        $("#beginTime").val(${defaultDate});
        $("#endTime").val(${defaultDate});
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();
        // 基于准备好的dom，初始化echarts实例
        myChart = echarts.init(document.getElementById('char1'), 'qqhr-Thrme');
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    });
    var TableInit = function () {
        var oTableInit = {};
        //初始化Table
        oTableInit.Init = function () {
            $('#table').bootstrapTable(
                {
                    url: '${pageContext.request.contextPath}/basic/analysis/electricity/list',
                    pagination: true,
                    dataField: 'rows',
                    striped: true,
                    method: 'post',
                    mobileResponsive: true,
                    toolbar: '#toolbar',
                    clickToSelect: true,
                    cache: false,
                    queryParams: oTableInit.queryParams,
                    sidePagination: 'server',
                    pageSize: 12,
                    pageNumber: 1,
                    showRefresh: true,
                    pageList: [12, 20, 50],
                    silent: true, //刷新事件必须设置
                    formatLoadingMessage: function () {
                        return "";
                    },
                    formatNoMatches: function () { //没有匹配的结果
                        return '';
                    },
                    onLoadError: function (data) {
                        $('#table').bootstrapTable('removeAll');
                    },
                    onLoadSuccess: function (data) {
                        setChar();
                    },
                    columns: [
                        {
                            title: '换热站名称',
                            field: 'station_name',
                            align: 'center',
                            valign: 'middle'
                        }, {
                            field: 'real_area',
                            title: '实际面积(m²)',
                            align: 'center'
                        }, {
                            field: 'dt',
                            title: '日期',
                            align: 'center'
                        }, {
                            field: 'jqi_total',
                            title: '用电量',
                            align: 'center'
                        }, {
                            field: 'yxdhstr',
                            title: '运行单耗(kWh/m²)',
                            align: 'center',
                            sortable: true
                        }, {
                            field: 'hours',
                            title: '统计时间(小时)',
                            align: 'center',
                            valign: 'middle'
                        }, {
                            field: 'bDateTime',
                            title: '开始时间',
                            align: 'center',
                            valign: 'middle'
                        }, {
                            field: 'eDateTime',
                            title: '结束时间',
                            align: 'center',
                            valign: 'middle'
                        }]

                });
        }

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {
                limit: params.limit,
                offset: params.offset,
                beginTime: $('#beginTime').val().trim(),
                endTime: $('#endTime').val().trim(),
                sort: params.sort,      //排序列名  
                sortOrder: params.order //排位命令（desc，asc）
            };
            return temp;
        };
        return oTableInit;
    }
    var ButtonInit = function () {
        var oInit = new Object();
        var postdata = {
            limit: 12,
            offset: 0
        };
        //初始化页面上面的按钮事件
        oInit.Init = function () {
            $('#search').click(function () {
                $('#table').bootstrapTable('refresh');
            });
            $('#btn_download')
                .click(
                    function () {
                        $('#electricity_form')
                            .attr("action",
                                "${pageContext.request.contextPath}/basic/analysis/electricity/list/export");
                        $('#electricity_form').submit();
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
    var setChar = function () {
        var sname = new Array(), jqi = new Array();
        var jsonData = $('#table').bootstrapTable('getData');
        for (var i = 0; i < jsonData.length; i++) {
            sname[i] = jsonData[i].station_name;
            jqi[i] = jsonData[i].jqi_total;
        }
        option.xAxis.data = sname;
        option.series.data = jqi;
        myChart.clear();
        myChart.setOption(option);
    };
</script>

