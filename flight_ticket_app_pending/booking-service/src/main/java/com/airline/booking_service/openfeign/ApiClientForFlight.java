package com.airline.booking_service.openfeign;

import com.airline.booking_service.dto.FlightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "FLIGHT-SERVICE",url = "${flight-service.url}")
@Configuration
public interface ApiClientForFlight {
    @RequestMapping(method = RequestMethod.GET,value = "/flight/{id}")
    public FlightDTO getFlight(@PathVariable long id);
    @RequestMapping(method = RequestMethod.PUT,value = "/flight/booking/cancel", consumes = "application/json")
    public String updateSeats(FlightDTO flightDTO);

}
