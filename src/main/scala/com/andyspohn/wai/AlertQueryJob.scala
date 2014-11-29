package com.andyspohn.wai

import com.typesafe.config.Config
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import spark.jobserver._

import scala.util.Try

object AlertQueryJob extends SparkJob with NamedRddSupport {

  val QUERY_CONFIG_PARAM = "query"

  override def runJob(sc:SparkContext, config: Config): Any = {
    val alerts: RDD[Alert] = this.namedRdds.get[Alert](config.getString(AlertLoadingJob.RDD_NAME_CONFIG_PARAM)).get
    alerts.count()
  }

  override def validate(sc:SparkContext, config: Config): SparkJobValidation = {
    Try(config.getString(QUERY_CONFIG_PARAM))
      .map(x => SparkJobValid)
      .getOrElse(SparkJobInvalid("No $QUERY_CONFIG_PARAM config param"))

    Try(config.getString(AlertLoadingJob.RDD_NAME_CONFIG_PARAM))
      .map(x => SparkJobValid)
      .getOrElse(SparkJobInvalid("No $AlertLoadingJob.RDD_NAME_CONFIG_PARAM config param"))
  }

}
