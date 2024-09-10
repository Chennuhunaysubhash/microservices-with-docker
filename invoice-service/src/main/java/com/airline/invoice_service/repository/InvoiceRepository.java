package com.airline.invoice_service.repository;

import com.airline.invoice_service.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {


    List<Invoice> findByCid(int cid);

    Optional<Invoice> findByPaymentId(long paymentId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Invoice i set i.definition= :definition  where i.paymentId= :paymentId")
    void statusDefinition(@Param(value = "definition") String definition, @Param(value = "paymentId") long paymentId);


}
