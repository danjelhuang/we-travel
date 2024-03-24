const TripService = require('../services/trip');

const tripService = new TripService();

exports.createTrip = async (req, res) => {
  try {
    const tripData = req.body;
    const trip = await tripService.createTrip(tripData);
    console.log("Trip created successfully");
    res.status(201).json(trip);
  } catch (error) {
    console.error('Error creating trip:', error);
    res.status(500).json({ error: 'Failed to create trip' });
  }
};
