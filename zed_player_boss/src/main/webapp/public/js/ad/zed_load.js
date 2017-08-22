$(function(){
    $.ajax({
        type : "get",
        url : "http://boss.3dzed.com/boss/ad/getAdInfo.action",
        data : {
            "appId":"{appId}"
        },
        dataType :'jsonp',
        jsonp : 'callback',
        jsonpCallback:'callback',
        success :function(data){
            console.info("getAdInfo",data);
            $('#content > a').attr('href',data.bannerClickUrl);
            $('#content > a > img').attr('src',data.bannerImageUrl);
            $('#text-5 > div > p > a').attr('href',data.viewClickUrl);
            $('#text-5 > div > p > a > img').attr('src',data.viewImageUrl).attr('srcset',data.viewImageUrl);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown){
            console.info(XMLHttpRequest.status,XMLHttpRequest.readyState,textStatus);
        }
    });
});