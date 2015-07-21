lazy val root = (project in file("."))
  .settings(
    name := "slick-tutorial",
    version := "1.0",
    scalaVersion := "2.11.7",
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.0.0",
      "com.typesafe.slick" %% "slick-codegen" % "3.0.0",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.h2database" % "h2" % "1.3.175",
      "mysql" % "mysql-connector-java" % "5.1.36",
      "org.scalatest" %% "scalatest" % "2.2.4" % Test
    )
  )

lazy val slickGenerate = taskKey[Seq[File]]("slick code generation")
slickGenerate := {
  val slickDriver = "slick.driver.MySQLDriver"
  val jdbcDriver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost/mydb"
  val outputDir = (sourceManaged in Compile).value.getPath
  val pkg = "jp.gr.java_conf.ka2ush19e.slick_turorial.models"
  val user = "mydbuser"
  val password = "mydbuser"
  (runner in Compile).value.run(
    "slick.codegen.SourceCodeGenerator",
    (dependencyClasspath in Compile).value.files,
    Array(slickDriver, jdbcDriver, url, outputDir, pkg, user, password),
    streams.value.log
  )
  val fname = outputDir + "/demo/Tables.scala"
  Seq(file(fname))
}
