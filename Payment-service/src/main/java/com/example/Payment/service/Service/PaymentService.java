package com.example.Payment.service.Service;

import com.example.Payment.service.Payload.PaymentResponse.PaymentLinkResponse;
import com.example.Payment.service.Payload.dto.BookingDto;
import com.example.Payment.service.Payload.dto.UserDto;
import com.example.Payment.service.domain.PaymentMethod;
import com.example.Payment.service.modal.PaymentOrder;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

import java.util.UUID;

public interface PaymentService {
    PaymentLinkResponse createOrder(
            UserDto user,
            BookingDto booking,
            PaymentMethod paymentMethod
    ) throws StripeException;

    PaymentOrder getPaymentOrderById(UUID id);
    PaymentOrder getPaymentOrderByPaymentId(String PaymentId);

    PaymentLink createRazorpayPaymentLink(UserDto user,
                                          Long amount,
                                          UUID orderId);
    String createStripePaymentLink(UserDto user,
                                   Long amount,
                                   UUID orderId) throws StripeException;
    Boolean proceedPayment(PaymentOrder paymentOrder,String paymentId, String paymentLinkId) throws RazorpayException;
}
