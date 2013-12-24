package net.reinventor.ss

import net.reinventor.hex._

trait Game {
  def map: Set[Coordinate]
  def characters: List[CharacterState]

  def step(): Unit

  def addCharacter(c: Character): Unit
}

class GameImpl extends Game {
  def map = Region.Range(4)

  //val characters: List[Character] = List(new Character)
  def step(): Unit = {

    /*characters.foreach(c => c.controller.action match {
      case Move(q, r) => c.pos = Axial(c.pos.axial._1 + q, c.pos.axial._2 + r)
    })*/
  }

  def addCharacter(c: Character): Unit = {
    characters = c :: characters
  }

  var characters: List[Character] = List.empty
}

class Character(pos: Coordinate) extends CharacterState {
  var movementPoints: Int = 0
  var position: Coordinate = pos
}

trait CharacterState {
  def movementPoints: Int
  def position: Coordinate
}

trait CharacterController {
  def action: Action
}

sealed trait Action {}
case class Move(q: Int, r: Int) extends Action