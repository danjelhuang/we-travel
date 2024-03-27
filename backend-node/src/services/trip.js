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

  async loadTrip(tripId) {
    try {
      const docRef = db.collection('trips').doc(tripId);
      console.log(tripId)
      const doc = await docRef.get();
      
      if (!doc.exists) {
        console.log('No such trip!');
        return null; 
      } else {
        console.log('Trip data:', doc.data());
        return { id: doc.id, ...doc.data() };
      }
    } catch (error) {
      console.error('Error getting trip:', error);
      throw new Error('Failed to get trip');
    }
  }

  async addDestination(tripId, newDestination) {
    try {
      const docRef = db.collection('trips').doc(tripId);
      const doc = await docRef.get();
      
      if (!doc.exists) {
        console.log('No such trip!');
        return null; 
      } 

      const trip = doc.data();

      const isExisting = trip.destinationsList.some(dest =>dest.address == newDestination.address);

      if (isExisting) {
        return { error: true, message: 'Destination already exists', status: 409 };
      } else {
        await docRef.update({
          destinationsList: [...trip.destinationsList, newDestination]
        });
        return { error: false, message: 'Destination added successfully', status: 200 };
      }
  } catch (error) {
    console.error('Error  adding destination:', error);
    throw new Error('Error adding destination to the trip');
  }
}

}

module.exports = TripService;
