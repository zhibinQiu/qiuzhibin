package com.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mall.common.ServerResponse;
import com.mall.dao.ShippingMapper;
import com.mall.pojo.Shipping;
import com.mall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
           shipping.setUserId(userId);
           int rowCount = shippingMapper.insert(shipping);
           if(rowCount>0){
               Map result = Maps.newHashMap();
               result.put("shippingId",shipping.getId());
               return ServerResponse.createBySuccess("创建地址成功",result);
           }
           return ServerResponse.createByErrorMessage("创建地址失败");
    }

    @Override
    public ServerResponse<String> del(Integer userId, Integer shippingId) {
         int rowCount = shippingMapper.deleteByShippingIdUserId(userId,shippingId);
         if(rowCount>0){
             return ServerResponse.createBySuucessMessage("删除地址成功");
         }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping){
        int rowCount = shippingMapper.updateByShipping(shipping);
        if(rowCount>0){
            return ServerResponse.createBySuucessMessage("地址更新成功");
        }
        return ServerResponse.createByErrorMessage("地址更新失败");
    }

    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if(shipping==null){
            return ServerResponse.createByErrorMessage("无法查询到该地址");
        }
        return ServerResponse.createBySuccess("更新地址成功",shipping);
    }

    @Override
<<<<<<< HEAD
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize){
=======
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {
>>>>>>> 8360355204d135e73030ee9d1a45deb156048445
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
