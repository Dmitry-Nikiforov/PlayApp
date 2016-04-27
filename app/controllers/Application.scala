package controllers


import models.{Db, GameResult, PlayerResult, User}
import org.joda.time._
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import org.joda.time._
import play.api.data.format.Formats._

import scala.pickling.Defaults._
import scala.pickling.json._

class Application extends Controller {

  val userForm = Form(
    mapping(
      "username" -> nonEmptyText(5, 20),
      "password" -> nonEmptyText(5, 20),
      "isAdmin" -> ignored[Boolean](false)
    )(User.apply)(User.unapply) verifying("Неверное имя пользователя или пароль", user => user.checkOrReg)
  )

//  val gameForm = Form(
//    mapping(
//      "gameNum" -> number,
//      "tableNum" -> number,
//      "date" -> of[DateTime],
//      "players" -> Set[PlayerResult]
//    )(GameResult.apply)(GameResult.unapply)
//  )

  def stat = Action { implicit request =>
    request.session.get("user").map { user =>
      Ok(views.html.stat(user.unpickle[User]))
    }.getOrElse(Redirect(routes.Application.index()))
  }

  def newGame = Action { implicit request =>
    request.session.get("user").map { user =>
      user.unpickle[User].isTech match {
        //case false => Ok(views.html.newGame(gameForm))
        case true => Redirect(routes.Application.index())
      }
    }.getOrElse(Redirect(routes.Application.index()))
  }

  def index() = Action { implicit request =>
    request.session.get("user").map { user =>
      Ok(views.html.home("", user.unpickle[User]))
    }.getOrElse(Ok(views.html.index("", userForm)))
  }

  def doLogin = Action { implicit request =>
    userForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.index("Ошибка", formWithErrors)),
      user => {

        Ok(views.html.home("", user)).withSession("user" -> user.pickle.value)
      }
    )
  }

  def doUnlogin = Action { implicit request =>
    Redirect(routes.Application.index()).withNewSession
  }

//  def regGame = Action { implicit request =>
//    gameForm.bindFromRequest().fold(
//      formWithErrors => BadRequest(views.html.newGame(formWithErrors)),
//      game => {
//        Db.save(game)
//        Ok(game.toString)
//      }
//    )
//
//  }


}