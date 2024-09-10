package com.airline.customer_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class CustomerDTO {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator",initialValue = 11,allocationSize = 1,sequenceName = "customer_seq")
    private int id;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String name;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Length(min = 10,max = 12,message = "please check no of digits in enter mobile number")
    private String phoneNumber;


    private String email;

    private String password;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String county;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Size(min=2,message = "name should be more than three letters")
    private String city;

    public CustomerDTO() {
    }

    public CustomerDTO(int id, String name, String phoneNumber, String email, String password, String county, String city) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.county = county;
        this.city = city;
    }

    public CustomerDTO(String name, String phoneNumber, String email, String password, String county, String city) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.county = county;
        this.city = city;
    }

    public CustomerDTO(int id, String name, String phoneNumber, String county, String city) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.county = county;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
