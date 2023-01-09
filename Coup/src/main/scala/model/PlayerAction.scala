package model

/** A game action in Coup.
  *
  * @constructor creates a new action for player.
  * @param user player doing the action
*/
abstract class PlayerAction(player: Player) {

  val name = "Action"
  val canBeBlocked = false
  private var blocked = false 
  val cost = 0

  /** Performs the action */
  def executeAction(card: Card): Unit = player.withdrawCoins(cost)

  /** Shows action name */
  def showName: String = name

  /** Shows action cost */
  def showActionCost: String = cost.toString

  /** Shows if the action is blockable
   * @return false if not blockable, true otherwise.
   */
  def beBlocked: Boolean = canBeBlocked

  /** Shows if the action was blocked
   * @return false if not blocked, true otherwise.
   */
  def wasBlocked: Boolean = blocked
}

/** The "Income" action in the game of Coup.
 *
 * @constructor creates a new "Income" action for the player.
 * @param player player using the "Income" action
*/
class Income(player: Player) extends PlayerAction(player) {
  override val name = "Income"
  override val cost = 1
}

/** The "Foreign-Aid" action in the game of Coup.
 *
 * @constructor creates a new "Foreign-Aid" action for the player.
 * @param player player using the "Foreign-Aid" action
*/
class ForeignAid(player: Player) extends PlayerAction(player) {
  override val name = "Foreign-Aid"
  override val canBeBlocked = true
  override val cost = 2
}

/** The "Coup" action in the game of Coup.
 *
 * @constructor creates a new "Coup" action for the player.
 * @param player player using the "Coup" action
 * @param victim player losing an influence card
*/
class Coup(player: Player, victim: Player) extends PlayerAction(player) {
  override val name = "Coup"
  override val cost = 7

  /** Removes a card from victim's hand & takes subtracts cost from player's total amount of coins
   * @param card card to remove from victim's hand
  */
  override def executeAction(card: Card): Unit = {
    player.depositCoins(cost)
    victim.removeInfluence(card)
  }
}

/** The "Tax" action in the game of Coup.
 * 
 * @constructor creates a new "Tax" action for the player.
 * @param player player using the "Tax" action
*/
class Tax(player: Player) extends PlayerAction(player) {
  override val name = "Tax"
  override val cost = 3
}

/** The "Assassinate" action in the game of Coup.
 *
 * @constructor creates a new "Assassinate" action for the player.
 * @param player player using the "Assassinate" action
 * @param victim player losing an influence card
*/
class Assassinate(player: Player, victim: Player) extends PlayerAction(player) {
  override val name = "Assasinate"
  override val canBeBlocked = true
  override val cost = 3

  /** Removes a card from victim's hand & takes subtracts cost from player's total amount of coins
   * @param card card to remove from victim's hand
  */
  override def executeAction(card: Card): Unit = {
    player.depositCoins(cost)
    victim.removeInfluence(card)
  }
}

/** The "Exchange" action in the game of Coup.
 *
 * @constructor creates a new "Exchange" action for the player.
 * @param player player using the "Exchange" action
*/
class Exchange(player: Player) extends PlayerAction(player) {
  override val name = "Exchange"

  /** Replaces the 1st card in player's hand with another.
   *
  */
  override def executeAction(card: Card): Unit = {
    player.removeInfluence(player.showFirstCard())
    Deck += card
    val newCard = Deck.pop()
    player.addInfluence(newCard)
  }
}

/** The "Steal" action in the game of Coup.
 *
 * @constructor creates a new "Steal" action for the player.
 * @param player player using the "Steal" action
 * @param victim player losing coins
*/
class Steal(player: Player, victim: Player) extends PlayerAction(player) {
  override val name = "Steal"
  override val canBeBlocked = true
  override val cost = 2

  /** Takes coins from victim and gives them to the player
   * 
  */
  override def executeAction(card: Card): Unit =
    victim.depositCoins(cost)
    player.withdrawCoins(cost)

}

/** The "Block Foreign-Aid" action in the game of Coup.
 *
 * @constructor creates a new "Block Foreign-Aid" action for the player.
 * @param player player using the "Block Foreign-Aid" action
 * @param victim player whose "Foreign-Aid" action is being blocked
*/
class BlockForeignAid(player: Player, victim: Player) extends PlayerAction(player) {
  override val name = "Block Foreign-Aid"
  override val cost = 2

  /** Victims deposits coins back into the Treasury.
   * 
  */
  override def executeAction(card: Card): Unit = 
    victim.depositCoins(cost)
}

/** The "Block Foreign-Aid" action in the game of Coup.
 *
 * @constructor creates a new "Block Steak" action for the player.
 * @param player player using the "Block Steal" action
 * @param victim player whose "Steal" action is being blocked
*/
class BlockSteal(player: Player, victim: Player) extends PlayerAction(player) {
  override val name = "Block Steal"
  override val cost = 2

  /** Victim gives coins back to player.
   * 
  */
  override def executeAction(card: Card): Unit = 
    victim.depositCoins(cost)
    player.withdrawCoins(cost)

}

/** The "Block Assassinate" action in the game of Coup.
 *
 * @constructor creates a new "Block Assassinate" action for the player.
 * @param player player using the "Block Assassinate" action
 * @param victim player whose "Assasinate" action is being blocked
*/
class BlockAssassinate(player: Player, victim: Player) extends PlayerAction(player) {
  override val name = "Block Assassinate"
  override val cost = 3

  /** Gives victim their coins back & gives player a new influence card
   *
  */
  override def executeAction(card: Card): Unit = 
    victim.withdrawCoins(cost)
    player.addInfluence(Deck.pop())
}