package net.reinventor.hex

trait Region extends Set[Coordinate]

object Region {
  import Coordinate._

  def Range(n: Int): Region = {
    val cells: Set[Coordinate] = for {
      x <- (-n to n).toSet[Int]
      i = math.max(-n, -x-n)
      j = math.min(n, -x+n)
      y <- i to j
      z = -x-y
    } yield Cube(x, y, z)
    new RegionImpl(cells)
  }

  private class RegionImpl(cells: Set[Coordinate]) extends Region {
    def contains(elem: Coordinate): Boolean = cells.contains(elem)
    def iterator: Iterator[Coordinate] = cells.iterator

    def +(elem: Coordinate): Region = new RegionImpl(cells + elem)
    def -(elem: Coordinate): Region = new RegionImpl(cells - elem)
  }
}