<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="wrapper" style='padding-right: 0'>
    <div class="row state-overview col-lg-12" style='padding-right: 0'>
    </div>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <!-- 负荷预测参数维护 -->
            <section class="panel panel-warning">
                <header class="panel-heading">
                    <span><b>负荷预测参数维护</b></span>
                </header>
                <div class="panel-body" style="margin-left:33px;">
                    <form id="predictDataForm" class="form-group-lg pull-left pull-top">
                        <div class="form-group" style="line-height:20px;margin-left:-33px;">
                            <label>&nbsp;城市：</label>
                            <label>${thePredict.getCityName()}</label>
                        </div>
                        <div class="form-group" style="line-height:20px;margin-left:-33px;">
                            <label>&nbsp;供暖室外计算温度：</label>
                            <label>${thePredict.getTw1()}（℃）</label>
                        </div>
                        <div class="form-group" style="line-height:20px;margin-left:-33px;">
                            <label>&nbsp;供暖期平均温度：</label>
                            <label>${thePredict.getTpj1()}（℃）</label>
                        </div>
                        <div class="form-group" style="line-height:20px;margin-left:-33px;">
                            <label>&nbsp;供暖天数：</label>
                            <label>${thePredict.getN()}（d）</label>
                        </div>
                        <div class="form-group" style="line-height:20px;margin-left:-33px;">
                            <label>&nbsp;供热面积：</label>
                            <label>${thePredict.getF()}（万平米）</label>
                        </div>
                        <div class="form-group" style="line-height:20px;margin-left:-33px;">
                            <label>&nbsp;设计热指标：</label>
                            <input type="text" class="required" id="q" name="q"
                            value="${thePredict.getQ()}" placeholder="请输入设计热指标">（W/平米）
                        </div>
                        <div class="form-group" style="line-height:20px;margin-left:-33px;">
                            <label>&nbsp;室内计算温度：</label>
                            <input type="text" class="required" id="Tn" name="Tn"
                            value="${thePredict.getTn()}" placeholder="请输入室内计算温度">（℃）
                        </div>
                        <div class="form-group" style="line-height:20px;margin-left:-33px;">
                            <label>&nbsp;全年日平均耗热量：</label>
                            <label>${thePredict.getQ1()}（GJ）</label>
                        </div>
                        <div class="btn-group" style="line-height:20px;margin-left:-33px;">
                            <button type="button" style="margin-left:43px;" class="btn btn-danger"
                                    onclick="submitForm()">保存
                            </button>
                        </div>
                    </form>
                </div>
            </section>
        </div>
    </div>
</section>

<script type="text/javascript" charset="utf-8">
    $(document).ready(function () {
        //负荷预测参数维护验证
        $('#predictDataForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                //valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                q: {
                    message: '&nbsp&nbsp&nbsp&nbsp&nbsp负荷预测参数验证失败',
                    validators: {
                        notEmpty: {
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp负荷预测参数不能为空'
                        },
                        stringLength: {
                            min: 1,
                            max: 4,
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp负荷预测参数长度必须在1到4位之间'
                        },
                        regexp: {
                            regexp: /^(\d+|(\d+\.\d+))$/,
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp负荷预测参数只能包含数字和小数点'
                        }
                    }
                }, Tn: {
                    message: '&nbsp&nbsp&nbsp&nbsp&nbsp负荷预测参数验证失败',
                    validators: {
                        notEmpty: {
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp负荷预测参数不能为空'
                        },
                        stringLength: {
                            min: 1,
                            max: 5,
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp负荷预测参数长度必须在1到5位之间'
                        },
                        regexp: {
                            regexp: /^(\d+|(\d+\.\d+))$/,
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp负荷预测参数只能包含数字和小数点'
                        }
                    }
                }
            }
        }).on("success.form.bv", function (e) {
            //不能在这里执行，这个是一个异步的过程
        });
        // end负荷预测参数维护验证
    });

    function submitForm() {
        var bootstrapValidator = $("#predictDataForm").data('bootstrapValidator');
        bootstrapValidator.validate();
        if (bootstrapValidator.isValid()) {
            layer.confirm('确定要修改参数吗?', {icon: 3, title: '确认', btn: ['确定', '取消']}, function (index) {
                var q = $('#q').val().trim();
                var Tn = $('#Tn').val().trim();
                var postData;
                postData = "&Tn=" + Tn + "&q=" + q;
                $.ajax({
                    url: '${pageContext.request.contextPath}/predict/updateData',
                    type: 'post',
                    async: true,
                    data: postData,
                    success: function (data) {
                        if (data === true) {
                            layer.msg('参数修改成功', {icon: 1});
                            $('#table').bootstrapTable('refresh');
                        } else {
                            layer.msg('参数修改失败', {icon: 2});
                        }
                    },
                    error: function () {
                        layer.msg('error', {icon: 2});
                    }
                });
                layer.close(index);
            });
        } else {
            layer.msg('输入不合法，请重新输入!', {icon: 2});
        }
    }
</script>
