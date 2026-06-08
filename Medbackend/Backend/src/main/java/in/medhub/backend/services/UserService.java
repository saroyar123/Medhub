package in.medhub.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import in.medhub.backend.model.Booking;
import in.medhub.backend.model.Slot;
import in.medhub.backend.model.User;
import in.medhub.backend.repository.BookingRepository;
import in.medhub.backend.repository.SlotRepository;
import in.medhub.backend.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	SlotRepository slotRepository;
	
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	
	public String bookSlot(Booking booking) {
		
		 Long slotId=booking.getSlot().getId();
		 Slot slot=slotRepository.findById(slotId).orElseThrow(() -> new ResponseStatusException(
                 HttpStatus.NOT_FOUND, "Slot with ID " + slotId + " does not exist."
         ));
		 slot.setBooked(true);
		 slotRepository.save(slot);
		 
		 bookingRepository.save(booking);
		 return "record created";
	}
	
}
