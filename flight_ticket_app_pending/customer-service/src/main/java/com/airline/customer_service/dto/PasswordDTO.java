package com.airline.customer_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class PasswordDTO {

    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customer_generator")
    @SequenceGenerator(name = "customer_generator",initialValue = 11,allocationSize = 1,sequenceName = "customer_seq")
    private int id;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Email(message = "should be in email format")
    private String email;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Length(min = 8,max = 16,message = "please check length of the password")
    private String oldPassword;
    @Column(unique = true)
    @NotEmpty(message = "name should be present")
    @Length(min = 8,max = 16,message = "please check length of the password")
    private String newPassword;

    public PasswordDTO() {
    }

    public PasswordDTO(int id, String email, String oldPassword, String newPassword) {
        this.id = id;
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
