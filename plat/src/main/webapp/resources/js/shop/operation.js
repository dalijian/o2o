//从url中拿到 参数
function getQueryString(parameter) {
    var reg = new RegExp("(^|&)" + parameter + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return decodeURI(r[2]);
    return null; //返回参数值
}


$(function () {

    var shopId = getQueryString("shopId");
    /*
    如果 shopId 存在 则 是谢盖 shop
    如果shopId 不存在 则是 添加shop
     */
    var isEdit = shopId ? true : false;

    //初始化 shop 的url
    var initUrl = "/platform/shopadmin/getshopinitinfo";
    //添加   shop的 url
    var registerShopUrl = "/platform/shopadmin/registershop";

    //修改 shop 的 url

    var editInfoUrl = "/platform/shopadmin/modifyshop";


    var getshopInfoUrl = "/platform/shopadmin/getshopbyid?shopId=" + shopId;


    if (!isEdit) {
        //调用getShopInitInfo();
        getShopInitInfo();
    } else {
        getShopInfo(shopId);
    }

//根据店铺Id获取店铺信息

    function getShopInfo(shopId) {
        $.getJSON(getshopInfoUrl, function (data) {

            if (data.success) {
                // 若访问成功，则依据后台传递过来的店铺信息为表单元素赋值
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                // 给店铺类别选定原先的店铺类别值

                var shopCategory = `<option date-id=${shop.shopCategory.shopCategoryId} selectd>${shop.shopCategory.shopCategoryName}</option>`;
                var tempAreaHtml = '';
                // 初始化区域列表
                $.each(data.areaList, function (index, content) {

                    tempAreaHtml += `<option data-id=${content.areaId}>${content.areaName}</option>`;

                });
                $('#shop-category').html(shopCategory);
                // 不允许选择店铺类别
                $('#shop-category').attr('disabled', 'disabled');
                $('#area').html(tempAreaHtml);
                // 给店铺选定原先的所属的区域
                $("#area option[data-id='" + shop.area.areaId + "']").attr(
                    "selected", "selected");

            }
        });


    }

    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                //将shopCategory name  area name 添加到 option中
                $.each(data.shopCategoryList, function (index, content) {

                    tempHtml += `<option data-id=${content.shopCategoryId}>${content.shopCategoryName}</option>`;

                });
                $.each(data.areaList, function (index, content) {
                    tempAreaHtml += `<option data-id=${content.areaId}>${content.areaName}</option>`;
                });
                $("#shop-category").html(tempHtml);
                $("#area").html(tempAreaHtml);
            }

        });


    }

    //提交表单
    $("#submit").click(function () {

        // 创建shop对象
        var shop = {};
        if (isEdit) {
            // 若属于编辑，则给shopId赋值
            shop.shopId = shopId;
        }
        // 获取表单里的数据并填充进对应的店铺属性中
        shop.shopName = $("#shop-name").val();
        shop.shopAddr = $("#shop-addr").val();
        shop.phone = $("#shop-phone").val();
        shop.shopDesc = $("#shop-desc").val();
        shop.shopCategory = {
            shopCategoryId: $("#shop-category").find("option").not(function () {
                return !this.selected;


            }).data("id")
        };
        shop.area = {
            areaId: $("#area").find("option").not(function () {
                return !this.selected;


            }).data("id")
        };
        var shopImg = $("#shop-img")[0].files[0];

        //FormData对象用以将数据编译成键值对，以便用XMLHttpRequest来发送数据。
        var formData = new FormData();
        formData.append("shopImg", shopImg);
        formData.append("shopStr", JSON.stringify(shop));

        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast("请输入验证码");
            return;
        }

        formData.append("verifyCodeActual", verifyCodeActual);

        $.ajax({
            url: (registerShopUrl),
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
                    if (!isEdit) {
                        // 若为注册操作，成功后返回店铺列表页
                        window.location.href = "/o2o/shopadmin/shoplist";
                    }
                } else {
                    $.toast('提交失败！' + data.errMsg);
                }
                // 点击验证码图片的时候，注册码会改变
                $('#captcha_img').click();
            }
        });

    });
});