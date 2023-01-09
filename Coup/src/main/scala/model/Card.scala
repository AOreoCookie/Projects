package model

/** A influence card in Coup. 
  *
  * @constructor create a new card with a name.
  * @param name card's name
*/
class Card(name: String) {
  
  private var cost = "" // Gain/Loss of coins
  private var action = "None"
  private var counterAction = "None"

  name match
    case "Duke" =>
      cost = "+3"
      action = "Withdraw 3 coins from the treasury"
      counterAction = "Block Foreign Aid"
    case "Assassin" =>
      cost = "-3"
      action = "Choose a player to remove 1 influence from"
    case "Ambassador" =>
      action = "Exchange one card with the the first card of the deck"
      counterAction = "Block Steal"
    case "Captain" =>
      cost = "+2"
      action = "Choose a player to steal 2 coins from"
      counterAction = "Block Steal"
    case "Contessa" =>
      counterAction = "Block Assassinate"

  /** Shows the cost of the card 
   * @return String
  */
  def showCardCost: String = this.cost

  /** Shows the name of the card 
   * @return String
  */
  def showCardName: String = this.name

  /** Shows the action of the card 
   * @return String
  */
  def showCardAction: String = this.action

  /** Shows the counter-action of the card */
  def showCardCounterAction: String = this.counterAction
}
