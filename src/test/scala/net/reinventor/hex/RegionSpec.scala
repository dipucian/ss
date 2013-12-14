package net.reinventor.hex

import org.scalatest._

class RegionSpec extends FlatSpec with Matchers {
  "Region((0, 0))" should "contains Axial(0, 0)" in {
    val r = Region((0, 0))
    assert(r contains Axial(0, 0))
  }

/*
  "Region((0, 0))" should "contains Cube(0, 0, 0)" in {
    val r = Region((0, 0))
    assert(r contains Cube(0, 0, 0))
  }
  */
}