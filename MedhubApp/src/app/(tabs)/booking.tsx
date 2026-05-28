import { ScrollView, StyleSheet, Text, View } from 'react-native';

export default function BookingScreen() {
  // Creating an array of 20 items to simulate a long list
  const dummyBookings = Array.from({ length: 20 }, (_, index) => `Booking #${index + 1}`);

  return (
    <ScrollView 
      style={styles.container} 
      contentContainerStyle={styles.scrollContent} // 👈 This handles centering the content inside the scroll area
    >
      <Text style={styles.headerText}>Your Bookings 📅</Text>
      
      {dummyBookings.map((booking, index) => (
        <View key={index} style={styles.card}>
          <Text style={styles.cardText}>{booking}</Text>
          <Text style={styles.subText}>Confirmed • 10:00 AM</Text>
        </View>
      ))}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5', // Changed to light gray so card components stand out
  },
  scrollContent: {
    alignItems: 'center',       // Centers items horizontally
    paddingVertical: 20,       // Adds space at the top and bottom so it doesn't clip
  },
  headerText: {
    fontSize: 22,
    fontWeight: '700',
    marginBottom: 20,
    color: '#333',
  },
  card: {
    width: '90%',               // Makes the card take up 90% of screen width
    backgroundColor: '#fff',
    padding: 16,
    borderRadius: 12,
    marginBottom: 12,           // Spacing between cards
    shadowColor: '#000',        // Subtle shadow for iOS
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 2,               // Subtle shadow for Android
  },
  cardText: {
    fontSize: 16,
    fontWeight: '600',
    color: '#007AFF',
  },
  subText: {
    fontSize: 14,
    color: '#666',
    marginTop: 4,
  },
});