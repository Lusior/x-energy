<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<aside>
    <div id="sidebar1" class="sidebar1">
        <div id="nav-wrapper">
            <ul class="nav-list1">
                <!-- >h2>占位</h2 -->
                <c:forEach items="${menus}" var="menu" varStatus="status">
                    <c:if test="${menu.leaf}">
                        <li id='home'>
                            <a id="homeUrl" href="javascript:redirectHome()"><i class='fa fa-home'></i>${menu.name}</a>
                        </li>
                    </c:if>
                    <c:if test="${!menu.leaf}">
                        <li>
                            <a href="#" class="dropdown-toggle">
                                <i class="${menu.icon}"></i>
                                <span class="menu-text normal">${menu.name}</span>
                                <b class="arrow fa fa-angle-right normal"></b>
                            </a>
                            <ul class='submenu'>
                                <c:forEach items="${menu.subMenus}" var="sm" varStatus="status">
                                    <li content="${sm.url}">
                                        <a>
                                            <i class="fa"></i>
                                            <!-- 把二级菜单带上小手 -->
                                            <span class="menu-text" style="cursor:pointer">${sm.name}</span>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>
</aside>
<script type="text/javascript" charset="utf-8">
    $(".submenu li").bind('click', function () {
        // alert($(this).attr('content'));
        loadContent($(this).attr('content'));
    });
    //菜单点击处理逻辑
    $(".nav-list1").on("click", function (event) {
        var closest_a = $(event.target).closest("a");//在BaseController里面，拼接的是span。找到这个点击对像的第一个a元素父结点。
        if (!closest_a || closest_a.length === 0) {//如果没有超链接元素'a',别往下走了，什么也不干返回。
            return
        }
        if (!closest_a.hasClass("dropdown-toggle")) {//如果是“首页”，或者是二级菜单，直接返回走href跳转了
            return
        }
        var closest_a_next = closest_a.next().get(0);
        if (!$(closest_a_next).is(":visible")) {//如果所点击的一级菜单是关闭状态
            //开始找'ul',因为'ul'的class给的是submenu。
            //'sapn'上面的parentNode是'a'，'a'的上面是'li'，忽略，再往上找。找到了'ul'.
            var closest_ul = $(closest_a_next.parentNode).closest("ul");//原来你在这里。
            closest_ul.find("> .open > .submenu").each(function () {//找到打开的子菜单
                if (this !== closest_a_next && !$(this.parentNode).hasClass("active")) {
                    $(this).slideUp(150).parent().removeClass("open")
                }
            });
        }
        //把这个菜单打开。
        $(closest_a_next).slideToggle(150).parent().toggleClass("open");
        //加了下面这句，因为点击的是span，把它上层的'a'的第一个父节点'li'的class设置成acvive。
        //不然多个菜单颜色一样，不知道点的是哪个。把它变成蓝色。
        //把点击菜单的上层节点的父'li'的class设置成active。
        //$(closest_a_next.parentNode).closest("ul").toggleClass("active");
        return false;
    });

    function redirectHome() {
        var pathName = window.document.location.pathname;
        // var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
        // alert('pathName=' + pathName);
        // alert('substr1=' + pathName.substr(1));
        // alert('projectName=' + projectName);
        $('#homeUrl').attr('href', pathName);
        $('#homeUrl>i').trigger('click');
    }
</script>