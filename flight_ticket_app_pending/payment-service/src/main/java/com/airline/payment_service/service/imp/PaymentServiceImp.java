package com.airline.payment_service.service.imp;

import com.airline.payment_service.dto.FlightDTO;
import com.airline.payment_service.dto.InvoiceDTO;
import com.airline.payment_service.dto.PaymentDTO;
import com.airline.payment_service.dto.StatusUpdateDTO;
import com.airline.payment_service.entity.Payment;
import com.airline.payment_service.exception.*;
import com.airline.payment_service.openfeign.ApiClientForBooking;
import com.airline.payment_service.openfeign.ApiClientForFlight;
import com.airline.payment_service.openfeign.ApiClientForInvoice;
import com.airline.payment_service.repository.PaymentRepository;
import com.airline.payment_service.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
public class PaymentServiceImp implements PaymentService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ApiClientForFlight apiClientForFlight;
    @Autowired
    private ApiClientForBooking apiClientForBooking;
    @Autowired
    private ApiClientForInvoice apiClientForInvoice;
    @Override
    public PaymentDTO mapToPaymentDTO(Payment payment) {
        PaymentDTO paymentDTO = modelMapper.map(payment, PaymentDTO.class);
        return paymentDTO;
    }

    @Override
    public Payment mapToPayment(PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        return payment;
    }

    @Override
    public List<PaymentDTO> findAll() {
        return paymentRepository.findAll().stream().map(this::mapToPaymentDTO).collect(Collectors.toList());
    }

    @Override
    public PaymentDTO findById(long paymentId) {
        Optional<Payment> result = paymentRepository.findById(paymentId);
        Payment payment = null;
        if (result.isPresent()){
            payment=result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return this.mapToPaymentDTO(payment);
    }

    @Override
    public String deleteById(long paymentId) {
        String message;
        Optional<Payment> result = paymentRepository.findById(paymentId);
        if (result.isPresent()){
            paymentRepository.deleteById(paymentId);
            message ="Delete customer successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }

        return message;
    }

    @Override
    public PaymentDTO save(PaymentDTO paymentDTO) {
        Payment payment = paymentRepository.save(mapToPayment(paymentDTO));
        PaymentDTO paymentDTO1 = mapToPaymentDTO(payment);
        System.out.println(payment.getId());
        return paymentDTO1;
    }

    @Override
    public List<PaymentDTO> findByBothIds(PaymentDTO paymentDTO) {
        return paymentRepository.findByBothIds(paymentDTO.getcId(),paymentDTO.getBookingId()).stream().map(this::mapToPaymentDTO).collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> findByBothIdForSearch(int cid, long bookingId) {
        return paymentRepository.findByBothIds(cid,bookingId).stream().map(this::mapToPaymentDTO).collect(Collectors.toList());
    }

    @Override
    public String paymentDone(PaymentDTO paymentDTO) {
        String msg;
        PaymentDTO paymentDTO1 = findById(paymentDTO.getId());
        if(paymentDTO1==null){
            throw new ResourceNotFoundException();
        }
        if(paymentDTO1.getBookingId()!=paymentDTO.getBookingId()){
            throw new DataNotFoundException();
        }
        if(paymentDTO1.getStatus().equals("Done")){
            throw new PaymentAlreadyDoneExceptionMethod();
        }
        else {
            if(paymentDTO1.getTotal()==paymentDTO.getTotal()){

                paymentRepository.paymentDone(paymentDTO.getPaymentType(),"Done",paymentDTO.getId(),paymentDTO.getBookingId());
                FlightDTO flightDTO = new FlightDTO();
                flightDTO.setId(paymentDTO1.getFlightId());
                flightDTO.setFlightName(paymentDTO1.getFlightName());
                flightDTO.setBookedSeats(paymentDTO1.getNoSeats());
                PaymentDTO paymentDTO2 = findById(paymentDTO.getId());
                if(paymentDTO2.getStatus().equals("Done")){
                    msg="Payment Successfully Done";
                    apiClientForFlight.updateSeats(flightDTO);
                    /* System.out.println(flightDTO.getId());
                    System.out.println(flightDTO.getFlightName());
                    System.out.println(flightDTO.getBookedSeats());*/
                    StatusUpdateDTO statusUpdateDTO = new StatusUpdateDTO();
                    statusUpdateDTO.setId(paymentDTO.getBookingId());
                    statusUpdateDTO.setStatus("Payment Done");
                    apiClientForBooking.setStatus(statusUpdateDTO);
                    FlightDTO flightDTO1 = apiClientForFlight.getFlight(paymentDTO2.getFlightId());
                    String invoiceDef = "Customer ID: "+paymentDTO2.getcId()+"\n"+
                            "Customer Name: "+paymentDTO2.getCustomerName()+"\n"+
                            "Flight ID: "+paymentDTO2.getFlightId()+"\n"+
                            "Flight Name: "+paymentDTO2.getFlightName()+"\n"+
                            flightDTO1.getFrom()+" to "+flightDTO1.getTo()+" at the following Date & Time \n"+
                            flightDTO1.getDeparture()+" "+flightDTO1.getTime()+".\n"+
                            "Amount is "+paymentDTO2.getTotal()+" with GST and Payment type changer.\nPayment Types : "+paymentDTO2.getPaymentType();
                    if(!paymentDTO2.getInvoiceStatus()){
                        try {
                            System.out.println(invoiceDef);
                            InvoiceDTO invoiceDTO = new InvoiceDTO();
                            invoiceDTO.setPaymentId(paymentDTO2.getId());
                            invoiceDTO.setCid(paymentDTO2.getcId());
                            invoiceDTO.setDefinition(invoiceDef);
                            addInvoice(invoiceDTO);
                            invoiceStatus(true,paymentDTO2.getcId(),paymentDTO2.getId());
                        }catch (Exception ex){
                            throw new InvoiceServiceNotAvailableException();
                        }
                    }

                }
                else {
                    msg="Payment Not Successfully";
                }
            }
            else {
                throw new AmountNotMatchExceptionMethod();
            }
        }

        return msg;
    }

    @Override
    public String paymentDoneWithCancel(PaymentDTO paymentDTO) {
        String msg;
        PaymentDTO paymentDTO1 = new PaymentDTO();
        paymentDTO1.setcId(paymentDTO.getcId());
        paymentDTO1.setBookingId(paymentDTO.getBookingId());
        List<PaymentDTO> paymentDTOS =  findByBothIds(paymentDTO1);
        if(paymentDTOS.size()==0){
            throw new ResourceNotFoundException();
        }
        else {
            if(paymentDTOS.get(0).getStatus().contains("Booking ")){
                msg = "Payment Canceling is Closed or Canceling Date Not Accepting";
            }
            else {
                paymentRepository.paymentCancel(paymentDTO.getStatus(),paymentDTO.getcId(),paymentDTO.getBookingId());
                PaymentDTO  paymentDTO2 = paymentDTOS.get(0);
                System.out.println(paymentDTO2.getId());
                //InvoiceDTO invoiceDTO = getInvoiceByPaymentId(paymentDTO2.getId());

                updateInvoiceCancel("Payment canceled",paymentDTO2.getId());
                msg="Payment Cancel successfully";
            }
        }
        return msg;
    }

    @Override
    public String updateSeats(FlightDTO flightDTO) {
        return apiClientForFlight.updateSeats(flightDTO);
    }

    @Override
    public String setStatus(StatusUpdateDTO statusUpdateDTO) {

        return apiClientForBooking.setStatus(statusUpdateDTO);
    }

    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        return apiClientForInvoice.addInvoice(invoiceDTO);
    }

    @Override
    public FlightDTO getFlight(long id) {
        return apiClientForFlight.getFlight(id);
    }

    @Override
    public String invoiceStatus(Boolean invoiceSt, int cid, long id) {
        try{
            paymentRepository.invoiceStatus(invoiceSt,cid,id);
        }
        catch (Exception ex){
            throw new DataNotFoundException();
        }
        return "Update";
    }

    @Override
    public String regenerateInvoice(long paymentId) {
        PaymentDTO paymentDTO = findById(paymentId);
         if(paymentDTO.equals(null)){
             throw new ResourceNotFoundException();
         }
         else {
             if(!paymentDTO.getInvoiceStatus()){
                 FlightDTO flightDTO1 = getFlight(paymentDTO.getFlightId());
                 String invoiceDef = "Customer ID: "+paymentDTO.getcId()+"\n"+
                         "Customer Name: "+paymentDTO.getCustomerName()+"\n"+
                         "Flight ID: "+paymentDTO.getFlightId()+"\n"+
                         "Flight Name: "+paymentDTO.getFlightName()+"\n"+
                         flightDTO1.getFrom()+" to "+flightDTO1.getTo()+" at the following Date & Time \n"+
                         flightDTO1.getDeparture()+" "+flightDTO1.getTime()+".\n"+
                         "Amount is "+paymentDTO.getTotal()+" with GST and Payment type changer.\nPayment Types : "+paymentDTO.getPaymentType();
                     try {
                         System.out.println(invoiceDef);
                         InvoiceDTO invoiceDTO1 = new InvoiceDTO();
                         invoiceDTO1.setCid(paymentDTO.getcId());
                         invoiceDTO1.setPaymentId(paymentDTO.getId());
                         invoiceDTO1.setDefinition(invoiceDef);
                         regenerate(invoiceDTO1);
                         invoiceStatus(true,paymentDTO.getcId(),paymentDTO.getId());
                         return "Invoice Successfully Create";
                     }catch (Exception ex){
                         throw new InvoiceServiceNotAvailableException();
                     }

             }
             else {
                 return "Already Created Please Check Once";
             }
         }

    }

    @Override
    public InvoiceDTO regenerate(InvoiceDTO invoiceDTO) {
        return apiClientForInvoice.regenerateInvoice(invoiceDTO);
    }

    @Override
    public String updateInvoiceCancel(String definition, Long paymentId) {
        apiClientForInvoice.updateInvoiceCancel(definition,paymentId);
        return "update";
    }


    @Override
    public InvoiceDTO getInvoiceByPaymentId(long id) {
        return apiClientForInvoice.getInvoiceByPaymentId(id);
    }

    @Override
    public void printPayment(PaymentDTO paymentDTO) {
        System.out.println(paymentDTO.getNoSeats());
    }


}
