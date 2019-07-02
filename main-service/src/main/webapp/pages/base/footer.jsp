<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
<div id="footer" class="header white-bg" style="background-color:#333;color:#eee;width:100%;text-align:center;bottom:0;
			position:relative; z-index: 1002"><br>Copyright &copy; 2015-2018 &nbsp;&nbsp;|&nbsp;&nbsp;哈尔滨轩云科技有限公司
</div>
<script>
    function ct() {
        return document.compatMode === "BackCompat" ? document.body.clientHeight : document.documentElement.clientHeight;
    }

    var f = document.getElementById('footer');
    (window.onresize = function () {
        f.style.position = document.body.scrollHeight > ct() ? '' : 'absolute';
    })();
</script>
</body>
