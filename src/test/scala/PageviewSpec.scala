package com.banno.model

import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import java.util.UUID
import com.banno.{AvroWriter, AvroReader}

object PageviewSpec extends Specification {
  "avrohugger and scavro" should {
    "do stuff right" in new context {
      val p1 = Pageview(eventId, userId, url, timestamp)
      val bytes = AvroWriter[Pageview].toBytes(p1)
      val p1Deserialized = AvroReader[Pageview].fromBytes(bytes)
      p1Deserialized must_== p1
    }
  }

  trait context extends Scope {
    def newUuid = UUID.randomUUID.toString
    val eventId = newUuid
    val userId = newUuid
    val url = "http://site.com"
    val timestamp = System.currentTimeMillis
  }
}
