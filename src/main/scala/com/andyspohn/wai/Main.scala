package com.andyspohn.wai

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

object Main extends App {
  val sc = new SparkContext("local", "Main")

  val files = sc.wholeTextFiles("/Users/andy/Desktop/reports/*.xml", 1)
  val entries: RDD[Alert] = files.flatMap { file =>
    val feed = scala.xml.XML.loadString(file._2)
    (feed \ "entry").map(entry => Alert.parseXml(entry))
  }

  println(entries.count())

}
