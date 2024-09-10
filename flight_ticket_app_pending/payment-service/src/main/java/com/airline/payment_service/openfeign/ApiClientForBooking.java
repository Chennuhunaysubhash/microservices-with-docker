package com.airline.payment_service.openfeign;

import com.airline.payment_service.dto.StatusUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "BOOKING-SERVICE",url = "${booking-service.url}")
@Configuration
public interface ApiClientForBooking {
    @RequestMapping(method = RequestMethod.PUT,value = "/booking/status", consumes = "application/json")
    public String setStatus(StatusUpdateDTO statusUpdateDTO);
}
