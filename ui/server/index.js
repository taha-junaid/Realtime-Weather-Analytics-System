const express = require('express');
const mongo = require('../mongo');

const app = express();

mongo.connect();

app.use((req, res, next) => {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
    res.setHeader('Access-Control-Allow-Credentials', true);
    next();
});

app.get('/weather', (req, res) => {
    if (!mongo.getIsConnected()) {
        return res.status(500);
    }
    mongo.getLatestWeather()
        .then(results => res.status(200).json(results))
        .catch(err => res.status(500).send(err));
});

app.listen(8080, () => {
    console.log('server is listening on port 8080');
});