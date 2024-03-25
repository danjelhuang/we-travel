const express = require('express');
const router = express.Router();
const tripController = require('../controller/controller');

// create a trip
router.post('/trips', tripController.createTrip);
// get a trip by id (access code)
router.get('/trips/:id', tripController.loadTrip);

// Update a trip based on a specific ID
router.patch('/update-trip/:id', tripController.updateTrip);

// add participant to an existing trip route
router.post('/add-participant-to-trip/:id', tripController.addParticipantToTrip)


// remove participant from existing trip route
router.post('/remove-participant-from-trip/:id', tripController.removeParticipantFromTrip)

module.exports = router;
