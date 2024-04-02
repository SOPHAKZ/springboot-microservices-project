package com.order.service;

import com.order.model.OrderRequest;
import com.order.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
