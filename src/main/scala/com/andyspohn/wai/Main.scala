package com.andyspohn.wai

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.joda.time.{Interval, DateTime}

object Main extends App {
  val sc = new SparkContext("local", "Main")

  val files = sc.wholeTextFiles("/Users/andy/Desktop/reports/*.xml", 1)
  val entries: RDD[Alert] = files.flatMap { file =>
    val feed = scala.xml.XML.loadString(file._2)
    (feed \ "entry").map(entry => Alert.parseXml(entry))
  }

  println(entries.count())

}

object Alert {
  def parseXml(e: scala.xml.Node): Alert = new Alert(
    getText(e, "id"),
    getDate(e, "updated"),
    getDate(e, "published"),
    getInterval(e, "effective", "expires"),
    (e \ "author" \ "name").text.trim(),
    getText(e, "title")
  )

  def getDate(event: scala.xml.Node, tagName: String): DateTime =
    new DateTime(getText(event, tagName))

  def getInterval(event: scala.xml.Node, startTag: String, stopTag: String): Interval =
    new Interval(getDate(event, startTag), getDate(event, stopTag))

  def getText(event: scala.xml.Node, tagName: String): String =
    (event \ tagName).text.trim()
}

class Alert(
  id: String,
  updated: DateTime,
  published: DateTime,
  duration: Interval,
  author: String,
  title: String)

//<entry>
//  <id>http://alerts.weather.gov/cap/wwacapget.php?x=AK1251785120E4.DenseFogAdvisory.1251785E31D0AK.AJKNPWAJK.5825ee17abc40a3f2af54d86ae069d3a</id>
//  <updated>2014-11-20T14:37:00-09:00</updated>
//  <published>2014-11-20T14:37:00-09:00</published>
//  <author>
//    <name>w-nws.webmaster@noaa.gov</name>
//  </author>
//  <title>Dense Fog Advisory issued November 20 at 2:37PM AKST until November 21 at 12:00AM AKST by NWS</title>
//  <link href='http://alerts.weather.gov/cap/wwacapget.php?x=AK1251785120E4.DenseFogAdvisory.1251785E31D0AK.AJKNPWAJK.5825ee17abc40a3f2af54d86ae069d3a'/>
//  <summary>...DENSE FOG ADVISORY IN EFFECT UNTIL MIDNIGHT AKST TONIGHT... THE NATIONAL WEATHER SERVICE IN JUNEAU HAS ISSUED A DENSE FOG ADVISORY...WHICH IS IN EFFECT UNTIL MIDNIGHT AKST TONIGHT. * VISIBILITY...ONE QUARTER MILE OR LESS * TIMING...FOG IS STARTING TO THICKEN UP AGAIN THIS AFTERNOON. GUSTAVUS IS ALREADY DOWN TO A HALF MILE AGAIN AND WHILE</summary>
//  <cap:event>Dense Fog Advisory</cap:event>
//  <cap:effective>2014-11-20T14:37:00-09:00</cap:effective>
//  <cap:expires>2014-11-21T00:00:00-09:00</cap:expires>
//  <cap:status>Actual</cap:status>
//  <cap:msgType>Alert</cap:msgType>
//  <cap:category>Met</cap:category>
//  <cap:urgency>Expected</cap:urgency>
//  <cap:severity>Minor</cap:severity>
//  <cap:certainty>Likely</cap:certainty>
//  <cap:areaDesc>Glacier Bay; Juneau Borough and Northern Admiralty Island</cap:areaDesc>
//  <cap:polygon></cap:polygon>
//  <cap:geocode>
//    <valueName>FIPS6</valueName>
//    <value>002105 002110 002195</value>
//    <valueName>UGC</valueName>
//    <value>AKZ020 AKZ025</value>
//  </cap:geocode>
//  <cap:parameter>
//    <valueName>VTEC</valueName>
//    <value>/X.NEW.PAJK.FG.Y.0035.141120T2337Z-141121T0900Z/</value>
//  </cap:parameter>
//</entry>
