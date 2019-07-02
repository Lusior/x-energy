<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section class="wrapper" style='padding-right: 0'>
    <div class="row state-overview col-lg-12" style='padding-right: 0'>
    </div>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>实时数据</b></span>
                    <span style="float:right;">&nbsp;公共参数&nbsp;</span>
                    <span class="roundElement"
                          style="background:#32CD32;float:right;width:30px;height:12px;margin-top:5px;">&nbsp;</span>
                    <span style="float:right;">&nbsp;二网参数&nbsp;</span>
                    <span class="roundElement"
                          style="background:blue;float:right;width:30px;height:12px;margin-top:5px;">&nbsp;</span>
                    <span style="float:right;">&nbsp;一网参数&nbsp;</span>
                    <span class="roundElement"
                          style="background:red;float:right;width:30px;height:12px;margin-top:5px;">&nbsp;</span>
                </header>
                <div class="panel-body">
                    <%--<form action="" id="actualdata_form" name="actualdata_form">
                        <div id="toolbar" class="navbar-btn">
                            <button type="button" class="btn btn-sm btn-primary" id="btn_download" id="btn_download">
                                <span class="fa fa-cloud-download" aria-hidden="true">导出</span>
                            </button>
                            <button type="button" class="btn btn-sm btn-primary" id="btn_download">
                                <span class="fa fa-cloud-download" aria-hidden="true" onclick="printTable()">打印</span>
                            </button>
                        </div>
                    </form>--%>
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <table id="actualTable" class="table" data-show-columns="true" data-row-style="rowStyle">
                            </table>
                            <div id="printDiv" style="display:none">
                                <!-- 这个table样式不要写在style里，打印的时候，不认 -->
                                <table id="printTable" border="1"
                                       style="border-collapse: collapse; table-layout: fixed; border: solid 1px black; font-size: 10px">
                                    <tr>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;"
                                            rowspan="2">时间
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;"
                                            rowspan="2">换热站名称
                                        </td>
                                        <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;"
                                            rowspan="2">机组名称
                                        </td>
                                        <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px; font-size: 12px"
                                            colspan="8">一网运行参数
                                        </td>
                                        <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px; font-size: 12px"
                                            colspan="9">二网运行参数
                                        </td>
                                        <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;"
                                            rowspan="2">水箱液位
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;"
                                            rowspan="2">累计补水量
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;"
                                            rowspan="2">累计电量
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;"
                                            rowspan="2">累计热量
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            供压
                                        </td>
                                        <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            回压
                                        </td>
                                        <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            供温
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            回温
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 3px; padding-right: 3px;">
                                            调节阀开度
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            瞬时热量
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            瞬时流量
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            累计流量
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            供压
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            回压
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            压差
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            供温
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            回温
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            流量
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            补水流量
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            累计补水流量
                                        </td>
                                        <td style="border: solid 1px black; white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">
                                            循环泵频率
                                        </td>
                                    </tr>
                                    <tbody>
                                    <c:forEach var="collectExtendData" items="${collectExtendDataList}"
                                               varStatus="status">
                                        <tr>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.opTimeStr}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.stationName}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.standName}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.pt1}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.pt2}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.te1}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.te2}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.cvi1}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.qi}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.ft1}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.ft1q}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.pt3}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.pt4}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.pt3_pt4}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.te3}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.te4}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.ft2}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.ft3}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.ft3q}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.fc1v1}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.lt1}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.ft3q}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.jqi}</td>
                                            <td style="white-space: nowrap; text-align: center; padding-left: 5px; padding-right: 5px;">${collectExtendData.qqi}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
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
        $("#heat_station_time").addClass("active");
        $("#heat_station_time").parents(".sub").show();

        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();
        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();

        //这里合并一下打印表格
        //引入merge_table_cells.js  这个还要整理，有点乱。
        $('#printTable').mergeCell({
            //第一个传入的值，是一样的合并，所以我传入了stationName
            cols: [1, 3, 4, 5, 6, 8, 9, 10, , 19, 20, 22, 23]
        });


        //处理颜色

