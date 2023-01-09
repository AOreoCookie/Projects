package model

import view.Menu

/** Interacts with the database and handles data logic  
 * @constructor Creates the Model
*/
class Model {
  private var turnCount = 0

  /** Returns the game area.
   * @return String
  */
  def getGameArea: String = 
    var textSto = new StringBuilder("")
    textSto.append(Menu.showGameArea() + "\n\n")
    textSto.append("Strategies:\n" + Menu.showStrategies)
    textSto.toString

  /** Advances the player order. */
  def advancePlayerOrder: Unit = 
    Menu.advancePlayerOrder()
    turnCount += 1

  /** Returns the player order.
   * @return String
  */
  def showPlayerOrder: String = Menu.showPlayerOrder()

  /** Returns how many turns have passed.
   * @return Int
  */
  def numAdvances: Int = turnCount

  /** Returns the winner if there is one.
   * @return String
  */
  def checkWinner: String = Menu.checkForWinner()

  /** Executes a player move. */
  def playMove: Unit = Menu.doMove()

  /** Executes a game turn. */
  def playTurn: Unit = Menu.doTurn()

  /** Executes the game. */
  def playGame: Unit = Menu.doGame()

  /** Initializes the game. */
  def initializeGame: Unit = Menu.initialize()

  /** Randomly initializes the game. */
  def randInitializeGame: Unit = Menu.randInitialize()

  /** Gets a player at the specified index.
   * @return Player
  */
  def getPlayer(idx: Int): Player = PlayerOrder.players(idx)

  /** Gets a player at the specified index.
   * @return Player
  */
  def getStrategies(): String = Menu.showStrategies

  /** Set player strategy to a randomly selected one */
  def setRandomPlayerStrategy(idx: Int): Unit = Menu.setRandomStrategy(PlayerOrder.players(idx))
}
