package net.reinventor.hex

import org.scalatest._

class CoordinateSpec extends FlatSpec with Matchers {
  "Axial(0, 0)" should "equals Cube(0, 0, 0)" in {
  	assert(Axial(0, 0) == Cube(0, 0, 0))
  	assert(Cube(0, 0, 0) == Axial(0, 0))
  }

/*
  "Region((0, 0))" should "contains Cube(0, 0, 0)" in {
    val r = Region((0, 0))
    assert(r contains Cube(0, 0, 0))
  }
  */
}