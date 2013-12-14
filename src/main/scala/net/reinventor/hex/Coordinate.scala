package net.reinventor.hex

sealed trait Coordinate {
  def cube: (Int, Int, Int)
  def axial: (Int, Int)
}

case class Cube(x: Int, y: Int, z: Int) extends Coordinate {
  val cube = (x, y, z)
  val axial = (x, z)

  override def equals(o: Any): Boolean = o match {
    case Axial(q2, r2) => x == q2 && z == r2
    case Cube(x2, y2, z2) => x == x2 && y == y2 && z == z2
    case _ => false
  }
}

case class Axial(q: Int, r: Int) extends Coordinate {
  val cube = (q, -q - r, r)
  val axial = (q, r)

  override def equals(o: Any): Boolean = o match {
    case Axial(q2, r2) => q == q2 && r == r2
    case c @ Cube(x2, y2, z2) => x2 == q && y2 == -q -r && z2 == r
    case _ => false
  }
}

object Coordinate {
}