# --- Coup Game Simulation ---

## --- Table of Contents ---
- [--- Coup Game Simulation ---](#----coup-game-simulation----)
  - [--- Table of Contents ---](#----table-of-contents----)
  - [--- Description ---](#----description----)
    - [--- Goal ---](#----goal----)
    - [--- Equipment Needed ---](#----equipment-needed----)
    - [--- How To Play ---](#----how-to-play----)
      - [---- Player Order ----](#-----player-order-----)
      - [--- Starting Configuration ---](#----starting-configuration----)
      - [--- Player Actions ---](#----player-actions----)
      - [--- Rules ---](#----rules----)

## --- Description ---
In a future where the government is run for profit, all but a privileged few suffer lives of poverty and desperation. Out of these oppressed masses are sown the seeds of a rebellion that throws the government into chaos.

Many see hope for a brighter future for the first time in their lives. Others see an opportunity for absolute power.

**To take command, you must destroy the influence of your rivals and drive them into exile**

***Only one can survive***

### --- Goal ---
- To be the last **player** standing. A **player** wins the game when all other players have flipped over both of their **influence cards**.

### --- Equipment Needed ---
- **Deck of Influence cards**
  - **3 Duke Cards**
  - **3 Assassin Cards**
  - **3 Captain Cards**
  - **3 Ambassador Cards**
  - **3 Contessa Cards**
- **6 summary cards**
- **50 coins**

### --- How To Play ---

#### ---- Player Order ----
The **player** order will be as follows...

```
Player 1 -> Player 2 -> Player 3 -> Player 4
```

As the game progresses the **player order** will _change_ depending on who was _eliminated_. For example, if `Player 2` was _eliminated_ from the game the **player order** will be as follows...

```
Player 1 -> Player 3 -> Player 4
```

#### --- Starting Configuration ---
After shuffling the **deck of influence cards** each **player** will be given the following...
- **Player Starting Inventory**
  - _2 Influence cards_
  - _2 Coins_
  - _1 Summary Card_

During the game, each **player** has their _two influence cards_ facedown so that no one else can see them.

The rest of the _influence cards_ and _coins_ will be placed in the middle of all players representing _`The Court`_ and _`The Treasury`_ respectively.

#### --- Player Actions ---
Due to the anonymity of everyone's _influence cards_ **players** may choose **any** of the following actions in the table below during their turn. Other players can try to _block an action_ by using any of the following _counteractions_ in the table. This is also allowed due to the anonymity of the _influence cards_.

| Character | Action | Effect | Counteraction |
|:---:|:---:|:---:|:---:|
| - | _Income_ | Take 1 _coin_ from the **treasury** | - |
| - | _Foreign Aid_ | Take 2 _coins_ from the **treasury** | - |
| - | _Coup_ | Pay 7 _coins_ to the **treasury**<br>*Choose a player to lose influence*<br>**If you have 10+, you must take this action** | - |
| **Duke** | _Tax_ | Take 3 _coins_ from the **treasury** | _Blocks Foreign Aid_ |
| **Assassin** | _Assassinate_ | Pay 3 _coins_ to the **treasury**<br> *Choose player to lose influence* | - |
| **Ambassador** | _Exchange_ | Exchange _cards_ with the **Court Deck** | _Blocks Stealing_ |
| **Captain** | _Steal_ | Take 2 _coins_ from another player | _Blocks Stealing_  |
| **Contessa** | - | - | _Blocks Assasination_ |

**Counteractions** may be performed before an action takes place. If occurs the **player** being _countered_ must _prove_ that they have the card that corresponds to the _action_. For example, if `Player 1` chooses the _*Assassinate*_ action and chooses `Player 2` to lose an _influence card_, either Player 2, Player 3, or Player 4 may call a _challenge_ to the action, forcing `Player 1` to either not _show one of their influence cards_ or _show everyone that they have the right card_.

If `Player 1` _shows that they have the proper influence card_ that corresponds with their desired action they will _perform the desired action_ and _draw a new card_ from the `The Court`. The player who tried to _counter the action_ will _lose one of their influence cards_.

If `Player 1` _does not have the proper influence card_ or they _choose not to show any of their cards_. then the _action is blocked_ and they _lose an influence card_.

#### --- Rules --- 
1. All *players* will be given 2 _influence cards_ and 2 _coins_ at the start.
2. The game is played in a clockwise manner and is turn-based.
3. All _influence cards_ will be flipped over so that other **players** cannot see each other's cards.
4. All **players**
5. Each turn a **player** chooses one _action_ only. All of the possible _actions_ are listed [here](#----player-actions----).
    - A **player** may not pass.
    - If a **character** has 10+ _coins_ they must choose the _"Coup" action_ 
6. A _player's action_ may be _countered_ using any of the _counteractions_ listed [here](#----player-actions----).
    - If you are _countered/challenged_ you must _reveal one of your cards_ to everyone.
      - If it's the _proper influence card_ for the _action_ the **player* who tried to _counter_ you will _lose an influence card_.
      - If it's an _improper influence card_ or you _opt not to show any of your cards_ you will _lose that card or one of your cards respectively_.
7. If a **player** _loses both of their influence cards_ then they are _eliminated from the game_. A **player** _wins_ when they are the last person with _influence cards_ left.

## --- Design Practices ---

### --- Open Design ---

Open design is defined as creating code where security is not reliant on the secrecy of the implementation details or obscurity/obfuscation. The idea behind it is that attackers will take the time to figure out the requisite information in order to create exploits. By having readable and clear code open to others to read, they can help to identify and fix issues, and it encourages more secure coding practices.

In our design, we publish all of our functions in terms of how the game works on the backend and displays data. The code is readable with properly named functions and variables. For example, our game area printing function is shown below:

```scala
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
```

These functions are all open to be viewed in our repository, and thus, we are forced to rely on secure coding practices instead of security through obscurity and obfuscation. Additionally, viewers of our code can help us to identify possible vulnerabilities so we can fix them.

This open design forces us to use private variables and values for security and create functions to manage these values within classes and objects, because we do not want players to be able to cheat by editing the values associated with players or the game. An example can be seen with the player's money:

```scala
class Player(val name: String) {

  private var hand = scala.collection.mutable.ArrayBuffer.empty[Card]
  private var influence = 0
  private var money = 0
  private var strategy: Strategy = new DukeCoup(this)

  def withdrawCoins(coins: Int): Unit = 
    Treasury.withdrawTreasury(coins)
    money += coins
  
  def depositCoins(coins: Int): Unit =
    money -= coins
    Treasury.depositTreasury(coins)

}
```

Similarly, instead of interacting directly with the Players inside PlayerOrder, there are functions that control the output and mutability of those items.

```scala
   def advance: Unit = this += this.dequeue

    /** Removes a player from the player order. 
     * @param player Player 
    */
    def removePlayer(player: Player): Unit = 
        if player.showInfluenceAmount == 0 then 
            this -= player

    /** Shows the Player Order.
     * @return String
    */
    def show: String = 
        val sb = new StringBuilder("")
        for p <- this.toArray yield
            sb.append(p.name + ", ")
        sb.setLength(sb.length() - 2)
        sb.toString
```

Additionally, our scalaDoc and UML diagrams complement this open design, as they allow users to understand what is going on in the code.

### --- Fail Safe Defaults ---

Fail safe defaults are defined as explicit denials to objects unless access is specifically granted. In this case of our project, this could mean the variables being stored in the different objects, such as the player, the deck, or the treasury. One example of fail safe defaults is seen below:

```scala
  def setStrategy(newStrategy: String): Unit =
    if newStrategy == "Steal-Coup" then strategy = new StealCoup(this)
    else if newStrategy == "Income-Coup" then strategy = new IncomeCoup(this)
    else if newStrategy == "Income-Assassinate" then strategy = new IncomeAssassinate(this) 
    else if newStrategy == "Default" then strategy = new DukeCoup(this)
    else strategy = curStrategy
```

This block of code is from our Player class, and this specific function is used to change the player's strategy. Fail safe defaults are implemented in this function, as the Player's strategy is only able to change if a proper strategy name is supplied as the `newStrategy`, and if there is an improper `newStrategy` inputted, the strategy of the player remains the same.

Additionally the use of private variables in our code means that these values can only be modified by certain functions, and if someone were to try to change it, they would not be able to unless they had the express permission to do so with a method in the class or object.

Some examples of these private variables in our implementation follow:

```scala
class Player(val name: String) {

  private var hand = scala.collection.mutable.ArrayBuffer.empty[Card]
  private var influence = 0
  private var money = 0
  private var strategy: Strategy = new DukeCoup(this)
}
```

```scala
abstract class PlayerAction(player: Player) {

  val name = "Action"
  val canBeBlocked = false
  private var blocked = false 
  val cost = 0
}
```

```scala
object PlayerOrder extends scala.collection.mutable.Queue[Player] {
    private val alpha = new Player("Alpha")
    private val bravo = new Player("Bravo")
    private val charlie = new Player("Charlie")
    private val delta = new Player("Delta") 
}
```

### --- Economy of Mechanism ---

Economy of mechanism relates to security and functionality being as simple as possible. In the case of our project, we used classes, objects, and reused functions in order to reduce the amount of confusing code necessary. One example is with our initialization code:

```scala
  def initialize(): Unit = 
    Treasury.initializeTreasury()
    Deck.initializeDeck()
    PlayerOrder.initializePlayerOrder()
    for player <- PlayerOrder do player.initializePlayer()
  
  def randInitialize(): Unit = 
    Treasury.initializeTreasury()
    Deck.shuffle()
    PlayerOrder.initializePlayerOrder()
    for player <- PlayerOrder do player.randInitializePlayer()
```

For the case of these two functions, which are menu initialization functions, we call upon functions that we had previously implemented in the Treasury, Deck, and PlayerOrder objects, decreasing the amount of code we need to write and increasing the simplicity of our system, as only a few functions do the heavy lifting, and work is not duplicated.

A similar functionality was used with the View, Controller, and Model functions.

View calls Controller functions:

```scala
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
```

Controller calls Model functions:

```scala
  def advancePlayerOrder = Action("Advance Player Order") {
    model.advancePlayerOrder
    view.playerOrderArea.text = model.showPlayerOrder
  }
```


