package com.mall.service;

import com.mall.common.ServerResponse;

public interface IOrderService {
    ServerResponse createOrder(Integer userId, Integer shippingId);
}
