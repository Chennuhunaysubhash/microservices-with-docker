package com.airline.payment_service.openfeign;

import com.airline.payment_service.dto.InvoiceDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "INVOICE-SERVICE",url = "${invoice-service.url}")
@Configuration
public interface ApiClientForInvoice {
    @RequestMapping(method = RequestMethod.POST,value = "/invoice/add", consumes = "application/json")
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    @RequestMapping(method = RequestMethod.POST,value = "/invoice/regenerate", consumes = "application/json")
    public InvoiceDTO regenerateInvoice(InvoiceDTO invoiceDTO);

    @RequestMapping(method = RequestMethod.PUT,value = "/invoice/more/update/{definition}/{paymentId}", consumes = "application/json")
    public String updateInvoiceCancel(@PathVariable String definition,@PathVariable Long  paymentId);
    @RequestMapping(method = RequestMethod.GET,value = "/invoice/customer/pay/{id}")
    public InvoiceDTO getInvoiceByPaymentId(@PathVariable long id);

}
