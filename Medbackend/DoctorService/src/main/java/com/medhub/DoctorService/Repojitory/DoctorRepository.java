package com.medhub.DoctorService.Repojitory;

import com.medhub.DoctorService.Model.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<DoctorEntity, UUID> {

    List<DoctorEntity> findBySpecializationName(String name);
}
