package models


import sorm._

@SerialVersionUID(100L)
case class User(username: String, password: String, isTech: Boolean = false) extends Serializable
{


  def checkOrReg: Boolean = {
    //if (User.users.contains(username)) User.users(username).password == password
    val u = Db.query[User].whereEqual("username", username).fetchOne()
    u.map(user => user.password == password).getOrElse(saveToDb)
  }


  def saveToDb:Boolean = {
    Db.save (this)
    true
  }

}

object User {

}

object Db extends Instance(
  entities = Set(Entity[User]()),
  url = "jdbc:postgresql://localhost/testDB",
  user = "sid",
  password = "12345",
  initMode = InitMode.Create
)


