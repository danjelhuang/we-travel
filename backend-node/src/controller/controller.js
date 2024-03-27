const { generateTripCode } = require('../util/trip')

const TripService = require('../services/trip');
const tripService = new TripService();

exports.createTrip = async (req, res) => {
  try {
    const tripData = req.body;
    tripData.code = generateTripCode();
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
    console.log("Trip fetched successfully");
    res.status(201).json(trip);
  } catch (error) {
    console.error('Error fetching trip:', error);
    res.status(500).json({ error: 'Failed to fetch trip' });
  }
};

exports.addDestination = async (req, res) => {

  try {
    console.log("hihihii:", req.params);
    const tripId = req.params.id;
    console.log("controller js is:", tripId);
    const newDestination = req.body;
    console.log("check dest:", newDestination);
    const trip = await tripService.addDestination(tripId, newDestination);
    if (trip.error) {
      res.status(500).json({ error: 'Failed to add destination' });
      return
    } else {
      console.log("Destination added successfully");
      res.status(201).json(trip);
      return
      
    }
    
  } catch (error) {
    console.error('Error adding destination:', error);
    res.status(500).json({ error: 'Failed to add destination' });
  }
};

