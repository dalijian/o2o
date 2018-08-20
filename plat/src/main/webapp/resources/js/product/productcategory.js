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
        var $html = $(`<div class= "row row-shop">
            <div class="col-40">${item.productCategoryName}</div>
            <div class="col-40">${item.priority}</div>
            <div class="col-20">${delCatogory(item.productCategoryId)}</div>
            </div>`);

        $html.appendTo('.shop-wrap');

    })
};

/*
删除分类
 */
function delCatogory(productcategoryId) {
    return `<a href="/platform/productadmin/productcategorydelete?productcategoryId=${productcategoryId}">删除</a>`;
}

/*
批量添加分类
 */
function addBatchProductCategory() {
    var $newProductCategory = $(`<div class="row temp">
      <div class="col-33"><input type="text" name="product_category_name" id="product_category_name" placeholder="商品分类名"></div>
      <div class="col-33"><input type="number" name="product_category_priority" id="product_category_priority" placeholder="优先级"></div>
      <div class="col-33"></div>
    </div>`);
    $newProductCategory.appendTo('.shop-wrap');

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
                $(".shop-wrap").empty();
                getList();
            } else {
                $.toast('提交失败！');
            }
        }
    });

}

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

$(".category-wrap").on("click", "",
    function (e) {
        console.log($(this).parent().parent());
        $(this.parent().parent().remove());

    });
$(".category-wrap").on("click", " ",

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
                }else {
                    $.toast("删除失败");
                }

            }
        })

    });

}
