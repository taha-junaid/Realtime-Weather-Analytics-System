# realtime-weather-analytics

This sbt project was generated using [this](https://github.com/MrPowers/spark-sbt.g8) g8 template

*add project short description*

## Schema
```
root
 |-- weatherData: struct (nullable = true)
 |    |-- location: struct (nullable = true)
 |    |    |-- name: string (nullable = true)
 |    |    |-- region: string (nullable = true)
 |    |    |-- country: string (nullable = true)
 |    |    |-- lat: float (nullable = false)
 |    |    |-- lon: float (nullable = false)
 |    |    |-- tz_id: string (nullable = true)
 |    |    |-- localtime_epoch: float (nullable = false)
 |    |    |-- localtime: string (nullable = true)
 |    |-- current: struct (nullable = true)
 |    |    |-- last_updated_epoch: long (nullable = false)
 |    |    |-- last_updated: string (nullable = true)
 |    |    |-- temp_c: float (nullable = false)
 |    |    |-- temp_f: float (nullable = false)
 |    |    |-- is_day: integer (nullable = false)
 |    |    |-- condition: struct (nullable = true)
 |    |    |    |-- text: string (nullable = true)
 |    |    |    |-- icon: string (nullable = true)
 |    |    |    |-- code: integer (nullable = false)
 |    |    |-- wind_mph: float (nullable = false)
 |    |    |-- wind_kph: float (nullable = false)
 |    |    |-- wind_degree: float (nullable = false)
 |    |    |-- wind_dir: string (nullable = true)
 |    |    |-- pressure_mb: float (nullable = false)
 |    |    |-- pressure_in: float (nullable = false)
 |    |    |-- precip_mm: float (nullable = false)
 |    |    |-- precip_in: float (nullable = false)
 |    |    |-- humidity: float (nullable = false)
 |    |    |-- cloud: float (nullable = false)
 |    |    |-- feelslike_c: float (nullable = false)
 |    |    |-- vis_km: float (nullable = false)
 |    |    |-- vis_miles: float (nullable = false)
 |    |    |-- uv: float (nullable = false)
 |    |    |-- gust_mph: float (nullable = false)
 |    |    |-- gust_kph: float (nullable = false)
 |-- timestamp: timestamp (nullable = true)
```

### Example Output
```
-------------------------------------------
Batch: 2
-------------------------------------------
+------------------------------------------+------------------+------------------+
|window                                    |avg_temp_c        |avg_feelslike_c   |
+------------------------------------------+------------------+------------------+
|{2023-04-27 18:54:00, 2023-04-27 18:55:00}|13.300000190734863|12.100000381469727|
+------------------------------------------+------------------+------------------+
```