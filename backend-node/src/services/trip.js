const { FieldValue } = require('firebase-admin').firestore;
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

      const isExisting = trip.destinationsList.some(dest =>dest.placeID == newDestination.placeID);

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

  async addParticipantToTrip(userId, tripId) {
    try {
      const tripRef = db.collection('trips').doc(tripId);
  
      await tripRef.update({
        participants: FieldValue.arrayUnion(userId)
      });
  
      return { success: true, message: 'Participant added successfully to the trip' };
  
    } catch (error) {
      console.error('Error adding participant: ', error)
      throw new Error('Failed to add participant');
    }
  }

  async removeParticipantFromTrip(userId, tripId) {
    try {
      const tripRef = db.collection('trips').doc(tripId);
  
      await tripRef.update({
        participants: FieldValue.arrayRemove(userId)
      });
  
      return { success: true, message: 'Participant removed successfully from the trip' };
  
    } catch (error) {
      console.error('Error removing participant: ', error)
      throw new Error('Failed to remove participant');
    }
  }

  async addVote(tripId, userId, placeId) {

    try {
      const tripRef = db.collection('trips').doc(tripId);
      const doc = await tripRef.get();

      const destinations = doc.data().destinationsList;
      const userList = doc.data().users;
      
      const destinationIndex = destinations.findIndex(d => d.placeID === placeId);
      const userIndex = userList.findIndex(u => u.userID === userId);

      destinations[destinationIndex].votes = destinations[destinationIndex].votes + 1 || 1;
      userList[userIndex].votes = userList[userIndex].votes - 1 || 1;
   
      await tripRef.update({
        destinationsList: destinations,
        users: userList
    });
  
      return { success: true, message: 'Vote added successfully to destination' };
  
    } catch (error) {
      console.error('Error adding vote: ', error)
      throw new Error('Failed to add vote');
    }
  }


  async removeVote(tripId, userId, placeId) {

    try {
      const tripRef = db.collection('trips').doc(tripId);
      const doc = await tripRef.get();

      const destinations = doc.data().destinationsList;
      const userList = doc.data().users;
      
      const destinationIndex = destinations.findIndex(d => d.placeID === placeId);
      const userIndex = userList.findIndex(u => u.userID === userId);

      destinations[destinationIndex].votes = destinations[destinationIndex].votes - 1 || 1;
      userList[userIndex].votes = userList[userIndex].votes + 1 || 1;
   
      await tripRef.update({
        destinationsList: destinations,
        users: userList
    });
  
      return { success: true, message: 'Vote removed successfully from destination' };
  
    } catch (error) {
      console.error('Error removing vote: ', error)
      throw new Error('Failed to remove vote');
    }
  }
}



module.exports = TripService;
