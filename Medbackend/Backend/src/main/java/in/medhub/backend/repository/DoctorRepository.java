package in.medhub.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.medhub.backend.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	
//	find the doctor list by their specialization
	List<Doctor> findBySpecializationIgnoreCase(String specialization);

}
