package com.mall.service;

import com.github.pagehelper.PageInfo;
<<<<<<< HEAD
=======
import com.mall.common.ServerResponse;
import com.mall.pojo.Product;
import com.mall.vo.ProductDetailVo;
>>>>>>> 8360355204d135e73030ee9d1a45deb156048445

public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

<<<<<<< HEAD
    ServerResponse<String> setSaleStatus(Integer productId,Integer status);
=======
    ServerResponse<String>setSaleStatus(Integer productId,Integer status);
>>>>>>> 8360355204d135e73030ee9d1a45deb156048445

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);
<<<<<<< HEAD
=======


>>>>>>> 8360355204d135e73030ee9d1a45deb156048445
}
