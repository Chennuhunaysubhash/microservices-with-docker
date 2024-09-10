package com.airline.payment_service.openfeign;


import com.airline.payment_service.dto.FlightDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "FLIGHT-SERVICE",url = "${flight-service.url}")
@Configuration
public interface ApiClientForFlight {

    @RequestMapping(method = RequestMethod.PUT,value = "/flight/booking/seats", consumes = "application/json")
    public String updateSeats(FlightDTO flightDTO);
    @RequestMapping(method = RequestMethod.GET,value = "/flight/{id}")
    public FlightDTO getFlight(@PathVariable long id);

}
