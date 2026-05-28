package in.medhub.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
    // Changed to @ManyToOne to allow historical/cancelled rows to reference the same slot
    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false) 
    private Slot slot;

    // Track when the action happened
    private LocalDateTime bookingTime;

    // Track the status lifecycle: "CONFIRMED", "CANCELLED", "RESCHEDULED"
    private String status; 
	
    public Booking() {
        this.bookingTime = LocalDateTime.now();
        this.status = "CONFIRMED"; // Default status when created
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Slot getSlot() { return slot; }
    public void setSlot(Slot slot) { this.slot = slot; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}