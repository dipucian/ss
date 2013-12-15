package net.reinventor.hex

trait Region extends Set[Coordinate] {
  def translate(delta: Coordinate): Region
}

object Region {

  def apply(axial: (Int, Int)*): Region = {
    val cells = axial.map {case (q, r) => Axial(q, r)}
    new RegionImpl(cells.toSet)
  }

  def apply(row: Int, col: Int): Region = {
    val cells: Seq[Coordinate] = for {
      q <- 0 until col
      offset = q / 2
      r <- 0 until row
    } yield Axial(q, r - offset)

    new RegionImpl(cells.toSet)
  }

  def apply(n: Int): Region = Range(n)

  def Range(n: Int): Region = {
    val cells: Set[Coordinate] = for {
      x <- (-n to n).toSet[Int]
      i = math.max(-n, -x - n)
      j = math.min(n, -x + n)
      y <- i to j
      z = -x - y
    } yield Cube(x, y, z)
    new RegionImpl(cells)
  }

  private class RegionImpl(cells: Set[Coordinate]) extends Region {
    def contains(elem: Coordinate): Boolean = cells.contains(elem)

    def iterator: Iterator[Coordinate] = cells.iterator

    def +(elem: Coordinate): Region = new RegionImpl(cells + elem)

    def -(elem: Coordinate): Region = new RegionImpl(cells - elem)

    def translate(delta: Coordinate): Region = {
      val (dq, dr) = delta.axial
      val newCells: Set[Coordinate] = cells.map(c => {
        val (q, r) = c.axial
        Axial(q + dq, r + dr)
      })
      new RegionImpl(newCells)
    }
  }

}