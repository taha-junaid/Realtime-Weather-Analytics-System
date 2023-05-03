package com.nyu.taha

import com.nyu.taha.models.{Event, Flat, Location, WeatherData}
import org.apache.spark.sql.functions.{avg, window}
import org.apache.spark.sql.streaming.OutputMode


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

  val windowedDfStreaming = dfStreaming
    .withWatermark("timestamp", "5 minutes")
    .groupBy(window($"timestamp", "5 minute", "5 minute"))
    .agg(avg("weatherData.current.temp_c").as("avg_temp_c"), avg("weatherData.current.feelslike_c").as("avg_feelslike_c"))

  dfStreaming.printSchema()
  windowedDfStreaming.printSchema()

  // Note - the write configuration is extremely important for
  // time-series data. see https://www.mongodb.com/docs/spark-connector/current/configuration/write/
  windowedDfStreaming
    .writeStream
    .outputMode("complete")
    .format("mongodb")
    .option("checkpointLocation", "/tmp/checkpointDir")
    .option("forceDeleteTempCheckpointLocation", "true")
    .option("upsertDocument", "false")
    .option("spark.mongodb.connection.uri", "<YOUR MONGO URI HERE>")
    .option("spark.mongodb.database", "mydb")
    .option("spark.mongodb.collection", "weather")
    .outputMode("append")
    .start()
    .awaitTermination()


}
