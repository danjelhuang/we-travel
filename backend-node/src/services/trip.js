const { FieldValue } = require("firebase-admin").firestore;
const db = require("../database/database");

class TripService {
  async createTrip(tripData) {
    try {
      const docRef = db.collection('trips').doc(tripData.tripID);
      tripData.users.push({ userID: tripData.adminUserID, votes: tripData.votesPerPerson })
      await docRef.set(tripData);
      return { id: docRef.id, ...tripData };
    } catch (error) {
      console.error("Error creating trip:", error);
      throw new Error("Failed to create trip");
    }
  }

  async updateTrip(updates, tripId) {
    try {
      const docRef = db.collection("trips").doc(tripId);
      await docRef.update(updates);
      return {
        success: true,
        message: "Trip updated successfully",
        id: tripId,
      };
    } catch (error) {
      console.error("Error updating trip: ", error);
      throw new Error("Failed to update trip");
    }
  }

  async loadTrip(tripId) {
    try {
      const docRef = db.collection("trips").doc(tripId);
      const doc = await docRef.get();

      if (!doc.exists) {
        console.log("No such trip!");
        return { success: false, message: "Trip does not exist" };
      } else {
        return { success: true, ...doc.data() };
      }
    } catch (error) {
      console.error("Error getting trip:", error);
      throw new Error("Failed to get trip");
    }
  }

  async addDestination(tripId, newDestinationID) {
    try {
      const docRef = db.collection("trips").doc(tripId);
      const doc = await docRef.get();

      if (!doc.exists) {
        console.log("No such trip!");
        return {
          success: false,
          message: "Trip does not exist",
        };
      }

      const trip = doc.data();

      const isExisting = trip.destinationsList.some(dest => dest.placeID == newDestinationID);

      if (isExisting) {
        return {
          success: false,
          message: "Destination already exists in trip",
        };
      } else {
        await docRef.update({
          destinationsList: FieldValue.arrayUnion({ placeID: newDestinationID, votes: 0 })
        });
        return {
          success: true,
          message: "Destination added successfully",
        };
      }
    } catch (error) {
      console.error("Error  adding destination:", error);
      throw new Error("Error adding destination to the trip");
    }
  }

  async addParticipantToTrip(userId, tripId) {
    try {
      const tripRef = db.collection("trips").doc(tripId);
      const numOfVotes = (await tripRef.get()).data().finalDestinationCount;

      await tripRef.update({
        users: FieldValue.arrayUnion({ userID: userId, votes: numOfVotes }),
      });

      return {
        success: true,
        message: "Participant added successfully to the trip",
      };
    } catch (error) {
      console.error("Error adding participant: ", error);
      throw new Error("Failed to add participant");
    }
  }

  async removeParticipantFromTrip(userId, tripId) {
    const docRef = db.collection("trips").doc(tripId);
    try {
      const doc = await docRef.get();
      if (!doc.exists) {
        console.log("No such document!");
      } else {
        let data = doc.data();
        // Filter out the user with the given userID
        const updatedUsers = data.users.filter(
          (user) => user.userID !== userId
        );

        // Update the document with the new users array
        await docRef.update({
          users: updatedUsers,
        });
        return {
          success: true,
          message: "Participant removed successfully from the trip",
        };
      }
    } catch (error) {
      console.error("Error removing participant: ", error);
      throw new Error("Failed to remove participant");
    }
  }

  async getUserVotes(tripID, userID) {
    const trip = db.collection("trips").doc(tripID);
    try {
      const doc = await trip.get();
      if (!doc.exists) {
        console.log("No such document!");
        return {
          success: false,
          message: "TripID error",
        }
      } else {

        const userData = doc.data().users.find(user => user.userID === userID);
        if (!userData) {
          console.log('User not in trip');
          return {
              success: false,
              message: "User not in trip",
          };
        }

        return {
          success: true,
          votes: userData?.votes || -1
        };
      }
    } catch (error) {
      console.error("Error getting user votes: ", error);
      throw new Error("Failed to get user votes");
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
