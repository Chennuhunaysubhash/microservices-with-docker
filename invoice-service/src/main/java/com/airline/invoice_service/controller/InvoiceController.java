package com.airline.invoice_service.controller;

import com.airline.invoice_service.dto.InvoiceDTO;
import com.airline.invoice_service.exception.ResourceNotFoundException;
import com.airline.invoice_service.service.InvoiceService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceServiceImp;
    @GetMapping("/all")
    public ResponseEntity<List<InvoiceDTO>> allInvoice(){
        List<InvoiceDTO> temp = invoiceServiceImp.findAll();
        if(temp.size()==0){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/customer/{cid}")
    public ResponseEntity<List<InvoiceDTO>> allInvoiceByCid(@PathVariable int cid){
        List<InvoiceDTO> temp = invoiceServiceImp.findAllByCid(cid);
        if(temp.size()==0){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable long id){
        try {
            InvoiceDTO invoiceDTO = invoiceServiceImp.findById(id);
            System.out.println(invoiceDTO.getDefinition());
            HttpHeaders header = new HttpHeaders();
            header.add("desc", "Invoice get by invoice id");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(invoiceDTO);
        }catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }
    @GetMapping("/customer/pay/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceByPaymentId(@PathVariable long id){
        try {
            InvoiceDTO invoiceDTO = invoiceServiceImp.findByPaymentId(id);
            System.out.println(invoiceDTO.getDefinition());
            HttpHeaders header = new HttpHeaders();
            header.add("desc", "Invoice get by Payment id");
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(invoiceDTO);
        }catch (Exception ex){
            throw new ResourceNotFoundException();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInvoice(@Valid @PathVariable long id){
        String msg = invoiceServiceImp.deleteById(id);
        if(msg.equals(null)){
            throw new ResourceNotFoundException();
        }
        else {
            return new ResponseEntity<>(msg,HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<InvoiceDTO> addInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO){
        InvoiceDTO invoiceDTO1 =this.invoiceServiceImp.save(invoiceDTO);
        if(invoiceDTO1.equals(null)){
            new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        //System.out.println(paymentDTO1.getId());
        return new ResponseEntity<>(invoiceDTO1,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<InvoiceDTO> updateInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO){

        InvoiceDTO invoiceDTO1 = invoiceServiceImp.update(invoiceDTO);
        System.out.println(invoiceDTO1.getDefinition());
        if(invoiceDTO1.equals(null)){
            return new ResponseEntity<>(null,HttpStatus.NOT_MODIFIED);
        }
        else {
            return new ResponseEntity<>(invoiceDTO1,HttpStatus.OK);
        }

    }

    @PostMapping("/regenerate")
    public ResponseEntity<InvoiceDTO> regenerateInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO){
        InvoiceDTO invoiceDTO1 =this.invoiceServiceImp.save(invoiceDTO);
        if(invoiceDTO1.equals(null)){
            new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        //System.out.println(paymentDTO1.getId());
        return new ResponseEntity<>(invoiceDTO1,HttpStatus.CREATED);
    }
    @PutMapping("/more/update/{definition}/{paymentId}")
    public ResponseEntity<String> updateInvoiceCancel(@PathVariable String definition,@PathVariable Long  paymentId){
        try{
            System.out.println(definition);
            System.out.println(paymentId);
            invoiceServiceImp.statusDefinition(definition,paymentId);
            return new ResponseEntity<>("update",HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
        }

    }


}
