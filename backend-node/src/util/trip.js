const db = require('../database/database');

async function generateUniqueTripCode() {
    let tripCode = generateTripCode();
    while (await doesTripCodeExist(tripCode)) {
        tripCode = generateTripCode();
    }
    return tripCode;
}

function generateTripCode() {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let code = '';
    for (let i = 0; i < 5; i++) {
        code += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return code;
}

async function doesTripCodeExist(tripCode) {
    const collectionRef = db.collection('trips');

    try {
        const docSnapshot = await collectionRef.doc(tripCode).get();
        return docSnapshot.exists;
    } catch (error) {
        console.error('Error checking trip code existence:', error);
        return false;
    }
}

module.exports = { generateUniqueTripCode };