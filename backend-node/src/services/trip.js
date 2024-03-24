const db = require('../database/database');

class TripService {
  async createTrip(tripData) {
    try {
      const docRef = await db.collection('trips').add(tripData);
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
}

module.exports = TripService;
