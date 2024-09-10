package com.airline.booking_service.service.imp;

import com.airline.booking_service.dto.*;
import com.airline.booking_service.entity.Booking;
import com.airline.booking_service.exception.BookingAlreadyCanceledException;
import com.airline.booking_service.exception.DataNotFoundException;
import com.airline.booking_service.exception.PaymentServerNotRespondException;
import com.airline.booking_service.exception.ResourceNotFoundException;
import com.airline.booking_service.message.PaymentMessageProducer;
import com.airline.booking_service.openfeign.ApiClientForCustomer;
import com.airline.booking_service.openfeign.ApiClientForFlight;
import com.airline.booking_service.openfeign.ApiClientForPayment;
import com.airline.booking_service.repository.BookingRepository;
import com.airline.booking_service.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImp implements BookingService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ApiClientForCustomer apiClientForCustomer;

    @Autowired
    private ApiClientForFlight apiClientForFlight;
    @Autowired
    private ApiClientForPayment apiClientForPayment;

    @Autowired
    private PaymentMessageProducer paymentMessageProducer;

    @Override
    public BookingDTO mapToBookingDTO(Booking booking) {
        BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
        return bookingDTO;
    }

    @Override
    public Booking mapToBooking(BookingDTO bookingDTO) {
        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        return booking;
    }

    @Override
    public List<BookingDTO> findAll() {
        return bookingRepository.findAll().stream().map(this::mapToBookingDTO).collect(Collectors.toList());
    }

    @Override
    public BookingDTO findById(long bookingId) {
        Optional<Booking> result = bookingRepository.findById(bookingId);
        Booking booking = null;
        if (result.isPresent()){
            booking=result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return this.mapToBookingDTO(booking);
    }

    @Override
    public String deleteById(long bookingId) {
        String message;
        Optional<Booking> result = bookingRepository.findById(bookingId);
        if (result.isPresent()){
            bookingRepository.deleteById(bookingId);
            message ="Delete Booking successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }

        return message;

    }

    @Override
    public BookingDTO save(BookingDTO bookingDTO) {
        try{
            Booking booking = bookingRepository.save(mapToBooking(bookingDTO));
            BookingDTO bookingDTO1 = mapToBookingDTO(booking);
            PaymentDTO paymentDTO = getPaymentDTO(bookingDTO1);
            paymentMessageProducer.sendPayment(paymentDTO);
            //addPayment(paymentDTO);
            return bookingDTO1;
        }catch (Exception e){
            List<BookingDTO> bookingDTO1 = search(bookingDTO.getCid(),bookingDTO.getFlightId(),bookingDTO.getNoSeats());
            int size = bookingDTO1.size();
            //System.out.println(size);
            BookingDTO bookingDTO2 = bookingDTO1.get(size-1);
            deleteById(bookingDTO2.getId());
            //System.out.println(bookingDTO2.getId());
            throw new PaymentServerNotRespondException();
        }
        finally {

        }

    }

    @Override
    public List<BookingDTO> allByCid(int cid) {
        List<BookingDTO> result = bookingRepository.allByCid(cid).stream().map(this::mapToBookingDTO).collect(Collectors.toList());
        if(result.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return result;
    }

    @Override
    public CustomerDTO findByCusId(int customerId) {
        CustomerDTO customerDTO;
        try{
            customerDTO = apiClientForCustomer.getCustomer(customerId);
        }catch (Exception ex){
            throw new DataNotFoundException();
        }
        return customerDTO;
    }

    @Override
    public FlightDTO getFlight(long id) {
        FlightDTO flightDTO;
        try{
            flightDTO = apiClientForFlight.getFlight(id);
        }catch (Exception ex){
            throw new DataNotFoundException();
        }
        return flightDTO;
    }
    @Override
    public  PaymentDTO getPaymentDTO(BookingDTO addBooking) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setcId(addBooking.getCid());
        paymentDTO.setCustomerName(addBooking.getCustomerName());
        paymentDTO.setBookingId(addBooking.getId());
        paymentDTO.setFlightId(addBooking.getFlightId());
        paymentDTO.setFlightName(addBooking.getFlightName());
        paymentDTO.setNoSeats(addBooking.getNoSeats());
        paymentDTO.setPrice(addBooking.getPrice());
        paymentDTO.setTotal(addBooking.getTotal());
        paymentDTO.setPaymentType("Unknown(NextStep)");
        paymentDTO.setStatus(addBooking.getStatus());
        paymentDTO.setInvoiceStatus(false);

        return paymentDTO;
    }
    @Override
    public BookingDTO setPro(BookingDTO bookingDTO){
        CustomerDTO customerDTO = apiClientForCustomer.getCustomer(bookingDTO.getCid());
        FlightDTO flightDTO = apiClientForFlight.getFlight(bookingDTO.getFlightId());
        if(customerDTO==null){
            throw new DataNotFoundException();
        }
        if(flightDTO==null){
            throw new ResourceNotFoundException();
        }
        bookingDTO.setCustomerName(customerDTO.getName());
        bookingDTO.setFlightName(flightDTO.getFlightName());
        bookingDTO.setFrom(flightDTO.getFrom());
        bookingDTO.setTo(flightDTO.getTo());
        bookingDTO.setDeparture(flightDTO.getDeparture());
        bookingDTO.setTime(flightDTO.getTime());
        bookingDTO.setPrice(flightDTO.getPrice());
        bookingDTO.setTotal(flightDTO.getPrice()*bookingDTO.getNoSeats());
        bookingDTO.setStatus("Payment pending");
        return bookingDTO;
    }



    @Override
    public PaymentDTO addPayment(PaymentDTO paymentDTO) {
        return apiClientForPayment.addPayment(paymentDTO);
    }

    @Override
    /*public String statusUpdate(StatusUpdateDTO statusUpdateDTO) {
        String msg;
        bookingRepository.statusUpdate(statusUpdateDTO.getStatus(),statusUpdateDTO.getId());
        Optional<Booking> bookingDTO = bookingRepository.findById(statusUpdateDTO.getId());
        if(bookingDTO.get().getStatus().equals("Payment Done")){
            msg ="Payment successfully";
        }
        else {
            msg ="Payment not successfully";
        }
        return msg;
    }*/

    public String statusUpdate(StatusUpdateDTO statusUpdateDTO)
    {
        bookingRepository.statusUpdate(statusUpdateDTO.getStatus(), statusUpdateDTO.getId());
        return bookingRepository.findById(statusUpdateDTO.getId())
                .map(booking -> "Payment Done".equals(booking.getStatus()) ? "Payment successful" : "Payment not successful")
                .orElse("Booking not found");
    }


    @Override
    public String bookingClosingServiceMethod(BookingCancelDTO bookingCancelDTO) {
        String msg;
        BookingDTO bookingDTO = findById(bookingCancelDTO.getId());
        String bookingDate = bookingDTO.getDeparture();
        String closingDate = bookingCancelDTO.getCancelingDate();
        LocalDate bDate = LocalDate.parse(bookingDate);
        LocalDate cDate = LocalDate.parse(closingDate);
        if(cDate.isBefore(bDate) && bookingDTO.getTotal()==bookingCancelDTO.getTotal()){
            BookingDTO bookingDTO1 = findById(bookingCancelDTO.getId());
            if(bookingDTO1.getStatus().contains("Booking Canceled")){
                throw new BookingAlreadyCanceledException();
            }
            bookingRepository.bookingClosing("Booking Canceled with "+closingDate,bookingCancelDTO.getId(),bookingCancelDTO.getCid());
            msg="Booking Successfully Canceling";
            FlightDTO flightDTO = getFlight(bookingDTO1.getFlightId());
            flightDTO.setId(bookingDTO1.getFlightId());
            flightDTO.setFlightName(bookingDTO1.getFlightName());
            flightDTO.setBookedSeats(bookingDTO1.getNoSeats());
            updateSeatsWithCancel(flightDTO);
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setcId(bookingDTO1.getCid());
            paymentDTO.setBookingId(bookingDTO1.getId());
            paymentDTO.setStatus("Booking Canceled with "+closingDate);

           // paymentCancel(paymentDTO);
        }
        else {
            msg ="Booking Canceling is Closed or Canceling Date Not Accepting";
        }

        return msg;
    }

    @Override
    public String updateSeatsWithCancel(FlightDTO flightDTO) {
        return apiClientForFlight.updateSeats(flightDTO);
    }

    @Override
    public String paymentCancel(PaymentDTO paymentDTO) {
        return apiClientForPayment.paymentCancel(paymentDTO);
    }

    @Override
    public List<BookingDTO> search(int cid, long flightId, int noSeats) {
        return bookingRepository.search(cid,flightId,noSeats).stream().map(this::mapToBookingDTO).collect(Collectors.toList());
    }
}
