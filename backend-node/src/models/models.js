class Trip {
  constructor({ id, name, city, coinsPerPerson, adminUser, listOfParticipants, phase, destinations }) {
    this.id = id;
    this.name = name;
    this.city = city;
    this.coinsPerPerson = coinsPerPerson;
    this.adminUser = adminUser;
    this.listOfParticipants = listOfParticipants;
    this.phase = phase;
    this.destinations = destinations;
  }
}

class User {
  constructor({ id, tripIds }) {
    this.id = id;
    this.tripIds = tripIds;
  }
}

module.exports = Trip, User;
