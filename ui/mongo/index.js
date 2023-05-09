const { MongoClient } = require("mongodb");

const uri = "mongodb://localhost:27017";

const client = new MongoClient(uri);
let isConnected = false;

function getIsConnected() {
  return isConnected;
}

function connect() {
  client.connect()
    .then(() => isConnected = true)
    .catch((err) => {
      console.error(err);
      process.exit(1);
    });
}


function getLatestWeather() {
  const collection = client.db('mydb').collection('weather');
  const date = new Date().setHours(new Date().getHours() - 2);
  
  return collection.find({
    'window.end': { $gte: new Date(date) }
  }).toArray();
}


module.exports = {
  connect,
  getIsConnected,
  getLatestWeather,
};