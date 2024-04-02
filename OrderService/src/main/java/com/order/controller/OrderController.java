package com.order.controller;


import com.order.model.OrderRequest;
import com.order.model.OrderResponse;
import com.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        long placeOrder = orderService.placeOrder(orderRequest);

        return new ResponseEntity<>(placeOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long orderId){
        OrderResponse orderResponse =
                orderService.getOrderDetails(orderId);

        return new ResponseEntity<>(orderResponse,HttpStatus.OK);
    }
}
