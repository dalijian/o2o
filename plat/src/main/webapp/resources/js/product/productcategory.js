//从url中拿到 参数
function getQueryString(parameter) {
    var reg = new RegExp("(^|&)" + parameter + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r != null) return decodeURI(r[2]);
    return null; //返回参数值
}




$(function () {
    getList();


});
var getListUrl= '/platform/productadmin/getproductcategory';
var addUrl = '/platform/productadmin/addproductcategory';
var deleteUrl = '/platform/productadmin/removeproductcategory';
/*
拿到商品分类列表
 */
function getList() {
    $.ajax({
        url: getListUrl,
        type: "get",
        dataType: "json",
        success: function (data) {

            if (data.success) {
                handleList(data.data);
            }
        }

    })

}

function handleList(productList) {
    productList.map(function (item, index) {
        var $html = $(`<div class= "row row-shop now">
            <div class="col-40">${item.productCategoryName}</div>
            <div class="col-40">${item.priority}</div>
            <div class="col-20">${delCatogory(item.productCategoryId)}</div>
            </div>`);

        $html.appendTo('.category-wrap');

    })
};

/*
删除分类
 */
function delCatogory(productcategoryId) {
    return `<a class="delete button"href="#" data-id="${productcategoryId}">删除</a>`;
}

/*
批量添加分类
 */
function addBatchProductCategory() {
    var $newProductCategory = $(`<div class="row temp">
      <div class="col-33"><input type="text" name="product_category_name" id="product_category_name" placeholder="商品分类名"></div>
      <div class="col-33"><input type="number" name="product_category_priority" id="product_category_priority" placeholder="优先级"></div>
      <div class="col-33"><a href="javascript:void(0)" class="delete button">删除</a></div>
    </div>`);
    $newProductCategory.appendTo('.category-wrap');

}



/*
提交批量添加的分类
 */
function submitProductCategory() {
	$.ajax({
        url: addUrl,
        type: 'POST',
        data: getAddProductCategoryList(),
        contentType: 'application/json',
        success: function (data) {
            if (data.success) {
                $.toast('提交成功！');
                $(".category-wrap").empty();
                getList();
            } else {
                $.toast('提交失败！');
            }
        }
    });

}
/*
拿到批量添加的数据
 */
function getAddProductCategoryList() {

    var tempArr = $('.temp');
    var productCategoryList = [];
    tempArr.map(function(index, item) {
        var tempObj = {};
        tempObj.productCategoryName = $(item).find('#product_category_name').val();
        tempObj.priority = $(item).find('#product_category_priority').val();
        if (tempObj.productCategoryName && tempObj.priority) {
            productCategoryList.push(tempObj);
        }
    });
    productCategoryList = JSON.stringify(productCategoryList);
    return productCategoryList;
}

$(".category-wrap").on("click", ".row.temp .delete",
    function (e) {
        console.log($(this).parent().parent());
        $(this).parent().parent().remove();

    });
$(".category-wrap").on("click", ".row.now .delete",

function (e) {
    var target =e.currentTarget;
    $.confirm("确定吗", function () {
        $.ajax({
            url:deleteUrl,
            type:"post",
            data:{
                productCategoryId:target.dataset.id
            },
            dataType:"json",
            success: function (data) {
                if (data.success) {
                    $.toast("删除成功");
                    $(".category-wrap").empty();
                    getList();
                }else {
                    $.toast("删除失败");
                }

            }
        })

    });

});
