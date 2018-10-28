package com.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.mall.common.ServerResponse;
import com.mall.dao.ProductMapper;
import com.mall.pojo.Product;
import com.mall.service.IProductService;
import com.mall.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
@Service("IProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    public ServerResponse saveOrUpdateProduct(Product product){
        if(product!=null){
            if(StringUtils.isNoneBlank(product.getSubImages())){
=======
@Service("iProductService")
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public ServerResponse saveOrUpdateProduct(Product product) {
        if(product!=null){
            if(StringUtils.isNotBlank(product.getSubImages())){
>>>>>>> 8360355204d135e73030ee9d1a45deb156048445
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length>0){
                    product.setMainImage(subImageArray[0]);
                }
            }
<<<<<<< HEAD
            if(product.getId()!=null){
                productMapper.updateByPrimaryKey(product);
                return ServerResponse.createBySuucessMessage("更新产品成功");

            }else{
                productMapper.insert(product);
                return ServerResponse.createBySuucessMessage("新增产品成功");
            }
        }
        return ServerResponse.createByErrorMessage("更新产品失败");
=======
        }
        if(product.getId()!=null){
            int rowCount = productMapper.updateByPrimaryKey(product);
            if(rowCount>0){
                return ServerResponse.createBySuucessMessage("更新商品成功");
            }else{

            }
        }
        return null;
>>>>>>> 8360355204d135e73030ee9d1a45deb156048445
    }

    @Override
    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        return null;
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {
        return null;
    }
}
