package com.hieupahm.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hieupahm.domain.PaymentMethod;
import com.hieupahm.domain.PaymentOrderStatus;
import com.hieupahm.error.UserException;
import com.hieupahm.model.PaymentOrder;
import com.hieupahm.payload.dto.BookingDTO;
import com.hieupahm.payload.dto.UserDTO;
import com.hieupahm.payload.res.PaymentLinkResponse;
import com.hieupahm.repository.PaymentOrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl {
    private final PaymentOrderRepository paymentOrderRepository;

    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod)
            throws RazorpayException, UserException, StripeException {

        Long amount = (long) booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();
        order.setUserId(user.getId());
        order.setAmount(amount);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        order.setPaymentMethod(paymentMethod);
        PaymentOrder paymentOrder = paymentOrderRepository.save(order);

        PaymentLinkResponse res = new PaymentLinkResponse();
        if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            PaymentLink payment = createRazorpayPaymentLink(user,
                    paymentOrder.getAmount(),
                    paymentOrder.getId());
            String paymentUrl = payment.get("short_url");
            String paymentUrlId = payment.get("id");

            res.setPayment_link_url(paymentUrl);
            paymentOrder.setPaymentLinkId(paymentUrlId);
            paymentOrderRepository.save(paymentOrder);
        } else {
            String paymentUrl = createStripePaymentLink(user,
                    paymentOrder.getAmount(),
                    paymentOrder.getId());

            res.setPayment_link_url(paymentUrl);
        }

        return res;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        Optional<PaymentOrder> optionalPaymentOrder = paymentOrderRepository.findById(id);
        if (optionalPaymentOrder.isEmpty()) {
            throw new Exception("payment order not found with id " + id);
        }
        return optionalPaymentOrder.get();
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentLinkId) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository
                .findByPaymentLinkId(paymentLinkId);

        if (paymentOrder == null) {
            throw new Exception("payment order not found with id " + paymentLinkId);
        }
        return paymentOrder;
    }

    @Override
    public Boolean ProceedPaymentOrder(PaymentOrder paymentOrder, String paymentId, String paymentLinkId)
            throws StripeException {

        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {

            if (paymentOrder.getPaymentMethod().equals(PaymentMethod.STRIPE)) {

                // Integer amount = payment.get("amount");
                // String status = payment.get("status");

                if (status.equals("captured")) {
                    notificationEventProducer.sentNotificationEvent(
                            paymentOrder.getBookingId(),
                            paymentOrder.getUserId(),
                            paymentOrder.getSalonId());

                    bookingEventProducer.sentBookingUpdateEvent(paymentOrder);

                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderRepository.save(paymentOrder);

                    return true;
                }
                paymentOrder.setStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepository.save(paymentOrder);
                return false;
            } else {

                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderRepository.save(paymentOrder);

                return true;
            }

        }

        return false;
    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/" + orderId)
                .setCancelUrl("http://localhost:3000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount * 100)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                        .builder()
                                        .setName("Top up wallet")
                                        .build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);

        return session.getUrl();
    }
}
