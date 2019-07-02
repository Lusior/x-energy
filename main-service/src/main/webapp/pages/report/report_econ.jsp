<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<body>
<section class="wrapper" style='padding-right: 0'>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>经济分析报表</b></span>
                </header>
                <div class="panel-body">
                    <div id="toolbar" class="navbar-btn">
                        <form action="" class="form-inline pull-left pull-top" name="econ_form">
                            <span>日期段:</span>
                            <input id="beginTime" type="text" class="form-control default-date-picker dpd1 dealtime1" name="beginTime" readonly>
                            <span>-</span>
                            <input id="endTime" type="text" class="form-control default-date-picker dpd2 dealtime2" name="endTime" readonly>
                            <%--<span>选择换热站:</span>--%>
                            <%--<input type="text" class="form-control " id="stationName">--%>
                            <button type="button" class="btn btn-sm btn-primary" id="search">
                                <span class="fa fa-search" aria-hidden="true">查询</span>
                            </button>
                            <%--<button type="button" class="btn btn-sm btn-primary" id="btn_download">
                                <span id="export" class="fa fa-cloud-download" aria-hidden="true">导出</span>
                            </button>--%>
                            <button type="button" class="btn btn-sm btn-primary" id="btn_tubiao" onclick="show_change('chart')">
                                <span class="fa fa-adjust" aria-hidden="true">&nbsp;&nbsp;图形</span>
                            </button>
                            <button type="button" class="btn btn-sm btn-primary" style="display: none" id="btn_shuju" onclick="show_change('data')">
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
                        <div class="wrap-day chart-flow">
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
</body>
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

    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
                type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['热费', '电费', '水费']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: []
        },
        yAxis: [{
            type: 'value'
        }],
        series: [{
            name: '热费',
            type: 'bar',
            data: []
        }, {
            name: '电费',
            type: 'bar',
            data: []
        }, {
            name: '水费',
            type: 'bar',
            data: []
        }]
    };
    $(function () {
        $("#report").addClass("active");
        $("#report_economic").addClass("active");
        $("#report_economic").parents(".sub").show();
        var now = new Date();
        $("#beginTime").val(getBeforeDate(1));
        $("#endTime").val(getBeforeDate(1));
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
            $('#table').bootstrapTable({
                url: '${pageContext.request.contextPath}/report/econ/list',
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
                pageSize: 10,
                pageNumber: 1,
                pageList: [10, 20, 50],
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
                columns: [[{
                    title: '换热站名称',
                    field: 'station_name',
                    rowspan: 2,
                    align: 'center',
                    valign: 'middle',
                    sortable: true
                }, {
                    title: '面积',
                    colspan: 2,
                    align: 'center'
                }, {
                    title: '水',
                    colspan: 2,
                    align: 'center'
                }, {
                    title: '电',
                    colspan: 2,
                    align: 'center'
                }, {
                    title: '热',
                    colspan: 2,
                    align: 'center'
                }, {
                    field: 'price_total',
                    title: '总额(元)',
                    rowspan: 2,
                    align: 'center',
                    valign: 'middle'
                }], [{
                    field: 'sum_design_area',
                    title: '建筑面积',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'sum_real_area',
                    title: '设计面积',
                    align: 'center'
                }, {
                    field: 'sum_ft3q',
                    title: '累计水量(T)',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'sum_ft3q_price',
                    title: '水费(元)',
                    align: 'center'
                }, {
                    field: 'sum_jqi',
                    title: '累计电量(KWh)',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'sum_jqi_price',
                    title: '电费(元)',
                    align: 'center'
                }, {
                    field: 'sum_qqi',
                    title: '累计热量(GJ)',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'sum_qqi_price',
                    title: '热费(元)',
                    align: 'center'
                }]]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            return {
                limit: params.limit,
                offset: params.offset,
                beginTime: $('#beginTime').val().trim(),
                endTime: $('#endTime').val().trim(),
                // stationName: $('#stationName').val().trim()
            };
        };
        return oTableInit;
    };

    var ButtonInit = function () {
        var oInit = {};
        var postdata = {
            limit: 10,
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
                        $('#econ_form').attr("action", "${pageContext.request.contextPath}/report/econ/list/export");
                        $('#econ_form').submit();
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
        var sname = [], qqiPrices = [], jqiPrices = [], ft3qPrices = [];
        var jsonData = $('#table').bootstrapTable('getData');
        for (var i = 0; i < jsonData.length; i++) {
            sname[i] = jsonData[i].station_name;
            qqiPrices[i] = jsonData[i].sum_qqi_price;
            jqiPrices[i] = jsonData[i].sum_jqi_price;
            ft3qPrices[i] = jsonData[i].sum_jqi_price;
        }
        option.xAxis.data = sname;
        option.series[0].data = qqiPrices;
        option.series[1].data = jqiPrices;
        option.series[2].data = ft3qPrices;
        //option.title.text = "按日期显示";
        var larray = [];
        larray[0] = '热费';
        larray[1] = '电费';
        larray[2] = '水费';
        // console.log(larray);
        //option.legend.data =larray;
        myChart.clear();
        myChart.setOption(option);
    };
</script>