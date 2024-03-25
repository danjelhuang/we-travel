const { generateUniqueTripCode } = require('../util/trip')

const TripService = require('../services/trip');
const tripService = new TripService();

exports.createTrip = async (req, res) => {
  try {
    const tripData = req.body;
    tripData.code = await generateUniqueTripCode();
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
};