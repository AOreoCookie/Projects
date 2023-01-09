package controller

import scala.swing._
import view.View
import model.Model

/** Controls the interaction between the View and the model.
 * @constructor Creates a new Conroller.
 * @param view The View
 * @param model The Model
*/
class Controller(view: View, model: Model) {

  /** Returns the game area from the model.
   * @return String
  */
  def showGameArea = Action("Show Game Area") {
    view.textArea.text = model.getGameArea

  }

  /** Advances the player order in the model. */
  def advancePlayerOrder = Action("Advance Player Order") {
    model.advancePlayerOrder
    view.playerOrderArea.text = model.showPlayerOrder
    view.textArea.text = model.getGameArea
  }

  /** Returns the player order from the model.
   * @return String
  */
  def showPlayerOrder = Action("Show Player Order") {
    view.playerOrderArea.text = model.showPlayerOrder
  }

  /** Initializes the game and returns the game area from the model.
   * @return String
  */
  def initGame = Action("Initialize Game") {
    model.initializeGame
    view.textArea.text = model.getGameArea
    view.playerStrategies.alphaButtons.select(view.alphaDefault)
    view.playerStrategies.bravoButtons.select(view.bravoDefault)
    view.playerStrategies.charlieButtons.select(view.charlieDefault)
    view.playerStrategies.deltaButtons.select(view.deltaDefault)
  }

  /** Plays a move and returns the game area from the model.
   * @return String
  */
  def playMove = Action("Do Move") {
    model.playMove
    view.textArea.text = model.getGameArea
    view.playerOrderArea.text = model.showPlayerOrder
  }

  /** Plays a turn and returns the game area from the model.
   * @return String
  */
  def playTurn = Action("Do Turn") {
    model.playTurn
    view.textArea.text = model.getGameArea
    view.playerOrderArea.text = model.showPlayerOrder
  }

  /** Plays the game and returns the game area from the model.
   * @return String
  */
  def playGame = Action("Do Game") {
    model.playGame
    view.textArea.text = model.getGameArea
    view.playerOrderArea.text = model.showPlayerOrder
  }

  /** Plays the game and returns the game area from the model.
   * @return String
  */
  def checkForWinner = Action("Check For Winner") {
    view.textArea.text = model.checkWinner
  }
  /** Exits the GUI and stops the Coup simulation. */
  def exit = Action("Exit") {
    sys.exit(0)
  }

  /** Sets player strategies to a selected strategy.
   * @return Action
  */
  def setPlayerStrategy(idx: Int, newStart: String) = Action("") {
    model.getPlayer(idx).setStrategy(newStart)
    view.textArea.text = model.getGameArea
  }

  /** Sets player strategies to a randomly selected strategy.
   * @return Action
  */
  def setRandomPlayerStrategy(idx: Int) = Action("") {
    model.setRandomPlayerStrategy(idx)
    view.textArea.text = model.getGameArea
  }

  /** Randomizes deck order and player strategies before initializing players and treasury.
   * @return Action
  */
  def shuffle = Action("Shuffle") {
    model.randInitializeGame
    view.textArea.text = model.getGameArea
    
    view.playerStrategies.alphaButtons.select(view.alphaRandomized)
    view.playerStrategies.bravoButtons.select(view.bravoRandomized)
    view.playerStrategies.charlieButtons.select(view.charlieRandomized)
    view.playerStrategies.deltaButtons.select(view.deltaRandomized)
  }
}