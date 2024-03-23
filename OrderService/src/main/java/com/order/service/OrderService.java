package com.order.service;

import com.order.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

}
