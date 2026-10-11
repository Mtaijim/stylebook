package com.example.Payment.service.controllers;

import com.example.Payment.service.Payload.PaymentResponse.PaymentLinkResponse;
import com.example.Payment.service.Payload.dto.BookingDto;
import com.example.Payment.service.Payload.dto.UserDto;
import com.example.Payment.service.Service.PaymentService;
import com.example.Payment.service.domain.PaymentMethod;
import com.example.Payment.service.modal.PaymentOrder;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDto bookingDto,
            @RequestParam PaymentMethod paymentMethod
            ) throws StripeException {
        UserDto user = new UserDto();
        user.setUsername("Ashok");
        user.setId();
        user.setEmail("ashok@gmail.com");

        PaymentLinkResponse response = paymentService.createOrder(user,bookingDto,paymentMethod);
            return ResponseEntity.ok(response);
        

    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable UUID paymentOrderId
            ) throws StripeException {
        PaymentOrder response = paymentService.getPaymentOrderById(paymentOrderId);
            return ResponseEntity.ok(response);
        

    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId
    ) throws RazorpayException {
        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);
        Boolean res = paymentService.proceedPayment(paymentOrder,paymentId,paymentLinkId);
        return ResponseEntity.ok(res);
    }

}
