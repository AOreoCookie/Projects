package model

object PlayerOrder extends scala.collection.mutable.Queue[Player] {
    private val alpha = new Player("Alpha")
    private val bravo = new Player("Bravo")
    private val charlie = new Player("Charlie")
    private val delta = new Player("Delta") 

    this ++= List(alpha, bravo, charlie, delta)

    // Chose an array for the order, as I can index in and change the different player objects to update
    // their data easier than a queue

    val players = Array(alpha, bravo, charlie, delta)

    /** Initializes the Player Order. */
    def initializePlayerOrder(): Unit = 
      val alphaNew = new Player("Alpha")
      val bravoNew = new Player("Bravo")
      val charlieNew = new Player("Charlie")
      val deltaNew = new Player("Delta")

      this.clear()
      this ++= List(alphaNew, bravoNew, charlieNew, deltaNew)
      
      players(0) = alphaNew
      players(1) = bravoNew
      players(2) = charlieNew
      players(3) = deltaNew

    /** Advances the player order. */
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

    /** Returns the the current player.
     * @return Player Class
    */
    def current: Player = this.head

    /** Returns the size of the player order queue.
     * @return Int
    */
    def getSize: Int = this.length
}