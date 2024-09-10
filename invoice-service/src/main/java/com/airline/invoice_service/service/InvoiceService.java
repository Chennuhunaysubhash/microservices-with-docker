package com.airline.invoice_service.service;

import com.airline.invoice_service.dto.InvoiceDTO;
import com.airline.invoice_service.entity.Invoice;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceService {
    public List<InvoiceDTO> findAll();

    public InvoiceDTO findById(long invoiceId);

    public String deleteById(long invoiceId);

    public InvoiceDTO save(InvoiceDTO invoiceDTO);

    public InvoiceDTO update(InvoiceDTO invoiceDTO);
    public InvoiceDTO mapToInvoiceDto(Invoice invoice);
    public Invoice mapToInvoice(InvoiceDTO invoiceDTO);
    public List<InvoiceDTO> findAllByCid(int cid);
    public InvoiceDTO findByPaymentId(long paymentId);

    void statusDefinition(String definition, long paymentId);


}
