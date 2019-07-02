<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section class="wrapper" style='padding-right: 0'>
    <div class="row state-overview col-lg-12" style='padding-right: 0'>
    </div>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <!-- 价格维护 -->
            <section class="panel panel-warning">
                <header class="panel-heading">
                    <span><b>价格维护(请谨慎操作)</b></span>
                </header>
                <div class="panel-body" style="margin-left:33px;">
                    <form id="priceForm" class="form-inline pull-left pull-top">
                        <input type="hidden" id="id" name="id" value="${priceData.companyId}"/>
                        <div class="form-group" style="line-height:20px;margin-left:-33px;">
                            <label>水价：</label>
                            <input type="text" class="required" id="ft3qPrice" name="ft3qPrice"
                                   value="${priceData.ft3qPrice}" placeholder="请输入水价">&nbsp;元/t
                        </div>
                        <div class="form-group">
                            <label>&nbsp;电价：</label>
                            <input type="text" class="required" id="jqiPrice" name="jqiPrice"
                                   value="${priceData.jqiPrice}" placeholder="请输入电价">&nbsp;元/kWh
                        </div>
                        <div class="form-group">
                            <label>&nbsp;热价：</label>
                            <input type="text" class="required" id="qqiPrice" name="qqiPrice"
                                   value="${priceData.qqiPrice}" placeholder="请输入热价">&nbsp;元/GJ
                        </div>
                        <div class="btn-group">
                            <button type="button" style="margin-left:43px;" class="btn btn-danger"
                                    onclick="submitForm()">保存
                            </button>
                        </div>
                    </form>
                </div>
            </section>
            <!-- end价格维护 -->
        </div>
    </div>
</section>

<script type="text/javascript" charset="utf-8">
    $(document).ready(function () {

        //价格维护验证
        $('#priceForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                //valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                ft3qPrice: {
                    message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格验证失败',
                    validators: {
                        notEmpty: {
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格不能为空'
                        },
                        stringLength: {
                            min: 1,
                            max: 4,
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格长度必须在1到4位之间'
                        },
                        regexp: {
                            regexp: /^(\d+|(\d+\.\d+))$/,
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格只能包含数字和小数点'
                        }
                    }
                }, jqiPrice: {
                    message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格验证失败',
                    validators: {
                        notEmpty: {
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格不能为空'
                        },
                        stringLength: {
                            min: 1,
                            max: 5,
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格长度必须在1到5位之间'
                        },
                        regexp: {
                            regexp: /^(\d+|(\d+\.\d+))$/,
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格只能包含数字和小数点'
                        }
                    }
                }, qqiPrice: {
                    message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格验证失败',
                    validators: {
                        notEmpty: {
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格不能为空'
                        },
                        stringLength: {
                            min: 1,
                            max: 4,
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格长度必须在1到4位之间'
                        },
                        regexp: {
                            regexp: /^(\d+|(\d+\.\d+))$/,
                            message: '&nbsp&nbsp&nbsp&nbsp&nbsp价格只能包含数字和小数点'
                        }
                    }
                }
            }
        }).on("success.form.bv", function (e) {
            //不能在这里执行，这个是一个异步的过程
        });
        //end价格维护验证
    });

    function submitForm() {
        var bootstrapValidator = $("#priceForm").data('bootstrapValidator');
        bootstrapValidator.validate();
        if (bootstrapValidator.isValid()) {
            layer.confirm('确定要修改价格吗?', {icon: 3, title: '确认', btn: ['确定', '取消']}, function (index) {
                var id = $('#id').val();
                var ft3qPrice = $('#ft3qPrice').val().trim();
                var jqiPrice = $('#jqiPrice').val().trim();
                var qqiPrice = $('#qqiPrice').val().trim();
                var postData = "";
                postData = "id=" + id + "&ft3qPrice=" + ft3qPrice + "&jqiPrice=" + jqiPrice + "&qqiPrice=" + qqiPrice;
                $.ajax({
                    url: '${pageContext.request.contextPath}/basic/price/updatePrice',
                    type: 'post',
                    async: true,
                    data: postData,
                    success: function (data) {
                        if (data == true) {
                            layer.msg('价格修改成功', {icon: 1});
                            $('#table').bootstrapTable('refresh');
                        } else {
                            layer.msg('价格修改失败', {icon: 2});
                        }
                    },
                    error: function () {
                        layer.msg('error', {icon: 2});
                    }
                })
                layer.close(index);
            });
        } else {
            layer.msg('输入不合法，请重新输入!', {icon: 2});
        }
    }
</script>
