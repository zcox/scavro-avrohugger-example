Details
 * Write an Avro schema file by hand (e.g. Pageview.avsc)
 * sbt-avro generates an Avro Java class from that Avro schema, which implements SpecificRecord
 * sbt-avrohugger generates a Scavro Scala case class, with conversions to/from the Java class
 * Scavro's AvroWriter and AvroReader are re-written to work with single objects instead of lists/files of them

What do you end up with?
 * Write Avro schema files by hand, commit them to version control, etc.
 * Scala case classes are generated from the Avro schemas, with immutable val fields
 * Create instances of these case classes and use them in your Scala programs
 * Instances of these case classes are serializable and deserializable by Avro, because of the conversions to/from the generated Avro Java classes

Example of serializing and deserializing the generated case class:

```scala
val p = Pageview(eventId, userId, url, timestamp)
val bytes = AvroWriter[Pageview].toBytes(p)
val deserialized = AvroReader[Pageview].fromBytes(bytes)
p == deserialized //true
```

Note that currently sbt-avrohugger puts an incorrect import in the generated Scala file. It uses `com.oysterbooks.scavro` but you have to manually change this to `org.oedura.scavro`.
I plan to submit an issue to sbt-avrohugger to fix this.
