const { generateUniqueTripCode } = require('../util/trip')

const TripService = require('../services/trip');
const tripService = new TripService();

exports.createTrip = async (req, res) => {
  try {
    const tripData = req.body;
    tripData.tripID = await generateUniqueTripCode();
    const trip = await tripService.createTrip(tripData);
    console.log("Trip created successfully");
    res.status(201).json(trip);
  } catch (error) {
    console.error('Error creating trip:', error);
    res.status(500).json({ error: 'Failed to create trip' });
  }
};

exports.loadTrip = async (req, res) => {
  try {
    const tripId = req.params.id;
    const trip = await tripService.loadTrip(tripId);
    if (!trip.success) {
      res.status(404).json(trip)
    } else {
      console.log("Trip fetched successfully");
      res.status(200).json(trip);
    }
  } catch (error) {
    console.error('Error fetching trip:', error);
    res.status(500).json({ error: 'Failed to fetch trip' });
  }
};

exports.getAllTrips = async (req, res) => {
  try {
    const userId = req.params.id;
    const trip = await tripService.getAllTrips(userId);
    if (!trip.success) {
      res.status(404).json(trip)
    } else {
      console.log("All user trips fetched successfully");
      res.status(200).json(trip.data);
    }
  } catch (error) {
    console.error('Error fetching all user trips:', error);
    res.status(500).json({ error: 'Failed to fetch user trips' });
  }
};

exports.addDestination = async (req, res) => {

  try {
    const tripId = req.params.id;
    const newDestinationID = req.body.placeID;
    const trip = await tripService.addDestination(tripId, newDestinationID);
    if (trip.message === "Trip does not exist") {
      res.status(404).json(trip);
    }
    else if (trip.message === "Destination already exists in trip") {
      console.log("Destination exists in trip")
      res.status(409).json(trip);
    } else {
      console.log("Destination added successfully");
      res.status(201).json(trip);
    }
    return
  } catch (error) {
    console.error('Error adding destination:', error);
    res.status(500).json({ error: 'Failed to add destination' });
  }
};

exports.updateTrip = async (req, res) => {
  const tripId = req.params.id;
  const updates = req.body;
  try {
    const result = await tripService.updateTrip(updates, tripId)
    if (!result.success) {
      res.status(404).json(result)
    } else {
      console.log("Trip updated")
      res.status(200).json(result);
    }
  } catch (error) {
    console.error('Error updating trip: ', error);
    res.status(500).json({ error: 'Failed to update the trip' });
  }
}

exports.addParticipantToTrip = async (req, res) => {
  const tripId = req.params.id;
  const userId = req.body;

  if (!userId || !userId.userId) {
    res.status(400).send({ success: false, message: 'Participant userId is required' });
    return;
  }

  try {
    const result = await tripService.addParticipantToTrip(userId.userId, tripId)
    if (!result.success) {
      console.log(result)
      res.status(404).json(result)
    } else {
      console.log("Participant added to trip")
      res.status(200).json(result)
    }
  } catch (error) {
    console.error('Error adding participant to trip: ', error);
    res.status(500).json({ error: 'Failed to add participant to the trip' });
  }
}

exports.removeParticipantFromTrip = async (req, res) => {
  const tripId = req.params.id;
  const userId = req.body;

  if (!userId || !userId.userId) {
    res.status(400).send({ success: false, message: 'Participant userId is required' });
    return;
  }

  try {
    const result = await tripService.removeParticipantFromTrip(userId.userId, tripId)
    res.status(200).json(result)
  } catch (error) {
    console.error('Error removing participant to trip: ', error);
    res.status(500).json({ error: 'Failed to remove participant from the trip' });
  }
};

exports.getUserVotes = async (req, res) => {
  const tripID = req.params.tripID;
  const userID = req.params.userID;

  if (!tripID || !userID) {
    res.status(400).send({ success: false, message: 'tripID and userID required' });
    return;
  }

  try {
    const result = await tripService.getUserVotes(tripID, userID);
    if (!result.success) {
      res.status(404).json(result);
      return;
    }
    console.log("Successfully got user votes")
    res.status(200).json(result);
  } catch (error) {
    console.error('Error getting user votes: ', error);
    res.status(500).json({ error: 'Failed to get user votes' });
  }
}

exports.addVote = async (req, res) => {

  try {
    const tripId = req.params.id;
    const userId = req.params.userId;
    const placeId = req.params.placeId;

  
    const trip = await tripService.addVote(tripId, userId, placeId);

    if (trip.error) {
      res.status(500).json({ error: 'Failed to add vote' });
      return
    } else {
      console.log("Vote added successfully");
      res.status(201).json(trip);
      return
      
    }

  } catch (error) {
    console.error('Error adding vote:', error);
    res.status(500).json({ error: 'Failed to add vote' });
  } 

  
}

exports.removeVote = async (req, res) => {

  try {
    const tripId = req.params.id;
    const userId = req.params.userId;
    const placeId = req.params.placeId;

  
    const trip = await tripService.removeVote(tripId, userId, placeId);

    if (trip.error) {
      res.status(500).json({ error: 'Failed to remove vote' });
      return
    } else {
      console.log("Vote removed successfully");
      res.status(201).json(trip);
      return
      
    }

  } catch (error) {
    console.error('Error removing vote:', error);
    res.status(500).json({ error: 'Failed to remove vote' });
  } 

  
}
;


exports.createUser = async (req, res) => {
  try {
    const userId = req.body.userId;
    const user = await tripService.createUser(userId);
    if (!user.success) {
      res.status(404).json(user)
    } else {
      console.log("User added successfully");
      res.status(201).json({ success: user.success, userID: user.id, tripIDs: user.tripIds });
    }
  } catch (error) {
    console.error('Error adding user:', error);
    res.status(500).json({ error: 'Failed to add user' });
  }
}

exports.getUser = async (req, res) => {
  try {
    const userId = req.params.id;
    const user = await tripService.getUser(userId);
    if (!user.success) {
      res.status(404).json(user)
    } else {
      console.log("User fetched successfully");
      res.status(200).json({ success: user.success, userID: user.id, tripIDs: user.tripIds });
    }
  } catch (error) {
    console.error('Error fetching user:', error);
    res.status(500).json({ error: 'Failed to fetch user' });
  }
};

exports.updateUser = async (req, res) => {
  try {
    const userID = req.params.id;
    const tripID = req.body.tripID;
    const result = await tripService.updateUser(userID, tripID)
    if (!result.success) {
      res.status(404).json(result)
    } else {
      console.log("User updated")
      res.status(200).json({ success: result.success, userID: result.id, tripIDs: result.tripIds });
    }
  } catch (error) {
    console.error('Error updating user: ', error);
    res.status(500).json({ error: 'Failed to update the user' });
  }
}

exports.updateFinalDestinations = async (req, res) => {
  try {
    const tripID = req.params.id
    const result = await tripService.updateFinalDestinations(tripID)
    if (!result.success) {
      res.status(404).json(result)
    } else {
      console.log("Final Destinations Updated")
      res.status(200).json({ success: result.success, message: result});
    }
  } catch (error) {
    console.error('Error updating final destinations: ', error);
    res.status(500).json({ error: 'Failed to update final destinations' });
  }
}