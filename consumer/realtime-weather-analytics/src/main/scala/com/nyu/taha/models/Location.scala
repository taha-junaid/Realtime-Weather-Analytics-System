package com.nyu.taha.models
import io.circe._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}


case class Location(
                     name: String,
                     region: String,
                     country: String,
                     lat: Float,
                     lon: Float,
                     tz_id: String,
                     localtime_epoch: Float,
                     localtime: String
                   )

object Location {
  implicit val encoder: Encoder[Location] = deriveEncoder[Location]
  implicit val decoder: Decoder[Location] = deriveDecoder[Location]
}
