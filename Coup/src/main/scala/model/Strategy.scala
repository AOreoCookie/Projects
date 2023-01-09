package model

import model._

/** The influence card deck for Coup.
  * @constructor creates a new influence card deck.
  * @param curPlayer Player
*/
abstract class Strategy(curPlayer: Player) {
  val coinEarner: PlayerAction = new Income(curPlayer)
  val name: String = "Default"
  var killer: PlayerAction = new Coup(curPlayer, curPlayer)  
  var maxPlayer: Player = curPlayer
  var stealer: PlayerAction = new Steal(curPlayer, curPlayer)
  
  /** Executes Coup Action on other players.
   * Chooses the first player in the original order with the most influence.
  */
  def getTarget(): Player = 
    var maxInfluence = -1
    maxPlayer = curPlayer
    for player <- PlayerOrder.players do
      if player.showInfluenceAmount > maxInfluence && PlayerOrder.contains(player) then
        if player != curPlayer then
          maxInfluence = player.showInfluenceAmount
          maxPlayer = player
    maxPlayer

  /** Executes Steal Action on other players.
   * Chooses the first player in the original order with the most money to steal from.
  */
  def getStealer(): Player = 
    var maxMoney = -1
    maxPlayer = curPlayer
    for player <- PlayerOrder.players do
      if player.showMoneyAmount > maxMoney && PlayerOrder.contains(player) then
        if player != curPlayer then
          maxMoney = player.showMoneyAmount
          maxPlayer = player
    maxPlayer
  
  
  /** Executes Coup Action on other players.
   * Chooses the first player in the original order with the most influence and creates an associated PlayerAction object.
  */
  def updateTarget(): Unit = 
    killer = new Coup(curPlayer, getTarget())
  
  /** Executes the strategy based on what is set for the player */
  def execute(): Unit =
    updateTarget()
    if curPlayer.showStrategy == "Income-Assassinate" then
      if curPlayer.showMoneyAmount >= 3 then 
        killer.executeAction(maxPlayer.showFirstCard())
      else coinEarner.executeAction(curPlayer.showFirstCard())
    else if curPlayer.showStrategy == "Steal-Coup" then
      updateTarget()
      if curPlayer.showMoneyAmount >=7 then killer.executeAction(maxPlayer.showFirstCard())  
      else stealer.executeAction(curPlayer.showFirstCard())
    else
      if curPlayer.showMoneyAmount >= 7 then killer.executeAction(maxPlayer.showFirstCard())
      else coinEarner.executeAction(curPlayer.showFirstCard())
    PlayerOrder.advance
  
  /** Shows strategy name.
  */
  def showName: String = name
}

/** Selects the Duke-Coup strategy.
  * @constructor instantiates a new Duke-Coup strategy.
  * @param curPlayer Player
*/
class DukeCoup(curPlayer: Player) extends Strategy(curPlayer) {
  override val coinEarner = new Tax(curPlayer)
}

/** Selects the Income-Coup strategy.
  * @constructor instantiates a new Income-Coup strategy.
  * @param curPlayer Player
*/
class IncomeCoup(curPlayer: Player) extends Strategy(curPlayer) {
  override val name = "Income-Coup"
}

/** Selects the Steal-Coup strategy.
  * @constructor instantiates a new Steal-Coup strategy.
  * @param curPlayer Player
*/
class StealCoup(curPlayer: Player) extends Strategy(curPlayer) {
  override val coinEarner = new Steal(curPlayer, maxPlayer)
  override val name = "Steal-Coup"

  override def updateTarget(): Unit =
    stealer = new Steal(curPlayer, getStealer())

  /** Executes Coup Action on other players.
   * Chooses the first player in the original order with the most influence.
  */
}

/** Selects the Income-Assassinate strategy.
  * @constructor instantiates a new Income-Assassinate strategy.
  * @param curPlayer Player
*/
class IncomeAssassinate(curPlayer: Player) extends Strategy(curPlayer) {
  override val name = "Income-Assassinate"

  override def updateTarget(): Unit =
    killer = new Assassinate(curPlayer, getTarget())

}
