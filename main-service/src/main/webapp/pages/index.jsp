<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<body>
    <section id="container" class="">
	 	<%@include file="header.jsp"%>
	    <%@include file="left.jsp"%>
	    <!--main content start-->
	    <section id="main-content">
		    <!-- 这里加其他页面 -->
 		    <%@include file="home.jsp"%>
	    </section>
	    <%@include file="footer.jsp"%>
    </section>
</body>
</html>
<script>
$(function(){
	//$(".main-content").load("/basic/station/list");
	//alert($(".main-content").html);
	//$(".main-content").html("adfasdfasdfsd");
});

</script>
