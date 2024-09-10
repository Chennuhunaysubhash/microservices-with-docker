package com.airline.booking_service.openfeign;

import com.airline.booking_service.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@FeignClient(name = "CUSTOMER-SERVICE",url = "${customer-service.url}")
@Configuration
public interface ApiClientForCustomer {

    @RequestMapping(method = RequestMethod.GET,value = "/customer/{id}")
    public CustomerDTO getCustomer(@PathVariable int id);
}
