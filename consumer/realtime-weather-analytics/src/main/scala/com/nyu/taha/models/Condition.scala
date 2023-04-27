package com.nyu.taha.models

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class Condition(
                    text: String,
                    icon: String,
                    code: Int
                    )

object Condition {
  implicit val decoder: Decoder[Condition] = deriveDecoder[Condition]
  implicit val encoder: Encoder[Condition] = deriveEncoder[Condition]
}
