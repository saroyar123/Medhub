package in.medhub.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.medhub.backend.model.Slot;

public interface SlotRepository extends JpaRepository<Slot,Long> {

}
