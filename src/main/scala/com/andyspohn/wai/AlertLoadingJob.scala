package com.andyspohn.wai

import com.typesafe.config.{ConfigFactory, Config}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import spark.jobserver._

import scala.util.Try

object AlertLoadingJob extends SparkJob with NamedRddSupport {

  val RDD_NAME_CONFIG_PARAM = "rdd.name"

  def main(args: Array[String]) {
    val sc = new SparkContext("local[*]", "AlertLoadingJob")
    val config = ConfigFactory.parseString("")
    val results = runJob(sc, config)
    println("Result is " + results)
  }

  override def runJob(sc: SparkContext, config: Config): Any = {
    //TODO: Fix local directory loading
    val files = sc.wholeTextFiles("/Users/andy/Desktop/reports/*.xml", 1)
    val entries: RDD[Alert] = files.flatMap { file =>
      val feed = scala.xml.XML.loadString(file._2)
      (feed \ "entry").map(entry => Alert.parseXml(entry))
    }
    this.namedRdds.update(config.getString(RDD_NAME_CONFIG_PARAM), entries)
    entries.count()
  }

  override def validate(sc:SparkContext, config: Config): SparkJobValidation = {
    Try(config.getString(RDD_NAME_CONFIG_PARAM))
      .map(x => SparkJobValid)
      .getOrElse(SparkJobInvalid("No $RDD_NAME_CONFIG_PARAM config param"))
  }

}
