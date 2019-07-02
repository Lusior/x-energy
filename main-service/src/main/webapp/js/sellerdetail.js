$(function(){
    // 商户详情页
    $.ajax({
        url: 'http://192.168.1.212:9000/web/merc/detail?mercCd=8000000000000001&',
        contentType: "application/json; charset=utf-8",
        type: 'get',
        dataType: 'json',
        // data: {linkName,'name'},
        success: function(data){
                // $('#success .prompt').text(res.message);
                console.log(data)

        }
        // error:function(data){
        //     //
        // }
    })







})