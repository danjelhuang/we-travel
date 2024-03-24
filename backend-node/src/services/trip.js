const db = require('../database/database');

class TripService {
  async createTrip(tripData) {
    try {
      const docRef = db.collection('trips').doc(tripData.code);
      await docRef.set(tripData);
      return { id: docRef.id, ...tripData };
    } catch (error) {
      console.error('Error creating trip:', error);
      throw new Error('Failed to create trip');
    }
  }

  async updateTrip(updates, tripId) {
    try {
      const docRef = db.collection('trips').doc(tripId);
      await docRef.update(updates);
      return { success: true, message: 'Trip updated successfully', id: tripId };
    } catch (error) {
      console.error('Error updating trip: ', error);
      throw new Error('Failed to update trip')
    }
  }

  async loadTrip(tripId) {
    try {
      const docRef = db.collection('trips').doc(tripId);
      console.log(tripId)
      const doc = await docRef.get();
      
      if (!doc.exists) {
        console.log('No such trip!');
        return null; // or throw an error based on your error handling strategy
      } else {
        console.log('Trip data:', doc.data());
        return { id: doc.id, ...doc.data() };
      }
    } catch (error) {
      console.error('Error getting trip:', error);
      throw new Error('Failed to get trip');
    }
  }
}

module.exports = TripService;
