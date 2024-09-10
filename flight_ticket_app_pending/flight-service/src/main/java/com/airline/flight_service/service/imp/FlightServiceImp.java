package com.airline.flight_service.service.imp;

import com.airline.flight_service.dto.BookingRestartDTO;
import com.airline.flight_service.dto.DateAndTimeChangeDTO;
import com.airline.flight_service.dto.FlightDTO;
import com.airline.flight_service.entity.Flight;
import com.airline.flight_service.exception.BookingClosedException;
import com.airline.flight_service.exception.NoSeatsNotAvailableException;
import com.airline.flight_service.exception.ResourceNotFoundException;
import com.airline.flight_service.exception.ValidDateException;
import com.airline.flight_service.repository.FlightRepository;
import com.airline.flight_service.service.FlightService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImp implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<FlightDTO> findAll() {
        return flightRepository.findAll().stream().map(this::mapToFlightDTO).collect(Collectors.toList());
    }

    @Override
    public FlightDTO findById(long flightId) {
        Optional<Flight> result = flightRepository.findById(flightId);
        Flight flight = null;
        if(result.isPresent()){
            flight=result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        return this.mapToFlightDTO(flight);
    }

    @Override
    public String deleteById(long flightId) {
        String message;
        Optional<Flight> result = flightRepository.findById(flightId);
        if (result.isPresent()){
            flightRepository.deleteById(flightId);
            message ="Delete Flight successfully";
        }
        else {
            throw new ResourceNotFoundException();
        }

        return message;
    }

    @Override
    public FlightDTO save(FlightDTO flightDTO) {

        try{
            LocalDate localDate = LocalDate.parse(flightDTO.getDeparture());
            System.out.println(localDate);
            Flight flight = flightRepository.save(mapToFlight(flightDTO));
            FlightDTO flightDTO1 = mapToFlightDTO(flight);
            return flightDTO1;
        }catch (Exception ex){
            throw new ValidDateException();
        }

    }

    @Override
    public FlightDTO update(FlightDTO flightDTO) {
        Optional<Flight> result = flightRepository.findById(flightDTO.getId());
        Flight flight = null;
        if (result.isPresent()){
            flight = result.get();
        }
        else {
            throw new ResourceNotFoundException();
        }
        Flight flight1 = flightRepository.save(mapToFlight(flightDTO));
        FlightDTO flightDTO1 = mapToFlightDTO(flight1);
        return flightDTO1;
    }

    @Override
    public FlightDTO mapToFlightDTO(Flight flight) {
        FlightDTO flightDTO = modelMapper.map(flight,FlightDTO.class);
        return flightDTO;
    }

    @Override
    public Flight mapToFlight(FlightDTO flightDTO) {
        Flight flight = modelMapper.map(flightDTO, Flight.class);
        return flight;
    }

    @Override
    public List<FlightDTO> duplicateCheckService(String flightName) {
        return flightRepository.duplicateCheck(flightName).stream().map(this::mapToFlightDTO).collect(Collectors.toList());
    }

    @Override
    public String changeDateAndTime(DateAndTimeChangeDTO dateAndTimeChangeDTO) {
        String message;
        Optional<Flight> result = flightRepository.findById(dateAndTimeChangeDTO.getId());
        if (result.isPresent()){
            if(result.get().getFlightName().equals(dateAndTimeChangeDTO.getFlightName())){
                flightRepository.changeDateAndTime(dateAndTimeChangeDTO.getDeparture(),dateAndTimeChangeDTO.getTime(),dateAndTimeChangeDTO.getId(),dateAndTimeChangeDTO.getFlightName());
                message = "Date And Time Update Successfully";
            }
            else {
                message = "Date And Time Not Update Successfully";
            }

        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public String changeTime(DateAndTimeChangeDTO dateAndTimeChangeDTO) {
        String message;
        Optional<Flight> result = flightRepository.findById(dateAndTimeChangeDTO.getId());
        if (result.isPresent()){
            if(result.get().getFlightName().equals(dateAndTimeChangeDTO.getFlightName())){
                flightRepository.changeTime(dateAndTimeChangeDTO.getTime(),dateAndTimeChangeDTO.getId(),dateAndTimeChangeDTO.getFlightName());
                message = " Time Update Successfully";
            }
            else {
                message = " Time Not Update Successfully";
            }

        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public String bookingRestart(BookingRestartDTO bookingRestartDTO) {
        String message;
        Optional<Flight> result = flightRepository.findById(bookingRestartDTO.getId());
        if (result.isPresent()){
            if(result.get().getFlightName().equals(bookingRestartDTO.getFlightName())){
                flightRepository.bookingRestart(bookingRestartDTO.getTotalSeats(),bookingRestartDTO.getBookedSeats(),bookingRestartDTO.getAvailableSeats(),bookingRestartDTO.getStatus(),bookingRestartDTO.getId(),bookingRestartDTO.getFlightName());
                message = " Booking Opening  Successfully";
            }
            else {
                message = " Booking Opening Not Successfully";
            }

        }
        else {
            throw new ResourceNotFoundException();
        }
        return message;
    }

    @Override
    public List<FlightDTO> selectFromAndTo(String from, String to) {
        return flightRepository.selectFromAndTo(from,to).stream().map(this::mapToFlightDTO).collect(Collectors.toList());
    }

    @Override
    public String updateSeats(FlightDTO flightDTO) {
        String msg;
        FlightDTO flightDTO1 = findById(flightDTO.getId());
        int available = flightDTO1.getAvailableSeats();
        int bookingSeats = flightDTO.getBookedSeats();
        System.out.println(available);
        System.out.println(bookingSeats);

        int seats = available-flightDTO.getBookedSeats();
        int bookSeats = bookingSeats+flightDTO1.getBookedSeats();
        if(available==0){
            throw new BookingClosedException();
        }
        if(available>0 && bookingSeats<=available){
            flightRepository.updateSeats(bookSeats,seats,flightDTO.getId(),flightDTO.getFlightName());
            FlightDTO flightDTO2 = findById(flightDTO.getId());
            int av = flightDTO2.getAvailableSeats();
            System.out.println(av);
            if(av==0){
                flightRepository.bookingClosed("Booking Closed",flightDTO.getId(),flightDTO.getFlightName());
            }
        }
        else {
            throw new NoSeatsNotAvailableException();
        }

        FlightDTO flight = findById(flightDTO.getId());
      if(flight.getAvailableSeats()==seats && flight.getBookedSeats()==bookSeats){
          msg = "Update successfully";
      }else {
          msg ="Update Not successfully";
      }
       return msg;
    }

    @Override
    public String updateSeatsWithCancel(FlightDTO flightDTO) {
        String msg;
        FlightDTO flightDTO1 = findById(flightDTO.getId());
        if(flightDTO1==null){
            throw new ResourceNotFoundException();
        }
        else {
            int available = flightDTO1.getAvailableSeats();
            int bookingSeats = flightDTO.getBookedSeats();
            System.out.println(available);
            System.out.println(bookingSeats);
            int seats = available+flightDTO.getBookedSeats();
            int bookSeats = flightDTO1.getBookedSeats()-bookingSeats;
            if(available<=flightDTO1.getTotalSeats() && flightDTO1.getBookedSeats()!=0){
                flightRepository.updateSeats(bookSeats,seats,flightDTO.getId(),flightDTO.getFlightName());

                msg="Update successfully";
            }
            else{
                msg="Invalid Operation By User With Wrong Details";
            }
        }
        return msg;
    }

    @Override
    public String bookingClosed(FlightDTO flightDTO) {
         flightRepository.bookingClosed(flightDTO.getStatus(),flightDTO.getId(),flightDTO.getFlightName());
         return "Update successfully";
    }

    @Override
    public FlightDTO setPro(FlightDTO theFlight) {
        FlightDTO temp= this.findById(theFlight.getId());
        if(temp==null){
            throw new ResourceNotFoundException();
        }
        temp.setFlightName(theFlight.getFlightName());
        temp.setFrom(theFlight.getFrom());
        temp.setTo(theFlight.getTo());
        temp.setDeparture(theFlight.getDeparture());
        temp.setTime(theFlight.getTime());
        temp.setTotalSeats(theFlight.getTotalSeats());
        temp.setBookedSeats(theFlight.getBookedSeats());
        temp.setAvailableSeats(theFlight.getAvailableSeats());
        temp.setPrice(theFlight.getPrice());

        return temp;
    }
}
