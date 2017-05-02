name := "pokemon_api"

version := "1.0-SNAPSHOT"

lazy val `pokemon_api` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.amazonaws" % "aws-java-sdk" % "1.11.46",
  "com.lightbend.akka" %% "akka-stream-alpakka-sqs" % "0.7",
  "com.lightbend.akka" %% "akka-stream-alpakka-s3" % "0.7"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
