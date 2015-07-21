package jp.gr.java_conf.ka2ush19e.slick_tutorial

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

import slick.driver.H2Driver.api._

object HelloSlick {
  def main(args: Array[String]) {
    val db = Database.forConfig("h2mem1")
    try {
      val suppliers = TableQuery[Suppliers]
      val coffees = TableQuery[Coffees]

      val setup = DBIO.seq(
        (suppliers.schema ++ coffees.schema).create,

        suppliers +=(101, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199"),
        suppliers +=(49, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460"),
        suppliers +=(150, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966"),

        coffees ++= Seq(
          ("Colombian", 101, 7.99, 0, 0),
          ("French_Roast", 49, 8.99, 0, 0),
          ("Espresso", 150, 9.99, 0, 0),
          ("Colombian_Decaf", 101, 8.99, 0, 0),
          ("French_Roast_Decaf", 49, 9.99, 0, 0)
        )
      )

      val f = db.run(setup).flatMap { _ =>
        println("Coffees:")
        db.run(coffees.result).map(_.foreach { case (name, supID, price, sales, total) =>
          println(f"  $name%-20s $supID%5d $price%4.2f $sales%3d $total%3d")
        })
      }.flatMap { _ =>
        println()
        println("Coffees:")
        val q1 = for (c <- coffees)
          yield LiteralColumn("  ") ++ c.name ++ "\t" ++ c.supID.asColumnOf[String] ++
            "\t" ++ c.price.asColumnOf[String] ++ "\t" ++ c.sales.asColumnOf[String] ++
            "\t" ++ c.total.asColumnOf[String]
        db.stream(q1.result).foreach(println)
      }.flatMap { _ =>
        println()
        println("Coffee and supplier:")
        val q2 = for {
          c <- coffees if c.price < 9.0
          s <- suppliers if s.id === c.supID
        } yield (c.name, s.name)
        db.stream(q2.result).foreach { case (cname, sname) =>
          println(f"  $cname%-20s $sname")
        }
      }.flatMap { _ =>
        println()
        println("Coffee and supplier:")
        val q3 = for {
          c <- coffees if c.price < 9.0
          s <- c.supplier
        } yield (c.name, s.name)
        db.stream(q3.result).foreach { case (cname, sname) =>
          println(f"  $cname%-20s $sname")
        }
      }

      Await.result(f, Duration.Inf)

      {
        println()
        println("Materialized:")
        val q = for (c <- coffees) yield c.name
        val f = db.run(q.result)
        Await.result(f, Duration.Inf).foreach { case name =>
          println(s"  $name")
        }
      }

      {
        println()
        println("Streaming:")
        val q = for (c <- coffees) yield c.name
        val p = db.stream(q.result)
        p.foreach { case name =>
          println(s"  $name")
        }
        Thread.sleep(1000)
      }

      {
        println()
        println("Expressions:")
        val q = coffees.map(_.name).map(_.toUpperCase)
        val f = db.run(q.result)
        Await.result(f, Duration.Inf).foreach { case name =>
          println(s"  $name")
        }
      }

      {
        println()
        println("Sorting and Filtering:")
        val q1 = coffees.filter(_.supID === 101).map(_.name)
        val f = db.run(q1.result)
        Await.result(f, Duration.Inf).foreach { case name =>
          println(s"  $name")
        }
      }

      {
        println()
        println("Join:")
        val q = coffees.join(suppliers).on(_.supID === _.id).map { case (c, s) =>
          (c.name, s.name)
        }
        val f = db.run(q.result)
        Await.result(f, Duration.Inf).foreach { case (cname, sname) =>
          println(f"  $cname%-20s $sname")
        }
      }

      {
        println()
        println("Inserting:")
        val q = DBIO.seq(
          coffees += ("Colombian", 101, 7.99, 0, 0),
          coffees ++= Seq(
            ("French_Roast", 49, 8.99, 0, 0),
            ("Espresso",    150, 9.99, 0, 0)
          )
        )
        println(coffees.insertStatement)
      }

      {
        println()
        println("Deleting:")
        val q = coffees.filter(_.supID === 15)
        println(q.delete.statements.head)
        val count = db.run(q.delete)
        count.onSuccess {case c =>
          println(c)
        }
      }

    } finally {
      db.close()
    }
  }
}
