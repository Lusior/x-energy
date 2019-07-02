<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="wrapper" style='padding-right: 0'>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>操作员管理</b></span>
                </header>
                <div class="panel-body">
                    <div id="toolbar" class="navbar-btn">
                        <form action="" class="form-inline pull-left pull-top">
                            <span>操作员名称</span>
                            <input type="text" class="form-control " id="searchOprName">
                            <span>登陆账号</span>
                            <input type="text" class="form-control " id="searchLoginId">
                            <span>部门</span>
                            <input type="text" class="form-control " id="searchDepName">
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
<div class="modal fade" id="opaddModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">新增操作员</div>
            <form action="" class="modal-body" id="addForm" name="addForm">
                <table>
                    <tr>
                        <td class="opaddlabel">操作员名称<font color="red">*</font> ：
                        </td>
                        <td><input type="text" class="form-control" id="addOprName"/></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">登陆账号 <font color="red">*</font>：
                        </td>
                        <td><input type="text" class="form-control" id="addLoginId"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">部门<font color="red">*</font>：
                        </td>
                        <td><select id="addDep" class="form-control"></select></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">登陆密码 <font color="red">*</font>：
                        </td>
                        <td><input type="password" class="form-control"
                                   id="addLoginPwd"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">确认密码<font color="red">*</font>：
                        </td>
                        <td><input type="password" class="form-control"
                                   id="addLoginPwdCon"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">电子邮箱：</td>
                        <td><input type="text" class="form-control" id="addEmail"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">联系电话：</td>
                        <td><input type="text" class="form-control" id="addPhone"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">拥有角色<font color="red">*</font>：
                        </td>
                        <td><label for="1" class="opRole" id="addRoles"></label></td>
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
<!-- 详情小窗 -->
<div class="modal fade" id="opDetailModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                操作员详情
            </div>
            <form action="" class="modal-body">
                <table class="">
                    <tr>
                        <td class="opaddlabel">操作员名称</font> ：
                        </td>
                        <td><span id="detailOprName" class="form-control"></span></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">登陆账号 ：</td>
                        <td><span id="detailLoginId" class="form-control"></span></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">部门：</td>
                        <td><span 　 id="detailDep" class="form-control"></span></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">登陆密码 ：</td>
                        <td><span class="form-control">********</span></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">电子邮箱：</td>
                        <td><span id="detailEmail" class="form-control"></span></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">联系电话：</td>
                        <td><span id="detailPhone" class="form-control"></span></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">拥有角色：</td>
                        <td><label for="1" class="opRole" id="detailRoles"></label>
                        </td>
                    </tr>
                </table>
                <div class="modal-footer">
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
                <input type="hidden" class="form-control" id="modifyOprId">
                <table>
                    <tr>
                        <td class="opaddlabel">登陆账号<font color="red">*</font>：
                        </td>
                        <td class="opBelongm"><span id="modifyLoginId"></span></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">操作员名称<font color="red">*</font>：
                        </td>
                        <td><input type="text" class="form-control"
                                   id="modifyOprName"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">部门<font color="red">*</font>：
                        </td>
                        <td><select id="modifyDep" class="form-control"></select></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">电子邮箱：</td>
                        <td><input type="text" class="form-control" id="modifyEmail"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">联系电话：</td>
                        <td><input type="text" class="form-control" id="modifyPhone"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">拥用角色<font color="red">*</font>：
                        </td>
                        <td><label for="1" class="opRole" id="modifyRoles">
                        </label></td>
                    </tr>
                </table>
                <div class="modal-footer">
                    <button class="btn btn-primary" type="button"
                            onclick="modifysub()">确定
                    </button>
                    <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 修改登陆密码小窗 -->
