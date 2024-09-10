package com.airline.customer_service.service;

import com.airline.customer_service.dto.*;
import com.airline.customer_service.entity.Customer;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CustomerService {
    public List<CustomerDTO> findAll();

    public CustomerDTO findById(int customerId );

    public String deleteById(int customerId);

    public CustomerDTO save(CustomerDTO theCustomer);

    public String update(CustomerDTO theCustomer);

  //  public void updateInstructorBasicDetailService(int id,String Name,String phoneNo,String email,String password, String county,String city);
    public String updatePassword(int id,String email,String oldPassword,String newPassword);

    public String forgetPassword(ForgetPasswordDTO forgetPasswordDTO);
    public List<CustomerDTO> duplicateCheckService(String email);

    public CustomerDTO mapToCustomerDto(Customer customer);
    public Customer mapToCustomer(CustomerDTO customerDto);

    public FlightDTO getFlight(long id);

    public List<FlightDTO> allFlight();

    public List<FlightDTO> flightSearch(FlightDTO searchDTO);

    public BookingDTO getBooking(long id);

    public List<BookingDTO> getAllBookingByCid(long cid);

    public BookingDTO addBooking(BookingDTO bookingDTO);

    public String bookingCancel(BookingCancelDTO bookingCancelDTO);

    public CustomerDTO setPro(CustomerDTO theCustomer);



}
