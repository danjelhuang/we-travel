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

module.exports = Trip;
