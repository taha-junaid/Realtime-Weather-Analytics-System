package com.nyu.taha

import com.nyu.taha.models.{Event, Flat, Location, WeatherData}
import org.apache.spark.sql.functions.{avg, window}


object Main extends App with SparkSessionWrapper {
  import spark.sqlContext.implicits._

  val dfStreaming = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "weather")
    .load()
    .selectExpr("timestamp", "CAST(value AS STRING)")
    .select("value", "timestamp").as[Flat]
    .map(flat => Event(WeatherData.unsafeFromFlat(flat.value), flat.timestamp))
    .groupBy(window($"timestamp", "1 minute", "1 minute"))
    .agg(avg("weatherData.current.temp_c").as("avg_temp_c"), avg("weatherData.current.feelslike_c").as("avg_feelslike_c"))

  dfStreaming.printSchema()

  dfStreaming
    .writeStream
    .outputMode("update")
    .format("console")
    .option("truncate", "false")
    .start()
    .awaitTermination()


}
