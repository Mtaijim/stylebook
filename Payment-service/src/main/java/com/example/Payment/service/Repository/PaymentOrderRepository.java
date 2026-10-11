package com.example.Payment.service.Repository;

import com.example.Payment.service.modal.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, UUID> {
    PaymentOrder findByPaymentLinkId(String paymentLinkId);
}
