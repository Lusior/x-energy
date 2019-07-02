
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="en">
    <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.png">
    <title>能耗分析系统</title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-reset.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/simplebootadminindex.min.css"/>
   	<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
	
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="${pageContext.request.contextPath}/js/common-scripts.js"></script>
   	<style>
   		.weather_td {
    		border:1px solid #3466bb;
    		text-align:center;
    	}
    	.form-group .form-control-feedback {
		 	bottom: 0px;
		 	right: 0px;
		}
   	</style>
    </head>
<body>
<section id="container" class="">
 	<%@include file="../header.jsp"%>
    <%@include file="../left.jsp"%>
    <!--main content start-->
    <section id="main-content">
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
								<input type="hidden" id="id" name="id" value="${priceData.id}"/>
								<div class="form-group" style="line-height:20px;margin-left:-33px;">
					            	<label>水价：</label>
					                <input type="text" class="required" id="ft3qPrice" name="ft3qPrice" value="${priceData.ft3qPrice}" placeholder="请输入水价">&nbsp;元/t
					            </div>
					            <div class="form-group">
					                <label>&nbsp;电价：</label>
					                <input type="text" class="required" id="jqiPrice" name="jqiPrice" value="${priceData.jqiPrice}" placeholder="请输入电价">&nbsp;元/kWh
					            </div>
					            <div class="form-group">
					                <label>&nbsp;热价：</label>
					                <input type="text" class="required" id="qqiPrice" name="qqiPrice" value="${priceData.qqiPrice}" placeholder="请输入热价">&nbsp;元/GJ
					            </div>
					            <div class="btn-group">
					            	<button type="button" style="margin-left:43px;" class="btn btn-danger" onclick="submitForm()">保存</button>
					            </div>
							</form>
						</div>
                	</section>
                	<!-- end价格维护 -->
                    <section class="panel panel-info" >
                        <header class="panel-heading">
                            <span><b>换站站机组信息(带颜色的列可单击进行编辑)</b></span>
                        </header>
                        <div class="panel-body">
	                        <ul class="nav nav-tabs" id="stationTab" style="margin-left:13px">
								<li class="active"><a href="#all">全部</a></li>
								<c:forEach var="stationData" items="${stationDataList}" varStatus="status">
									<li class="dropdown">
										<a class="dropdown-toggle" data-toggle="dropdown" href="">${stationData.stationName}<span class="caret"></span></a>
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
								<!-- 换热站 -->
								<div class="tab-pane active" id="all"><!-- 显示所有的 -->
									<table id="table" class="table" style="width:99%;border:1px solid #3466bb;margin-left:15px;margin-right:15px;">
									    <tr><td colspan="7" style="background-color: #3466bb;color:white" >换热站</td></tr>
										<tr style="background-color:#F5F5F5">
											<th class="weather_td" style="background-color:#F5F5F5">换热站名称</th>
											<th class="weather_td" style="background-color:#F5F5F5">负责人</th>
											<th class="weather_td" style="background-color:#F5F5F5">负责人电话(可输入中文)</th>
											<th class="weather_td" style="background-color:#F5F5F5">地址</th>
											<th class="weather_td" style="background-color:#F5F5F5">坐标</th>
											<th class="weather_td" style="background-color:#F5F5F5">设计面积 m<sup>2</sup></th>
											<th class="weather_td" style="background-color:#F5F5F5">实际面积 m<sup>2</sup></th>
										</tr>
										<c:set var="totalDesignArea" value="0"/><!-- 总面积 -->
										<c:set var="totalRealArea" value="0"/>
										<c:forEach var="stationData" items="${stationDataList}" varStatus="status">
											<tr align="left">
												<td class="weather_td">${stationData.stationName}</td>
												<td class="weather_td" style="background-color: #FAFAD2" onclick='tdclick(this,false,true,1,"${stationData.id}")'>${stationData.personnel}</td>
												<td class="weather_td" style="background-color: #FAFAD2" onclick='tdclick(this,false,true,2,"${stationData.id}")'>${stationData.telephone}</td>
												<td class="weather_td" style="background-color: #FAFAD2" onclick='tdclick(this,false,true,3,"${stationData.id}")'>${stationData.address}</td>
												<td class="weather_td" style="background-color: #FAFAD2" onclick='tdclick(this,false,true,4,"${stationData.id}")'>${stationData.coordinate}</td>
												<td class="weather_td">
													<c:set var="designArea" value="0"/>
													<c:forEach var="standData" items="${standDataList}" varStatus="status">
														<c:choose>
															<c:when test="${standData.stationId == stationData.stationId}">
																<c:set var="designArea" value="${designArea + standData.designArea}"/>
																<c:set var="totalDesignArea" value="${totalDesignArea + standData.designArea}"/>
															</c:when>
														</c:choose>
													</c:forEach>
													<c:out value="${designArea}"/>
												</td>
												<td class="weather_td">
													<c:set var="realArea" value="0"/>
													<c:forEach var="standData" items="${standDataList}" varStatus="status">
														<c:choose>
															<c:when test="${standData.stationId == stationData.stationId}">
																<c:set var="realArea" value="${realArea + standData.realArea}"/>
																<c:set var="totalRealArea" value="${totalRealArea + standData.realArea}"/>
															</c:when>
														</c:choose>
													</c:forEach>
													<c:out value="${realArea}"/>
												</td>
											</tr>
										</c:forEach>
										<tr>
											<td colspan="5" style="text-align: right">总计：</td>
											<td class="weather_td"><c:out value="${totalDesignArea}"/></td>
											<td class="weather_td"><c:out value="${totalRealArea}"/></td>
										</tr>
							        </table>
								</div>
								<!-- end换热站 -->
								<!-- 机组 -->
								<c:forEach var="standData" items="${standDataList}" varStatus="status">
									<div class="tab-pane" id="${standData.standId}"><!-- 显示换热站的，有几个机组就显示几个 -->
										<table id="table" class="table" style="width:99%;border:1px solid #3466bb;margin-left:15px;margin-right:15px;">
										    <tr><td colspan="7" style="background-color: #3466bb;color:white" >${standData.standName}</td></tr>
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
												<td class="weather_td" style="background-color: #FAFAD2" onclick='tdclick(this,true,false,1,"${standData.id}")'>${standData.designArea}</td>
												<td class="weather_td" style="background-color: #FAFAD2" onclick='tdclick(this,true,false,2,"${standData.id}")'>${standData.realArea}</td>
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
    </section>
