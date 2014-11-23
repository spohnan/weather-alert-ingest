name := "weather-alert-ingest"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.1.0"

libraryDependencies += "com.stackmob" %% "newman" % "1.3.5"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.2.1" % "test"