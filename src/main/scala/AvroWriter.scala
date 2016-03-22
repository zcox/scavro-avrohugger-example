package com.banno

import org.oedura.scavro.{AvroSerializeable, AvroMetadata}
import java.io.{OutputStream, ByteArrayOutputStream}
import org.apache.avro.specific.SpecificDatumWriter
import org.apache.avro.io.EncoderFactory

class AvroWriter[S <: AvroSerializeable] {
  def write(item: AvroSerializeable, output: OutputStream)(implicit m: AvroMetadata[S, S#J]): Unit = {
    val encoder = EncoderFactory.get().directBinaryEncoder(output, null)
    val writer = new SpecificDatumWriter[S#J](m.avroClass)
    writer.write(item.toAvro.asInstanceOf[S#J], encoder)
    encoder.flush()
  }

  def toBytes(item: AvroSerializeable)(implicit m: AvroMetadata[S, S#J]): Array[Byte] = {
    val output = new ByteArrayOutputStream
    write(item, output)
    output.close()
    output.toByteArray
  }
}

object AvroWriter {
  def apply[S <: AvroSerializeable] = new AvroWriter[S]
}
