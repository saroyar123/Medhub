package com.medhub.DoctorService.Model;

import com.medhub.DoctorService.Dto.CreateDoctorDto;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class DoctorEntity {
    @Id
    @GeneratedValue
    private UUID Id;
    private String firstName;
    private String lastName;
    private String mobileNo;
    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(
            name = "speacialization_id",
            referencedColumnName = "Id"
    )
    private SpecializationEntity specialization;
    private String registration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public DoctorEntity(){};

    public DoctorEntity(CreateDoctorDto createDoctorDto,SpecializationEntity specialization){
        this.firstName=createDoctorDto.getFirstName();
        this.lastName=createDoctorDto.getLastName();
        this.email=createDoctorDto.getEmail();
        this.mobileNo=createDoctorDto.getMobileNo();
        this.specialization=specialization;
        this.registration=createDoctorDto.getRegistration();
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SpecializationEntity getSpecialization() {
        return specialization;
    }

    public void setSpecialization(SpecializationEntity specialization) {
        this.specialization = specialization;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
