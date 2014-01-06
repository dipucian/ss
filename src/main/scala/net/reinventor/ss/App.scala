package net.reinventor.ss

import scala.swing._
import scala.swing.event._
import java.awt.{RenderingHints, Polygon}
import scala.swing.event.MouseClicked

import net.reinventor.hex._

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
  focusable = true
  requestFocus

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

  def hexToPixel(axial: (Int, Int)): (Int, Int) = {
    val (q, r) = axial
    val x = q * 3 * quarterWidth
    val y = r * 2 * halfHeight + q * halfHeight
    (x, y)
  }

  val player1 = new Player { val base = Axial(0, 4) }
  val player2 = new Player { val base = Axial(0, -4) }
  val ai1 = new testAI(math.Pi/6)
  val ai2 = new testAI(math.Pi/3)

  class testAI(angle: Double) extends AI {
    var attacked = false
    def nextAction(selfState: CharacterState, gameState: GameState): Action = {
      if (!attacked) {
        val pos = hexToPixel(selfState.position.axial)
        val dPos = (pos._1.toDouble, pos._2.toDouble)
        return Attack(BulletInfo(dPos, 10, angle, 8))
      }

      val (q, r) = selfState.position.axial
      if (q > 0) Move(-1, 0)
      else if (r > 0) Move(0, -1)
      else if (q < 0) Move(1, 0)
      else if (r < 0) Move(0, 1)
      else Move(0, 0)
    }
  }

  listenTo(mouse.moves, mouse.clicks, keys)
  reactions += {
    //case e: MouseMoved => println(e)
    case e: MouseClicked => {
      game.step()
      repaint()
    }

    case KeyReleased(_, Key.Space, _, _) => {
      game.step()
      repaint()
    }

    case KeyReleased(_, Key.Key1, _, _) => {
      game.addCharacter(new Character(player1, ai1))
      repaint()
    }
    case KeyReleased(_, Key.Key2, _, _) => {
      game.addCharacter(new Character(player2, ai2))
      repaint()
    }
  }

  def drawHex(g: Graphics2D, coord: (Int, Int)): Unit = {
    val (x, y) = hexToPixel(coord)
    val (q, r) = coord
    // val x = q * 3 * quarterWidth
    // val y = r * 2 * halfHeight + q * halfHeight
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
    val (x, y) = hexToPixel(coord)
    val (q, r) = coord
    // val x = q * 3 * quarterWidth
    // val y = r * 2 * halfHeight + q * halfHeight
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
      fillHex(g, c.position.axial)
    }

    g.setColor(java.awt.Color.RED)
    for (b <- game.bullets) {
      drawBullet(g, b)
    }
  }

  private def drawBullet(g: Graphics2D, bullet: Bullet): Unit = {
    g.drawLine(bullet.position._1.toInt, bullet.position._2.toInt, (bullet.position._1 + bullet.dx).toInt, (bullet.position._2 + bullet.dy).toInt)
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