package com.airline.payment_service.service;

import com.airline.payment_service.dto.FlightDTO;
import com.airline.payment_service.dto.InvoiceDTO;
import com.airline.payment_service.dto.PaymentDTO;
import com.airline.payment_service.dto.StatusUpdateDTO;
import com.airline.payment_service.entity.Payment;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PaymentService {
    public PaymentDTO mapToPaymentDTO(Payment payment);
    public Payment mapToPayment(PaymentDTO paymentDTO);

    public List<PaymentDTO> findAll();

    public PaymentDTO findById(long paymentId);

    public String deleteById(long paymentId);

    public PaymentDTO save(PaymentDTO paymentDTO);
    public List<PaymentDTO> findByBothIds(PaymentDTO paymentDTO);

    public List<PaymentDTO> findByBothIdForSearch(int cid,long bookingId);

    public String paymentDone(PaymentDTO paymentDTO);
    public String paymentDoneWithCancel(PaymentDTO paymentDTO);
    public String updateSeats(FlightDTO flightDTO);
    public String setStatus(StatusUpdateDTO statusUpdateDTO);
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    public FlightDTO getFlight(@PathVariable long id);
    public String invoiceStatus(Boolean invoiceSt,int cid,long id);
    public String regenerateInvoice(long paymentId);
    public InvoiceDTO regenerate(InvoiceDTO invoiceDTO);

    public String updateInvoiceCancel(String definition,Long  paymentId);
    public InvoiceDTO getInvoiceByPaymentId(long id);

    public void printPayment(PaymentDTO paymentDTO);
}
