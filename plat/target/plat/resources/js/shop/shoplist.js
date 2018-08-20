function shopStatus(enableStatus) {
    if (enableStatus == 0) {
        return " 审核中";
    }else if (enableStatus == -1) {
        return "店铺非法";


    }else if(enableStatus===1) {
        return"审核通过";

    }
}

function goShop(enableStatus, shopId) {
    if (enableStatus === 1) {

        // return `<a href="/platform/shopadmin/shopmanagement?shopId=${shopId}">进入</a>`;
        return '<a href="/platform/shopadmin/shopmanagement?shopId=' + shopId
            + '">进入</a>';
    }else{
        return "";
    }
}

function handleList(shopList) {
    var html = "";
    shopList.map(function (item, index) {
        var $html=$(`<div class= "row row-shop">
            <div class="col-40">${item.shopName}</div>
            <div class="col-40">${shopStatus(item.enableStatus)}</div>
            <div class="col-20">${goShop(item.enableStatus,item.shopId)}</div>
            </div>`);

        $html.appendTo('.shop-wrap');
        }
    );


}

function handleUser(user) {

    $("#user_name").val(user.name);

}

$(function () {
    getList();
    function getList() {
        $.ajax({
            url:"/platform/shopadmin/getshoplist",
            type:"get",
            dataType:"json",
            success: function (data) {

                if (data.success) {
                    handleList(data.shopList);
                    handleUser(data.user);

                }

            }

        })



    }

});