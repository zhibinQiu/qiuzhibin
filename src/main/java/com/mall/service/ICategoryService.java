package com.mall.service;

import com.mall.common.ServerResponse;
import org.apache.ibatis.annotations.Param;

public interface ICategoryService {
    ServerResponse addCategory( String categoryName,Integer parentId);
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
