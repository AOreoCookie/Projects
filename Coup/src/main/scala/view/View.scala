package view

import scala.swing._
import controller.Controller
import view._
import view.Menu
import model._
import BorderPanel.Position._
import java.awt.geom.{Rectangle2D, Ellipse2D}
import java.awt.{Color, Font}
import scala.collection.mutable.ArrayBuffer
import java.awt.image.BufferedImage
import scala.swing.Orientation
import javax.swing.plaf.basic.BasicRadioButtonUI
import javax.swing.border.BevelBorder

/** Handles data presentation.
 * @constructor creates a new Option[Controller].
*/
class View extends MainFrame {

  var _controller: Option[Controller] = None

  //// Buttons ////
  object advancePlayerOrder extends Button("Advance Player Order")
  object startGame extends Button("Start Game")
  object doMove extends Button("Do a Move")
  object doTurn extends Button("Do a Turn")
  object doGame extends Button("Play Game")
  object checkForWinner extends Button("Check For Winner")
  object shuffle extends Button("Shuffle")

  object alphaDefault extends RadioButton() {
    selected = true
  }
  object alphaIncomeCoup extends RadioButton()
  object alphaIncomeAssasinate extends RadioButton()
  object alphaStealCoup extends RadioButton()
  object alphaRandomized extends RadioButton()
  object bravoDefault extends RadioButton() {
    selected = true
  }
  object bravoIncomeCoup extends RadioButton()
  object bravoIncomeAssasinate extends RadioButton()
  object bravoStealCoup extends RadioButton()
  object bravoRandomized extends RadioButton()
  object charlieDefault extends RadioButton() {
    selected = true
  }
  object charlieIncomeCoup extends RadioButton()
  object charlieIncomeAssasinate extends RadioButton()
  object charlieStealCoup extends RadioButton()
  object charlieRandomized extends RadioButton()
  object deltaDefault extends RadioButton() {
    selected = true
  }
  object deltaIncomeCoup extends RadioButton()
  object deltaIncomeAssasinate extends RadioButton()
  object deltaStealCoup extends RadioButton()
  object deltaRandomized extends RadioButton()

  // Game Components ////
  object textArea extends TextArea {
    this.font = new Font("Courier New", Font.PLAIN, 20)
    this.background = Color.gray
    var textSto = new StringBuilder("")

    textSto.append(Menu.showGameArea() + "\n\n")
    textSto.append("Strategies:\n" + Menu.showStrategies)

    this.text = textSto.toString

  }

  object playerOrderArea extends TextArea {
    this.text = Menu.showPlayerOrder()
    this.editable = false
  }

  object playerStrategies extends GridPanel(5, 6) {
    contents += new Label("Strategies") {
      opaque = true
      border = BevelBorder(1)
    }
    contents += new Label("Default") {
      opaque = true
      border = BevelBorder(1)
    }
    contents += new Label("Income-Coup") {
      opaque = true
      border = BevelBorder(1)
    }
    contents += new Label("Income-Assassinate") {
      opaque = true
      border = BevelBorder(1)
    }
    contents += new Label("Steal-Coup") {
      opaque = true
      border = BevelBorder(1)
    }
    contents += new Label("Randomized") {
      opaque = true
      border = BevelBorder(1)
    }

    contents += new Label("Alpha") {
      background = Color.red
      border = BevelBorder(1)
      opaque = true
    }
    val alphaButtons = new ButtonGroup(alphaDefault, alphaIncomeCoup, alphaIncomeAssasinate, alphaStealCoup, alphaRandomized)
    contents ++= alphaButtons.buttons
    contents += FlowPanel(alphaDefault)
    contents += FlowPanel(alphaIncomeCoup)
    contents += FlowPanel(alphaIncomeAssasinate)
    contents += FlowPanel(alphaStealCoup)
    contents += FlowPanel(alphaRandomized)

    contents += new Label("Bravo") {
      background = Color.blue
      border = BevelBorder(1)
      opaque = true
    }
    val bravoButtons = new ButtonGroup(bravoDefault, bravoIncomeCoup, bravoIncomeAssasinate, bravoStealCoup, bravoRandomized)
    contents ++= bravoButtons.buttons
    contents += FlowPanel(bravoDefault)
    contents += FlowPanel(bravoIncomeCoup)
    contents += FlowPanel(bravoIncomeAssasinate)
    contents += FlowPanel(bravoStealCoup)
    contents += FlowPanel(bravoRandomized)

