package com.airline.payment_service.repository;

import com.airline.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Query("select i from Payment i where i.cId = :cId AND i.bookingId= :bookingId")
    List<Payment> findByBothIds(@Param(value = "cId") int cId,@Param(value = "bookingId") long bookingId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Payment i set i.paymentType= :paymentType,i.status= :status  where i.id= :id AND i.bookingId= :bookingId")
    void paymentDone(@Param(value = "paymentType") String paymentType,@Param(value = "status") String status, @Param(value = "id") long id,@Param(value = "bookingId") long bookingId);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Payment i set i.status= :status  where i.cId= :cId AND i.bookingId= :bookingId")
    void paymentCancel(@Param(value = "status") String status, @Param(value = "cId") int cId,@Param(value = "bookingId") long bookingId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Payment i set i.invoiceStatus= :invoiceStatus  where i.cId= :cId AND i.id= :id")
    void invoiceStatus(@Param(value = "invoiceStatus") Boolean invoiceStatus, @Param(value = "cId") int cId,@Param(value = "id") long id);
}
