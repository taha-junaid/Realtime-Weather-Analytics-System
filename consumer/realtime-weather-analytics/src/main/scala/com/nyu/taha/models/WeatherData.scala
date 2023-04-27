package com.nyu.taha.models

import io.circe.{Decoder, Encoder, Json}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.EncoderOps
import io.circe.parser.parse

// TODO - serialize the rest of the weather data json
// rather than just location.
// {"location":{"name":"Brooklyn","region":"New York","country":"USA","lat":40.7,"lon":-73.99,"tz_id":"America/New_York","localtime_epoch":1682611133,"localtime":"2023-04-27 11:58"},"current":{"last_updated_epoch":1682610300,"last_updated":"2023-04-27 11:45","temp_c":12.2,"temp_f":54.0,"is_day":1,"condition":{"text":"Overcast","icon":"//cdn.weatherapi.com/weather/64x64/day/122.png","code":1009},"wind_mph":2.2,"wind_kph":3.6,"wind_degree":10,"wind_dir":"N","pressure_mb":1024.0,"pressure_in":30.25,"precip_mm":0.1,"precip_in":0.0,"humidity":69,"cloud":100,"feelslike_c":11.5,"feelslike_f":52.7,"vis_km":16.0,"vis_miles":9.0,"uv":3.0,"gust_mph":6.3,"gust_kph":10.1}}
case class WeatherData(location: Location, current: Current)

object WeatherData {

  implicit val encoder: Encoder[WeatherData] = deriveEncoder[WeatherData]
  implicit val decoder: Decoder[WeatherData] = deriveDecoder[WeatherData]

  def unsafeFromFlat(flat: Flat): WeatherData = {
    // flat.value is double-escaped json
    // parse it twice to actually get valid json
    val maybeWeatherData = for {
      json <- parse(flat.value)
      first <- json.as[String]
      second <- parse(first)
      weatherData <- second.as[WeatherData]
    } yield weatherData

    maybeWeatherData.toTry.get

  }

}
