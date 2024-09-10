package com.airline.booking_service.openfeign;

import com.airline.booking_service.dto.PaymentDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "PAYMENT-SERVICE",url = "${payment-service.url}")
@Configuration
public interface ApiClientForPayment {
    @RequestMapping(method = RequestMethod.POST,value = "/payment/add", consumes = "application/json")
    public PaymentDTO addPayment(PaymentDTO paymentDTO);
    @RequestMapping(method = RequestMethod.PUT,value = "/payment/cancel", consumes = "application/json")
    public String paymentCancel(PaymentDTO paymentDTO);
}
