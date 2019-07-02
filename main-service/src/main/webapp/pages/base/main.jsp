<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section id="container" class="">
    <c:import url="header.jsp"/>
    <c:import url="left.jsp"/>
    <section id="main-content">
        <!-- 这里加其他页面 -->
        <h1></h1>
        <h1></h1>
        <h1></h1>
        <h1></h1>
        <h1></h1>
    </section>
    <%@include file="../footer.jsp" %>
</section>
<script type="text/javascript" charset="utf-8">
    var content = "${content}";
    if (content) {
        loadContent(content);
    }

    // 加载内容区域
    function loadContent(content) {
        // console.log(content);
        window.location.hash = content;
        window.onhashchange = function () {
            //这里有可能有工程名称。如果有，就加上。 如energy，则生成的是 /energy/basic/station/list
            //这样锚点，刷新后是这样的 http://xxxxxx:xxxx/energy/basic/station/list
            var pathName = window.document.location.pathname;
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            if ('home' !== content) {
                content = projectName + window.location.hash.substr(1);
            } else {
                content = window.location.hash.substr(1);
            }

            menuActiveChange(content);

            $.get(content, function (data) {
                $("#main-content").html(data);
            }, "text");
            // console.log(content + " 加载完毕！");
        };
    }

    function menuActiveChange(content) {
        var aItems = $('.nav-list1 li');
        aItems.each(function () {
            var liTag = $(this);
            liTag.removeClass("active");
            var pathName = window.document.location.pathname;
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            // if (content.endsWith(liTag.attr("content"))) {
            if (content === projectName + liTag.attr("content")) {
                liTag.addClass("active");
            }
        });
    }
</script>