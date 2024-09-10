package com.airline.customer_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class ForgetPasswordDTO {
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator",initialValue = 11,allocationSize = 1,sequenceName = "customer_seq")
    private int id;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Size(min=3,message = "name should be more than three letters")
    private String name;
    /*@Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Length(min = 10,max = 12,message = "please check no of digits in enter mobile number")
    private String phoneNumber;*/
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Email(message = "should be in email format")
    private String email;

    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Length(min = 8,max = 16,message = "please check length of the password")
    private String newPassword;

    public ForgetPasswordDTO() {
    }

    public ForgetPasswordDTO(int id, String name, String email, String newPassword) {
        this.id = id;
        this.name = name;
        //this.phoneNumber = phoneNumber;
        this.email = email;
        this.newPassword = newPassword;
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

    /*public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }*/

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