</section>
 
<script type="text/javascript"  charset="utf-8">
	$(document).ready(function(){
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
                },jqiPrice: {
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
                },qqiPrice: {
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
    	}).on("success.form.bv",function(e){
			//不能在这里执行，这个是一个异步的过程    		
    	});
		//end价格维护验证
	});
	
	function submitForm() {
		
		var bootstrapValidator = $("#priceForm").data('bootstrapValidator');
		bootstrapValidator.validate();
		if(bootstrapValidator.isValid()) {
			layer.confirm('确定要修改价格吗?', {icon: 3, title:'确认',btn: ['确定','取消']},function(index){
				var id = $('#id').val();
				var ft3qPrice = $('#ft3qPrice').val().trim();
				var jqiPrice =  $('#jqiPrice').val().trim();
				var qqiPrice =  $('#qqiPrice').val().trim();
				var postData = "";
			    postData = "id=" + id + "&ft3qPrice=" + ft3qPrice + "&jqiPrice=" + jqiPrice +"&qqiPrice=" + qqiPrice;
			    $.ajax({
				    url: '${pageContext.request.contextPath}/basic/price/updatePrice',
				    type: 'post',
				    async : true,
				    data:postData,
				    success:function(data) {
						if(data == true){
							layer.msg('价格修改成功',{icon: 1}); 
							$('#table').bootstrapTable('refresh');
						}else{
							layer.msg('价格修改失败',{icon: 2}); 
						}
				    },
					error:function() {
						layer.msg('error',{icon: 2}); 
					}
				})
				layer.close(index);
			});
		} else {
		    layer.msg('输入不合法，请重新输入!',{icon: 2});
		}
	}
	
	//编辑table表格
	//参数1：td对象;参数2：是不是只能输入纯数字;参数3：true修改换热站，false修改机组;
	//参数3：用于controller里判断，修改的是哪个参数。
	//参数4：修改参数3第几个参数，如果参数3为true，这里为1，则修改的是换热站负责人。 如果参数3为false，这里为2，则修改的是机组实际面积。
	//参数5：如果3是true,这里就是stationId，如果是false这里就是standId
	function tdclick(tdobject,onlyNum,stationFlag,inOrder,tId) {
		
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
		input.bind("blur", function() {
			var inputnode = $(this);
			var inputtext = inputnode.val();
			var tdNode = inputnode.parent();
			tdNode.html(inputtext);
			tdNode.click(tdclick);
			//td.attr("onclick", "tdclick(this)");
			//这里存到数据库里
			var postData = "";
			postData = "stationFlag=" + stationFlag + "&inOrder=" + inOrder + "&tId=" + tId +"&tValue=" + inputtext;
			$.ajax({
			    url: '${pageContext.request.contextPath}/basic/station/updateInfoWithTable',
			    type: 'post',
			    async : true,
			    data:postData,
			    success:function(data) {
					if(data == true){
						console.log('success');
					}else{
						console.log('faild');
					}
			    },
				error:function() {
					console.log('error');
				}
			})
			
		});
		input.keyup(function(event) {
			if(true == onlyNum) {
				this.value=this.value.replace(/\D/g,'');
			};
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
</body>
</html>
