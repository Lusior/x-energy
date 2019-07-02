<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section class="wrapper" style='padding-right: 0'>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>季度报表</b></span>
                </header>
                <div class="panel-body">
                    <div id="toolbar" class="navbar-btn">
                        <form action="" class="form-inline pull-left pull-top"
                              id="quarter_form" name="quarter_form">
                            <ul class="require" style="overflow: hidden;">
                                <span style="float: left; line-height: 34px;">选择年份：</span>
                                <input id="tTime" type="text"
                                       class="form-control default-date-picker dpd1 dealtime1"
                                       readonly>
                                <select class="form-control " name="quarter" id="quarter">
                                    <option value="1">第一季度</option>
                                    <option value="2">第二季度</option>
                                    <option value="3">第三季度</option>
                                    <option value="4">第四季度</option>
                                </select>
                                <!-- 											<span>选择换热站:</span> -->
                                <!-- 											<input type="text" class="form-control " id="stationName"> -->
                                <button type="button" class="btn btn-sm btn-primary"
                                        id="search">
                                    <span class="fa fa-search" aria-hidden="true">查询</span>
                                </button>
                                <%--<button type="button" class="btn btn-sm btn-primary"
                                        id="btn_download">
												<span id="export" class="fa fa-cloud-download"
                                                      aria-hidden="true">导出</span>
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
        $("#report_quarter").addClass("active");
        $("#report_quarter").parents(".sub").show();
        //初始化时间控件
        $("#tTime").val(getCurrYear());
        $("#quarter").val(getCurrQuarter());
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
                url: '${pageContext.request.contextPath}/report/quarter/list',
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
                year: $('#tTime').val().trim(),
                quarter: $('#quarter').val(),
                // stationName: $('#stationName').val().trim()
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
                        $('#quarter_form')
                            .attr("action",
                                "${pageContext.request.contextPath}/report/quarter/list/export");
                        $('#quarter_form').submit();
                    });
        };
        return oInit;
    };

    $('#tTime').datetimepicker({
        format: 'yyyy',
        startView: "decade",
        minView: 'decade',
        language: 'zh-CN',
        autoclose: true,
        endDate: new Date()
    });

    function getHeight() {
        return $(window).height() - $('h1').outerHeight(true);
    }
</script>
