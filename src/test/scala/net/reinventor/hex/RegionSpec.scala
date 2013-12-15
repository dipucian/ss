package net.reinventor.hex

import org.scalatest._

class RegionSpec extends FlatSpec with Matchers {
  "Region(1, 1)" should "contains (0, 0) only" in {
    val r = Region(1, 1)
    r should contain theSameElementsAs List(Axial(0, 0))
  }

  "Region(2, 3)" should "contains rectangular grid of 2 rows and 3 cols" in {
    val r = Region(2, 3)
    r should contain theSameElementsAs List(Axial(0, 0), Axial(0, 1), Axial(1, 0), Axial(1, 1), Axial(2, -1), Axial(2, 0))
  }

  "Region(0)" should "contains Axial(0, 0) only" in {
    val r = Region(0)
    r should contain (Axial(0, 0))
    r should have size(1)
  }

  "Region(1)" should "contains 7 cells centered (0, 0)" in {
    val r = Region(1)
    r should have size(7)
    r should contain theSameElementsAs List(Axial(0, 0), Axial(1, 0), Axial(0, 1), Axial(-1, 0), Axial(0, -1), Axial(1, -1), Axial(-1, 1))
  }

  "Region(1).translate(1, 1)" should "contains 7 cells centered (1, 1)" in {
    val r = Region(1).translate(Axial(1, 1))
    r should have size(7)
    r should contain theSameElementsAs List(Axial(0+1, 0+1), Axial(1+1, 0+1), Axial(0+1, 1+1), Axial(-1+1, 0+1), Axial(0+1, -1+1), Axial(1+1, -1+1), Axial(-1+1, 1+1))
  }

}