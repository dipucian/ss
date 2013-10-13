package net.reinventor.ss

import scala.swing._
import java.awt.Polygon
import net.reinventor.hex.{Region, Coordinate}

object App extends SimpleSwingApplication {
  val game = new GameImpl
  val gameUI = new GameUI(game)

  def top: Frame = new MainFrame {
    title = "Hello world~~"
    minimumSize = new Dimension(800, 600)
    contents = gameUI
    centerOnScreen()
  }
}

trait Game {
  def map: Set[Coordinate]
}
class GameImpl extends Game {
  def map = Region.Range(4)
}

class GameUI(game: Game) extends Component {
  val grid = game.map

  val hexSize = 20
  val Hex = hexagon(hexSize)
  def drawHex(g: Graphics2D, coord: (Int, Int)): Unit = {
    val (q, r) = coord
    val x = q * 1.5 * hexSize
    val y = (r + q * .5) * math.sqrt(3) * hexSize
    val oriTransform = g.getTransform
    g.translate(x, y)
    g.drawPolygon(Hex)
    g.setTransform(oriTransform)
  }

  override protected def paintComponent(g: Graphics2D): Unit = {
    g.translate(300, 300)
    for (hex <- grid) {
      drawHex(g, hex.axial)
    }
  }

  private def hexagon(size: Double): Polygon = {
    val points = for {
      i <- 0 to 5
      angle = 2 * math.Pi / 6 * i
      x = size * math.cos(angle)
      y = size * math.sin(angle)
    } yield (x.toInt, y.toInt)

    val (x, y) = points.unzip

    new Polygon(x.toArray, y.toArray, 6)
  }
}