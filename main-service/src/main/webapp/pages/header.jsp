<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<body>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>能耗分析系统</title>
	    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
	    <link href="${pageContext.request.contextPath}/css/bootstrap-reset.css" rel="stylesheet">
	    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico">
	    <link href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.css" type="text/css">
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/bootstrap-datepicker/css/datepicker.css" />
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/bootstrap-timepicker/compiled/timepicker.css" />
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/bootstrap-daterangepicker/daterangepicker-bs3.css" />
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/assets/bootstrap-datetimepicker/css/datetimepicker.css" />
	    <link href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/echarts-liquidfill.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/amcharts.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/pie.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/export.min.js"></script>
		<link href="${pageContext.request.contextPath}/css/export.css" type="text/css" media="all" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts/light.js"></script>
	    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
	    <link href="${pageContext.request.contextPath}/css/bootstrap-table.min.css" rel="stylesheet">
	    <link href="${pageContext.request.contextPath}/css/zTreeStyle.css" rel="stylesheet">
	    <script src="${pageContext.request.contextPath}/js/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	    <script src="${pageContext.request.contextPath}/js/slidebars.min.js"></script>
	    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	    <script src="${pageContext.request.contextPath}/js/bootstrap-table/bootstrap-table.min.js"></script>
	    <script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ztree.all-3.5.min.js"></script>
	    <script class="include" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dcjqaccordion.2.7.js"></script>
	    <script src="${pageContext.request.contextPath}/js/respond.min.js" ></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	    <script src="${pageContext.request.contextPath}/js/common-scripts.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/js/layer/layer.min.js"></script>
		<link href="${pageContext.request.contextPath}/js/layer/skin/layer.css" rel="stylesheet">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/simplebootadminindex.min.css"/>
	   	<style>
	       .chart {
	           width: 30%;
	           height: 300px;
	           float: left;
	       }
	       .roundElement { border-radius: 3px;}
	       .weather_td{
	    		border:1px solid #3466bb;
	    		text-align:center;
	    	}
	    	.today .t .sk .h{ margin: 10px;}
	    	.today .t .sk .topic{ padding:13px 10px 10px 10px; color: #7e7e7e; font-size: 13px;}
	    	.table thead > tr > th, .table tbody > tr > th, .table tfoot > tr > th, .table thead > tr > td, .table tbody > tr > td, .table tfoot > tr > td{height:30px; line-height:30px; padding:3px;}
		</style>
    </head>
  	<header class="header white-bg" style="background-color:#66cccc;width:100%;">
      <a href="#" class="logo"><img src="${pageContext.request.contextPath}/img/${sessionScope.COMPANY_ICON}" width="50" >&nbsp;&nbsp;${sessionScope.COMPANY_TITLE}  <span id="logoTxt"></span></a>
      
      <ul class="head-rpart">
     	 <li style="text-align:right;">
         	 ${ sessionScope.weatherHtml} 
         </li>
         <a href="http://47.93.184.40:8060/energy/download/Lodop.zip"><font color="red"><b>打印控件下载</b></font></a>（如需打印功能，必须下载安装）
        <li class="dropdown">
          <span href="javascript:;" style="display:inline-block; float:left;">欢迎：&nbsp;</span>
          <a data-toggle="dropdown" class="dropdown-toggle userNameTop"  id="loginId" style=" float:left; line-height:30px; cursor:pointer;text-decoration:underline "></a>
          <ul class="dropdown-menu extended">
              <li><a data-toggle="modal" href="#modal-psinfo">个人信息</a></li>
              <li><a data-toggle="modal" href="#modal-pwdchange">修改密码</a></li>
          </ul>
          <a href="${pageContext.request.contextPath}/exit" class="loginout">退出</a>
        </li>
      </ul>
       
      <div class="modal fade modal-dialog-center" id="modal-psinfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog ">
          <div class="modal-content-wrap">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">个人信息</h4>
              </div>
              <div class="modal-body">
                <div class="panel panel-primary ud-info" style="margin:0;">
                  <ul class="panel-body">
                    <li><span style="width:200px;">登陆账号:</span><span id="udLoginName" class="udOprName"></span></li>
                    <li><span style="width:200px;">操作员名称:</span><span id="udOprName" class="udOprName"></span></li>
                    <li><span style="width:200px;">联系电话：</span><span id="udPhone" class="udPhone"></span></li>
                    <li><span style="width:200px;">邮箱：</span><span id="udEmail" class="udEmail"></span></li>
                    <li><span style="width:200px;">操作员状态：</span><span>正常</span></li>
                    <li><span style="width:200px;">最后登陆时间：</span><span  id="udLastDtTm" class="udLastDtTm"></span></li>
                  </ul>
                </div>
              </div>
              <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal fade modal-dialog-center" id="modal-pwdchange" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog ">
          <div class="modal-content-wrap">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">修改密码</h4>
              </div>
              <div class="modal-body">
                <div class="panel panel-primary ud-info" style="margin:0;">
                  <form action="" id="pwdchange">
                    <ul class="panel-body">
                      <li><span style="width:200px;">登陆信息：</span><span id="loginIdTxt" class="admin-name"></span></li>
                      <li><span style="width:200px;">原登陆密码：</span><input type="password" class="form-control" id="pwdold"></li>
                      <li><span style="width:200px;">新登陆密码：</span><input type="password" class="form-control" id="pwdnew"></li>
                      <li><span style="width:200px;">重复新登陆密码：</span><input type="password" class="form-control" id="pwdre"></li>
                    </ul>
                  </form>
                </div>
                <div class="modal-footer">
                  <button class="btn btn-primary btn-pwdchange" type="button" >确定</button>
                  <button data-dismiss="modal" class="btn btn-default" type="button">取消</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
  </header>
</body>
<script type="text/javascript" charset="utf-8">
	window.onload=function () {
		  $.ajax({
		    url: '${pageContext.request.contextPath}/login/info',
		    type: 'GET',
		    success:function(data) {
				$('#logoTxt').html(data.mercName);
			    $('#loginIdTxt').text(data.loginId);
			    $('#loginId').text(data.loginId);
			    $('#udLoginName').html(data.loginId);
		        $('#upLoginId').html(data.loginId);
		        $('#udOprName').html(data.oprName);
		        $('#udPhone').html(data.phone);
		        $('#udEmail').html(data.email);
		        $('#udLastDtTm').html(data.lastDtTm);
			  },
			  error:function() {
				    console.log("error");
			  }
		  })
	}

  	$('.btn-pwdchange').click(function() {
        var pwdold = $('#pwdold').val();
        var pwdnew = $('#pwdnew').val();
        var pwdre = $('#pwdre').val();
        if (pwdold.length == '') {
        	layer.msg('原密码不能为空',{icon:2});
            return false;
        } else if (pwdnew.length == '') {
        	layer.msg('新密码不能为空',{icon:2});
            return false;
        } else if (pwdre.length == '') {
        	layer.msg('重复新密码不能为空',{icon:2});
            return false;
        } else if (pwdre != pwdnew) {
        	layer.msg('二次密码不一样',{icon:2});
            return false;
        }else{
            $.ajax({
              url: '${pageContext.request.contextPath}/sys/oprinfo/up/passwd',
              type: 'POST',
              data: 'oldPassword=' + pwdold + '&newPassword=' + pwdnew,
              success:function(data) {
              	if(data.code == '00'){
              		layer.msg('密码修改成功',{icon:1});
              		 $('#pwdold').val("");
              	     $('#pwdnew').val("");
              	      $('#pwdre').val("");
              		$('#modal-pwdchange').fadeOut();
              	}else if(data.code == '02'){
              		layer.msg('原密码错误',{icon:2});
              	}else{
              		layer.msg('错误',{icon:2});
              	}
              },
              error : function() {
            	layer.msg('错误',{icon:2});
              }
         	})
        }
    });
	function formatDete(date) {
		 if(date != "" && date != null ){
			 return  date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date.substring(8,10)+":"+date.substring(10,12)+":"+date.substring(12,14);
		 }
		
	}
	function formatDeteTime(date, time) {
		 if(date != "" && date != null && (time == null || time == "")){
			 return  date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
		 }
		 if((date == "" || date == null) && time != "" && time != null){
			return  time.substring(0,2)+":"+time.substring(2,4)+":"+time.substring(4,6);
		 }
		 if(date!="" && date!=null && time!="" && time!=null){
			return  date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)
					+" "+ time.substring(0,2)+":"+time.substring(2,4)+":"+time.substring(4,6);
		 }
	}
	function formatDeteMMdd(date) {
		 if(date != "" && date != null ){
			 return date.substring(4,6)+"月"+date.substring(6,8)+"日";
		 }
	}
</script>
</html>