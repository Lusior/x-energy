<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无权限</title>
  	<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.png">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/bootstrap-reset.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/simplebootadminindex.min.css"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap-table.min.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="${pageContext.request.contextPath}/js/slidebars.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/common-scripts.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
 
</head>
<style >
</style>
<body>
<section id="container" class="">
  <!-- 头部 --> 
	<%@include file="header.jsp"%> 
	<!-- 侧边栏左 --> 
	<%@include file="left.jsp"%> 
    <section id="main-content" class="seller">
        <section class="wrapper">
        <section class="panel panel-info">
		    	<div style="margin:100px"><img width="100px" height="100px" src="${pageContext.request.contextPath}/img/timg.jpg"/>
			   <br/> 您没有足够的权限访问此页面!</div> 
           </section>
        </section>
    </section>
 
</section>
</body>	  
</html>