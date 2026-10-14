package com.medhub.DoctorService.Service;

import com.medhub.DoctorService.Model.SpecializationEntity;
import com.medhub.DoctorService.Repojitory.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpecializationService {
    @Autowired
    private SpecializationRepository specializationRepository;

    public SpecializationEntity findById(UUID uuid){
        return specializationRepository.findById(uuid).orElseThrow();
    }
}
