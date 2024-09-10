package com.airline.customer_service.controller;

import com.airline.customer_service.dto.*;
import com.airline.customer_service.exception.DuplicateUsernameException;
import com.airline.customer_service.exception.ResourceNotFoundException;
import com.airline.customer_service.exception.ServiceDownException;
import com.airline.customer_service.service.CustomerService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialException;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerServiceImp;

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDTO>> allCustomer(){
        List<CustomerDTO> temp = customerServiceImp.findAll();
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable int id){
        CustomerDTO theCustomer = customerServiceImp.findById(id);
        if(theCustomer==null){
            throw new ResourceNotFoundException();
        }
        HttpHeaders header = new HttpHeaders();
        header.add("desc","CustomerDTO get by Customer id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(theCustomer);
    }

    //code not push in docker and jar
    @GetMapping("/v1/{id}")
    public ResponseEntity<CustomerDTO> getCustomerV1(@PathVariable int id) {
        CustomerDTO theCustomer = customerServiceImp.findById(id);
        if (theCustomer == null) {
            throw new ResourceNotFoundException();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .header("desc", "CustomerDTO get by Customer id")
                .body(theCustomer);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@Valid @PathVariable int id){
        CustomerDTO tempCustomer = customerServiceImp.findById(id);
        if(tempCustomer==null){
            throw new ResourceNotFoundException();
        }
        customerServiceImp.deleteById(id);
        return ResponseEntity.ok("Delete successfully");
    }
    @PostMapping("/add")
    public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        try{
            System.out.println(customerDTO.getName()+""+customerDTO.getPassword());
            List<CustomerDTO> customerDTOS= customerServiceImp.duplicateCheckService(customerDTO.getEmail());
            if(customerDTOS.size()>0){
                throw new DuplicateUsernameException();
            }
            CustomerDTO addCustomer =this.customerServiceImp.save(customerDTO);
            return new ResponseEntity<>(addCustomer,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(@Valid @RequestBody CustomerDTO theCustomer){
       try {
           CustomerDTO customerDTO = customerServiceImp.setPro(theCustomer);
           if(customerDTO==null){
               throw new ResourceNotFoundException();
           }
           return new ResponseEntity<>( customerServiceImp.update(customerDTO),HttpStatus.CREATED);
       }
       catch (Exception ex){
           return new ResponseEntity<>("Not Updated",HttpStatus.NOT_FOUND);
       }
    }

    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO){
        CustomerDTO temp= this.customerServiceImp.findById(passwordDTO.getId());
        if(temp==null){
            throw new ResourceNotFoundException();
        }

        String msg=customerServiceImp.updatePassword(passwordDTO.getId(),passwordDTO.getEmail(),passwordDTO.getOldPassword(),passwordDTO.getNewPassword());
        return ResponseEntity.ok(msg);
    }
    @PutMapping("/forget/password")
    public ResponseEntity<String> forgetPassword(@Valid @RequestBody ForgetPasswordDTO forgetPasswordDTO){
        CustomerDTO temp= this.customerServiceImp.findById(forgetPasswordDTO.getId());
        if(temp==null){
            throw new ResourceNotFoundException();
        }

        String msg=customerServiceImp.forgetPassword(forgetPasswordDTO);
        return ResponseEntity.ok(msg);
    }
    @GetMapping("/flight/open/{id}")
    //@CircuitBreaker(name = "customerBreaker", fallbackMethod = "getFlightFallBack")
    public ResponseEntity<FlightDTO> getFlight(@PathVariable long id){

        FlightDTO flightDTO = customerServiceImp.getFlight(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","FlightDTO get by Flight id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(flightDTO);
    }
    /*public ResponseEntity<FlightDTO> getFlightFallBack(){
        FlightDTO flightDTO = new FlightDTO();
        return new ResponseEntity<>(flightDTO,HttpStatus.NOT_FOUND);
    }*/
    @GetMapping("/flight/all")
    public ResponseEntity<List<FlightDTO>> allFlight(){
        List<FlightDTO> temp = customerServiceImp.allFlight();
        return ResponseEntity.ok(temp);
    }
    @PutMapping("/flight/search")
    public ResponseEntity<List<FlightDTO>> flightSearch(@RequestBody FlightDTO searchDTO){
        List<FlightDTO> temp= this.customerServiceImp.flightSearch(searchDTO);
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable long id){
        BookingDTO theBooking = customerServiceImp.getBooking(id);
        HttpHeaders header = new HttpHeaders();
        header.add("desc","Bookings get by Booking id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(theBooking);
    }

    @GetMapping("/my/booking/{cid}")
    public ResponseEntity<List<BookingDTO>> AllMyBookingByCid(@PathVariable int cid){
        List<BookingDTO> temp = customerServiceImp.getAllBookingByCid(cid);
        return ResponseEntity.ok(temp);
    }

    @PostMapping("/booking/ticket")
    public ResponseEntity<BookingDTO> bookingMethod(@RequestBody BookingDTO bookingDTO){
        CustomerDTO customerDTO = customerServiceImp.findById(bookingDTO.getCid());
        if(customerDTO==null){
            throw new ResourceNotFoundException();
        }
        try {
            BookingDTO bookingDTO1 = customerServiceImp.addBooking(bookingDTO);
            return new ResponseEntity<>(bookingDTO1,HttpStatus.CREATED);
        }catch (Exception ex){
            throw new ServiceDownException();
        }

    }

    @PutMapping("/booking/cancel")
    public ResponseEntity<String> bookingCancelMethod(@RequestBody BookingCancelDTO bookingCancelDTO){
        CustomerDTO customerDTO = customerServiceImp.findById(bookingCancelDTO.getCid());
        if(customerDTO==null){
            throw new ResourceNotFoundException();
        }
        String  msg = customerServiceImp.bookingCancel(bookingCancelDTO);

        return new ResponseEntity<>(msg,HttpStatus.OK);

    }
}
