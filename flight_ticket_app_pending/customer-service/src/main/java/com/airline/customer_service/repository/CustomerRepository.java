package com.airline.customer_service.repository;

import com.airline.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query("select i from Customer i where i.email = :email")
    List<Customer> duplicateCheck(@Param(value = "email") String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Customer i set i.password= :password where i.email= :email")
    void updatePassword(@Param(value = "email") String email, @Param(value = "password") String password);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Customer i set i.password= :password where i.email= :email")
    void forgetPassword(@Param(value = "email") String email, @Param(value = "password") String password);

}
