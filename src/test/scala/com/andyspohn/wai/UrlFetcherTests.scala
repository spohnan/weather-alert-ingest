package com.andyspohn.wai

import com.stackmob.newman.response.HttpResponseCode._
import org.scalatest.FunSuite

class UrlFetcherTests extends FunSuite {

  test("not a great test, should not depend on an Internet site") {
    new {} with UrlFetcher {
      val retVal = fetch("http://www.google.com")
      assert(retVal._1 == Ok)
      assert(retVal._2.length > 0)
    }
  }

}
