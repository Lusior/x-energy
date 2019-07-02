<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<aside>
    <div id="sidebar1" class="sidebar1">
        <div id="nav-wrapper">
            <ul class="nav-list1">
                ${sessionScope.menuHtml}
            </ul>
        </div>
    </div>
</aside>
<script type="text/javascript" charset="utf-8">
//一个完整的打开的菜单是下面这样的
// 	<li class="open"> 
// 		<a href="#" class="dropdown-toggle">
// 			<i class="fa fa-adjust normal"></i>
// 			<span class="menu-text normal"> 报表系统 </span>
// 			<b class="arrow fa fa-angle-right normal"></b>
// 		</a> 
// 		<ul class="submenu" style="display: block;"> 
// 			<li> //如果这里是点击过的，应该加  class="active"
// 				<a href="http://localhost:8050/energy/report/day/">
// 					<i class="fa fa-caret-right"></i>
// 					<span class="menu-text">日报表</span>
// 				</a>
// 			</li>
// 			<li>
// 				<a href="http://localhost:8050/energy/report/month/">
// 					<i class="fa fa-caret-right"></i>
// 					<span class="menu-text">月报表</span>
// 				</a>
// 			</li>
// 		</ul>
// 	</li>
	//菜单点击处理逻辑
    $(".nav-list1").on("click", function (event) {
        var closest_a = $(event.target).closest("a");//在BaseController里面，拼接的是span。找到这个点击对像的第一个a元素父结点。
        if (!closest_a || closest_a.length == 0) {//如果没有超链接元素'a',别往下走了，什么也不干返回。
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
                if (this != closest_a_next && !$(this.parentNode).hasClass("active")) {
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
	
	
	//页面刷新后，菜单自动展开逻辑
	//$( document ).ready(function() {
		var aItems=$('.nav-list1 a');
		aItems.each(function(){
	    	var that=$(this),val=that.attr('href');
	    	//that就是遍历出来的单个a标签，你可以在线下面写你的逻辑
	    	if("#" != val && val == window.location.href && "/" != val.substr(val.length-1,1)) {
		    	console.log("val:"+val);
		    	console.log(val.substr(val.length-1,1));
		    	//$(this.parentNode).hasClass("active")
		    	$(this).slideUp(150).parent().parent().parent().toggleClass("open");
		    	$(this).slideUp(150).parent().parent().css("display", "block");
		    	//$(this).slideToggle(150).css("display", "block");
		    	$(this).slideToggle(150).parent().toggleClass("active");
	    	}
		});
	//});
</script>