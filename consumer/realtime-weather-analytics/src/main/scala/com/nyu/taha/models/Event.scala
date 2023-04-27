package com.nyu.taha.models

import java.sql.Timestamp

case class Event(weatherData: WeatherData, timestamp: Timestamp)
