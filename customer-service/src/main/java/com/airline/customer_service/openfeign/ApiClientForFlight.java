package com.airline.customer_service.openfeign;

import com.airline.customer_service.dto.FlightDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "FLIGHT-SERVICE",url = "${flight-service.url}")
@Configuration
public interface ApiClientForFlight {
    @RequestMapping(method = RequestMethod.GET,value = "/flight/{id}")
    public FlightDTO getFlight(@PathVariable long id);

    @RequestMapping(method = RequestMethod.GET,value = "/flight/all")
    public List<FlightDTO> allFlight();

    @RequestMapping(method = RequestMethod.PUT,value = "/flight/booking/search", consumes = "application/json")
    public List<FlightDTO> flightSearch(FlightDTO searchDTO);
}
