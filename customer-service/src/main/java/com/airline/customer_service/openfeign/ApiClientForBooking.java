package com.airline.customer_service.openfeign;

import com.airline.customer_service.dto.BookingCancelDTO;
import com.airline.customer_service.dto.BookingDTO;
import com.airline.customer_service.dto.FlightDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "BOOKING-SERVICE",url = "${booking-service.url}")
@Configuration
public interface ApiClientForBooking {
    @RequestMapping(method = RequestMethod.GET,value = "/booking/{id}")
    public BookingDTO getBooking(@PathVariable long id);

    @RequestMapping(method = RequestMethod.GET,value = "/booking/all/{cid}")
    public List<BookingDTO> getAllBookingByCid(@PathVariable long cid);
    @RequestMapping(method = RequestMethod.POST,value = "/booking/add",consumes = "application/json")
    public BookingDTO addBooking(BookingDTO bookingDTO);
    @RequestMapping(method = RequestMethod.PUT,value = "/booking/cancel",consumes = "application/json")
    public String bookingCancel(BookingCancelDTO bookingCancelDTO);
}
