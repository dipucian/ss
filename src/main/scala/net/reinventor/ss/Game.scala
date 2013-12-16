package net.reinventor.ss

import net.reinventor.hex._

trait Game {
  def map: Set[Coordinate]
  def characters: List[Character]

  def step(): Unit
}

class GameImpl extends Game {
  def map = Region.Range(4)

  val characters: List[Character] = List(new Character)
  def step(): Unit = {
    println("step")
    characters.foreach(c => c.controller.action match {
      case Move(q, r) => c.pos = Axial(c.pos.axial._1 + q, c.pos.axial._2 + r)
    })
  }
}

class Character {
  val controller: CharacterController = new CharacterController {
    val action = Move(0, -1)
  }

  var pos: Coordinate = Axial(0, 4)
}

trait CharacterController {
  def action: Action
}

sealed trait Action {}
case class Move(q: Int, r: Int) extends Action