package com.payment.service;

import com.payment.entity.TransactionDetails;
import com.payment.model.PaymentMode;
import com.payment.model.PaymentRequest;
import com.payment.model.PaymentResponse;
import com.payment.repository.TransactionDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final TransactionDetailsRepository tranRepository;
    @Override
    public long doPayment(PaymentRequest paymentRequest) {

        log.info("Recoding payment Details: {}",paymentRequest);

        TransactionDetails transactionDetails =
                TransactionDetails.builder()
                        .paymentDate(Instant.now())
                        .paymentMode(paymentRequest.getPaymentMode().name())
                        .paymentStatus("SUCCESS")
                        .orderId(paymentRequest.getOrderId())
                        .referenceNumber(paymentRequest.getReferenceNumber())
                        .amount(paymentRequest.getAmount())
                        .build();

        tranRepository.save(transactionDetails);

        log.info("Transaction competed with Id: {}",transactionDetails.getId());
        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(String orderId) {
        log.info("Getting payment details for the Order Id : {}",orderId);
        
        TransactionDetails transactionDetails = tranRepository.findByOrderId(Long.parseLong(orderId));

        return PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .status(transactionDetails.getPaymentStatus())
                .amount(transactionDetails.getAmount())
                .orderId(transactionDetails.getOrderId())
                .paymentDate(transactionDetails.getPaymentDate())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .build();
    }
}
