package com.airline.booking_service.message;

import com.airline.booking_service.dto.PaymentDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentMessageProducer {


    public final RabbitTemplate rabbitTemplate;

    public PaymentMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    /* public void sendPayment(PaymentDTO paymentDTO){
     PaymentDTO paymentDTO1 = new PaymentDTO();
        paymentDTO1.setcId(paymentDTO.getcId());
        paymentDTO1.setCustomerName(paymentDTO.getCustomerName());
        paymentDTO1.setBookingId(paymentDTO.getBookingId());
        paymentDTO1.setFlightId(paymentDTO.getFlightId());
        paymentDTO1.setFlightName(paymentDTO.getFlightName());
        paymentDTO1.setNoSeats(paymentDTO.getNoSeats());
        paymentDTO1.setPrice(paymentDTO.getPrice());
        paymentDTO1.setTotal(paymentDTO.getTotal());
        paymentDTO1.setPaymentType(paymentDTO.getPaymentType());
        paymentDTO1.setStatus(paymentDTO.getStatus());
        paymentDTO1.setInvoiceStatus(paymentDTO.getInvoiceStatus());
        rabbitTemplate.convertAndSend("paymentQueue",paymentDTO1);
    }
     public void sendPaymentForCancel(PaymentDTO paymentDTO){
        PaymentDTO paymentDTO1 = new PaymentDTO();
        paymentDTO1.setId(paymentDTO.getId());
        paymentDTO1.setcId(paymentDTO.getcId());
        paymentDTO1.setCustomerName(paymentDTO.getCustomerName());
        paymentDTO1.setBookingId(paymentDTO.getBookingId());
        paymentDTO1.setFlightId(paymentDTO.getFlightId());
        paymentDTO1.setFlightName(paymentDTO.getFlightName());
        paymentDTO1.setNoSeats(paymentDTO.getNoSeats());
        paymentDTO1.setPrice(paymentDTO.getPrice());
        paymentDTO1.setTotal(paymentDTO.getTotal());
        paymentDTO1.setPaymentType(paymentDTO.getPaymentType());
        paymentDTO1.setStatus(paymentDTO.getStatus());
        paymentDTO1.setInvoiceStatus(paymentDTO.getInvoiceStatus());
        rabbitTemplate.convertAndSend("paymentQueueCancel",paymentDTO1);
    }*/
    public void sendPayment(PaymentDTO paymentDTO){
        rabbitTemplate.convertAndSend("paymentQueue", paymentDTO);
    }

    public void sendPaymentForCancel(PaymentDTO paymentDTO){
        rabbitTemplate.convertAndSend("paymentQueueCancel", paymentDTO);
    }

}
