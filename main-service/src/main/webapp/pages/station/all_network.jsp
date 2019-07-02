<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style type="text/css">
    #allmap {
        height: 500px;
        width: 100%;
    }
</style>

<section class="wrapper">
    <section class="panel panel-info">
        <header class="panel-heading">
            <span><b>全网分布</b></span>
        </header>
        <div class="row" style="margin:15px">
            <div id="allmap"></div>
        </div>
    </section>
</section>

<script type="text/javascript">
    $(function () {
        $("#heat_station").addClass("active");
        $("#heat_station_all_network").addClass("active");
        $("#heat_station_all_network").parents(".sub").show();
    });

    //一网供水温度te1 一网回水温度te2   一网供水压力pt1  一网回水压力pt2 网瞬时热量qi 一网瞬时流量ft1
    var opts = {
        width: 300,     // 信息窗口宽度
        height: 120,     // 信息窗口高度
        title: "实时信息"  // 信息窗口标
    };
    var data_info = [];
    $.ajax({
        url: '${pageContext.request.contextPath}/basic/station/all/network/rtNw',
        type: 'get',
        async: false, //同步调用 否则外部无法使用
        success: function (data) {
            data_info = data;
        }
    });
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var point = new BMap.Point(126.624794, 45.776305);
    map.centerAndZoom(point, 11);
    map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    // 添加带有定位的导航控件
    var navigationControl = new BMap.NavigationControl({
        // 靠左上角位置
        anchor: BMAP_ANCHOR_TOP_LEFT,
        // LARGE类型
        type: BMAP_NAVIGATION_CONTROL_LARGE,
        // 启用显示定位
        enableGeolocation: true
    });
    map.addControl(navigationControl);
    // 添加定位控件
    var geolocationControl = new BMap.GeolocationControl();
    geolocationControl.addEventListener("locationSuccess", function (e) {
        // 定位成功事件
        var address = '';
        address += e.addressComponent.province;
        address += e.addressComponent.city;
        address += e.addressComponent.district;
        address += e.addressComponent.street;
        address += e.addressComponent.streetNumber;
        alert("当前定位地址为：" + address);
    });
    geolocationControl.addEventListener("locationError", function (e) {
        // 定位失败事件
        alert(e.message);
    });
    map.addControl(geolocationControl);

    for (var i = 0; i < data_info.length; i++) {
        var content = '<div style="width:100%;">' +
            '<div style="text-align:center;">' +
            '<div><b>' + data_info[i].station_name + '</b></div>' +
            '<div>' + data_info[i].dtime + '</div>' +
            '</div>' +
            '<div style="overflow:hidden;"><span style="display:inline-block;width:50%;">一网供水温度:<font style="font-weight:bold"; color="#3466bb" >' + data_info[i].te1 + '</font></span>' +
            '<span style="padding-left:5px; display:inline-block; width:49%;">一网回水温度:<font style="font-weight:bold"; color="#3466bb" >' + data_info[i].te2 + '</font></span>' +
            '</div>' +
            '<div style="overflow:hidden;"><span style="display:inline-block;width:50%;">一网供水压力:<font style="font-weight:bold"; color="#3466bb" >' + data_info[i].pt1 + '</font></span>' +
            '<span style="padding-left:5px; display:inline-block; width:49%;">一网回水压力:<font style="font-weight:bold"; color="#3466bb" >' + data_info[i].pt2 + '</font></span>' +
            '</div>' +
            '<div style="overflow:hidden;"><span style="display:inline-block;width:50%;">一网瞬时热量:<font style="font-weight:bold"; color="#3466bb" >' + data_info[i].qi + '</font></span>' +
            '<span style="padding-left:5px; display:inline-block; width:49%;">一网瞬时流量:<font style="font-weight:bold"; color="#3466bb" >' + data_info[i].ft1 + '</font></span>' +
            '</div>' +
            '</div>';
        var cdis = data_info[i].coordinate.split(",");
        var marker = new BMap.Marker(new BMap.Point(cdis[0], cdis[1]));  // 创建标注
        map.addOverlay(marker);               // 将标注添加到地图中
        addClickHandler(content, marker);
    }

    function addClickHandler(content, marker) {
        marker.addEventListener("onmouseover", function (e) {
                openInfo(content, e)
            }
        );
        marker.addEventListener("onmouseout", function (e) {
            map.closeInfoWindow();
        });
    }

    function openInfo(content, e) {
        var p = e.target;
        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
        var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
        map.openInfoWindow(infoWindow, point); //开启信息窗口
    }

</script>

 

