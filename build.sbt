seq(sbtavro.SbtAvro.avroSettings : _*)

stringType in sbtavro.SbtAvro.avroConfig := "String"

version in sbtavro.SbtAvro.avroConfig := "1.8.0"

sbtavrohugger.SbtAvrohugger.scavroSettings

libraryDependencies ++= Seq(
  "org.oedura" %% "scavro" % "1.0.1",
  "org.specs2" %% "specs2-core" % "3.7.2" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")
