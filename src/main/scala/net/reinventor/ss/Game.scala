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
case class BulletInfo(speed: Float, bearing: Float, lifeSpan: Int) extends AttackInfo

class Bullet(val owner: Player, info: BulletInfo) {
  var position: Coordinate = Axial(0, 0)
}