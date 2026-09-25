ThisBuild / scalaVersion := "3.9.0"

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.20",
    Compile / scalaJSLinkerConfig ~= { _.withOptimizer(true).withSemantics(_.optimized) }
  )

