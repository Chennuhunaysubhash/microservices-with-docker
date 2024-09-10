package com.airline.booking_service.service;

import com.airline.booking_service.dto.*;
import com.airline.booking_service.entity.Booking;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BookingService {
    public BookingDTO mapToBookingDTO(Booking booking);
    public Booking mapToBooking(BookingDTO bookingDTO);

    public List<BookingDTO> findAll();

    public BookingDTO findById(long bookingId );

    public String deleteById(long bookingId);

    public BookingDTO save(BookingDTO bookingDTO);

    public List<BookingDTO> allByCid(int cid);

    public CustomerDTO findByCusId(int customerId );

    public FlightDTO getFlight(long id);

    public PaymentDTO addPayment(PaymentDTO paymentDTO);

    public String statusUpdate(StatusUpdateDTO statusUpdateDTO);
    public String bookingClosingServiceMethod(BookingCancelDTO bookingCancelDTO);

    public String updateSeatsWithCancel(FlightDTO flightDTO);
    public String paymentCancel(PaymentDTO paymentDTO);

    public List<BookingDTO> search(int cid,long flightId,int noSeats);
    public  PaymentDTO getPaymentDTO(BookingDTO addBooking);
    public BookingDTO setPro(BookingDTO bookingDTO);



}
