package com.example.Payment.service.Payload.PaymentResponse;

import lombok.Data;

@Data
public class PaymentLinkResponse {
    private String payment_link_url;
    private String getPayment_link_url;
}
