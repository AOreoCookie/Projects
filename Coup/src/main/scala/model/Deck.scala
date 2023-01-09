package model

/** The influence card deck for Coup. 
 * 
 * @constructor creates a new influence card deck.
*/
object Deck extends scala.collection.mutable.Stack[Card] {

  initializeDeck()
  
  /** Intializes Deck with influence cards
   * 
  */
  def initializeDeck(): Unit = 
    this.clear()
    this.push(new Card("Ambassador"))
    this.push(new Card("Captain"))
    this.push(new Card("Duke"))

    for i <- 0 until 2 do 
      this.push(new Card("Assassin"))
      this.push(new Card("Contessa"))
      
    this.pushAll(Array(new Card("Ambassador"), new Card("Contessa"), new Card("Ambassador"), new Card("Assassin"), new Card("Duke"), new Card("Captain"), new Card("Captain"), new Card("Duke")))
  
  /** Intializes Deck with influence cards in a shuffled order
   * 
  */

  def shuffle(): Unit = 
    this.clear()
    this.pushAll(scala.util.Random.shuffle(Array(new Card("Assassin"), new Card("Contessa"), new Card("Duke"), new Card("Captain"), new Card("Ambassador"), new Card("Ambassador"), new Card("Contessa"), new Card("Ambassador"), new Card("Assassin"), new Card("Duke"), new Card("Captain"), new Card("Captain"), new Card("Duke"))))
}
