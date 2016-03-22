package com.banno

import org.oedura.scavro.{AvroSerializeable, AvroMetadata}
import java.io.{InputStream, ByteArrayInputStream}
import org.apache.avro.specific.SpecificDatumReader
import org.apache.avro.io.DecoderFactory

abstract class AvroReader[S <: AvroSerializeable] {
  type J = S#J

  def read(input: InputStream)(implicit m: AvroMetadata[S, J]): S = {
    val decoder = DecoderFactory.get().directBinaryDecoder(input, null)
    val reader = new SpecificDatumReader[J](m.avroClass)
    m.fromAvro(reader.read(null.asInstanceOf[J], decoder))
  }

  def fromBytes(bytes: Array[Byte])(implicit m: AvroMetadata[S, J]): S = {
    val input = new ByteArrayInputStream(bytes)
    val s = read(input)
    input.close()
    s
  }
}

object AvroReader {
  def apply[S <: AvroSerializeable](implicit reader: org.oedura.scavro.AvroReader[S]) = new AvroReader[S] {
    override type J = S#J
  }
}
