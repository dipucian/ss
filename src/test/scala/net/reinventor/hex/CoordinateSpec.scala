package net.reinventor.hex

import org.scalatest._

class CoordinateSpec extends FlatSpec with Matchers {
  "Axial(0, 0)" should "equals Cube(0, 0, 0)" in {
  	assert(Axial(0, 0) == Cube(0, 0, 0))
  	assert(Cube(0, 0, 0) == Axial(0, 0))
  }

  "Axial(0, 0)" should "have the same hashCode as Cube(0, 0, 0)" in {
    Axial(0, 0).hashCode should equal(Cube(0, 0, 0).hashCode)
  }

  "Axial(2, -1) + Cube(1, 1, -2)" should "equals Axial(3, -3)" in {
    Axial(2, -1) + Cube(1, 1, -2) should equal (Axial(3, -3))
  }

  "Axial(2, -1) - Axial(1, 1)" should "equals Axial(1, -2)" in {
    Axial(2, -1) - Axial(1, 1) should equal (Axial(1, -2))
  }
}