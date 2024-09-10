package com.airline.payment_service.controller;

import com.airline.payment_service.dto.PaymentDTO;
import com.airline.payment_service.exception.ResourceNotFoundException;
import com.airline.payment_service.service.PaymentService;
import com.airline.payment_service.service.imp.PaymentServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentServiceImp;


    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> AllPayment(){
        List<PaymentDTO> temp = paymentServiceImp.findAll();
        if(temp.size()>0){
            return new ResponseEntity<>(temp,HttpStatus.OK);
        }
        return new ResponseEntity<>(temp,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable long id){
        try {

            PaymentDTO paymentDTO = paymentServiceImp.findById(id);
            HttpHeaders header = new HttpHeaders();
            header.add("desc", "CustomerDTO get by Customer id");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(paymentDTO);
        }catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePayment(@Valid @PathVariable long id){
        try{
            PaymentDTO temp = paymentServiceImp.findById(id);
            paymentServiceImp.deleteById(id);
            return ResponseEntity.ok("Delete successfully");
        }catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }
    @PostMapping("/add")
    public ResponseEntity<PaymentDTO> addPayment(@Valid @RequestBody PaymentDTO paymentDTO){
        PaymentDTO paymentDTO1 =this.paymentServiceImp.save(paymentDTO);
        if(paymentDTO1==null){
            new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        //System.out.println(paymentDTO1.getId());
        return new ResponseEntity<>(paymentDTO1,HttpStatus.CREATED);
    }
    @GetMapping("/both")
    public ResponseEntity<List<PaymentDTO>> findByBothId(@Valid @RequestBody PaymentDTO paymentDTO){
        //System.out.println(paymentDTO.getcId());
        //System.out.println(paymentDTO.getBookingId());
        List<PaymentDTO> temp = paymentServiceImp.findByBothIds(paymentDTO);
        if(temp.size()>0){
            return new ResponseEntity<>(temp,HttpStatus.OK);
        }
        return new ResponseEntity<>(temp,HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{cid}/{bookingId}")
    public ResponseEntity<List<PaymentDTO>> findByBothIdForSearch(@PathVariable int cid,@PathVariable
                                                                  long bookingId){
        //System.out.println(paymentDTO.getcId());
        //System.out.println(paymentDTO.getBookingId());
        List<PaymentDTO> temp = paymentServiceImp.findByBothIdForSearch(cid,bookingId);
        if(temp.size()>0){
            return new ResponseEntity<>(temp,HttpStatus.OK);
        }
        return new ResponseEntity<>(temp,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/pay")
    public ResponseEntity<String> paymentMethod(@Valid @RequestBody PaymentDTO paymentDTO){

        String temp = paymentServiceImp.paymentDone(paymentDTO);
        if(temp.contains("Payment Successfully Done"))
        {
            return new ResponseEntity<>(temp,HttpStatus.OK);
        }
        return new ResponseEntity<>(temp,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/cancel")
    public ResponseEntity<String> paymentCancel(@Valid @RequestBody PaymentDTO paymentDTO){

        String temp = paymentServiceImp.paymentDoneWithCancel(paymentDTO);
        if(temp.contains("Payment Cancel successfully")){
            return new ResponseEntity<>(temp,HttpStatus.OK);
        }
        return new ResponseEntity<>(temp,HttpStatus.NOT_FOUND);
    }

    @PutMapping("/invoice/regenerate/{paymentDTO}")
    public ResponseEntity<String> invoiceRegenerate(@PathVariable long paymentDTO){
       String msg = paymentServiceImp.regenerateInvoice(paymentDTO);
       if(msg.contains("Successfully")){
           return new ResponseEntity<>(msg,HttpStatus.OK);
       }
        return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);

    }


}
