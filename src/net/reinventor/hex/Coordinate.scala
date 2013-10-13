trait Coordinate {
  def cube: (Int, Int, Int)
  def axial: (Int, Int)
}

object Coordinate {
  def Cube(x: Int, y: Int, z: Int): Coordinate = {
    new Coordinate {
      val cube = (x, y, z)
      val axial = (x, z)
    }
  }
  def Axial(q: Int, r: Int): Coordinate = {
    new Coordinate {
      val cube = (q, -q-r, r)
      val axial = (q, r)
    }
  }
}