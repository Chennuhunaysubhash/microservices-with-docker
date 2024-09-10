package com.airline.booking_service.repository;

import com.airline.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query("select i from Booking i where i.cid = :cid")
    List<Booking> allByCid(@Param(value = "cid") int cid);

    @Query("select i from Booking i where i.cid = :cid AND i.flightId= :flightId AND i.noSeats= :noSeats")
    List<Booking> search(@Param(value = "cid") int cid,@Param(value = "flightId") long flightId,@Param(value = "noSeats") int noSeats);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Booking i set i.status= :status  where i.id= :id")
    void statusUpdate(@Param(value = "status") String status, @Param(value = "id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Booking i set i.status= :status  where i.id= :id AND i.cid= :cid")
    void bookingClosing(@Param(value = "status") String status, @Param(value = "id") long id, @Param(value = "cid") int cid);
}
