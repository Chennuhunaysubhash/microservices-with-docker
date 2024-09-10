package com.airline.flight_service.service;

import com.airline.flight_service.dto.BookingRestartDTO;
import com.airline.flight_service.dto.DateAndTimeChangeDTO;
import com.airline.flight_service.dto.FlightDTO;
import com.airline.flight_service.entity.Flight;

import java.util.List;

public interface FlightService {
    public List<FlightDTO> findAll();

    public FlightDTO findById(long flightId );

    public String deleteById(long flightId);

    public FlightDTO save(FlightDTO flightDTO);

    public FlightDTO update(FlightDTO flightDTO);
    public FlightDTO mapToFlightDTO(Flight flight);
    public Flight mapToFlight(FlightDTO flightDTO);

    public List<FlightDTO> duplicateCheckService(String flightName);

    public String changeDateAndTime(DateAndTimeChangeDTO dateAndTimeChangeDTO);

    public String changeTime(DateAndTimeChangeDTO dateAndTimeChangeDTO);

    public String bookingRestart(BookingRestartDTO bookingRestartDTO);

    public List<FlightDTO> selectFromAndTo(String from,String to);

    public String updateSeats(FlightDTO flightDTO);

    public String updateSeatsWithCancel(FlightDTO flightDTO);
    public String bookingClosed(FlightDTO flightDTO);

    public FlightDTO setPro(FlightDTO flightDTO);
}
