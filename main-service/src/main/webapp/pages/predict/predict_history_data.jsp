<%@ page contentType="text/html;charset=UTF-8" %>
<section class="wrapper" style='padding-right: 0'>
    <div class="row state-overview col-lg-12" style='padding-right: 0'>
    </div>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>负荷预测历史</b></span>
                </header>
                <div class="panel-body">
                    <div id="toolbar" class="navbar-btn">
                        <form action="" id="history_form" name="history_form">
                            <span style="float:left; line-height:34px;">选择日期：</span>
                            <input id="tTime" name="tTime" type="text" data-date-format="yyyy-mm-dd"
                                   class="form-control default-date-picker dpd1 dealtime1" readonly value="">
                            <button type="button" class="btn btn-sm btn-primary" id="search">
                                <span class="fa fa-search" aria-hidden="true">查询</span>
                            </button>
                        </form>
                    </div>
                    <div class="row row-lg">
                        <table id="historyTable"></table>
                    </div>
                </div>
            </section>
        </div>
    </div>
</section>

<script type="text/javascript">
    $(function () {
        var now = new Date();
        $("#tTime").val(now.getFullYear() + "-" + (parseInt(now.getMonth()) + 1) + "-" + (now.getDate()));
        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();
    });

    var TableInit = function () {
        var oTableInit = {};
        //初始化Table
        oTableInit.Init = function () {
            $('#historyTable').bootstrapTable({
                url: '${pageContext.request.contextPath}/predict/history/history_data',
                pagination: true,
                method: 'post',
                showRefresh: true,
                mobileResponsive: true,
                toolbar: '#toolbar',
                cache: false,
                queryParams: oTableInit.queryParams,
                sidePagination: 'client',
                pageSize: 10,
                pageNumber: 1,
                pageList: [10],
                silent: true, //刷新事件必须设置
                formatLoadingMessage: function () {
                    return "";
                },
                formatNoMatches: function () { //没有匹配的结果
                    return '';
                },
                onLoadError: function () {
                    $('#historyTable').bootstrapTable('removeAll');
                },
                columns: [
                    {
                        title: '日期',
                        field: 'opTime',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) {
                            return value.substring(0, 10);
                        }
                    }, {
                        title: '昨天耗热',
                        field: 'q2',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit
                    }, {
                        title: '今日均温',
                        field: 'tpj2',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit
                    }, {
                        title: '预测今天耗热',
                        field: 'q3',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit
                    }, {
                        title: '预测明日均温',
                        field: 'tpj3',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit
                    }, {
                        title: '预测明天耗热',
                        field: 'q4',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit
                    }, {
                        title: '预测后日均温',
                        field: 'tpj4',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit
                    }, {
                        title: '预测后天耗热',
                        field: 'q5',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit
                    }, {
                        title: '预测大后日均温',
                        field: 'tpj5',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit
                    }, {
                        title: '预测大后天耗热',
                        field: 'q6',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit
                    }
                ]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            return {
                limit: params.limit,
                offset: params.offset,
                beginTime: $('#tTime').val().trim()
            };
        };
        return oTableInit;
    };

    var ButtonInit = function () {
        var oInit = {};
        //初始化页面上面的按钮事件
        oInit.Init = function () {
            $('#search').click(function () {
                $('#historyTable').bootstrapTable('refresh');
            });
        };
        return oInit;
    };

    $('#tTime').datetimepicker({
        minView: "month", //选择日期后，不会再跳转去选择时分秒 ;''
        format: "yyyy-mm-dd",
        language: 'zh-CN', //汉化
        autoclose: true, //选择日期后自动关闭
        todayBtn: 1,
        endDate: new Date()
    });

    //设置table column颜色一网参数
    function formatTableUnit(value, row, index) {
        var front_color = 'gray';
        var bg_color = changeRowStyle(index);//'#F5F5F5';
        return {
// 	    	{#classes: class_name,#}
            css: {
                "color": front_color,
                "background-color": bg_color
            }
        }
    }

    function changeRowStyle(index) {
        //var pageNumber= $("#actualTable").bootstrapTable('getOptions').pageNumber;
        //if(pageNumber == 1) {//如果是第一页
        if (index === 0 || index === 1 || index === 2 || index === 4 || index === 8 || index === 10 || index === 13 || index === 14 || index === 15) {
            //return '#FFFFFF';
        } else {
            //return '#F5F5F5';
        }
        //}
    }
</script>
