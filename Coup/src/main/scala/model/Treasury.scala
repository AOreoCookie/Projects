package model

/** The treasury for the game Coup.
  *  
  * @constructor Creates the treasury for the game, that holds the game coins.
*/
object Treasury {
  private var treasuryMoney = 50
  
  /** Shows how many coins are left in the treasury in string format.
   * 
   * @return String 
  */
  def initializeTreasury(): Unit = treasuryMoney = 50 
  
  /** Shows how many coins are left in the Treasury
   * @return String
  */
  def showTreasury(): String = "Treasury: " + treasuryMoney.toString + " coins left\n"

  /** Deposits a number of coins from the treasury.
    * @param value The amount of coins to deposit from the treasury.
  */
  def depositTreasury(value: Int): Unit = treasuryMoney += value

  /** Withdraws a number of coins from the treasury.
    * @param value The amount of coins to withdraw from the treasury.
  */
  def withdrawTreasury(value: Int): Unit = treasuryMoney -= value
}
