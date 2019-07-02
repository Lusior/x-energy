<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="wrapper" style='padding-right: 0'>
    <div class="row state-overview col-lg-12" style='padding-right: 0'>
    </div>
    <div class="wrap-day chart-flow" id="morris">
        <div class="col-lg-12">
            <section class="panel panel-info">
                <header class="panel-heading">
                    <span><b>机组信息(带颜色的列可单击进行编辑)</b></span>
                </header>
                <div class="panel-body">
                    <ul class="nav nav-tabs" id="stationTab" style="margin-left:13px">
                        <li class="active"><a href="#all">全部</a></li>
                        <c:forEach var="stationData" items="${stationDataList}" varStatus="status">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="">${stationData.stationName}<span
                                    class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <c:forEach var="standData" items="${standDataList}" varStatus="status">
                                    <c:choose>
                                        <c:when test="${standData.stationId == stationData.stationId}">
                                            <li class="divider"></li>
                                            <li><a href="#${standData.standId}">${standData.standName}</a></li>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                            </ul>
                        <li>
                            </c:forEach>
                    </ul>
                    <br>
                    <div class="tab-content">
                        <!-- 机组 -->
                        <div class="tab-pane active" id="all"><!-- 显示所有的机组 -->
                            <table id="table" class="table"
                                   style="width:99%;border:1px solid #3466bb;margin-left:15px;margin-right:15px;">
                                <tr>
                                    <td colspan="6" style="background-color: #3466bb;color:white">全部机组</td>
                                </tr>
                                <tr style="background-color:#F5F5F5">
                                    <th class="weather_td" style="background-color:#F5F5F5">换热站名称</th>
                                    <th class="weather_td" style="background-color:#F5F5F5">机组名称</th>
                                    <th class="weather_td" style="background-color:#F5F5F5">负责人</th>
                                    <th class="weather_td" style="background-color:#F5F5F5">负责人电话</th>
                                    <th class="weather_td" style="background-color:#F5F5F5">设计面积 m<sup>2</sup></th>
                                    <th class="weather_td" style="background-color:#F5F5F5">实际面积 m<sup>2</sup></th>
                                </tr>
                                <%--                                 <c:set var="totalDesignArea" value="0"/><!-- 总面积 --> --%>
                                <%--                                 <c:set var="totalRealArea" value="0"/> --%>
                                <%--                                 <c:forEach var="stationData" items="${stationDataList}" varStatus="status"> --%>
                                <!--                                     <tr align="left"> -->
                                <%--                                     	<c:set var="designArea" value="0"/> --%>
                                <%--                                     	<c:set var="designArea" value="0"/> --%>

                                <%--                                         <td class="weather_td">${stationData.stationName}</td> --%>
                                <!--                                         <td class="weather_td" style="background-color: #FAFAD2" -->
                                <%--                                             onclick='tdclick(this,false,true,1,"${stationData.id}")'>${stationData.personnel}</td> --%>
                                <!--                                         <td class="weather_td" style="background-color: #FAFAD2" -->
                                <%--                                             onclick='tdclick(this,false,true,2,"${stationData.id}")'>${stationData.telephone}</td> --%>
                                <!--                                         <td class="weather_td" style="background-color: #FAFAD2" -->
                                <%--                                             onclick='tdclick(this,false,true,3,"${stationData.id}")'>${stationData.address}</td> --%>
                                <!--                                         <td class="weather_td" style="background-color: #FAFAD2" -->
                                <%--                                             onclick='tdclick(this,false,true,4,"${stationData.id}")'>${stationData.coordinate}</td> --%>
                                <!--                                         <td class="weather_td"> -->

                                <%--                                             <c:forEach var="standData" items="${standDataList}" varStatus="status"> --%>
                                <%--                                                 <c:choose> --%>
                                <%--                                                     <c:when test="${standData.stationId == stationData.stationId}"> --%>
                                <%--                                                         <c:set var="designArea" --%>
                                <%--                                                                value="${designArea + standData.designArea}"/> --%>
                                <%--                                                         <c:set var="totalDesignArea" --%>
                                <%--                                                                value="${totalDesignArea + standData.designArea}"/> --%>
                                <%--                                                     </c:when> --%>
                                <%--                                                 </c:choose> --%>
                                <%--                                             </c:forEach> --%>
                                <%--                                             <c:out value="${designArea}"/> --%>
                                <!--                                         </td> -->
                                <!--                                         <td class="weather_td"> -->
                                <%--                                             <c:set var="realArea" value="0"/> --%>
                                <%--                                             <c:forEach var="standData" items="${standDataList}" varStatus="status"> --%>
                                <%--                                                 <c:choose> --%>
                                <%--                                                     <c:when test="${standData.stationId == stationData.stationId}"> --%>
                                <%--                                                         <c:set var="realArea" value="${realArea + standData.realArea}"/> --%>
                                <%--                                                         <c:set var="totalRealArea" --%>
                                <%--                                                                value="${totalRealArea + standData.realArea}"/> --%>
                                <%--                                                     </c:when> --%>
                                <%--                                                 </c:choose> --%>
                                <%--                                             </c:forEach> --%>
                                <%--                                             <c:out value="${realArea}"/> --%>
                                <!--                                         </td> -->
                                <!--                                     </tr> -->
                                <%--                                 </c:forEach> --%>
                                <c:set var="totalDesignArea" value="0"/><!-- 总设计面积 -->
                                <c:set var="totalRealArea" value="0"/><!-- 总实际面积 -->
                                <c:forEach var="standData" items="${standDataList}" varStatus="status">

                                    <tr align="left">
                                        <td class="weather_td">
                                            <c:forEach var="stationData" items="${stationDataList}" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${standData.stationId == stationData.stationId}">
                                                        ${stationData.stationName}
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </td>
                                        <td class="weather_td">${standData.standName}</td>
                                        <td class="weather_td">
                                            <c:forEach var="stationData" items="${stationDataList}" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${standData.stationId == stationData.stationId}">
                                                        ${stationData.personnel}
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </td>
                                        <td class="weather_td">
                                            <c:forEach var="stationData" items="${stationDataList}" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${standData.stationId == stationData.stationId}">
                                                        ${stationData.telephone}
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </td>
                                        <td class="weather_td" style="background-color: #FAFAD2"
                                            onclick='tdclick(this,true,false,1,"${standData.standId}")'>${standData.designArea}</td>
                                        <td class="weather_td" style="background-color: #FAFAD2"
                                            onclick='tdclick(this,true,false,2,"${standData.standId}")'>${standData.realArea}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- end换热站 -->
                        <!-- 机组 -->
                        <c:forEach var="standData" items="${standDataList}" varStatus="status">
                            <div class="tab-pane" id="${standData.standId}"><!-- 显示换热站的，有几个机组就显示几个 -->
                                <table id="table" class="table"
                                       style="width:99%;border:1px solid #3466bb;margin-left:15px;margin-right:15px;">
                                    <tr>
                                        <td colspan="7"
                                            style="background-color: #3466bb;color:white">${standData.standName}</td>
                                    </tr>
                                    <tr style="background-color:#F5F5F5">
                                        <th class="weather_td" style="background-color:#F5F5F5">机组名称</th>
                                        <th class="weather_td" style="background-color:#F5F5F5">负责人</th>
                                        <th class="weather_td" style="background-color:#F5F5F5">负责人电话</th>
                                        <th class="weather_td" style="background-color:#F5F5F5">设计面积 m<sup>2</sup></th>
                                        <th class="weather_td" style="background-color:#F5F5F5">实际面积 m<sup>2</sup></th>
                                    </tr>
                                    <tr align="left">
                                        <td class="weather_td">${standData.standName}</td>
                                        <td class="weather_td">
                                            <c:forEach var="stationData" items="${stationDataList}" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${standData.stationId == stationData.stationId}">
                                                        ${stationData.personnel}
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </td>
                                        <td class="weather_td">
                                            <c:forEach var="stationData" items="${stationDataList}" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${standData.stationId == stationData.stationId}">
                                                        ${stationData.telephone}
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </td>
                                        <td class="weather_td" style="background-color: #FAFAD2"
                                            onclick='tdclick(this,true,false,1,"${standData.standId}")'>${standData.designArea}</td>
                                        <td class="weather_td" style="background-color: #FAFAD2"
                                            onclick='tdclick(this,true,false,2,"${standData.standId}")'>${standData.realArea}</td>
                                    </tr>
                                </table>
                            </div>
                        </c:forEach>
                        <!-- end机组 -->
                    </div>
                </div>
            </section>
        </div>
    </div>
