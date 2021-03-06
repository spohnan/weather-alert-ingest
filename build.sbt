name := "weather-alert-ingest"

version := "1.0"

scalaVersion := "2.10.2"

resolvers += "Job Server Bintray" at "http://dl.bintray.com/spark-jobserver/maven"

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "2.5",
  "org.joda" % "joda-convert" % "1.7",
  "spark.jobserver" % "job-server-api" % "0.4.0" % "provided",
  "org.apache.spark" %% "spark-core" % "1.0.2" % "provided",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

autoScalaLibrary := false

// See https://github.com/sbt/sbt-assembly for more options
assemblyJarName := "weather-alert-ingest.jar"

