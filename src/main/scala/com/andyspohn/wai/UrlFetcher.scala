package com.andyspohn.wai

import java.net.URL

import com.stackmob.newman.caching.InMemoryHttpResponseCacher
import com.stackmob.newman.dsl._
import com.stackmob.newman.response.HttpResponseCode
import com.stackmob.newman.{ApacheHttpClient, ETagAwareHttpClient}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, _}

trait UrlFetcher {

  val DefaultSecondsToWaitTimeout = 3

  //TODO: Allow more configuration options
  val cache = new InMemoryHttpResponseCacher(10, 1, Duration(2, HOURS), Duration(90, MINUTES))

  def fetch(urlStr: String, secondsToWait: Int = DefaultSecondsToWaitTimeout): (HttpResponseCode, String) = {
    implicit val eTagClient = new ETagAwareHttpClient(new ApacheHttpClient, cache)
    val url = new URL(urlStr)
    val response = Await.result(GET(url).apply, Duration(secondsToWait, SECONDS))
    (response.code, response.bodyString)
  }

}
