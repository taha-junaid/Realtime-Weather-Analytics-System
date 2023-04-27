package com.nyu.taha

import com.nyu.taha.models.{Flat, Location, WeatherData}
import io.circe.Json
import io.circe.parser.parse
import io.circe.syntax.EncoderOps


object Main extends App with SparkSessionWrapper {
  import spark.sqlContext.implicits._

  val dfStreaming = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "weather")
    .load()
    .selectExpr("CAST(value AS STRING)")
    .select("value").as[Flat]
    .map(WeatherData.unsafeFromFlat(_))

  dfStreaming.printSchema()

  dfStreaming
    .writeStream
    .outputMode("update")
    .format("console")
    .option("truncate", "false")
    .start()
    .awaitTermination()


}
