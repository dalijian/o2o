package com.lijian.o2o.service.impl;

import com.lijian.o2o.enums.ProductStateEnum;
import com.lijian.o2o.dao.ProductDao;
import com.lijian.o2o.dao.ProductImgDao;
import com.lijian.o2o.dto.ProductExecution;
import com.lijian.o2o.entity.Product;
import com.lijian.o2o.entity.ProductImg;
import com.lijian.o2o.exception.ProductOperationException;
import com.lijian.o2o.service.ProductService;
import com.lijian.o2o.util.ImageHolder;
import com.lijian.o2o.util.ImageUtil;
import com.lijian.o2o.util.PageCalculator;
import com.lijian.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 1. 处理缩略图，获取缩略图相对路径并赋值给product
 * 2. 往tb_product 写入商品信息，获取productId
 * 3.结合productId批量处理商品详情图
 * 4.将商品详情图列表批量插入tb——product——img中
 *
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Transactional
    @Override
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> thumbnailList)throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            product.setCreateTime(new Date());
            product.setEnableStatus(1);

            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                ProductExecution productExecution = new ProductExecution();
                int effectedNum = productDao.addProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }

            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败：" + e.toString());
            }
            if (thumbnailList != null && thumbnailList.size() != 0) {
                addProductImgList(product, thumbnailList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);

        }

    }

    /**
     * 1. 若缩略图参数有值，则处理缩略图，
     * 若原先存在缩略图先删除在添加新图，之后获取缩略图相对路径并赋值给Product
     *
     *  * 2. 若商品详情图列表参数有值，对商品详情图列表进行同样的操作
     *  3. 将tb_product_img 下面的该商品原先的商品详情图记录全部删除 ，数据库记录使用update 覆盖 在删除物理地址
     *  4。 更新tb_product，tb_product_img的信息
     *  5.在Service 层只在 详细图列表 和 缩略图 更新， product 其他属性更新 有 web form表单提供 调用updateProduct 完成

     * @param product
     * @param thumbnail
     * @param thumbnailList
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> thumbnailList) throws ProductOperationException {
        //判断product， product。getshop（） 是否为空
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {

            //设置值
            product.setLastEditTime(new Date());


            if (thumbnail != null) {

//                先获取缩略图片 如果有图片 则删除物理地址， 数据库记录使用update 覆盖
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());

                }
                //设置product.ImgAddr 属性
                addThumbnail(product, thumbnail);
            }

            if (thumbnailList != null && thumbnailList.size() > 0) {
                //            先获取详情图片 如果有图片 则删除
                deleteProductImgList(product.getProductId());
                //添加详情图片
                addProductImgList(product, thumbnailList);
            }
            try {
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("更新商品信息失败");
                }
//                更新成功后
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("更新商品信息失败：" + e.toString());
            }

        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public Product getProductById(Long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
//        根据 pageIndex 与pageSize 得到 数据查询 的 limit 第一个 参数 rowIndex
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);

        ProductExecution productExecution=new ProductExecution();
        productExecution.setCount(count);
        productExecution.setProductList(productList);
        return productExecution;
    }

    //根据productID 拿到 详情图address ，在删除详情图物理地址
    private void deleteProductImgList(Long productId) {
        List<ProductImg> productImgList = productDao.queryProductImgList(productId);
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());

        }
//        删除数据库记录，多条记录没办法使用update更新
        productImgDao.deleteProductImgByProductId(productId);
    }
    //增加 productImg 详细图
    private void addProductImgList(Product product, List<ImageHolder> thumbnailList) {
        //  商品  缩略图 和详情图 保存在 shopId 文件夹 下 拿到目标文件夹
        String desc = PathUtil.getShopImagePath(product.getShop().getShopId()
        );
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        for (ImageHolder productImgHolder : thumbnailList) {
            //拿到详细图保存的相对地址
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder, desc);
            ProductImg productImg= new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setCreateTime(new Date());
            productImg.setProductId(product.getProductId());
            productImgList.add(productImg);

//            设置product.setProductImgList
            product.setProductImgList(productImgList);
        }
        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图片失败：" + e.toString());
            }
        }
    }

    private void addThumbnail(Product product, ImageHolder imageHolder) {
        // 获取shop 图片目录的相对值路径
        String desc = PathUtil.getShopImagePath(product.getShop().getShopId());
        //返回缩略   图片 相对路径
        String shopImgInputAddr = ImageUtil.generateThumbnail(imageHolder,desc);
        //设置 product缩略图片地址
       product.setImgAddr(shopImgInputAddr);
    }


}
