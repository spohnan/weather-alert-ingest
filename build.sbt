name := "weather-alert-ingest"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.5",
  "org.joda" % "joda-convert" % "1.7",
  "org.apache.spark" %% "spark-core" % "1.1.0",
  "com.stackmob" %% "newman" % "1.3.5",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)
