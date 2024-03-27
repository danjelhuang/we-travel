const express = require('express');
const router = express.Router();
const tripController = require('../controller/controller');

// create a trip
router.post('/trips', tripController.createTrip);
router.get('/trips/:id', tripController.loadTrip);
router.post('/trips/:id/destinationsList', tripController.addDestination);

module.exports = router;
