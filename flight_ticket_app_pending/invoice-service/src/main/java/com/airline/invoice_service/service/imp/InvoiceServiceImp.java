package com.airline.invoice_service.service.imp;

import com.airline.invoice_service.dto.InvoiceDTO;
import com.airline.invoice_service.entity.Invoice;
import com.airline.invoice_service.exception.ResourceNotFoundException;
import com.airline.invoice_service.repository.InvoiceRepository;
import com.airline.invoice_service.service.InvoiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImp implements InvoiceService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<InvoiceDTO> findAll() {
        return invoiceRepository.findAll().stream().map(this::mapToInvoiceDto).collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findById(long invoiceId) {
        Optional<Invoice> result = invoiceRepository.findById(invoiceId);
        Invoice invoice = null;
        if(result.isPresent()){
            invoice=result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return this.mapToInvoiceDto(invoice);
    }

    @Override
    public String deleteById(long invoiceId) {
        String message;
        Optional<Invoice> result = invoiceRepository.findById(invoiceId);
        if (result.isPresent()){
            invoiceRepository.deleteById(invoiceId);
            message ="Delete Invoice successfully";
        }
        else {
            return null;
        }

        return message;
    }

    @Override
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceRepository.save(mapToInvoice(invoiceDTO));
        InvoiceDTO invoiceDTO1 = mapToInvoiceDto(invoice);
        return invoiceDTO1;
    }

    @Override
    public InvoiceDTO update(InvoiceDTO invoiceDTO) {
        Optional<Invoice> temp = invoiceRepository.findById(invoiceDTO.getId());

        Invoice invoice = null;
        if (temp.isPresent()){
            invoice = temp.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        invoice.setDefinition(invoiceDTO.getDefinition());
        invoice.setLocalDateTime(invoiceDTO.getLocalDateTime());
        InvoiceDTO invoiceDTO1 = mapToInvoiceDto(invoice);
        return invoiceDTO1;
    }

    @Override
    public InvoiceDTO mapToInvoiceDto(Invoice invoice) {
        InvoiceDTO invoiceDTO = modelMapper.map(invoice,InvoiceDTO.class);
        return invoiceDTO;
    }

    @Override
    public Invoice mapToInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = modelMapper.map(invoiceDTO, Invoice.class);
        return invoice;
    }

    @Override
    public List<InvoiceDTO> findAllByCid(int cid) {
        return invoiceRepository.findByCid(cid).stream().map(this::mapToInvoiceDto).collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findByPaymentId(long paymentId) {
        Optional<Invoice> result = invoiceRepository.findByPaymentId(paymentId);
        Invoice invoice = null;
        if(result.isPresent()){
            invoice=result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return this.mapToInvoiceDto(invoice);
    }

    @Override
    public void statusDefinition(String definition, long paymentId) {
        invoiceRepository.statusDefinition(definition,paymentId);
    }


}