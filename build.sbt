ThisBuild / scalaVersion := "3.9.0"

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    Compile / scalaJSLinkerConfig ~= { _.withOptimizer(true).withSemantics(_.optimized) }
  )

