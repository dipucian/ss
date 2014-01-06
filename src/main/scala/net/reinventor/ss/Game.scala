package net.reinventor.ss

import net.reinventor.hex._

trait Game extends GameState {
  def step(): Unit
  def addCharacter(c: Character): Unit
}

trait GameState {
  def map: Set[Coordinate]
  def characters: List[CharacterState]
  def bullets: List[Bullet]
}

class GameImpl extends Game {
  def map = Region.Range(4)

  def step(): Unit = {
    bullets.foreach { bullet =>
      bullet.move()
    }

    characters.foreach { character =>
      character.movementPoints += 1

      if (character.movementPoints == 10) {
        character.movementPoints = 0

        character.ai.nextAction(character, this) match {
          case Move(q, r) => character.position += Axial(q, r)
          case Attack(info: BulletInfo) => bullets ::= new Bullet(character.owner, info)
        }
      }
    }
  }

  def addCharacter(c: Character): Unit = {
    characters = c :: characters
  }

  var characters: List[Character] = List.empty
  var bullets: List[Bullet] = List.empty
}

class Character(val owner: Player, var ai: AI) extends CharacterState {
  var movementPoints: Int = 0
  var position: Coordinate = owner.base
}

trait CharacterState {
  def owner: Player
  def movementPoints: Int
  def position: Coordinate
}

trait Player {
  def base: Coordinate
}

trait AI {
  def nextAction(selfState: CharacterState, gameState: GameState): Action
}

sealed trait Action
case class Move(q: Int, r: Int) extends Action
case class Attack(info: AttackInfo) extends Action

sealed trait AttackInfo
case class BulletInfo(from: (Double, Double), speed: Double, bearing: Double, lifeSpan: Int) extends AttackInfo

class Bullet(val owner: Player, info: BulletInfo) {
  import scala.math._

  var moveCount = 0
  val (dx, dy) = (sin(info.bearing), cos(info.bearing))
  var position: (Double, Double) = (0, 0)

  def move(): Unit = {
    moveCount += 1
    val r = moveCount * info.speed
    position = (info.from._1 + dx * r, info.from._2 + dy * r)
  }
}