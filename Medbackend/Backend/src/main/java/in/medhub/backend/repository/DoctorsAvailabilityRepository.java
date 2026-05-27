package in.medhub.backend.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import in.medhub.backend.model.DoctorAvailability;

public interface DoctorsAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {

	boolean existsByDoctorIdAndDate(Long doctorId, LocalDate date);
}
