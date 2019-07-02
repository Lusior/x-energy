<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<section class="wrapper" style='padding-right: 0'>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>角色管理</b></span>
                </header>
                <div class="panel-body">
                    <div id="toolbar" class="navbar-btn">
                        <form action="" class="form-inline pull-left pull-top">
                            <span>角色名称</span>
                            <input type="text" class="form-control" id="roleName">
                            <span>角色描述</span>
                            <input type="text" class="form-control" id="roleDes">
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

<!-- 增加角色小窗 -->
<div class="modal fade" id="opaddModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style='width: 55%;'>
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                </button>
                角色新增
            </div>
            <form action="" class="modal-body">
                <table class="modalLeft col-lg-5 ">
                    <tr>
                        <td class="opaddlabel">角色名称：</td>
                        <td><input type="text" class="form-control" id="addRoleName"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">角色描述：</td>
                        <td><input type="text" class="form-control" id="addRoleDes"></td>
                    </tr>
                </table>
                <ul class="ztree col-lg-5" id="ztreeadd"></ul>
            </form>
            <div class="modal-footer">
                <button class="btn btn-primary" type="button" onclick="addSub()">确定</button>
                <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- 详情小窗 -->
<div class="modal fade" id="opDetailModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog  modal-lg" style='width: 45%;'>
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                </button>
                角色详情
            </div>
            <form action="" class="modal-body">
                <table class="modalLeft col-lg-5">
                    <tr>
                        <td class="opaddlabel">角色名称：</td>
                        <td class="opNamem"><span id="detailRoleName"></span></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">角色描述：</td>
                        <td class="shenglue" style="max-width: 100px"><span
                                id="detailRoleDes"></td>
                    </tr>
                </table>
                <ul class="ztree  col-lg-5" id="ztreedetail"></ul>
            </form>
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
            </div>

        </div>
    </div>
