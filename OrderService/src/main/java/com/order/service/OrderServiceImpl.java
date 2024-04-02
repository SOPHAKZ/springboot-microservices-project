package com.order.service;

import com.order.entity.Order;
import com.order.exception.CustomException;
import com.order.external.client.PaymentService;
import com.order.external.client.ProductService;
import com.order.external.request.PaymentRequest;
import com.order.model.*;
import com.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final RestTemplate restTemplate;

    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // order
        // reduce product quantity
        // payment
        // cancel

        log.info("Placing Order Request:  {} ",orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());


        log.info("Creating Order with Status CREATED");

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepository.save(order);
        log.info("Calling Payment service to complete the payment ");

        PaymentRequest paymentRequest =
                PaymentRequest.builder()
                        .orderId(order.getId())
                        .paymentMode(orderRequest.getPaymentMode())
                        .amount(orderRequest.getTotalAmount())
                        .build();
        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully . Changing order status to place");
            orderStatus = "PLACED";
        }catch (Exception e){
            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);

        orderRepository.save(order);
        log.info("Created Order successfully with Order Id: {}",order.getId());
        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get Order Details for order Id  : {}",orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new CustomException("Order not found for the order Id : "+orderId,HttpStatus.NOT_FOUND ));

        log.info("Invoking Product service to fetch the product details ");

        ProductResponse productResponse = restTemplate.getForObject("http://PRODUCT-SERVICE/product/" +order.getProductId(),
                ProductResponse.class);


        log.info("Getting Payment information from the Payment service");

        PaymentResponse paymentResponse
                = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/"+orderId,PaymentResponse.class);

        OrderResponse orderResponse =
                OrderResponse.builder()
                        .orderId(order.getId())
                        .orderDate(order.getOrderDate())
                        .orderStatus(order.getOrderStatus())
                        .amount(order.getAmount())
                        .productResponse(productResponse)
                        .paymentResponse(paymentResponse)
                        .build();


        return orderResponse;
    }
}
