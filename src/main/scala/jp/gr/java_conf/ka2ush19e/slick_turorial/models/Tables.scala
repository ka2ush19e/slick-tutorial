package jp.gr.java_conf.ka2ush19e.slick_turorial.models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema = Coffees.schema ++ Suppliers.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Coffees
   *  @param cofName Database column cof_name SqlType(VARCHAR), PrimaryKey, Length(50,true)
   *  @param supId Database column sup_id SqlType(INT)
   *  @param price Database column price SqlType(DOUBLE)
   *  @param sales Database column sales SqlType(INT)
   *  @param total Database column total SqlType(INT) */
  case class CoffeesRow(cofName: String, supId: Int, price: Double, sales: Int, total: Int)
  /** GetResult implicit for fetching CoffeesRow objects using plain SQL queries */
  implicit def GetResultCoffeesRow(implicit e0: GR[String], e1: GR[Int], e2: GR[Double]): GR[CoffeesRow] = GR{
    prs => import prs._
    CoffeesRow.tupled((<<[String], <<[Int], <<[Double], <<[Int], <<[Int]))
  }
  /** Table description of table coffees. Objects of this class serve as prototypes for rows in queries. */
  class Coffees(_tableTag: Tag) extends Table[CoffeesRow](_tableTag, "coffees") {
    def * = (cofName, supId, price, sales, total) <> (CoffeesRow.tupled, CoffeesRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(cofName), Rep.Some(supId), Rep.Some(price), Rep.Some(sales), Rep.Some(total)).shaped.<>({r=>import r._; _1.map(_=> CoffeesRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column cof_name SqlType(VARCHAR), PrimaryKey, Length(50,true) */
    val cofName: Rep[String] = column[String]("cof_name", O.PrimaryKey, O.Length(50,varying=true))
    /** Database column sup_id SqlType(INT) */
    val supId: Rep[Int] = column[Int]("sup_id")
    /** Database column price SqlType(DOUBLE) */
    val price: Rep[Double] = column[Double]("price")
    /** Database column sales SqlType(INT) */
    val sales: Rep[Int] = column[Int]("sales")
    /** Database column total SqlType(INT) */
    val total: Rep[Int] = column[Int]("total")

    /** Foreign key referencing Suppliers (database name sup_fk) */
    lazy val suppliersFk = foreignKey("sup_fk", supId, Suppliers)(r => r.supId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Coffees */
  lazy val Coffees = new TableQuery(tag => new Coffees(tag))

  /** Entity class storing rows of table Suppliers
   *  @param supId Database column sup_id SqlType(INT), PrimaryKey
   *  @param supName Database column sup_name SqlType(VARCHAR), Length(50,true)
   *  @param street Database column street SqlType(VARCHAR), Length(50,true)
   *  @param city Database column city SqlType(VARCHAR), Length(50,true)
   *  @param state Database column state SqlType(VARCHAR), Length(2,true)
   *  @param zip Database column zip SqlType(VARCHAR), Length(5,true) */
  case class SuppliersRow(supId: Int, supName: String, street: String, city: String, state: String, zip: String)
  /** GetResult implicit for fetching SuppliersRow objects using plain SQL queries */
  implicit def GetResultSuppliersRow(implicit e0: GR[Int], e1: GR[String]): GR[SuppliersRow] = GR{
    prs => import prs._
    SuppliersRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String]))
  }
  /** Table description of table suppliers. Objects of this class serve as prototypes for rows in queries. */
  class Suppliers(_tableTag: Tag) extends Table[SuppliersRow](_tableTag, "suppliers") {
    def * = (supId, supName, street, city, state, zip) <> (SuppliersRow.tupled, SuppliersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(supId), Rep.Some(supName), Rep.Some(street), Rep.Some(city), Rep.Some(state), Rep.Some(zip)).shaped.<>({r=>import r._; _1.map(_=> SuppliersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column sup_id SqlType(INT), PrimaryKey */
    val supId: Rep[Int] = column[Int]("sup_id", O.PrimaryKey)
    /** Database column sup_name SqlType(VARCHAR), Length(50,true) */
    val supName: Rep[String] = column[String]("sup_name", O.Length(50,varying=true))
    /** Database column street SqlType(VARCHAR), Length(50,true) */
    val street: Rep[String] = column[String]("street", O.Length(50,varying=true))
    /** Database column city SqlType(VARCHAR), Length(50,true) */
    val city: Rep[String] = column[String]("city", O.Length(50,varying=true))
    /** Database column state SqlType(VARCHAR), Length(2,true) */
    val state: Rep[String] = column[String]("state", O.Length(2,varying=true))
    /** Database column zip SqlType(VARCHAR), Length(5,true) */
    val zip: Rep[String] = column[String]("zip", O.Length(5,varying=true))
  }
  /** Collection-like TableQuery object for table Suppliers */
  lazy val Suppliers = new TableQuery(tag => new Suppliers(tag))
}