<div class="modal fade" id="opLoginPwdModal" tabindex="-1"
     role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">修改登陆密码</div>
            <form action="" class="modal-body">
                <input type="hidden" id="modifyPassOprId" value="">
                <table>
                    <tr>
                        <td class="opaddlabel">登陆账号：</td>
                        <td><span id="modifyPassLoginName" style="width: 220px"></span></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">新登陆密码<font color="red">*</font>：
                        </td>
                        <td><input type="password" class="form-control"
                                   id="modifyPassLoginPassWord" maxlength="30"></td>
                    </tr>
                    <tr>
                        <td class="opaddlabel">重复新登陆密码<font color="red">*</font>：
                        </td>
                        <td><input type="password" class="form-control" id="modifyPassLoginPassWordCon" maxlength="30"></td>
                    </tr>
                </table>
                <div class="modal-footer">
                    <button class="btn btn-primary" type="button"
                            onclick="modifyPassSub()">确定
                    </button>
                    <button data-dismiss="modal" class="btn btn-default" type="cancel">取消</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(function () {
        $("#sys").addClass("active");
        $("#oprinfo").addClass("active");
        $("#oprinfo").parents(".sub").show();

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
            $('#table').bootstrapTable(
                    {
                        url: '${pageContext.request.contextPath}/sys/oprinfo/list',
                        pagination: true,
                        // height: 555,
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
                                title: '操作员名称',
                                field: 'oprName',
                                align: 'center'
                            },
                            {
                                title: '登陆账号',
                                field: 'loginId',
                                align: 'center'
                            },
                            {
                                title: '部门',
                                field: 'depName',
                                align: 'center'
                            },
                            {
                                title: '电子邮箱',
                                field: 'email',
                                align: 'center'
                            },
                            {
                                title: '联系电话',
                                field: 'phone',
                                align: 'center'
                            },
                            {
                                title: '创建时间',
                                field: 'crtDt',
                                align: 'center',
                                formatter: function (value, row, index) {
                                    return formatDeteTime(
                                        row.crtDt, row.crtTm);
                                }
                            },
                            {
                                title: '更新时间',
                                field: 'uptDt',
                                align: 'center',
                                formatter: function (value, row, index) {
                                    return formatDeteTime(row.uptDt, row.uptTm);
                                }
                            },
                            {
                                title: '操作',
                                field: '',
                                align: 'center',
                                formatter: function (value, row, index) {
                                    var str = "<a  href='javascript:;' class='fa fa-th' title='详情' onclick='openDetail("
                                        + row.oprId + ")'></a>";
                                    str += "<a href='javascript:;' class='fa fa-pencil' title=修改' onclick='openModify("
                                        + row.oprId + ")'></a>";
                                    str += "<a href='javascript:;' class='fa fa-trash opDelate' title='删除' onclick='deleteOpr("
                                        + row.oprId
                                        + ")' ></a>";
                                    str += "<a href='javascript:;' class='fa fa-key' title='修改登陆密码' onclick='openModifyPass(\""
                                        + row.oprId
                                        + "\",\""
                                        + row.oprName
                                        + "\")' ></a>";
                                    return str;
                                }
                            }]
                    });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            return {
                limit: params.limit,
                offset: params.offset,
                oprName: $('#searchOprName').val().trim(),
                loginId: $('#searchLoginId').val().trim(),
                depName: $('#searchDepName').val().trim()
            };
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
    //添加
    var openAdd = function () {
        $("#addLoginId").val("");
        $("#addOprName").val("");
        $("#addLoginPwd").val("");
        $("#addPhone").val("");
        $("#addEmail").val("");
        $("#addLoginPwdCon").val("");
        $.ajax({
                url: '${pageContext.request.contextPath}/sys/role/list',
                type: 'get',
                date: "{limit: 50, offset: 0}",
                success: function (data) {
                    var rolesHtml = "";
                    for (var i = 0; i < data.rows.length; i++) {
                        rolesHtml += "<input type='checkbox' ";
                        rolesHtml += "value=" + data.rows[i].roleId;
                        rolesHtml += ">"
                            + data.rows[i].roleName + "&nbsp;&nbsp;";
                    }
                    $('#addRoles').html(rolesHtml);
                },
                error: function () {
                    layer.msg('error', {
                        icon: 2
                    });
                }
            });
        //获取用户部门列表
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/dep/list',
            type: 'get',
            date: "{limit: 50, offset: 0}",
            success: function (data) {
                for (var k = 0; k < data.rows.length; k++) {
                    $("#addDep").append(
                        "<option value='" + data.rows[k].depId + "'> "
                        + data.rows[k].depName + "</option>");
                }
            },
            error: function () {
                layer.msg('error', {
                    icon: 2
                });
            }
        });

        $('#opaddModal').modal();
    };
    //添加
    var addSub = function () {
        var loginId = $("#addLoginId").val().trim();
        var oprName = $("#addOprName").val().trim();
        var loginPwd = $("#addLoginPwd").val().trim();
        var phone = $("#addPhone").val().trim();
        var email = $("#addEmail").val().trim();
        var loginPwdCon = $("#addLoginPwdCon").val();
        var roles = $('#addRoles input[type=checkbox]:checked');
        var depId = $("#addDep").val();
        if (oprName == "") {
            layer.msg('操作员名称不能为空', {
                icon: 2
            });
            return false;
        } else if (oprName.length > 50) {
            layer.msg('操作员名称不能大于50个字符', {
                icon: 2
            });
            return false;
        } else if (loginId == "") {
            layer.msg('登陆账号不能为空', {
                icon: 2
            });
            return false;
        } else if (loginId.length > 20) {
            layer.msg('登陆账号不能大于20', {
                icon: 2
            });
            return false;
        } else if (loginPwd == "") {
            layer.msg('登陆密码不能为空', {
                icon: 2
            });
            return false;
        } else if (loginPwd.length > 30) {
            layer.msg('登陆密码不能大于30个字符', {
                icon: 2
            });
            return false;
        } else if (loginPwd != loginPwdCon) {
            layer.msg('二次密码不一致', {
                icon: 2
            });
            return false;
        } else if (email.length > 100) {
            layer.msg('邮箱不能大于100个字符', {
                icon: 2
            });
            return false;
        } else if (phone.length > 30) {
            layer.msg('联系电话不能大于30个字符', {
                icon: 2
            });
            return false;
        } else if (roles == "" || roles.length < 1) {
            layer.msg('请勾选角色', {
                icon: 2
            });
            return false;
        } else {
            if (!isExsitLoginId(loginId)) {
                layer.msg('该登陆账号已存在', {
                    icon: 2
                });
                return false;
            }
        }
        var rolesStr = "";
        for (var i = 0; i < roles.length; i++) {
            rolesStr += roles[i].value;
            rolesStr += ",";
        }
        var data = "loginId=" + loginId + "&oprName=" + oprName + "&depId="
            + depId + "&loginPwd=" + loginPwd + "&email=" + email
            + "&phone=" + phone + "&rolesStr=" + rolesStr;
        //提交
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/oprinfo/adddo',
            type: 'post',
            data: data,
            success: function (data) {
                if (data == true) {
                    layer.msg('添加操作员成功', {
                        icon: 1
                    });
                    $('#opaddModal').modal('hide');
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
    };

    var openModify = function (oprId) {
        $("#modifyOprId").val(oprId);
        $('#modifyLoginId').html("");
        $('#modifyOprName').val("");
        $('#modifyEmail').val("");
        $('#modifyPhone').val("");
        $('#modifyRoles').val("");
        $("#modifyDep").empty();
        $.ajax({
                url: '${pageContext.request.contextPath}/sys/oprinfo/modify/' + oprId,
                type: 'get',
                success: function (data) {
                    $('#modifyOprId').val(data.oprInfo.oprId);
                    $('#modifyOprName').val(data.oprInfo.oprName);
                    $('#modifyLoginId').html(data.oprInfo.loginId);
                    $('#modifyEmail').val(data.oprInfo.email);
                    $('#modifyPhone').val(data.oprInfo.phone);
                    var rolesHtml = "";
                    for (var i = 0; i < data.allRolesList.length; i++) {
                        rolesHtml += "<input type='checkbox' ";
                        for (var j = 0; j < data.rolesList.length; j++) {
                            if (data.allRolesList[i].roleId == data.rolesList[j].roleId) {
                                rolesHtml += "checked=true ";
                            }
                        }
                        rolesHtml += "value=" + data.allRolesList[i].roleId;
                        rolesHtml += ">" + data.allRolesList[i].roleName + "&nbsp;&nbsp;";
                    }
                    $('#modifyRoles').html(rolesHtml);
                    for (var k = 0; k < data.depList.length; k++) {
                        $("#modifyDep").append(
                            "<option value='" + data.depList[k].depId + "'> "
                            + data.depList[k].depName
                            + "</option>");
                    }
                },
                error: function () {
                    layer.msg('error');
                }
            });
        $('#opAlterModal').modal();
    };

    //修改提交
    var modifysub = function () {
        var oprId = $('#modifyOprId').val();
        var oprName = $('#modifyOprName').val();
        var email = $('#modifyEmail').val();
        var phone = $('#modifyPhone').val();
        var roles = $('#modifyRoles input[type=checkbox]:checked');
        var depId = $("#modifyDep").val();
        if (oprName == "") {
            layer.msg('操作员名称不能为空', {
                icon: 2
            });
            return false;
        } else if (oprName.length > 50) {
            layer.msg('操作员名称不能大于50个字符', {
                icon: 2
            });
            return false;
        } else if (email.length > 100) {
            layer.msg('邮箱不能大于100个字符', {
                icon: 2
            });
            return false;
        } else if (phone.length > 30) {
            layer.msg('联系电话不能大于30个字符', {
                icon: 2
            });
            return false;
        } else if (roles == "" || roles.length < 1) {
            layer.msg('请勾选角色', {
                icon: 2
            });
            return false;
        }

        var rolesStr = "";
        for (var i = 0; i < roles.length; i++) {
            rolesStr += roles[i].value;
            rolesStr += ",";
        }
        var data = "oprId=" + oprId + "&oprName=" + oprName + "&email=" + email + "&phone=" + phone + "&rolesStr=" + rolesStr + "&depId=" + depId;
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/oprinfo/modifydo',
            type: 'post',
            data: data,
            success: function (data) {
                if (data == true) {
                    $('#opAlterModal').modal('hide');
                    layer.msg('修改操作员信息成功', {
                        icon: 1
                    });
                    $('#table').bootstrapTable('refresh');
                } else {
                    layer.msg('修改操作员信息失败', {
                        icon: 2
                    });
                }
            },
            error: function () {
                layer.msg('修改操作员信息失败', {
                    icon: 2
                });
            }
        })
    };
    //检查登陆ID 是否 存在
    var isExsitLoginId = function (loginId) {
        var url = '${pageContext.request.contextPath}/sys/oprinfo/isExist?loginId=' + loginId;
        var ret = false;
        $.ajax({
            url: url,
            type: 'get',
            async: false,
            success: function (result) {
                ret = result;
            },
            error: function () {
                layer.msg('error');
            }
        });
        return ret;
    };
    var openDetail = function (oprId) {
        $('#detailOprName').html("");
        $('#detailLoginId').html("");
        $('#detailEmail').html("");
        $('#detailPhone').html("");
        $('#detailRoles').html("");
        $('#detailDep').html("");
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/oprinfo/detail/' + oprId,
            type: 'get',
            success: function (data) {
                $('#detailOprName').html(data.oprInfo.oprName);
                $('#detailLoginId').html(data.oprInfo.loginId);
                $('#detailEmail').html(data.oprInfo.email);
                $('#detailPhone').html(data.oprInfo.phone);
                $('#detailDep').html(data.oprInfo.depName);
                var rolesHtml = "";
                for (var i = 0; i < data.rolesList.length; i++) {
                    rolesHtml += "<input type='checkbox' checked=true";
                    rolesHtml += "value=" + data.rolesList[i].roleId;
                    rolesHtml += ">" + data.rolesList[i].roleName + "&nbsp;&nbsp;";
                }
                $('#detailRoles').html(rolesHtml);
            },
            error: function () {
                layer.msg('error');
            }
        });
        $('#opDetailModal').modal();
    };
    //删除
    var deleteOpr = function (oprId) {
        layer.confirm('是否要删除该操作员?', {
            icon: 3,
            title: '删除操作员',
            btn: ['确定', '取消']
        }, function (index) {
            $.ajax({
                url: '${pageContext.request.contextPath}/sys/oprinfo/delete/' + oprId,
                type: 'get',
                success: function (data) {
                    if (data == true) {
                        layer.msg('删除操作员成功');
                        $('#table').bootstrapTable('refresh');
                    } else {
                        layer.msg('error', {
                            icon: 2
                        });
                    }
                },
                error: function () {
                    layer.msg('error');
                }
            });
            layer.close(index);
        });
    };

    /** 打开修改登陆密码 */
    var openModifyPass = function (oprId, loginName) {
        $('#modifyPassLoginName').html(loginName);
        $('#modifyPassLoginPassWord').val("");
        $('#modifyPassLoginPassWordCon').val("");
        $('#modifyPassOprId').val(oprId);
        $('#opLoginPwdModal').modal();
    };
    /** 修改登陆密码 */
    var modifyPassSub = function () {
        var oprId = $('#modifyPassOprId').val();
        var loginPwd = $('#modifyPassLoginPassWord').val().trim();
        var loginPwdCon = $('#modifyPassLoginPassWordCon').val().trim();
        if (oprId == "") {
            layer.msg("修改错误", {
                icon: 2
            });
            return false;
        } else if (loginPwd == "") {
            layer.msg('登陆密码不能为空', {
                icon: 2
            });
            return false;
        } else if (loginPwd.length > 30) {
            layer.msg('登陆密码不能大于30个字符', {
                icon: 2
            });
            return false;
        } else if (loginPwd != loginPwdCon) {
            layer.msg('两次密码不一致', {
                icon: 2
            });
            return false;
        }
        var data = "oprId=" + oprId + "&loginPwd=" + loginPwd;
        $.ajax({
            url: '${pageContext.request.contextPath}/sys/oprinfo/modifyPwddo',
            type: 'post',
            data: data,
            success: function (data) {
                if (data == true) {
                    $('#opLoginPwdModal').modal('hide');
                    layer.msg('修改登陆密码成功', {
                        icon: 1
                    });
                } else {
                    layer.msg('修改登陆密码失败', {
                        icon: 2
                    });
                }
            },
            error: function () {
                layer.msg('修改登陆密码失败');
            }
        });
        $('#opLoginPwdModal').modal();
    }
</script>

