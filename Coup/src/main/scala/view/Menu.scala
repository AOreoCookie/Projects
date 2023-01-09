package view

import scala.collection.mutable._
import model._

object Menu {
  private var winner = "None"
  
  /** Shows the current state of the player order
   * @return String
  */
  def showPlayerOrder(): String = PlayerOrder.show
  
  /** Advances the player order */
  def advancePlayerOrder(): Unit = PlayerOrder.advance

  /** Returns the game area.
   * @return String
  */
  def showGameArea(): String = 
    var sb = new StringBuilder("")
    sb.append(Treasury.showTreasury())

    // Printing the player money:

    sb.append("Player Money:\n")
    for player <- PlayerOrder.players yield
      sb.append(player.showMoney() + ", ")
    sb.setLength(sb.length() - 2)

    // Printing the player influence:

    sb.append("\n" + "Player Influence:\n")
    for player <- PlayerOrder.players yield
      sb.append(player.showInfluence() + ", ")
    sb.setLength(sb.length() - 2)
    sb.append("\n\n")

    // Printing player cards:
    for player <- PlayerOrder.players yield
      sb.append(player.showCards() + "\n")
    
    sb.append("\n")
    sb.append("Player Order: [")  
    sb.append(PlayerOrder.show)
    sb.append("]")

    sb.toString

  /** Initializes the Menu/Game. */
  def initialize(): Unit = 
    Treasury.initializeTreasury()
    Deck.initializeDeck()
    PlayerOrder.initializePlayerOrder()
    for player <- PlayerOrder do player.initializePlayer()
  
  /** Randomly initializes the Menu/Game. */
  def randInitialize(): Unit = 
    Treasury.initializeTreasury()
    Deck.shuffle()
    PlayerOrder.initializePlayerOrder()
    for player <- PlayerOrder do player.randInitializePlayer()

  /** Checks for winner and returns the winner if there is one
   * @return String
  */
  def checkForWinner(): String = if PlayerOrder.getSize == 1 then PlayerOrder.current.name else "None"
  
  /** Changes the player's strategy.
   * @param player Player
   * @param newStrategy Name of the strategy to give to the player 
  */
  def setStrategy(player: Player, newStrategy: String): Unit = player.setStrategy(newStrategy)
  
  def setRandomStrategy(player: Player): Unit =
    val strats = Array("Default", "Income-Coup", "Income-Assassinate", "Steal-Coup")
    player.setStrategy(strats(scala.util.Random.between(0, 4)))

  /** Shows the name of the current strategy for each player.
  * @return String
  */
  def showStrategies: String =
    var sb = new StringBuilder("")
    for player <- PlayerOrder.players do
      sb.append(player.name + ": " + player.showStrategy + "\n")
    sb.toString

  /** Executes a move for the current player. */
  def doMove(): Unit = 
    val curPlayer = PlayerOrder.current
    val curTax = new Tax(curPlayer)
    if curPlayer.showInfluenceAmount == 0 then 
      PlayerOrder.removePlayer(curPlayer)
    else 
      val curStrategy = curPlayer.curStrategy
      curStrategy.execute()
  
  /** Executes the next 4 moves in the game. */
  def doTurn(): Unit = 
    for i <- 0 to 3 do
      if checkForWinner() == "None" then
        doMove()
  
  /** Executes the entire game. */
  def doGame(): Unit = 
    var stealCount = 0
    for player <- PlayerOrder.players do
      if player.curStrategy.name == "Steal-Coup" then stealCount += 1
    
    if stealCount == 4 then doMove()
    else
      while checkForWinner() == "None" do
        doMove()
}