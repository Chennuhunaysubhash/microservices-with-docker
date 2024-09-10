package com.airline.customer_service.service.imp;

import com.airline.customer_service.dto.*;
import com.airline.customer_service.entity.Customer;
import com.airline.customer_service.exception.BookingAlreadyCanceledException;
import com.airline.customer_service.exception.DataNotFoundException;
import com.airline.customer_service.exception.ResourceNotFoundException;
import com.airline.customer_service.openfeign.ApiClientForBooking;
import com.airline.customer_service.openfeign.ApiClientForFlight;
import com.airline.customer_service.repository.CustomerRepository;
import com.airline.customer_service.service.CustomerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImp implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApiClientForFlight apiClientForFlight;

    @Autowired
    private ApiClientForBooking apiClientForBooking;


    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().stream().map(this::mapToCustomerDto).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findById(int customerId) {
        Optional<Customer> result = customerRepository.findById(customerId);
        Customer theCustomer = null;
        if(result.isPresent()){
            theCustomer=result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return this.mapToCustomerDto(theCustomer);
    }

    @Override
    public String deleteById(int customerId) {
        String message;
        Optional<Customer> result = customerRepository.findById(customerId);
        if (result.isPresent()){
            customerRepository.deleteById(customerId);
            message ="Delete customer successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }

        return message;
    }

    @Override
    public CustomerDTO save(CustomerDTO theCustomer) {
        Customer customer = customerRepository.save(mapToCustomer(theCustomer));
        CustomerDTO customerDTO = mapToCustomerDto(customer);
        return customerDTO;
    }

    @Override
    public String update(CustomerDTO theCustomer) {
        Optional<Customer> result = customerRepository.findById(theCustomer.getId());
        Customer customer = null;
        if (result.isPresent()){
            customer = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        Customer customer1 = customerRepository.save(mapToCustomer(theCustomer));
        CustomerDTO customerDTO = mapToCustomerDto(customer1);
        return "Successfully Update";
    }

    @Override
    public String updatePassword(int id,String email, String oldPassword, String newPassword) {
        String message;
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            if(oldPassword.equals(customer.get().getPassword())){
                customerRepository.updatePassword(email,newPassword);
                message = "Password Update Successfully";
            }
            else {
                message = "Password Update Not Successfully";
            }

        }
        else {
            throw new ResourceNotFoundException();
        }
        return  message;
    }

    @Override
    public String forgetPassword(ForgetPasswordDTO forgetPasswordDTO) {
        String message;
        Optional<Customer> customer = customerRepository.findById(forgetPasswordDTO.getId());
        if(customer.isPresent()){
            if(forgetPasswordDTO.getName().equals(customer.get().getName())&&forgetPasswordDTO.getEmail().equals(customer.get().getEmail())){
                customerRepository.forgetPassword(forgetPasswordDTO.getEmail(),forgetPasswordDTO.getNewPassword());
                message = "Password Update Successfully";
            }else {
                message = "Password Update Not Successfully";
            }
        }else {
            throw new ResourceNotFoundException();
        }

        return message;
    }


    @Override
    public List<CustomerDTO> duplicateCheckService(String email) {
        return customerRepository.duplicateCheck(email).stream().map(this::mapToCustomerDto).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO mapToCustomerDto(Customer customer) {
        CustomerDTO customerDTO = modelMapper.map(customer,CustomerDTO.class);
        return customerDTO;
    }

    @Override
    public Customer mapToCustomer(CustomerDTO customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        return customer;
    }

    @Override
    @CircuitBreaker(name = "customerBreaker", fallbackMethod = "getFlightFallBack")
    public FlightDTO getFlight(long id) {
        FlightDTO flightDTO;
       /* try{
            flightDTO = apiClientForFlight.getFlight(id);

        }catch (Exception ex){
                throw  new DataNotFoundException();
        }*/
        flightDTO = apiClientForFlight.getFlight(id);
        return flightDTO;

    }
    public FlightDTO getFlightFallBack(long id, Throwable ex){
        FlightDTO flightDTO = new FlightDTO();
        //System.out.println("in fallback"+id);
       flightDTO.setFlightName("Dummy(fallback and service down)");
       return flightDTO;
    }


    @Override
    public List<FlightDTO> allFlight() {
        List<FlightDTO> flightDTOS = apiClientForFlight.allFlight().stream().collect(Collectors.toList());
        if(flightDTOS.isEmpty()){
            throw new DataNotFoundException();
        }
        return flightDTOS;
    }

    @Override
    public List<FlightDTO> flightSearch(FlightDTO searchDTO) {
        List<FlightDTO> flightDTOS = apiClientForFlight.flightSearch(searchDTO).stream().collect(Collectors.toList());
        if(flightDTOS.isEmpty()){
            throw new DataNotFoundException();
        }
        return flightDTOS;
    }

    @Override
    public BookingDTO getBooking(long id) {
        BookingDTO bookingDTO;
        try{
            bookingDTO = apiClientForBooking.getBooking(id);
        }catch (Exception ex){
            throw new DataNotFoundException();
        }
        return bookingDTO;
    }

    @Override
    public List<BookingDTO> getAllBookingByCid(long cid) {
        List<BookingDTO> bookingDTO;
        try{
            bookingDTO = apiClientForBooking.getAllBookingByCid(cid);
        }catch (Exception ex){
            throw new DataNotFoundException();
        }
        return bookingDTO;
    }

    @Override
    public BookingDTO addBooking(BookingDTO bookingDTO) {
        return apiClientForBooking.addBooking(bookingDTO);
    }

    @Override
    public String bookingCancel(BookingCancelDTO bookingCancelDTO) {
        String msg;
        BookingDTO bookingDTO = getBooking(bookingCancelDTO.getId());
        if(bookingDTO==null){
            return "Booking Not Found";
        }
        try {
            msg = apiClientForBooking.bookingCancel(bookingCancelDTO);
        }catch (Exception e){
            throw new BookingAlreadyCanceledException();
        }
        return msg;
    }

    @Override
    public CustomerDTO setPro(CustomerDTO theCustomer) {
        CustomerDTO temp= this.findById(theCustomer.getId());
        if(temp==null){
            throw new ResourceNotFoundException();
        }
        temp.setName(theCustomer.getName());
        temp.setPhoneNumber(theCustomer.getPhoneNumber());
        temp.setCity(theCustomer.getCity());
        temp.setCounty(theCustomer.getCounty());
        return temp;
    }


}
