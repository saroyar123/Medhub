package in.medhub.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.medhub.backend.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
