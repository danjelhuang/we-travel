const express = require('express');
const admin = require('firebase-admin');
const serviceAccount = require('../wetravel-service-account-key.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();
const app = express();

app.use(express.json());

// Routes
app.post('/api/data', async (req, res) => {
  try {
    const { message } = req.body;
    const docRef = await db.collection('data').add({ message });
    res.status(201).json({ id: docRef.id, message });
  } catch (error) {
    console.error('Error adding document', error);
    res.status(500).json({ error: 'Something went wrong' });
  }
});

app.get('/api/data', async (req, res) => {
  try {
    const snapshot = await db.collection('data').get();
    const data = [];
    snapshot.forEach(doc => {
      data.push({ id: doc.id, ...doc.data() });
    });
    res.json(data);
  } catch (error) {
    console.error('Error getting documents', error);
    res.status(500).json({ error: 'Something went wrong' });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});

