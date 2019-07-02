<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<section class="wrapper" style='padding-right: 0'>
    <div class="row state-overview col-lg-12" style='padding-right: 0'>
    </div>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <div class="panel-body">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <select id="curveSelect" name="curveSelect" class="form-control" onchange="$('#actualTable').bootstrapTable('refresh')">
                                <c:forEach var="project" items="${projects}" varStatus="status">
                                    <c:choose>
                                        <c:when test="${status.index == 0}">
                                            <option selected="selected" value="${project.projectId}">${project.projectName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${project.projectId}">${project.projectName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <table id="actualTable"></table>
                        </div>
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
                url: '${pageContext.request.contextPath}/valve/actualDataAjax',
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
                        field: 'append',
                        align: 'center',
                        valign: 'middle'
                    }, {
                        title: '阀门状态',
                        field: 'online',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableUnit,
                        formatter: function (value, row, index) {
                            if (value === false) {
                                return "关闭";
                            } else {
                                return "打开";
                            }
                        }
                    }, {
                        title: '控制开度(%)',
                        field: 'openCtl',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        formatter: formatIntValue
                    }, {
                        title: '实际开度(%)',
                        field: 'open',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        formatter: formatIntValue
                    }, {
                        title: '回温(℃)',
                        field: 'tempBack',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        formatter: formatIntValue
                    }, {
                        title: '回温补偿(℃)',
                        field: 'tempHome',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        formatter: formatIntValue
                    }, {
                        title: '补偿后回温(℃)',
                        field: 'tempBack',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        formatter: formatIntValue
                    }, {
                        title: '补偿后目标回温(℃)',
                        field: 'tempHome',
                        align: 'center',
                        valign: 'middle',
                        sortable: true,
                        formatter: function (value, row, index) {
                            return 37.1;
                        }
                    }
                ]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            return {
                limit: params.limit,
                offset: params.offset,
                projectId: $("#curveSelect option:selected").val()
            };
        };
        return oTableInit;
    };

    function formatIntValue(value, row, index) {
        return value / 10;
    }

    function formatTableUnit(value, row, index) {
        if (value === '关闭') {
            return {
                css: {
                    "color": '#ffffff',
                    "background-color": '#ff4c4d'
                }
            }
        } else {
            return {
                css: {
                    "color": '#ffffff',
                    "background-color": '#42bf45'
                }
            }
        }
    }
</script>
