$(function () {
    var shopId = getQueryString("shopId");
    var shopInfoUrl = '/platform/shopadmin/getshopmanagementinfo?shopId='+shopId;
    $.getJSON(shopInfoUrl, function (data) {

        if (data.redirect) {
            location.assign(data.url);

        }else{
            if (data.shopId != undefined && data.shopId != null) {

                shopId =data.shopId;

            }
            $("#shopInfo").attr('href', '/platform/shopadmin/shopoperation?shopId=' + shopId);
        }

    });

});
//从url中拿到 参数
function getQueryString(parameter) {
    var reg = new RegExp("(^|&)" + parameter + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return decodeURI(r[2]);
    return null; //返回参数值
}