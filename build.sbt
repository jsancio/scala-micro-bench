import Dependencies._

ThisBuild / scalaVersion     := "2.13.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "map-benchmark",
    libraryDependencies += scalaTest % Test
  )

/* To run benchmarks:
    jmh:run -t 1 -f 1 -wi 5 -i 5 .*Bench.*
 */
enablePlugins(JmhPlugin)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
