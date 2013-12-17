package net.reinventor.hex

sealed trait Coordinate {
  def cube: (Int, Int, Int)
  def axial: (Int, Int)

  override def equals(o: Any): Boolean = o match {
    case c: Coordinate => c.axial == axial
    case _ => false
  }

  override def hashCode(): Int = axial.hashCode
}

case class Cube(x: Int, y: Int, z: Int) extends Coordinate {
  val cube = (x, y, z)
  val axial = (x, z)
}

case class Axial(q: Int, r: Int) extends Coordinate {
  val cube = (q, -q - r, r)
  val axial = (q, r)
}

object Coordinate {
}