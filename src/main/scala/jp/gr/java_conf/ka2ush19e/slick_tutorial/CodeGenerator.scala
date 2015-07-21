package jp.gr.java_conf.ka2ush19e.slick_tutorial

import slick.codegen.SourceCodeGenerator

object CodeGenerator {
  def main(args: Array[String]) {
    SourceCodeGenerator.main(
      Array(
        "slick.driver.MySQLDriver",
        "com.mysql.jdbc.Driver",
        "jdbc:mysql://localhost/mydb",
        "src/main/scala/",
        "jp.gr.java_conf.ka2ush19e.slick_turorial.models",
        "mydbuser",
        "mydbuser"
      )
    )
  }
}
