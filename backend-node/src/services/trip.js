const { FieldValue } = require("firebase-admin").firestore;
const db = require("../database/database");

class TripService {
  async createTrip(tripData) {
    try {
      const docRef = db.collection('trips').doc(tripData.tripID);
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
        return null;
      } else {
        console.log("Trip data:", doc.data());
        return { id: doc.id, ...doc.data() };
      }
    } catch (error) {
      console.error("Error getting trip:", error);
      throw new Error("Failed to get trip");
    }
  }

  async addDestination(tripId, newDestination) {
    try {
      const docRef = db.collection("trips").doc(tripId);
      const doc = await docRef.get();

      if (!doc.exists) {
        console.log("No such trip!");
        return null;
      }

      const trip = doc.data();

      const isExisting = trip.destinationsList.some(dest =>dest.placeID == newDestination.placeID);

      if (isExisting) {
        return {
          error: true,
          message: "Destination already exists",
          status: 409,
        };
      } else {
        await docRef.update({
          destinationsList: [...trip.destinationsList, newDestination],
        });
        return {
          error: false,
          message: "Destination added successfully",
          status: 200,
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
}

module.exports = TripService;
