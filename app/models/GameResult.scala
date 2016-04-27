package models

import org.joda.time._

import play.api.data.format.Formats._

case class GameResult(gameNum:Int ,tableNum: Int, date: DateTime,players: Vector[PlayerResult]){


}
case class PlayerResult(number: Int,role:String,username:String,extraPoint: Float){
}

object GameResult{


}