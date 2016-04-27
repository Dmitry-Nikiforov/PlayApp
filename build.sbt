name := "PlayApp"

version := "1.0"

lazy val `playapp` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

// Resolver is needed only for SNAPSHOT versions
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test,
  "com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3",
  "org.postgresql" % "postgresql" % "9.4.1208.jre7",
  "org.sorm-framework" % "sorm" % "0.3.19",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value force(),
  "org.scala-lang.modules" %% "scala-pickling" % "0.10.1",
  play.sbt.Play.autoImport.cache)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

scalaVersion := "2.11.8"

dependencyOverrides += "org.scala-lang" % "scala-compiler" % scalaVersion.value