package com.airline.customer_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "customer")
public class Customer {
    /* this for mysql support
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator",initialValue = 11,allocationSize = 1,sequenceName = "customer_seq")*/
    //this for postgresql support
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
    @SequenceGenerator(
            name = "customer_generator",
            sequenceName = "customer_seq",
            initialValue = 11,
            allocationSize = 1
    )
    private int id;

    private String name;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Length(min = 10,max = 12,message = "please check no of digits in enter mobile number")
    private String phoneNumber;


    @NotEmpty(message = "name should be present")
    @Email(message = "should be in email format")
    private String email;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Length(min = 8,max = 16,message = "please check length of the password")
    private String password;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String county;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Size(min=2,message = "name should be more than three letters")
    private String city;

    public Customer() {
    }

    public Customer(String name, String phoneNumber, String email, String password, String county, String city) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", county='" + county + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
