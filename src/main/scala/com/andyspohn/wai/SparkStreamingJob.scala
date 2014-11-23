package com.andyspohn.wai

import com.stackmob.newman.response.HttpResponseCode.Ok
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Minutes, StreamingContext}

object SparkStreamingJob {

  // Process the nationwide weather alerts, the feed is updated every hour
  val weatherFeedUrlStr = "http://alerts.weather.gov/cap/us.php?x=0"

  def main() {
    val sparkConf = new SparkConf().setAppName("ProcessWeatherFeed")
    val ssc = new StreamingContext(sparkConf, Minutes(60))
    val processor = new FeedProcessor()
    val feed = processor.fetch(weatherFeedUrlStr)
    if(feed._1 != Ok) {
      System.err.println("Error code ${feed._1} received while fetching feed: ${weatherFeedUrlStr}")
    }
    ssc.start()
    ssc.awaitTermination()
  }

}
