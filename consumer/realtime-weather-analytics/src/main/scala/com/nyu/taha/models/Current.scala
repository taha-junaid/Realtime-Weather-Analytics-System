package com.nyu.taha.models

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Current(
                    last_updated_epoch: Long,
                    last_updated: String,
                    temp_c: Float,
                    temp_f: Float,
                    is_day: Int,
                    condition: Condition,
                    wind_mph: Float,
                    wind_kph: Float,
                    wind_degree: Float,
                    wind_dir: String,
                    pressure_mb: Float,
                    pressure_in: Float,
                    precip_mm: Float,
                    precip_in: Float,
                    humidity: Float,
                    cloud: Float,
                    feelslike_c: Float,
                    vis_km: Float,
                    vis_miles: Float,
                    uv: Float,
                    gust_mph: Float,
                    gust_kph: Float
                  )

object Current {
  implicit val decoder: Decoder[Current] = deriveDecoder[Current]
  implicit val encoder: Encoder[Current] = deriveEncoder[Current]
}
