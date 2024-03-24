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


exports.updateTrip = async (req, res) => {
  const tripId = req.params.id;
  const updates = req.body;
  try {
    const result = await tripService.updateTrip(updates, tripId)
    res.status(200).json(result);
  } catch (error) {
    console.error('Error updating trip: ', error);
    res.status(500).json({ error: 'Failed to update the trip' });
  }
}