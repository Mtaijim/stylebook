package com.example.Payment.service.Service;

import com.example.Payment.service.Payload.PaymentResponse.PaymentLinkResponse;
import com.example.Payment.service.Payload.dto.BookingDto;
import com.example.Payment.service.Payload.dto.UserDto;
import com.example.Payment.service.Repository.PaymentOrderRepository;
import com.example.Payment.service.domain.PaymentMethod;
import com.example.Payment.service.domain.PaymentOrderStatus;
import com.example.Payment.service.modal.PaymentOrder;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${Stripe.api.key}")
    private String stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;


    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;
    @Override
    public PaymentLinkResponse createOrder(UserDto user,
                                           BookingDto booking, PaymentMethod paymentMethod) throws StripeException {

        Long amount = (long) booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        PaymentOrder savedOrder = paymentOrderRepository.save(order);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();
if (paymentMethod.equals(PaymentMethod.RAZORPAY)){
    PaymentLink payment = createRazorpayPaymentLink(user,
            savedOrder.getAmount(),
            savedOrder.getId());

    String paymentUrl = payment.get("short_url");
    String paymentUrlId = payment.get("id");
    paymentLinkResponse.setPayment_link_url(paymentUrl);
    paymentLinkResponse.setGetPayment_link_url(paymentUrlId);
    savedOrder.setPaymentLinkId(paymentUrlId);
    paymentOrderRepository.save(savedOrder);

}else {
    String paymentUrl=createStripePaymentLink(
            user,savedOrder.getAmount(),
            savedOrder.getId()
    );
    paymentLinkResponse.setPayment_link_url(paymentUrl);
}

        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderById(UUID id) {
        return paymentOrderRepository.findById(id).orElseThrow(()->
                new RuntimeException("Invalid order Id .payment not found"));
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String PaymentId) {
        return paymentOrderRepository.findByPaymentLinkId(PaymentId);
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(UserDto user, Long Amount, UUID orderId) {
         Long amount = Amount*100;
         try{
             RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey,razorpayApiSecret);
             JSONObject paymentLinkRequest = new JSONObject();
             paymentLinkRequest.put("amount",amount);
             paymentLinkRequest.put("currency","INR");

             JSONObject customer = new JSONObject();
             customer.put("name",user.getUsername());
             customer.put("email",user.getEmail());

             paymentLinkRequest.put("customer",customer);

             JSONObject notify = new JSONObject();
             notify.put("email",true);
             paymentLinkRequest.put("notify",notify);
             paymentLinkRequest.put("remainder_enable",true);

             paymentLinkRequest.put("callback_url","http://localhost:3000/payment-success/"+orderId);
             paymentLinkRequest.put("callback_method","get");

             return razorpayClient.paymentLink.create(paymentLinkRequest);


         }
         catch (RazorpayException e) {
             throw new RuntimeException(e);
         }

    }

    @Override
    public String createStripePaymentLink(UserDto user, Long amount, UUID orderId) throws StripeException {
        Stripe.apiKey =stripeSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/"+orderId)
                .setCancelUrl("http://localhost:3000/payment-cancel/")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                 .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem.PriceData
                                        .ProductData
                                .builder()
                                        .setName("salon appointment Booking").build()).build()
                        )
                        .build())
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException {
        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
      RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey,razorpayApiSecret);

                Payment payment = razorpayClient.payments.fetch(paymentId);
                Integer amount = payment.get("amount");
                String status = payment.get("status");

                if(status.equals("captured")){
//                    produce kafka event
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderRepository.save(paymentOrder);
                    return true;
                }
                return false;
            }else {
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);
                return true;
            }
        }

        return false;
    }
}