// 		$("actualTable tbody").find('tr').each(function(index) {
// 			alert(index);
// 		       $(this).find("td").eq(4).css('background-color','green');
// 		       alert(123312);
// 		});
        //if(_index != 0 && _index != 1 && _index != 4 && _index != 6) {
        $("#actualTable tbody tr:eq(3)").addClass("active");
        $("actualTable tbody tr").each(function (index, element) {
            if (index == 2) {
                $(this).attr("style", "background:red;")
            }
        });

        //$("#actualTable tbody tr:eq(1)").hide();
// 			$("#actualTable tbody tr:eq(3)").css("background-color", "#bbf"); 
// 			$("#actualTable tbody tr:eq(3)").hide(); 
        //}
        //处理颜色结束
    });

    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#actualTable').bootstrapTable({
                url: '${pageContext.request.contextPath}/basic/station/actualdataAjax',
                pagination: true,
                striped: false,
                method: 'post',
                showColumns: false,
                showRefresh: true,
                mobileResponsive: false,
                toolbar: '#toolbar',
                //fixedColumns: true,
                //fixedNumber: 3,
                cache: false,
                queryParams: oTableInit.queryParams,
                sidePagination: 'client',
                pageSize: 20,
                pageNumber: 1,
                pageList: [10],
                silent: true, //刷新事件必须设置
                formatLoadingMessage: function () {
                    return "";
                },
                formatNoMatches: function () { //没有匹配的结果
                    return '';
                },
                onLoadError: function (data) {
                    $('#actualTable').bootstrapTable(
                        'removeAll');
                },
                columns: [
                    {
                        title: '时间',
                        field: 'opTimeStr',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableWidth
                    }, {
                        title: '换热站名称',
                        field: 'stationName',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableWidth
                    }, {
                        title: '机组名称',
                        field: 'standName',
                        align: 'center',
                        valign: 'middle',
                        cellStyle: formatTableWidth
                    }, {
                        field: 'pt1',
                        title: '供压(Mpa)',
                        align : 'center',
                        valign: 'middle',
                        //width: 80,
                        cellStyle: formatTableUnit1
                    }, {
                        field: 'pt2',
                        title: '回压(Mpa)',
                        align : 'center',
                        valign: 'middle',
                        width: 80,
                        cellStyle: formatTableUnit1
                    }, {
                        field: 'te1',
                        title: '供温(℃)',
                        align : 'center',
                        valign: 'middle',
                        width: 80,
                        cellStyle: formatTableUnit1
                    }, {
                        field: 'te2',
                        title: '回温(℃)',
                        align : 'center',
                        valign: 'middle',
                        width: 80,
                        cellStyle: formatTableUnit1
                    }, {
                        field: 'cvi1',
                        title: '调节阀开度(%)',
                        align : 'center',
                        valign: 'middle',
                        width: 110,
                        cellStyle: formatTableUnit1
                    }, {
                        field: 'qi',
                        title: '瞬时热量(GJ/h)',
                        align : 'center',
                        valign: 'middle',
                        width: 110,
                        cellStyle: formatTableUnit1
                    }, {
                        field: 'ft1',
                        title: '瞬时流量(t/h)',
                        align : 'center',
                        valign: 'middle',
                        width: 110,
                        cellStyle: formatTableUnit1
                    }, {
                        field: 'ft1q',
                        title: '累计流量(t/h)',
                        align : 'center',
                        valign: 'middle',
                        width: 110,
                        cellStyle: formatTableUnit1
                    }, {
                        field: 'pt3',
                        title: '供压(Mpa)',
                        align : 'center',
                        valign: 'middle',
                        width: 80,
                        cellStyle: formatTableUnit2
                    }, {
                        field: 'pt4',
                        title: '回压(Mpa)',
                        align : 'center',
                        valign: 'middle',
                        width: 80,
                        cellStyle: formatTableUnit2
                    }, {
                        field: 'pt3_pt4',
                        title: '压差(Mpa)',
                        align : 'center',
                        valign: 'middle',
                        width: 100,
                        cellStyle: formatTableUnit2
                    }, {
                        field: 'te3',
                        title: '供温(℃)',
                        //align : 'center',
                        valign: 'middle',
                        width: 80,
                        cellStyle: formatTableUnit2
                    }, {
                        field: 'te4',
                        title: '回温(℃)',
                        align : 'center',
                        valign: 'middle',
                        width: 80,
                        cellStyle: formatTableUnit2
                    }, {
                        field: 'ft2',
                        title: '二供流量(t/h)',
                        align : 'center',
                        valign: 'middle',
                        width: 110,
                        cellStyle: formatTableUnit2
                    }, {
                        field: 'ft3',
                        title: '补水流量(t/h)',
                        //align : 'center',
                        valign: 'middle',
                        width: 110,
                        cellStyle: formatTableUnit2
                    }, {
                        field: 'fc1v1',
                        title: '循环泵频率(Hz)',
                        align : 'center',
                        valign: 'middle',
                        width: 120,
                        cellStyle: formatTableUnit2
                    }, {
                        field: 'lt1',
                        title: '水箱液位(mm)',
                        align: 'center',
                        valign: 'middle',
                        width: 150,
                        cellStyle: formatTableUnit3
                    }, {
                        field: 'ft3q',
                        title: '累计补水量(t/h)',
                        align: 'center',
                        valign: 'middle',
                        width: 150,
                        cellStyle: formatTableUnit3
                    }, {
                        field: 'jqi',
                        title: '累计电量(kWh/h)',
                        align: 'center',
                        valign: 'middle',
                        width: 150,
                        cellStyle: formatTableUnit3
                    }, {
                        field: 'qqi',
                        title: '累计热量(GJ/h)',
                        align: 'center',
                        valign: 'middle',
                        width: 150,
                        cellStyle: formatTableUnit3
                    }]
            });
        }

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {
                limit: params.limit,
                offset: params.offset
            }
            return temp;
        };
        return oTableInit;
    };

    var ButtonInit = function () {
        var oInit = new Object();
        var postdata = {
            limit: 10,
            offset: 0
        };
        //初始化页面上面的按钮事件
        oInit.Init = function () {
            $('#search').click(function () {
                //$('#table').bootstrapTable('refresh');
                //alert(JSON.stringify($('#table').bootstrapTable('getData')));
            });
            $('#add').click(function () {
                openAdd();
            });
            $('#btn_download').click(function () {
                $('#actualdata_form').attr("action", "${pageContext.request.contextPath}/basic/station/actualdataAjax/export");
                $('#actualdata_form').submit();
            });
        };
        return oInit;
    };

    var LODOP;

    function printTable() {
        LODOP = getLodop();
        LODOP.PRINT_INIT("actual_data");
        LODOP.SET_PRINT_STYLE("FontSize", 20);
        //LODOP.SET_PRINT_STYLE("Bold",1);
        LODOP.ADD_PRINT_TEXT(20, 370, 460, 30, "哈尔滨热力分公司瞬时数据");
        LODOP.SET_PRINT_STYLE("FontSize", 12);
        LODOP.ADD_PRINT_TEXT(60, 620, 460, 20, "压力(Mpa) 温度(℃) 流量(t/h) 频率(Hz) 液位(mm)");
        LODOP.SET_PRINT_PAGESIZE(2, 0, 0, "A4");
        LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED", 1);//横向时的正向显示}
        LODOP.ADD_PRINT_HTM(100, 20, "100%", "100%", document.getElementById("printDiv").innerHTML);
        LODOP.SET_PRINT_MODE("RESELECT_ORIENT", 1); //用户不可以自己设置打印方向
        LODOP.PREVIEW();
    };

    //数据翻页成功后，合并一些单元格
    var pageNum = 1;
    $('#actualTable').on('page-change.bs.table', function (data) {
        var actualData = '{' + '"data"' + ':' + JSON.stringify($('#actualTable').bootstrapTable('getData')) + '}';
        var actualDataJson = JSON.parse(actualData);
        var stationName = actualDataJson.data[0].stationName; //stationName 第一个换热站的名称
        if (pageNum == 1) {
            pageNum = 2;
            stationName = actualDataJson.data[10].stationName; //stationName 第二页第一个换热站的名称
        } else {
            pageNum = 1;
            $('#actualTable').bootstrapTable(('refresh'));
            return;
        }
        var _index = 0;//要合并的行
        var j = 0;//合并几行
        var len = actualDataJson.data.length;//这里看一下长度
        $.each(actualDataJson.data, function (i, item) {
            if (pageNum == 2) {
                if (i > 9) {
                    if (stationName != item.stationName) {
                        mergeTableCells(_index, j);//合并单元格
                        _index = i - 10;
                        j = 1;
                        stationName = item.stationName;

                    } else {
                        j = j + 1;
                    }
                    if (i == len - 1) {//lzq最后一行，不能合，在找原因，这里硬 合一下
                        mergeTableCells(6, 4);
                    }
                }
            }
        });
    });

    $('#actualTable').on('load-success.bs.table', function (data) {

        var actualData = '{' + '"data"' + ':' + JSON.stringify($('#actualTable').bootstrapTable('getData')) + '}';
        console.log("actualData=" + actualData);
        var actualDataJson = JSON.parse(actualData);
        var _index = 0;
        var j = 0;
        console.log(actualDataJson);
        var stationName = actualDataJson.data[0].stationName; //stationName 第一个热电站的名称
        $.each(actualDataJson.data, function (i, item) {
            if (stationName != item.stationName) {
                mergeTableCells(_index, j);//合并单元格
                _index = i;
                j = 1;
                stationName = item.stationName;
            } else {
                j = j + 1;
            }
        });
    });

    //合并单元格函数,只能处理bootstrapTable,  type: 1处理的是actualTable. 2处理的是打印table. index：第几行开始合并，第0个开始， rowspan:合并几行
    function mergeTableCells(index, rowspan) {
        //定义一个数组用来存放要合并的field
        var arr = new Array('stationName', 'pt1', 'pt2', 'te1', 'te2', 'qi', 'ft1', 'ft1q', 'lt1', 'jqi', 'qqi');
        for (var i = 0; i < arr.length; i++) {
            $('#actualTable').bootstrapTable('mergeCells', {
                index: index,
                field: arr[i],
                colspan: 0,
                rowspan: rowspan
            });
        }
    }

    //设置table column颜色一网参数
    function formatTableUnit1(value, row, index) {
        var front_color = 'red';
        var bg_color = changeRowStyle(index);//'#F5F5F5';
        return {
// 	    	{#classes: class_name,#}
            css: {
                "color": front_color,
                "background-color": bg_color
            }
        }
    }

    function formatTableUnit2(value, row, index) {
        var front_color = 'blue';
        var bg_color = changeRowStyle(index);//'white';
        return {
// 	    	{#classes: class_name,#}
            css: {
                "color": front_color,
                "background-color": bg_color
            }
        }
    }

    function formatTableUnit3(value, row, index) {
        var front_color = '#32CD32';
        var bg_color = changeRowStyle(index);//'white';
        return {
// 	    	{#classes: class_name,#}
            css: {
                "color": front_color,
                "background-color": bg_color
            }
        }
    }

    function formatTableWidth(value, row, index) {
        return {
// 	    	{#classes: class_name,#}
            css: {
            		"overflow": 'hidden',
                "text-overflow":'ellipsis',
                "white-space":'nowrap'
            }
        }
    }


    function changeRowStyle(index) {
        //var pageNumber= $("#actualTable").bootstrapTable('getOptions').pageNumber;
        //if(pageNumber == 1) {//如果是第一页
        if (index == 0 || index == 1 || index == 2 || index == 4 || index == 8 || index == 10 || index == 13 || index == 14 || index == 15) {
            //return '#FFFFFF';
        } else {
            //return '#F5F5F5';
        }
        //}
    }
</script>

