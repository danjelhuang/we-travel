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
}

module.exports = TripService;
