package com.medhub.DoctorService.Dto;

import java.util.UUID;

public class CreateDoctorDto {
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String email;
    private UUID specialization_id;
    private String registration;

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

    public UUID getSpecialization_id() {
        return specialization_id;
    }

    public void setSpecialization_id(UUID specialization_id) {
        this.specialization_id = specialization_id;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    @Override
    public String toString() {
        return "CreateDoctorDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", specialization_id=" + specialization_id +
                ", registration='" + registration + '\'' +
                '}';
    }
}
