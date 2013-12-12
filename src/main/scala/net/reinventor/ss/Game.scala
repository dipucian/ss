package net.reinventor.ss

import net.reinventor.hex.{Region, Coordinate}

trait Game {
  def map: Set[Coordinate]
}

class GameImpl extends Game {
  def map = Region.Range(4)
}