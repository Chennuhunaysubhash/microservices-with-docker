package com.airline.booking_service.controller;

import com.airline.booking_service.dto.*;
import com.airline.booking_service.exception.DataNotFoundException;
import com.airline.booking_service.exception.PaymentServerNotRespondException;
import com.airline.booking_service.exception.ResourceNotFoundException;
import com.airline.booking_service.message.PaymentMessageProducer;
import com.airline.booking_service.service.BookingService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingServiceImp;
    @Autowired
    private PaymentMessageProducer paymentMessageProducer;

    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> AllBooking(){
        List<BookingDTO> temp = bookingServiceImp.findAll();
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable long id){
        BookingDTO theBooking = bookingServiceImp.findById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","CustomerDTO get by Customer id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(theBooking);
    }

    @GetMapping("/all/{cid}")
    public ResponseEntity<List<BookingDTO>> AllBookingByCid(@PathVariable int cid){
        List<BookingDTO> temp = bookingServiceImp.allByCid(cid);
        return ResponseEntity.ok(temp);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@Valid @PathVariable long id){
        BookingDTO temp = bookingServiceImp.findById(id);
        if(temp==null){
            throw new ResourceNotFoundException();
        }
        bookingServiceImp.deleteById(id);
        return ResponseEntity.ok("Delete successfully");
    }
    @PostMapping("/add")
    public ResponseEntity<BookingDTO> addBooking(@Valid @RequestBody BookingDTO bookingDTO){
        try{
            BookingDTO bookingDTO1 = bookingServiceImp.setPro(bookingDTO);
            BookingDTO addBooking =this.bookingServiceImp.save(bookingDTO1);
            return new ResponseEntity<>(addBooking,HttpStatus.CREATED);
        }catch (Exception ex){
           // return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
            throw new PaymentServerNotRespondException();
        }

    }
    @PostMapping("/rabbit/add")
    public ResponseEntity<PaymentDTO> addPayment(@Valid @RequestBody PaymentDTO paymentDTO){
        paymentMessageProducer.sendPayment(paymentDTO);
        return new ResponseEntity<>(paymentDTO,HttpStatus.OK);
    }

    @PutMapping("/status")
    public ResponseEntity<String> setStatus(@Valid @RequestBody StatusUpdateDTO statusUpdateDTO){
        BookingDTO bookingDTO = bookingServiceImp.findById(statusUpdateDTO.getId());
        if(bookingDTO==null){
            throw new ResourceNotFoundException();
        }
        String str = bookingDTO.getDeparture();
        System.out.println(str.substring(str.length() - 2));
        String msg = bookingServiceImp.statusUpdate(statusUpdateDTO);
        return ResponseEntity.ok(msg);

    }

    @PutMapping("/cancel")
    public ResponseEntity<String> bookingCancel(@Valid @RequestBody BookingCancelDTO bookingCancelDTO){
        String msg;
        BookingDTO bookingDTO = bookingServiceImp.findById(bookingCancelDTO.getId());
        if(bookingDTO==null){
            throw new ResourceNotFoundException();
        }
        if(bookingDTO.getStatus().equals("Payment pending")){
            msg = "Payment Not Complete Yet. Please Check Once.";
        }else {
            msg = bookingServiceImp.bookingClosingServiceMethod(bookingCancelDTO);
        }
        return ResponseEntity.ok(msg);

    }

}
