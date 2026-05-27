package in.medhub.backend.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import in.medhub.backend.model.Doctor;
import in.medhub.backend.model.DoctorAvailability;
import in.medhub.backend.model.Slot;
import in.medhub.backend.repository.DoctorRepository;
import in.medhub.backend.repository.DoctorsAvailabilityRepository;
import in.medhub.backend.repository.SlotRepository;

@Controller
public class DoctorService {

	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	DoctorsAvailabilityRepository doctorsAvailabilityRepository;
	
	@Autowired
	SlotRepository slotRepo;
	
//	created doctor
	public Doctor createDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}
	
//	search doctor by their specialization
	public List<Doctor> searchBySpecialization(String speciality){
		return doctorRepository.findBySpecializationIgnoreCase(speciality);
	}
	
//	create the available slots
	public String createAvailability(DoctorAvailability availability) {
		
//		validate the doctor id
		
		Long doctorId = availability.getDoctor().getId();
	    LocalDate date = availability.getDate();

	    // 2. NEW CHECK: Prevent duplicate availability records for the same day
	    boolean isAlreadyAvailable = doctorsAvailabilityRepository.existsByDoctorIdAndDate(doctorId, date);
	    if (isAlreadyAvailable) {
	        throw new ResponseStatusException(
	            HttpStatus.CONFLICT, 
	            "Availability schedule has already been created for this doctor on " + date
	        );
	    }
		
		Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Doctor with ID " + doctorId + " does not exist."
                ));
		
		
        LocalTime start = availability.getStartTime();
        LocalTime end = availability.getEndTime();

        while (start.isBefore(end)) {

            LocalTime slotEnd = start.plusMinutes(15);

            Slot slot = new Slot();
            slot.setDate(date);
            slot.setStartTime(start);
            slot.setEndTime(slotEnd);
            slot.setBooked(false);
            slot.setDoctor(doctor);
            
            slotRepo.save(slot);

            start = slotEnd;
        }
        
        availability.setDoctor(doctor);
        doctorsAvailabilityRepository.save(availability);
        
		return "Doctor slots are created";
	}
	
	
//	get all the slot for a doctor on a specific date
	public List<Slot> getAllSlotsForTheDoctor(Long doctorId, LocalDate date){
		return slotRepo.findByDoctorIdAndDate(doctorId, date);
	}
}
