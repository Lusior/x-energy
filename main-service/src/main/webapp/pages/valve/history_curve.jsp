<%@ page contentType="text/html;charset=UTF-8" %>
<section class="wrapper" style='padding-right: 0'>
    <div class="row state-overview col-lg-12" style='padding-right: 0'>
    </div>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>历史曲线（需求暂未提供）</b></span>
                </header>
                <div class="panel-body">
                    <div class="row row-lg">
                        <select id="curveSelect" name="curveSelect" class="form-control" onchange="$('#actualTable').bootstrapTable('refresh')">
                            <option selected="selected" value="nor">北区</option>
                            <option value="sou">南区</option>
                        </select>
                        <table id="actualTable"></table>
                    </div>
                </div>
            </section>
        </div>
    </div>
</section>

<script type="text/javascript">
    $(function () {
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
            $('#actualTable').bootstrapTable({
                url: '${pageContext.request.contextPath}/valve/actualdataAjax',
                <%--url: '${pageContext.request.contextPath}//predict/history/history_data',--%>
                pagination: true,
                method: 'post',
                showRefresh: true,
                mobileResponsive: true,
                toolbar: '#toolbar',
                cache: false,
                queryParams: oTableInit.queryParams,
                sidePagination: 'client',
                pageSize: 20,
                pageNumber: 1,
                pageList: [10, 20, 30],
                silent: true, //刷新事件必须设置
                formatLoadingMessage: function () {
                    return "";
                },
                formatNoMatches: function () { //没有匹配的结果
                    return '';
                },
                onLoadError: function () {
                    $('#actualTable').bootstrapTable('removeAll');
                },
                onLoadSuccess: function (data) {
                    console.info(JSON.stringify(data));
                },
                columns: [
                    {
                        title: '序号',
                        align: 'center',
                        valign: 'middle',
                        formatter: function (value, row, index) {
                            return index + 1;
                        }
                    }, {
                        title: '阀门编号',
                        field: 'valveNum',
                        align: 'center',
                        valign: 'middle'
                    }, {
                        title: '阀门状态',
                        field: 'valveSta',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit
                    }, {
                        title: '控制开度(%)',
                        field: 'controlSet',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        title: '实际开度(%)',
                        field: 'siteDeg',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        title: '回温(℃)',
                        field: 'returnTep',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        title: '回温补偿(℃)',
                        field: 'returnTepCom',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        title: '补偿后回温(℃)',
                        field: 'returnTepAftCom',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }, {
                        title: '补偿后目标回温(℃)',
                        field: 'targetReturnTep',
                        align: 'center',
                        valign: 'middle',
                        sortable: true
                    }
                ]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            return {
                limit: params.limit,
                offset: params.offset,
                valveWhere: $("#curveSelect option:selected").val()
            };
        };
        return oTableInit;
    };

    function formatTableUnit(value, row, index) {
        if (value === 'online') {
            return {
                css: {
                    "color": '#ffffff',
                    "background-color": '#ff4c4d'
                }
            }
        } else if (value === 'offline') {
            return {
                css: {
                    "color": '#ffffff',
                    "background-color": '#42bf45'
                }
            }
        }
    }
</script>
