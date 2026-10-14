package com.medhub.DoctorService.Service;

import com.medhub.DoctorService.Dto.CreateDoctorDto;
import com.medhub.DoctorService.Model.DoctorEntity;
import com.medhub.DoctorService.Model.SpecializationEntity;
import com.medhub.DoctorService.Repojitory.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationService specializationService;

    public DoctorEntity createDoctor(CreateDoctorDto createDoctorDto){

        System.out.println(createDoctorDto.toString());
        SpecializationEntity specialization=specializationService.findById(createDoctorDto.getSpecialization_id());
        System.out.println("Specialization is found");
        return doctorRepository.save(new DoctorEntity(createDoctorDto,specialization));
    }

    public List<DoctorEntity> getDoctorBySpeciality(String speciality){
        System.out.println("speciality: "+speciality);
        List<DoctorEntity> res=doctorRepository.findBySpecializationName(speciality);
        System.out.println("res: "+res.toString());
        return res;
    }
}
