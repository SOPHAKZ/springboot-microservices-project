package com.payment.repository;

import com.payment.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,Long> {
    TransactionDetails findByOrderId(long orderId);
}
