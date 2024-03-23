package com.order.service;

import com.order.entity.Order;
import com.order.model.OrderRequest;
import com.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // order
        // reduce product quantity
        // payment
        // cancel

        log.info("Placing Order Request:  {} ",orderRequest);

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);
        log.info("Created Order successfully with Order Id: {}",order.getId());
        return order.getId();
    }
}
