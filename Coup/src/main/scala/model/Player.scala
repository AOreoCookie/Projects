package model

/** A player who plays Coup.
  *
  * @constructor create a new player with a name. 
  * @param name the player's name
*/
class Player(val name: String) {

  private var hand = scala.collection.mutable.ArrayBuffer.empty[Card]
  private var influence = hand.length
  private var money = 0
  private var strategy: Strategy = new DukeCoup(this)

  /** Shows the name of the player's current strategy.
  * @return String
  */
  def showStrategy: String = strategy.showName

  /** Shows the name of the player's current strategy.
  * @return String
  */
  def curStrategy: Strategy = strategy

  /** Changes the player's strategy.
  * @param newStrategy Name of the strategy to give to the player 
  */
  def setStrategy(newStrategy: String): Unit =
    if newStrategy == "Steal-Coup" then strategy = new StealCoup(this)
    else if newStrategy == "Income-Coup" then strategy = new IncomeCoup(this)
    else if newStrategy == "Income-Assassinate" then strategy = new IncomeAssassinate(this) 
    else if newStrategy == "Default" then strategy = new DukeCoup(this)
    else strategy = curStrategy

  /** Initalizes player with two cards from the Deck and two coins from the Treasury.
  *
  */
  def initializePlayer(): Unit = 
    for i <- 0 to 1 do addInfluence(Deck.pop())
    withdrawCoins(2)

  /** Initalizes player with two cards from the shuffled Deck and two coins from the Treasury. Also sets a random strategy for the player.
  *
  */
  def randInitializePlayer(): Unit = 
    initializePlayer()
    val strats = Array("Default", "Income-Coup", "Income-Assassinate", "Steal-Coup")
    setStrategy(strats(scala.util.Random.between(0, 4)))

  /** Withdraw a number of player held coins into the Treasury.
  * 
  * @param coins coin amount to withdraw
  */
  def withdrawCoins(coins: Int): Unit = 
    Treasury.withdrawTreasury(coins)
    money += coins

  /** Deposit a number of player held coins into the Treasury.
  * 
  * @param coins coin amount to deposit
  */
  def depositCoins(coins: Int): Unit =
    money -= coins
    Treasury.depositTreasury(coins)
  
  /** Removes influence card from player's hand & Subtracts from total influence count.
  *
  * @param card the card in the player's hand
  */
  def removeInfluence(card: Card): Unit = 
    hand -= card
    influence = hand.length

  /** Adds influence card from Deck to player's hand & Adds to total influence count.
  *
  * @param card the card from the Deck
  */
  def addInfluence(card: Card): Unit = 
    hand += card
    influence = hand.length
  
  /** Displays player's total influence.
  * @return String
  */
  def showInfluence(): String = name + " = " + influence.toString

  /** Returns the amount of influence a player has.
  * @return Int 
  */
  def showInfluenceAmount: Int = influence

  /** Returns the amount of money a player has.
   * @return Integer
  */
  def showMoneyAmount: Int = money

  /** Displays player's total money.
  * @return String
  */
  def showMoney(): String = name + " = " + money.toString

  /** Displays player's cards in hand.
  * @return String
  */
  def showCards(): String =
    var cardNames = scala.collection.mutable.ArrayBuffer.empty[String]
    for card <- hand do cardNames += card.showCardName 
    name + " Cards: " + cardNames.mkString("[", ", ", "]")

  /** Shows the first card in hand
   * @return Card class/object
  */
  def showFirstCard(): Card = 
    hand(0)
  
  /** Shows the second card in hand
   * @return Card class/object
  */
  def showSecondCard(): Card = 
    hand(1)
}