</section>

<script type="text/javascript" charset="utf-8">
    $(document).ready(function () {
        $("#basic").addClass("active");
        $("#basic_station").addClass("active");
        $("#basic_station").parents(".sub").show();

        //换热站tab
        $("#stationTab a").click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        });
        //end换热站

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

    //编辑table表格
    //参数1：td对象;参数2：是不是只能输入纯数字;参数3：true修改换热站，false修改机组;
    //参数3：用于controller里判断，修改的是哪个参数。
    //参数4：修改参数3第几个参数，如果参数3为true，这里为1，则修改的是换热站负责人。 如果参数3为false，这里为2，则修改的是机组实际面积。
    //参数5：如果3是true,这里就是stationId，如果是false这里就是standId
    function tdclick(tdobject, onlyNum, stationFlag, inOrder, tId) {

        var td = $(tdobject);
        //td.attr("onclick", "");
        // 1,取出当前td中的文本内容保存起来
        var text = td.text();
        // 2,清空td里面的内容
        td.html(""); // 也可以用td.empty();
        // 3，建立一个文本框，也就是input的元素节点
        var input = $("<input class='form-control'>");
        // 4，设置文本框的值是保存起来的文本内容
        input.attr("value", text);
        input.bind("blur", function () {
            var inputnode = $(this);
            var inputtext = inputnode.val();
            var tdNode = inputnode.parent();
            tdNode.html(inputtext);
            tdNode.click(tdclick);
            //td.attr("onclick", "tdclick(this)");
            //这里存到数据库里
            var postData = "";
            postData = "stationFlag=" + stationFlag + "&inOrder=" + inOrder + "&tId=" + tId + "&tValue=" + inputtext;
            $.ajax({
                url: '${pageContext.request.contextPath}/basic/station/updateInfoWithTable',
                type: 'post',
                async: true,
                data: postData,
                success: function (data) {
                    if (data == true) {
                        console.log('success');
                    } else {
                        console.log('faild');
                    }
                },
                error: function () {
                    console.log('error');
                }
            })

        });
        input.keyup(function (event) {
            if (true == onlyNum) {
                this.value = this.value.replace(/\D/g, '');
            }
            ;
            var myEvent = event || window.event;
            var kcode = myEvent.keyCode;
            if (kcode == 13) {
                var inputnode = $(this);
                var inputtext = inputnode.val();
                var tdNode = inputnode.parent();
                tdNode.html(inputtext);
                tdNode.click(tdclick);
            }
        });

        // 5，将文本框加入到td中
        td.append(input);
        var t = input.val();
        input.val("").focus().val(t);
        // input.focus();

        // 6,清除点击事件
        td.unbind("click");
    }
</script>
