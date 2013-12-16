package net.reinventor.ss

import scala.swing._
import java.awt.{RenderingHints, Polygon}
import scala.swing.event.MouseClicked

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

class GameUI(game: Game) extends Component {
  val grid = game.map

  /**
   * for regular hexagon,
   * width = 2 * size, height = sqrt(3)/2 * width
   * => halfHeight = sqrt(3) * quarterWidth
   */
  val hexSize = 24
  val quarterWidth: Int = hexSize / 2
  val halfHeight: Int = (math.sqrt(3) * quarterWidth).toInt
  val Hex = hexagon(quarterWidth, halfHeight)

  listenTo(mouse.moves, mouse.clicks)
  reactions += {
    //case e: MouseMoved => println(e)
    case e: MouseClicked => {
      game.step()
      repaint()
    }
  }

  def drawHex(g: Graphics2D, coord: (Int, Int)): Unit = {
    val (q, r) = coord
    val x = q * 3 * quarterWidth
    val y = r * 2 * halfHeight + q * halfHeight
    val oriTransform = g.getTransform
    g.translate(x, y)
    g.drawPolygon(Hex)
    val s = s"($q, $r)"
    val fm = g.getFontMetrics
    val sw = fm.stringWidth(s)
    g.drawString(s, -sw/2, fm.getAscent/2)
    g.setTransform(oriTransform)
  }

  def fillHex(g: Graphics2D, coord: (Int, Int)): Unit = {
    val (q, r) = coord
    val x = q * 3 * quarterWidth
    val y = r * 2 * halfHeight + q * halfHeight
    val oriTransform = g.getTransform
    g.translate(x, y)
    g.fillPolygon(Hex)
    val s = s"($q, $r)"
    val fm = g.getFontMetrics
    val sw = fm.stringWidth(s)
    g.drawString(s, -sw/2, fm.getAscent/2)
    g.setTransform(oriTransform)
  }

  override protected def paintComponent(g: Graphics2D): Unit = {
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g.translate(300, 300)
    for (hex <- grid) {
      drawHex(g, hex.axial)
    }

    for (c <- game.characters) {
      fillHex(g, c.pos.axial)
    }
  }

  private def hexagon(quarterWidth: Int, halfHeight: Int): Polygon = {
    val points = Array(
      (2*quarterWidth, 0),
      (quarterWidth, halfHeight),
      (-quarterWidth, halfHeight),
      (-2*quarterWidth, 0),
      (-quarterWidth, -halfHeight),
      (quarterWidth, -halfHeight)
    )
    val (xs, ys) = points.unzip
    new Polygon(xs.toArray, ys.toArray, 6)
  }

}