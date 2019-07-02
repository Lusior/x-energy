<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
<section class="wrapper" style='padding-right: 0'>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>日报表</b></span>
                </header>
                <div class="panel-body">
                    <div id="toolbar" class="navbar-btn">
                        <form action="" class="form-inline pull-left pull-top"
                              id="day_form" name="day_form">
                            <span>选择日期：</span> <input id="tTime" type="text"
                                                      class="form-control default-date-picker dpd1 dealtime1"
                                                      readonly value="">
                            <!-- 											<span>选择换热站:</span> <input -->
                            <!-- 											type="text" class="form-control " id="stationName"> -->
                            <button type="button" class="btn btn-sm btn-primary"
                                    id="search">
                                <span class="fa fa-search">查询</span>
                            </button>
                            <%--<button type="button" class="btn btn-sm btn-primary"
                                    id="btn_download">
                                <span class="fa fa-cloud-download" id="weather_export">导出</span>
                            </button>--%>
                        </form>
                    </div>
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <table id="table" class="table"></table>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</section>
<script type="text/javascript">
    $(function () {
        $("#report").addClass("active");
        $("#report_day").addClass("active");
        $("#report_day").parents(".sub").show();
        //初始化时间控件
        $("#tTime").val(getBeforeDate(1));
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
            $('#table').bootstrapTable({
                url: '${pageContext.request.contextPath}/report/day/list',
                pagination: true,
                dataField: 'rows',
                striped: true,
                method: 'post',
                mobileResponsive: true,
                toolbar: '#toolbar',
                clickToSelect: true,
                showRefresh: true,
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
                columns: [[{
                    title: '换热站名称',
                    field: 'station_name',
                    rowspan: 2,
                    align: 'center',
                    valign: 'middle',
                    sortable: true
                }, {
                    title: '补水量',
                    colspan: 2,
                    align: 'center'
                }, {
                    title: '用电量',
                    colspan: 2,
                    align: 'center'
                }, {
                    title: '供热量',
                    colspan: 2,
                    align: 'center'
                }], [{
                    field: 'day_ft3q_total',
                    title: '补水量(t)',
                    sortable: true,
                    editable: true,
                    align: 'center'
                }, {
                    field: 'day_ft3q',
                    title: '累积量(t)',
                    align: 'center'
                }, {
                    field: 'day_jqi_total',
                    title: '用电量(KWh)',
                    sortable: true,
                    editable: true,
                    align: 'center'
                }, {
                    field: 'day_jqi',
                    title: '累积量(KWh)',
                    align: 'center'
                }, {
                    field: 'day_qqi_total',
                    title: '供热量(GJ)',
                    sortable: true,
                    editable: true,
                    align: 'center'
                }, {
                    field: 'day_qqi',
                    title: '累积量(GJ)',
                    align: 'center'
                }]]
            });
        }

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {
                limit: params.limit,
                offset: params.offset,
                searchDateTime: $('#tTime').val().trim(),
// 					stationName : $('#stationName').val().trim()
            };
            return temp;
        };
        return oTableInit;
    }
    var ButtonInit = function () {
        var oInit = new Object();
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
                        $('#day_form')
                            .attr("action",
                                "${pageContext.request.contextPath}/report/day/list/export");
                        $('#day_form').submit();
                    });
        };
        return oInit;
    };
    $('#tTime').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒
        format: "yyyy-mm-dd", //选择日期后，文本框显示的日期格式
        language: 'zh-CN', //汉化
        autoclose: true, //选择日期后自动关闭
        todayBtn: 1,
        endDate: new Date()
    });

    function getHeight() {
        return $(window).height() - $('h1').outerHeight(true);
    }
</script>