    contents += new Label("Charlie") {
      background = Color.green
      border = BevelBorder(1)
      opaque = true
    }
    val charlieButtons = new ButtonGroup(charlieDefault, charlieIncomeCoup, charlieIncomeAssasinate, charlieStealCoup, charlieRandomized)
    contents ++= charlieButtons.buttons
    contents += FlowPanel(charlieDefault)
    contents += FlowPanel(charlieIncomeCoup)
    contents += FlowPanel(charlieIncomeAssasinate)
    contents += FlowPanel(charlieStealCoup)
    contents += FlowPanel(charlieRandomized)

    contents += new Label("Delta") {
      background = Color.yellow
      border = BevelBorder(1)
      opaque = true
    }
    val deltaButtons = new ButtonGroup(deltaDefault, deltaIncomeCoup, deltaIncomeAssasinate, deltaStealCoup, deltaRandomized)
    contents ++= deltaButtons.buttons
    contents += FlowPanel(deltaDefault)
    contents += FlowPanel(deltaIncomeCoup)
    contents += FlowPanel(deltaIncomeAssasinate)
    contents += FlowPanel(deltaStealCoup)
    contents += FlowPanel(deltaRandomized)
  }

  object gameButtons extends GridPanel(6,1) {
      contents += startGame
      contents += checkForWinner
      contents += doMove
      contents += doTurn
      contents += doGame
      contents += shuffle
  }
  
  val north = new FlowPanel {
    contents += new Label("Player Order:") {
      opaque = true
    }
    contents += playerOrderArea
    contents += advancePlayerOrder
  }

  object gameArea extends BorderPanel {
    layout += north -> North
    layout += textArea -> Center
    layout += playerStrategies -> South
    layout += gameButtons -> East
  }

  /** Initializes the view.
    * @param controller Controller 
    */
  def init(controller: Controller): Unit = {
    _controller = Some(controller)
    title = "Coup Game Simulation"
    contents = gameArea
    centerOnScreen()


    menuBar = new MenuBar {
      contents += new Menu("Milestone 6") {
        contents += new MenuItem(controller.showGameArea)
        contents += new MenuItem(controller.advancePlayerOrder)
        contents += new MenuItem(controller.showPlayerOrder)
        contents += new MenuItem(controller.playMove)
        contents += new MenuItem(controller.playTurn)
        contents += new MenuItem(controller.playGame)
        contents += new MenuItem(controller.checkForWinner)
        contents += new MenuItem(controller.initGame)
        contents += new Separator
        contents += new MenuItem(controller.exit) // end Exit menuItem
      } // end Menu 1
    } // end MenuBar


    advancePlayerOrder.action = controller.advancePlayerOrder
    startGame.action = controller.initGame
    doMove.action = controller.playMove
    doTurn.action = controller.playTurn
    doGame.action = controller.playGame
    checkForWinner.action = controller.checkForWinner
    shuffle.action = controller.shuffle
  
    alphaDefault.action = controller.setPlayerStrategy(0, "Default") 
    alphaIncomeCoup.action = controller.setPlayerStrategy(0, "Income-Coup")
    alphaIncomeAssasinate.action = controller.setPlayerStrategy(0, "Income-Assassinate")
    alphaStealCoup.action = controller.setPlayerStrategy(0, "Steal-Coup")
    alphaRandomized.action = controller.setRandomPlayerStrategy(0)
    bravoDefault.action = controller.setPlayerStrategy(1, "Default") 
    bravoIncomeCoup.action = controller.setPlayerStrategy(1, "Income-Coup")
    bravoIncomeAssasinate.action = controller.setPlayerStrategy(1, "Income-Assassinate")
    bravoStealCoup.action = controller.setPlayerStrategy(1, "Steal-Coup")
    bravoRandomized.action = controller.setRandomPlayerStrategy(1)
    charlieDefault.action = controller.setPlayerStrategy(2, "Default") 
    charlieIncomeCoup.action = controller.setPlayerStrategy(2, "Income-Coup")
    charlieIncomeAssasinate.action = controller.setPlayerStrategy(2, "Income-Assassinate")
    charlieStealCoup.action = controller.setPlayerStrategy(2, "Steal-Coup")
    charlieRandomized.action = controller.setRandomPlayerStrategy(2)
    deltaDefault.action = controller.setPlayerStrategy(3, "Default")
    deltaIncomeCoup.action = controller.setPlayerStrategy(3, "Income-Coup")
    deltaIncomeAssasinate.action = controller.setPlayerStrategy(3, "Income-Assassinate")
    deltaStealCoup.action = controller.setPlayerStrategy(3, "Steal-Coup")
    deltaRandomized.action = controller.setRandomPlayerStrategy(3)

    size = new Dimension(750, 750)
    visible = true
  }

  
}