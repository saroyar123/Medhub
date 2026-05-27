package in.medhub.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.medhub.backend.model.Slot;

public interface SlotRepository extends JpaRepository<Slot,Long> {

	List<Slot> findByDoctorIdAndDate(Long doctorId,LocalDate date);
}