</div>
<!-- 修改角色 -->
<div class="modal fade" id="opAlterModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style='width: 55%;'>
        <div class="modal-content">
            <div class="modal-header">角色修改</div>
            <form action="" class="modal-body">
                <input type="hidden" value="" id="modifyRoleId">
                <table class="modalLeft col-lg-5 ">
                    <tr>
                        <td class="opaddlabel">角色名称：</td>
                        <td><input type="text" class="form-control" value="" id="modifyRoleName"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">角色描述：</td>
                        <td><input type="text" class="form-control" value="" id="modifyRoleDes"></td>
                    </tr>
                </table>
                <ul class="ztree col-lg-5" id="ztreemodify"></ul>
            </form>
            <div class="modal-footer">
                <button class="btn btn-primary" type="button" onclick="modifySub()">确定</button>
                <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $("#sys").addClass("active");
        $("#role").addClass("active");
        $("#role").parents(".sub").show();

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
            $('#table').bootstrapTable(
                {
                    url: '${pageContext.request.contextPath}/sys/role/list',
                    pagination: true,
                    // height: 515,
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
                    onLoadError: function (data) {
                        $('#table').bootstrapTable('removeAll');
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
                            title: '角色名称',
                            field: 'roleName',
                            align: 'center'
                        },
                        {
                            title: '角色描述',
                            field: 'roleDes',
                            align: 'center'
                        },
                        {
                            title: '创建时间',
                            field: 'crtDt',
                            align: 'center',
                            formatter: function (value, row,
                                                 index) {
                                return formatDeteTime(
                                    row.crtDt, row.crtTm);
                            }
                        },
                        {
                            title: '更新时间',
                            field: 'uptDt',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return formatDeteTime(
                                    row.uptDt, row.uptTm);
                            }
                        },
                        {
                            title: '操作',
                            field: '',
                            align: 'center',
                            formatter: function (value, row, index) {
                                var str = "<a data-toggle='modal' href='javascript:void(0);' title=''"
                                    + '详情'
                                    + "'   onclick='openDetail("
                                    + row.roleId
                                    + ")' class='fa fa-th' >";
                                if (row.roleId != '1000') {
                                    str += "<a href='javascript:;' class='fa fa-trash opDelate' title='"
                                        + '删除'
                                        + "'  onclick='delRole("
                                        + row.roleId
                                        + ")' ></a>";
                                    str += "<a href='javascript:;' class='fa fa-pencil' title='"
                                        + '修改'
                                        + "'  onclick='openModify("
                                        + row.roleId
                                        + ")'></a>";
                                }
                                return str;
                            }
                        }]
                });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {
                limit: params.limit,
                offset: params.offset,
                roleName: $('#roleName').val().trim(),
                roleDes: $('#roleDes').val().trim()

            };
            return temp;
        };
        return oTableInit;
    };
    var ButtonInit = function () {
        var oInit = {};
        var postdata = {
            limit: 20,
            offset: 0
        };
        //初始化页面上面的按钮事件
        oInit.Init = function () {
            $('#search').click(function () {
                $('#table').bootstrapTable('refresh');
            });
            $('#add').click(function () {
                openAdd();
            });
        };
        return oInit;
    };
    //打开详情
    var openDetail = function (roleId) {
        $('#detailRoleName').html("");
        $('#detailRoleDes').html("");
        var dataTree = [];
        //获取角色详情
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/role/detail/'
            + roleId,
            type: 'get',
            async: false,
            success: function (data) {
                $('#detailRoleName').html(data.role.roleName);
                $('#detailRoleDes').html(data.role.roleDes);
                dataTree = data.treeData;
            },
            error: function () {
                layer.msg('error', {
                    icon: 2
                });
            }
        })
        var showSetting = {
            check: {
                enable: false,
                chkboxType: {
                    "Y": "ps",
                    "N": "ps"
                }
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            view: {
                showIcon: true,
                nameIsHTML: true
            }
        };

        $.fn.zTree.init($("#ztreedetail"), showSetting, dataTree);
        $('#opDetailModal').modal();
    }
    //打开增加
    var openAdd = function () {
        $('#addRoleName').val("");
        $('#addRoleDes').val("");
        var dataTree = [];
        //获取角色详情
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/role/item',
            type: 'get',
            async: false,
            success: function (data) {
                dataTree = data;
            },
            error: function () {
                layer.msg('error', {
                    icon: 2
                });
            }
        })
        var showSetting = {
            check: {
                enable: true,
                chkboxType: {
                    "Y": "ps",
                    "N": "ps"
                }
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            view: {
                showIcon: true,
                nameIsHTML: true
            }
        };

        $.fn.zTree.init($("#ztreeadd"), showSetting, dataTree);
        $('#opaddModal').modal();
    }
    var addSub = function () {
        var roleName = $('#addRoleName').val().trim();
        var roleDes = $('#addRoleDes').val().trim();
        if (roleName == "") {
            layer.msg('角色名称不能为空', {
                icon: 2
            });
            return false;
        } else if (roleName.length > 30) {
            layer.msg('角色名称不能大于30个字符', {
                icon: 2
            });
            return false;
        } else if (roleDes.length > 100) {
            layer.msg('角色描述不能大于100个字符', {
                icon: 2
            });
            return false;
        }
        var treeObj = $.fn.zTree.getZTreeObj('ztreeadd');
        var nodes = treeObj.getCheckedNodes();
        if (nodes == "" || nodes.length == 0) {
            layer.msg('请勾选权限', {
                icon: 2
            });
            return false;
        }
        var rolesStr = "";
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].isParent == false) {
                rolesStr += nodes[i].id + ",";
            }
        }
        var postData = "";
        postData = "roleName=" + roleName + "&roleDes=" + roleDes
            + "&rolesStr=" + rolesStr;
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/role/adddo',
            type: 'post',
            data: postData,
            async: false,
            success: function (data) {
                if (data == true) {
                    $('#opaddModal').modal('hide');
                    layer.msg('新增角色成功', {
                        icon: 1
                    });
                    $('#table').bootstrapTable('refresh');
                } else {
                    layer.msg('新增角色失败', {
                        icon: 2
                    });
                }
            },
            error: function () {
                layer.msg('error', {
                    icon: 2
                });
            }
        })
    }
    var openModify = function (roleId) {
        $('#modifyRoleId').val(roleId);
        var dataTree = [];
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/role/modify/'
            + roleId,
            type: 'get',
            success: function (data) {
                $('#modifyRoleName').val(data.role.roleName);
                $('#modifyRoleDes').val(data.role.roleDes);
                dataTree = data.treeData;
                var showSetting = {
                    check: {
                        enable: true,
                        chkboxType: {
                            "Y": "ps",
                            "N": "ps"
                        }
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    view: {
                        showIcon: true,
                        nameIsHTML: true
                    }
                };
                $.fn.zTree.init($("#ztreemodify"), showSetting, dataTree);
            },
            error: function () {
                layer.msg('error');
            }
        })

        $('#opAlterModal').modal();

    }
    var modifySub = function () {
        var roleId = $('#modifyRoleId').val();
        var roleName = $('#modifyRoleName').val().trim();
        var roleDes = $('#modifyRoleDes').val().trim();
        if (roleName == "") {
            layer.msg('角色名称不能为空', {
                icon: 2
            });
            return false;
        } else if (roleName.length > 30) {
            layer.msg('角色名称不能大于30个字符', {
                icon: 2
            });
            return false;
        } else if (roleDes.length > 100) {
            layer.msg('角色描述不能大于100个字符', {
                icon: 2
            });
            return false;
        }
        var treeObj = $.fn.zTree.getZTreeObj('ztreemodify');
        var nodes = treeObj.getCheckedNodes();
        if (nodes == "" || nodes.length == 0) {
            layer.msg('请购权权限', {
                icon: 2
            });
            return false;
        }
        var rolesStr = "";
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].isParent == false) {
                rolesStr += nodes[i].id + ",";
            }
        }
        var postData = "";
        postData = "roleId=" + roleId + "&roleName=" + roleName + "&roleDes="
            + roleDes + "&rolesStr=" + rolesStr;
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/role/modifydo',
            type: 'post',
            data: postData,
            async: false,
            success: function (data) {
                if (data == true) {
                    $('#opAlterModal').modal('hide');
                    layer.msg('更新角色成功', {
                        icon: 1
                    });
                    $('#table').bootstrapTable('refresh');
                } else {
                    layer.msg('修改角色错误', {
                        icon: 2
                    });
                }
            },
            error: function () {
                layer.msg('error', {
                    icon: 2
                });
            }
        })
    }
    var delRole = function (roleId) {
        layer.confirm('确定要删除角色吗?', {
            icon: 3,
            title: '删除',
            btn: ['确定', '取消']
        }, function (index) {
            $.ajax({
                url: '${pageContext.request.contextPath}/sys/role/delete/'
                + roleId,
                type: 'get',
                async: false,
                success: function (data) {
                    if (data == true) {
                        layer.msg('删除角色成功', {
                            icon: 1
                        });
                        $('#table').bootstrapTable('refresh');
                    } else {
                        layer.msg('删除角色失败', {
                            icon: 2
                        });
                    }
                },
                error: function () {
                    layer.msg('error', {
                        icon: 2
                    });
                }
            })
            layer.close(index);
        });
    }
</script>
</html>