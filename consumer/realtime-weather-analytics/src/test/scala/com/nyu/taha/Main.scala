package com.nyu.taha

object Main extends App with SparkSessionWrapper {

  val df = spark.createDataFrame(Seq(
    (1, false),
    (2, true),
    (3, false)
  )
  )
  println(df.show())


}
