package in.medhub.backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.medhub.backend.dto.ApiResponse;
import in.medhub.backend.model.Doctor;
import in.medhub.backend.model.DoctorAvailability;
import in.medhub.backend.model.Slot;
import in.medhub.backend.services.DoctorService;

@RestController
@RequestMapping("/v1/doctor")
public class DoctorController {

	@Autowired
	DoctorService doctorService;
	
    @PostMapping()
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        
        return doctorService.createDoctor(doctor);
    }
    
    @GetMapping("/search")
    public List<Doctor> findBySpecialization(@RequestParam String speciality){
    	return doctorService.searchBySpecialization(speciality);
    }
    
    @GetMapping("/availability")
    public ResponseEntity<ApiResponse<List<Slot>>> getAllSlotOfTheDoctorOnDate(@RequestParam Long doctorId, @RequestParam LocalDate date){
    	List<Slot> slots=doctorService.getAllSlotsForTheDoctor(doctorId, date);    	
    	return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Doctor slots fetch successfully", slots));
    }
    
    @PostMapping("/availability")
    public String createDoctorAvailability(@RequestBody DoctorAvailability doctorAvailability) {
        return doctorService.createAvailability(doctorAvailability);        
    }
    
    
}
