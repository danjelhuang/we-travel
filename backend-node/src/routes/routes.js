const express = require('express');
const router = express.Router();
const tripController = require('../controller/controller');

// create a trip
router.post('/trips', tripController.createTrip);

// Update a trip based on a specific ID
router.patch('/trips/:id', tripController.updateTrip);

module.exports = router;
