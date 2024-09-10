package com.airline.payment_service.message;

import com.airline.payment_service.dto.PaymentDTO;
import com.airline.payment_service.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMessageConsumer {
    @Autowired
    private PaymentService paymentService;

    @RabbitListener(queues = "paymentQueue")
    public void consumeMessage(PaymentDTO paymentDTO){
        //paymentService.printPayment(paymentDTO);
        paymentService.save(paymentDTO);
    }
    @RabbitListener(queues = "paymentQueueCancel")
    public void consumeMessageCancel(PaymentDTO paymentDTO){
        //paymentService.printPayment(paymentDTO);
        paymentService.paymentDoneWithCancel(paymentDTO);
    }

}
