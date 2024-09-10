package com.airline.flight_service.controller;

import com.airline.flight_service.dto.BookingRestartDTO;
import com.airline.flight_service.dto.DateAndTimeChangeDTO;
import com.airline.flight_service.dto.FlightDTO;
import com.airline.flight_service.exception.DuplicateUsernameException;
import com.airline.flight_service.exception.ResourceNotFoundException;
import com.airline.flight_service.service.FlightService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightServiceImp;
    @GetMapping("/all")
    public ResponseEntity<List<FlightDTO>> allFlight(){
        List<FlightDTO> temp = flightServiceImp.findAll();
        if(temp.size()==0){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlight(@PathVariable long id){
        FlightDTO flightDTO = flightServiceImp.findById(id);
       //LocalDate l = LocalDate.parse(flightDTO.getDeparture());
        //System.out.println(l);
        if(flightDTO==null)
        {
            throw new ResourceNotFoundException();
        }
        HttpHeaders header = new HttpHeaders();
        header.add("desc","FlightDTO get by Flight id");
        return ResponseEntity.status(HttpStatus.OK).headers(header).body(flightDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFlight(@Valid @PathVariable long id){
        String message;
        FlightDTO tempFlight = flightServiceImp.findById(id);
        if(tempFlight==null){
            throw new ResourceNotFoundException();
        }
        message = flightServiceImp.deleteById(id);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/add")
    public ResponseEntity<FlightDTO> addFlight(@Valid @RequestBody FlightDTO flightDTO){
        System.out.println(flightDTO.getFlightName()+" "+flightDTO.getDeparture());
        List<FlightDTO> flightDTOS= flightServiceImp.duplicateCheckService(flightDTO.getFlightName());
        if(flightDTOS.size()>0){
            throw new DuplicateUsernameException();
        }
        //code change not pull to docker and jar file
        if(flightDTO.getAvailableSeats()>=0&& flightDTO.getAvailableSeats()<=flightDTO.getTotalSeats()){
            flightDTO.setStatus("Available");
        }
        else {
            flightDTO.setStatus("Booking Closed");
        }
        FlightDTO addFlight =this.flightServiceImp.save(flightDTO);
        return new ResponseEntity<>(addFlight,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<FlightDTO> updateFlight(@Valid @RequestBody FlightDTO theFlight){
        FlightDTO flightDTO = flightServiceImp.setPro(theFlight);
        if(flightDTO.equals(null)){
            throw new ResourceNotFoundException();
        }
        return new ResponseEntity<>(flightServiceImp.update(flightDTO),HttpStatus.CREATED);
    }
    @PutMapping("/change/date")
    public ResponseEntity<String> dataAndTimeChange(@Valid @RequestBody DateAndTimeChangeDTO dateAndTimeChangeDTO){
        FlightDTO temp= this.flightServiceImp.findById(dateAndTimeChangeDTO.getId());
        if(temp==null){
            throw new ResourceNotFoundException();
        }

        String msg=flightServiceImp.changeDateAndTime(dateAndTimeChangeDTO);
        return ResponseEntity.ok(msg);
    }
    @PutMapping("/change/time")
    public ResponseEntity<String> timeChange(@Valid @RequestBody DateAndTimeChangeDTO dateAndTimeChangeDTO){
        FlightDTO temp= this.flightServiceImp.findById(dateAndTimeChangeDTO.getId());
        if(temp==null){
            throw new ResourceNotFoundException();
        }

        String msg=flightServiceImp.changeTime(dateAndTimeChangeDTO);
        return ResponseEntity.ok(msg);
    }

    @PutMapping("/booking/restart")
    public ResponseEntity<String> bookingStart(@Valid @RequestBody BookingRestartDTO bookingRestartDTO){
        FlightDTO temp= this.flightServiceImp.findById(bookingRestartDTO.getId());
        if(temp==null){
            throw new ResourceNotFoundException();
        }
        bookingRestartDTO.setBookedSeats(0);
        bookingRestartDTO.setStatus("Available");
        String msg=flightServiceImp.bookingRestart(bookingRestartDTO);
        return ResponseEntity.ok(msg);
    }
    @PutMapping("/booking/search")
    public ResponseEntity<List<FlightDTO>> flightSearch(@Valid @RequestBody FlightDTO flightDTO){
        List<FlightDTO> temp= this.flightServiceImp.selectFromAndTo(flightDTO.getFrom(),flightDTO.getTo());
        return ResponseEntity.ok(temp);
    }

    @PutMapping("/booking/seats")
    public ResponseEntity<String > updateSeats(@Valid @RequestBody FlightDTO flightDTO){
        String temp= this.flightServiceImp.updateSeats(flightDTO);
        return ResponseEntity.ok(temp);
    }

    @PutMapping("/booking/cancel")
    public ResponseEntity<String > updateSeatsWithCancel(@Valid @RequestBody FlightDTO flightDTO){
        String temp= this.flightServiceImp.updateSeatsWithCancel(flightDTO);
        return ResponseEntity.ok(temp);
    }
}

