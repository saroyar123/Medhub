package com.medhub.DoctorService.Repojitory;

import com.medhub.DoctorService.Model.SpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecializationRepository extends JpaRepository<SpecializationEntity, UUID> {
}
