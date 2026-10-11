package com.medhub.DoctorService.Controller;

import com.medhub.DoctorService.Dto.CreateDoctorDto;
import com.medhub.DoctorService.Model.DoctorEntity;
import com.medhub.DoctorService.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping()
    public DoctorEntity createDoctor(@RequestBody CreateDoctorDto createDoctorDto){
        return doctorService.createDoctor(createDoctorDto);
    }

    @GetMapping()
    public List<DoctorEntity> getDoctorBySpeciality(@RequestParam String speciality){
        return doctorService.getDoctorBySpeciality(speciality);
    }
}
