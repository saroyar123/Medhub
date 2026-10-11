package com.medhub.UserService.Entity;

import com.medhub.UserService.Dto.CreateUserDto;
import jakarta.persistence.*;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue
    private UUID Id;
    private String firstName;
    private String lastName;
    @Column(unique = true,nullable = false)
    private String email;
    private String phone;
    private Date dateOfBirth;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public UserEntity() {
    }

    public UserEntity(CreateUserDto user){
        this.firstName= user.getFirstName();
        this.lastName= user.getLastName();
        this.address= user.getAddress();
        this.email= user.getEmail();
        this.phone= user.getPhone();
        this.dateOfBirth=user.getDateOfBirth();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateAt = LocalDateTime.now();
    }
}
