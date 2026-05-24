package in.medhub.backend.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.medhub.backend.model.Doctor;
import in.medhub.backend.model.DoctorAvailability;
import in.medhub.backend.model.Slot;
import in.medhub.backend.repository.DoctorRepository;
import in.medhub.backend.repository.DoctorsAvailabilityRepository;
import in.medhub.backend.repository.SlotRepository;

@RestController
@RequestMapping("/v1/doctor")
public class DoctorController {

	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	DoctorsAvailabilityRepository doctorsAvailabilityRepository;
	
	@Autowired
	SlotRepository slotRepo;
	
    @PostMapping()
    public Doctor createDoctor() {
        Doctor doctor=new Doctor();
        doctor.setName("Rahul");
        doctor.setSpecialization("MD");
        
        return doctorRepository.save(doctor);
    }
    
    @GetMapping()
    public List<Doctor> findBySpecialization(@RequestParam String speciality){
    	return doctorRepository.findBySpecializationIgnoreCase(speciality);
    }
    
    @PostMapping("/availability")
    public DoctorAvailability createDoctorAvailability() {
        Doctor doctor=new Doctor();
        doctor.setId((long) 1);
        
        DoctorAvailability availability=new DoctorAvailability();
        availability.setDayOfWeek(DayOfWeek.MONDAY);
        availability.setStartTime(LocalTime.of(10, 0));
        availability.setEndTime(LocalTime.of(13, 0));
        availability.setDoctor(doctor);
        
        
//        List<Slot> slots = new ArrayList<>();
        
        LocalDate date=LocalDate.now();
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
        
        
        
        return doctorsAvailabilityRepository.save(availability);
    }
}
