package com.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.mall.common.ServerResponse;
import com.mall.pojo.Shipping;
import com.mall.service.IShippingService;
import org.springframework.stereotype.Service;

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        return null;
    }

    @Override
    public ServerResponse<String> del(Integer userId, Integer shippingId) {
        return null;
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        return null;
    }

    @Override
    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {
        return null;
    }

    @Override
    public ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize) {
        return null;
    }
}
