<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<section>
    <section class="wrapper" style='padding-right: 0'>
        <div class="wrap-day chart-flow" id="morris">
            <div class="col-lg-12">
                <section class="panel panel-info">
                    <header class="panel-heading">
                        <span><b>部门管理</b></span>
                    </header>
                    <div class="panel-body">
                        <div id="toolbar" class="navbar-btn">
                            <form action="" class="form-inline pull-left pull-top">
                                <span>部门名称</span>
                                <input type="text" class="form-control " id="searchDept">
                                <button type="button" class="btn btn-sm btn-primary" id="search">
                                    <span class="fa fa-search" aria-hidden="true">查询</span>
                                </button>
                                <button type="button" class="btn btn-sm btn-primary" id="add">
                                    <span class="fa fa-plus" aria-hidden="true">新增</span>
                                </button>
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

    <!-- 增加操作员小窗 -->
    <div id="opaddModal" name="opaddModal" class="modal fade" tabindex="-1"
         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">新增操作员</div>
                <form action="" class="modal-body" id="addForm">
                    <table>
                        <tr>
                            <td class="opaddlabel">部门名称：</td>
                            <td><input type="text" class="form-control" id="adddepName" placeholder="请输入部门"></td>
                        </tr>
                        <tr>
                            <td class="opaddlabel">部门备注：</td>
                            <td><input type="text" class="form-control" id="adddepRmk" placeholder="请输入描述"></td>
                        </tr>
                    </table>
                    <div class="modal-footer">
                        <button class="btn btn-primary" type="button" onclick="addSub()">确定</button>
                        <button data-dismiss="modal" class="btn btn-default">取消</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- 修改小窗 -->
    <div class="modal fade" id="opAlterModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">修改操作员</div>
                <form action="" class="modal-body">
                    <input type="hidden" class="form-control" id="modifydepId">
                    <table>
                        <tr>
                            <td class="opaddlabel">部门名称：</td>
                            <td><input type="text" class="form-control" id="modifydepName"></td>
                        </tr>
                        <tr>
                            <td class="opaddlabel">部门备注：</td>
                            <td><input type="text" class="form-control" id="modifydepRmk"></td>
                        </tr>
                    </table>
                    <div class="modal-footer">
                        <button class="btn btn-primary" type="button" onclick="modifysub()">确定
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- 弹出部门人员 -->
    <div class="modal fade" id="detailModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="width: 1000px">
            <div class="modal-content">
                <div class="modal-header">部门操作员</div>
                <form action="" class="modal-body">
                    <input type="hidden" class="form-control" id="detaildepId">
                    <table id="tableOprInfo">
                    </table>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<script>
    $(function () {
        $("#sys").addClass("active");
        $("#dep").addClass("active");
        $("#dep").parents(".sub").show();

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
            $("#table").bootstrapTable('destroy');
            $('#table')
                .bootstrapTable(
                    {
                        url: '${pageContext.request.contextPath}/sys/dep/list',
                        pagination: true,
                        // height: 515,
                        striped: true,
                        method: 'post',
                        mobileResponsive: true,
                        toolbar: '#toolbar',
                        clickToSelect: true,
                        showRefresh: true,
                        cache: false,
                        dataType: "json",
                        contentType: "application/x-www-form-urlencoded",
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
                        columns: [
                            {
                                title: '序号',
                                field: 'num',
                                align: 'center',
                                bgcolor: 'blue',
                                formatter: function (value, row,
                                                     index) {
                                    return index + 1;
                                }
                            },
                            {
                                title: '部门ID',
                                field: 'depId',
                            },
                            {
                                title: '部门名称',
                                field: 'depName',
                                align: 'center'
                            },
                            {
                                title: '描述',
                                field: 'depRmk',
                                align: 'center'
                            },
                            {
                                title: '创建时间',
                                field: 'crtDt',
                                align: 'center'
                            },
                            {
                                title: '更新日期',
                                field: 'uptDt',
                                align: 'center'
                            },
                            {
                                title: '更新时间',
                                field: 'uptTm',
                                align: 'center',
                                formatter: function (value, row,
                                                     index) {
                                    return formatDeteTime(
                                        row.uptDt, row.uptTm);
                                }
                            },
                            {
                                title: '操作',
                                field: '',
                                align: 'center',
                                formatter: function (value, row,
                                                     index) {
                                    var str = "<a data-toggle='modal'   data-target='#detailModal' onclick='openDetail("
                                        + row.depId
                                        + ")' title='详情' class='fa fa-th'>";
                                    str += "<a href='javascript:;' class='fa fa-pencil' title=修改' onclick='openModify("
                                        + row.depId + ")'></a>";
                                    str += "<a href='javascript:;' class='fa fa-trash opDelate' title='删除' onclick='deleteOpr("
                                        + row.depId
                                        + ")' ></a>";
                                    return str;
                                }
                            }]
                    });
            $('#table').bootstrapTable('hideColumn', 'depId');
            $('#table').bootstrapTable('hideColumn', 'uptDt');
        }
        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {
                limit: params.limit,
                offset: params.offset,
                depName: $('#searchDept').val().trim(),
            };
            return temp;
        };
        return oTableInit;
    }

    var ButtonInit = function () {
        var oInit = new Object();
        var postdata = {
            limit: 20,
            offset: 0
        };
        //初始化页面上面的按钮事件
        oInit.Init = function () {
            //	$('#search').click(function() {
            //$('#table').bootstrapTable('resetSearch');
            //$('#table').bootstrapTable('refresh');
            //	});
            $('#add').click(function () {
                openAdd();
            });
        };
        return oInit;
    };

    //添加
    var openAdd = function () {
        $('#opaddModal').modal();
        $("#adddepName").val("");
        $("#adddepRmk").val("");
        //$("#adddepSts option[id='1']").val("1");
        /**
         $("#adddepName").focus(function(){
			  var depName_value=$(this).val();
			  if(depName_value==""){
				$("#SaddName").html("*部门不能为空");
			  }
			  if(depName_value!="" &&depName_value.length<2){
					$("#SaddName").html("*部门至少输入2位");
				  }
			 });
         $("#adddepName").blur(function(){
			 var depName_value=$(this).val();
				$("#SaddName").html("*请输入部门");
			 });
         **/
    }

    //添加提交
    var addSub = function () {
        var adddepName = $("#adddepName").val().trim();
        var adddepRmk = $("#adddepRmk").val().trim();
        var data = "&depName=" + adddepName + "&depRmk=" + adddepRmk;
        if (adddepName == "") {
            layer.msg('操作员名称不能为空', {
                icon: 2
            });
            return false;
        }
        //else if(adddepName.length <2){
        //   layer.msg('操作员名称不能小于1个字符',{icon: 2});
        //   return false;
        //   }
        if (checkName_Exist(adddepName)) {
            layer.msg('该部门已存在', {
                icon: 2
            });
            return false;
        }
        //提交
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/dep/insertDept',
            type: 'post',
            data: data,
            async: false,
            success: function (data) {
                if (data.msg == true) {
                    layer.msg('添加操作员成功', {
                        icon: 1
                    });
                    $('#opaddModal').modal('hide');
                    $('#table').bootstrapTable('resetSearch');
                    $('#table').bootstrapTable('refresh');
                } else {
                    layer.msg('添加操作员失败', {
                        icon: 2
                    });
                }
            },
            error: function () {
                layer.msg('添加操作员失败', {
                    icon: 2
                });
            }
        })
    }

    //部门名称校验
    var checkName_Exist = function (valueName) {
        var ret = false;
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/dep/existDepName',
            tyep: "post",
            data: {
                "depName": valueName
            },
            async: false,//是否异步执行
            success: function (msg) {
                ret = msg;
            },
            error: function (errorMSG) {
                ret = false;
            }
        });
        return ret;
    }

    //修改
    var old_value;
    var openModify = function (depId) {
        $('#opAlterModal').modal();
        $('#modifydepName').val("");
        $('#modifydepRmk').val("");
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/dep/findDepId',
            type: 'post',
            data: {
                "depId": depId
            },
            async: false,
            success: function (data) {
                $('#modifydepId').val(data.depId);
                $('#modifydepName').val(data.depName);
                $('#modifydepRmk').val(data.depRmk);
                old_value = $('#modifydepName').val();
            }
        })
    }

    //修改提交
    var modifysub = function () {
        var depId = $('#modifydepId').val();
        var depName = $('#modifydepName').val();
        var depRmk = $('#modifydepRmk').val();
        var data = "depId=" + depId + "&depName=" + depName + "&depRmk="
            + depRmk;
        if (old_value != depName) {
            if (checkName_Exist(depName)) {
                layer.msg('该部门已存在', {
                    icon: 2
                });
                return false;
            }
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/dep/updateDept',
            type: 'post',
            data: data,
            async: false,
            success: function (data) {
                if (data.msg == true) {
                    layer.msg('更新操作成功', {
                        icon: 1
                    });
                    $('#opAlterModal').modal('hide');
                    $('#table').bootstrapTable('resetSearch');
                    $('#table').bootstrapTable('refresh');
                } else {
                    layer.msg('更新操作失败', {
                        icon: 2
                    });
                }
            },
            error: function () {
                layer.msg('更新操作失败', {
                    icon: 2
                });
            }
        })

    }

    //删除
    var deleteOpr = function (depId) {
        layer
            .confirm(
                '是否要删除该部门?',
                {
                    icon: 3,
                    title: '删除部门',
                    btn: ['确定', '取消']
                },
                function (index) {
                    $
                        .ajax({
                            url: '${pageContext.request.contextPath}/sys/dep/find0prDepId',
                            type: 'post',
                            data: {
                                "depId": depId
                            },
                            async: false,
                            success: function (data) {
                                if (data.msg == true) {
                                    deleteDept(index)
                                } else {
                                    layer.msg('该部门存在操作员', {
                                        icon: 2
                                    });
                                }
                            },
                            error: function () {
                                layer.msg('删除部门失败', {
                                    icon: 2
                                });
                            }
                        })
                    parent.layer.close(index);
                });
    }

    //删除
    var deleteOpr = function (depId) {
        layer
            .confirm(
                '是否要删除该部门?',
                {
                    icon: 3,
                    title: '删除部门',
                    btn: ['确定', '取消']
                },
                function (index) {
                    $
                        .ajax({
                            url: '${pageContext.request.contextPath}/sys/dep/find0prDepId',
                            type: 'post',
                            data: {
                                "depId": depId
                            },
                            async: false,
                            success: function (data) {
                                if (data.msg == true) {
                                    layer.msg('删除部门成功', {
                                        icon: 1
                                    });
                                    deleteDept(depId);
                                } else {
                                    layer.msg('该部门存在操作员', {
                                        icon: 2
                                    });
                                }
                            },
                            error: function () {
                                layer.msg('删除部门失败', {
                                    icon: 2
                                });
                            }
                        })
                    parent.layer.close(index);
                });
    }

    //删除提交
    var deleteDept = function (depId) {
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/dep/deleteDept',
            type: 'post',
            data: {
                "depId": depId
            },
            async: false,
            success: function (data) {
                if (data.msg == true) {
                    layer.msg('删除部门成功', {
                        icon: 1
                    });
                    $('#table').bootstrapTable('resetSearch');
                    $('#table').bootstrapTable('refresh');
                } else {
                    layer.msg('该部门存在操作员', {
                        icon: 2
                    });
                }
            },
            error: function () {
                layer.msg('删除部门失败', {
                    icon: 2
                });
            }
        })
    }

    //详情模态框
    var openDetail = function (depId) {
        $("#tableOprInfo").bootstrapTable('destroy');
        $("#detaildepId").val(depId);
        initTable();
    }

    //详情初始化table
    function initTable() {
        $('#tableOprInfo').bootstrapTable({
            url: '${pageContext.request.contextPath}/sys/dep/detailDepId',
            pagination: true,
            height: 400,
            striped: true,
            method: 'post',
            clickToSelect: true,
            cache: false,
            dataType: 'json',
            contentType: "application/x-www-form-urlencoded",
            mobileResponsive: true,
            clickToSelect: true,
            cache: false,
            queryParams: function (params) {
                var temp = {
                    limit: params.limit,
                    offset: params.offset,
                    depId: $("#detaildepId").val(),
                };
                return temp;
            },
            sidePagination: 'server',
            pageSize: 10,
            pageNumber: 1,
            pageList: [10, 20, 50],
            silent: true, //刷新事件必须设置
            columns: [{
                title: '序号',
                field: 'num',
                align: 'center',
                bgcolor: 'blue',
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                title: '部门名称',
                field: 'oprInfo.orgId',
                align: 'center'
            }, {
                title: '操作员名称',
                field: 'oprInfo.oprName',
                align: 'center'
            }, {
                title: '部门操作员',
                field: 'oprInfo.loginId',
                align: 'center'
            }, {
                title: '登录账号',
                field: 'logonID',
                align: 'center'
            }, {
                title: '邮箱',
                field: 'oprInfo.email',
                align: 'center'
            },]
        });
    }

    //搜索查询
    $('#search').click(function () {
        $("#table").bootstrapTable('refresh', {
            url: '${pageContext.request.contextPath}/sys/dep/list',
            method: 'post',
            queryParams: function (params) {
                var temp = {
                    limit: params.limit,
                    offset: params.offset,
                    depName: $('#searchDept').val().trim(),
                };
                return temp;
            },
        })

    })
</script>

