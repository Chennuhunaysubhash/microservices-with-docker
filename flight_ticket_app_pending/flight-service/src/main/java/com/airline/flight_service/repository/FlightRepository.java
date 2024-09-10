package com.airline.flight_service.repository;

import com.airline.flight_service.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {

    @Query("select i from Flight i where i.flightName = :flightName")
    List<Flight> duplicateCheck(@Param(value = "flightName") String flightName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Flight i set i.departure= :departure,i.time= :time  where i.id= :id AND i.flightName= :flightName")
    void changeDateAndTime(@Param(value = "departure") String departure, @Param(value = "time") String time, @Param(value = "id") long id,@Param(value = "flightName") String flightName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Flight i set i.time= :time  where i.id= :id AND i.flightName= :flightName")
    void changeTime(@Param(value = "time") String time, @Param(value = "id") long id,@Param(value = "flightName") String flightName);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Flight i set i.totalSeats= :totalSeats,i.bookedSeats=:bookedSeats,i.availableSeats= :availableSeats,i.status= :status  where i.id= :id AND i.flightName= :flightName")
    void bookingRestart(@Param(value = "totalSeats") int totalSeats,@Param(value = "bookedSeats") int bookedSeats,@Param(value = "availableSeats") int availableSeats,@Param(value = "status") String status, @Param(value = "id") long id,@Param(value = "flightName") String flightName);

    @Query("select i from Flight i where i.from = :from AND i.to = :to")
    List<Flight> selectFromAndTo(@Param(value = "from") String from,@Param(value = "to") String to);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Flight i set i.bookedSeats= :bookedSeats,i.availableSeats= :availableSeats  where i.id= :id AND i.flightName= :flightName")
    void updateSeats(@Param(value = "bookedSeats") int bookedSeats,@Param(value = "availableSeats") int availableSeats, @Param(value = "id") long id,@Param(value = "flightName") String flightName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Flight i set i.status= :status  where i.id= :id AND i.flightName= :flightName")
    void bookingClosed(@Param(value = "status") String status, @Param(value = "id") long id,@Param(value = "flightName") String flightName);
}

