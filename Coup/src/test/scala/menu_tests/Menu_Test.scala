import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should._
import scala.collection.mutable._
import model._
import view._

class Menu_Test extends AnyFunSpec with Matchers {
  describe("Coup Game Simulation") {
    describe("Has a Menu") {
      //******* SHOW PLAYER ORDER *******
      it("Can show the player order") {     
        val expectedResult = "Alpha, Bravo, Charlie, Delta"
        Menu.showPlayerOrder() should be(expectedResult)

      }

      //******* ADVANCE PLAYER ORDER *******
      it("Advances the player order") {
        val expectedResult_0 = "Alpha, Bravo, Charlie, Delta"
        val expectedResult_1 = "Bravo, Charlie, Delta, Alpha"
        val expectedResult_2 = "Charlie, Delta, Alpha, Bravo"
        val expectedResult_4 = "Alpha, Bravo, Charlie, Delta"

        Menu.showPlayerOrder() should be(expectedResult_0)

        Menu.advancePlayerOrder()
        Menu.showPlayerOrder() should be(expectedResult_1)

        Menu.advancePlayerOrder()
        Menu.showPlayerOrder() should be(expectedResult_2)

        Menu.advancePlayerOrder()
        Menu.advancePlayerOrder()
        Menu.showPlayerOrder() should be(expectedResult_0)
        Menu.showPlayerOrder() should be(expectedResult_4)
      }

      //******* SHOW GAME AREA *******
      it("Can show the game area") {
        val expectedBoard = "Treasury: 50 coins left\n" + 
                            "Player Money:\nAlpha = 0, Bravo = 0, Charlie = 0, Delta = 0\n" +
                            "Player Influence:\nAlpha = 0, Bravo = 0, Charlie = 0, Delta = 0\n\n" +
                            "Alpha Cards: []\n" +
                            "Bravo Cards: []\n" +
                            "Charlie Cards: []\n" +
                            "Delta Cards: []\n\n" +
                            "Player Order: [Alpha, Bravo, Charlie, Delta]"
        Menu.showGameArea() should be(expectedBoard)
      }

      //******* INITIALIZE GAME *******
      it("Can initialize to cause the game board to be cleared and player data reset") {
        Menu.initialize()
        
        for player <- PlayerOrder.players do
          player.removeInfluence(player.showFirstCard())
          player.depositCoins(1)

        val expectedResult = // Removes 1 coin/influence card per player
          "Treasury: 46 coins left\n" + 
          "Player Money:\nAlpha = 1, Bravo = 1, Charlie = 1, Delta = 1\n" +
          "Player Influence:\nAlpha = 1, Bravo = 1, Charlie = 1, Delta = 1\n\n" +
          "Alpha Cards: [Captain]\n" +
          "Bravo Cards: [Duke]\n" +
          "Charlie Cards: [Ambassador]\n" +
          "Delta Cards: [Ambassador]\n\n" +
          "Player Order: [Alpha, Bravo, Charlie, Delta]"

        Menu.showGameArea() should be(expectedResult)

        val expectedResult2 = // Advances player order
          "Treasury: 46 coins left\n" + 
          "Player Money:\nAlpha = 1, Bravo = 1, Charlie = 1, Delta = 1\n" +
          "Player Influence:\nAlpha = 1, Bravo = 1, Charlie = 1, Delta = 1\n\n" +
          "Alpha Cards: [Captain]\n" +
          "Bravo Cards: [Duke]\n" +
          "Charlie Cards: [Ambassador]\n" +
          "Delta Cards: [Ambassador]\n\n" +
          "Player Order: [Bravo, Charlie, Delta, Alpha]"
        
        Menu.advancePlayerOrder()
        Menu.showGameArea() should be (expectedResult2)

        Menu.initialize()

        val expectedResult3 = // Resets game to initial state
          "Treasury: 42 coins left\n" + 
          "Player Money:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Alpha, Bravo, Charlie, Delta]"

        Menu.showGameArea() should be(expectedResult3)
      }

      //******* CHECK FOR WINNER *******
      it("Can check for winner to show whether a player has won the game") {
        Menu.initialize()
        Menu.checkForWinner() should be("None")
        for index <- 0 to 2 do
          Menu.checkForWinner() should be("None")
          val player = PlayerOrder.players(index)
          player.removeInfluence(player.showSecondCard())
          player.removeInfluence(player.showFirstCard())
          PlayerOrder.removePlayer(player)
        Menu.checkForWinner() should be("Delta")
      }
      
      //******* DO MOVE *******
      it("Can do move, causing the player at the front of the player order to take their next action") {
        Menu.initialize()
        Menu.doMove()
        val expectedResult1 =
          "Treasury: 39 coins left\n" + 
          "Player Money:\nAlpha = 5, Bravo = 2, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Bravo, Charlie, Delta, Alpha]"
        Menu.showGameArea() should be(expectedResult1)
        val expectedOrder1 = "Bravo, Charlie, Delta, Alpha"
        Menu.showPlayerOrder() should be(expectedOrder1)

        Menu.doMove()
        Menu.doMove()
        Menu.doMove()

        val expectedResult2 = 
          "Treasury: 30 coins left\n" + 
          "Player Money:\nAlpha = 5, Bravo = 5, Charlie = 5, Delta = 5\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Alpha, Bravo, Charlie, Delta]"
        Menu.showGameArea() should be(expectedResult2)
        val expectedOrder2 = "Alpha, Bravo, Charlie, Delta"
        Menu.showPlayerOrder() should be(expectedOrder2)
      }
      
      //******* DO MOVE (NO POSSIBILITIES) *******
      it("Can do move when a player has no move possibilities") {
        // players are removed from the player order when it is their turn the next time after they are eliminated
        for i <- 0 to 17 do Menu.doMove()
        val move33 = 
          "Treasury: 29 coins left\n" + 
          "Player Money:\nAlpha = 0, Bravo = 7, Charlie = 7, Delta = 7\n" +
          "Player Influence:\nAlpha = 1, Bravo = 0, Charlie = 1, Delta = 1\n\n" +
          "Alpha Cards: [Captain]\n" +
          "Bravo Cards: []\n" +
          "Charlie Cards: [Ambassador]\n" +
          "Delta Cards: [Ambassador]\n\n" +
          "Player Order: [Charlie, Delta, Alpha]"
        Menu.showGameArea() should be(move33)
        val expectedOrder33 = "Charlie, Delta, Alpha"
        Menu.showPlayerOrder() should be(expectedOrder33)
      }
      
      //******* PERFORM A TURN *******
      it("Can do turn to perform do move four times, unless game has been won") {
        Menu.initialize()
        Menu.doTurn()
        val expectedResult1 = 
          "Treasury: 30 coins left\n" + 
          "Player Money:\nAlpha = 5, Bravo = 5, Charlie = 5, Delta = 5\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Alpha, Bravo, Charlie, Delta]"
        
        Menu.showGameArea() should be(expectedResult1)

        Menu.doTurn()
        val expectedResult2 = 
          "Treasury: 18 coins left\n" + 
          "Player Money:\nAlpha = 8, Bravo = 8, Charlie = 8, Delta = 8\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Alpha, Bravo, Charlie, Delta]"
        
        Menu.showGameArea() should be(expectedResult2)
      }
      
      //******* PERFORM THE GAME *******
      it("Can do game to perform do turn until game is won") {
        Menu.initialize()
        val expectedResult = 
          "Treasury: 43 coins left\n" + 
          "Player Money:\nAlpha = 0, Bravo = 7, Charlie = 0, Delta = 0\n" +
          "Player Influence:\nAlpha = 0, Bravo = 0, Charlie = 0, Delta = 1\n\n" +
          "Alpha Cards: []\n" +
          "Bravo Cards: []\n" +
          "Charlie Cards: []\n" +
          "Delta Cards: [Ambassador]\n\n" +
          "Player Order: [Delta]"
        Menu.doGame()
        Menu.showGameArea() should be(expectedResult)
      }

      //******* SHOW PLAYER STRATEGY  *******
      it("Can show the player strategy") {
        Menu.initialize()
        val expectedResult1 = "Alpha: Default\n" +
                              "Bravo: Default\n" +
                              "Charlie: Default\n" +
                              "Delta: Default\n"

        Menu.showStrategies should be (expectedResult1)
      }

      //******* SET PLAYER STRATEGY  *******
      it("Can set the player strategy") {
        Menu.initialize()
        val expectedResult1 = "Default"
        val expectedResult2 = "Steal-Coup"
        val expectedResult3 = "Income-Assassinate"
        val expectedResult4 = "Income-Coup"
        
        for player <- PlayerOrder.players do
          Menu.setStrategy(player, "Default")
          player.showStrategy should be(expectedResult1)
          Menu.setStrategy(player, "Steal-Coup")
          player.showStrategy should be(expectedResult2)
          Menu.setStrategy(player, "Income-Assassinate")
          player.showStrategy should be(expectedResult3) 
          Menu.setStrategy(player, "Income-Coup")
          player.showStrategy should be(expectedResult4) 
      }

      it("Can do move with player strategy (Income-Coup)") {
        Menu.initialize()

        val expectedresult1 = "Income-Coup"
        for player <- PlayerOrder do
          player.setStrategy("Income-Coup")
          player.showStrategy should be(expectedresult1)
          

        Menu.doMove()
        val expectedResult1 =
          "Treasury: 41 coins left\n" + 
          "Player Money:\nAlpha = 3, Bravo = 2, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Bravo, Charlie, Delta, Alpha]"
        Menu.showGameArea() should be(expectedResult1)

        Menu.doMove()
        val expectedResult2 =
          "Treasury: 40 coins left\n" + 
          "Player Money:\nAlpha = 3, Bravo = 3, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Charlie, Delta, Alpha, Bravo]"
        Menu.showGameArea() should be(expectedResult2)
        
        Menu.doMove()
        val expectedResult3 =
          "Treasury: 39 coins left\n" + 
          "Player Money:\nAlpha = 3, Bravo = 3, Charlie = 3, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Delta, Alpha, Bravo, Charlie]"
        Menu.showGameArea() should be(expectedResult3)

        Menu.doMove()
        val expectedResult4 =
          "Treasury: 38 coins left\n" + 
          "Player Money:\nAlpha = 3, Bravo = 3, Charlie = 3, Delta = 3\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Alpha, Bravo, Charlie, Delta]"
        Menu.showGameArea() should be(expectedResult4)

        for i <- 0 until 17 do Menu.doMove()

        val expectedResult5 =
          "Treasury: 29 coins left\n" + 
          "Player Money:\nAlpha = 0, Bravo = 7, Charlie = 7, Delta = 7\n" +
          "Player Influence:\nAlpha = 2, Bravo = 1, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Bravo, Charlie, Delta, Alpha]"
        Menu.showGameArea() should be(expectedResult5)
      }

      it("Can do move with player strategy (Steal-Coup)") {
        Menu.initialize()

        val expectedresult1 = "Steal-Coup"
        for player <- PlayerOrder do
          player.setStrategy("Steal-Coup")
          player.showStrategy should be(expectedresult1)
          
        Menu.doMove()
        val expectedResult1 =
          "Treasury: 42 coins left\n" + 
          "Player Money:\nAlpha = 4, Bravo = 0, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Bravo, Charlie, Delta, Alpha]"
        Menu.showGameArea() should be(expectedResult1)

        Menu.doMove() 
        val expectedResult2 =
          "Treasury: 42 coins left\n" + 
          "Player Money:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Charlie, Delta, Alpha, Bravo]"
        Menu.showGameArea() should be(expectedResult2)

        Menu.doMove()
        val expectedResult3 =
          "Treasury: 42 coins left\n" + 
          "Player Money:\nAlpha = 0, Bravo = 2, Charlie = 4, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Delta, Alpha, Bravo, Charlie]"
        Menu.showGameArea() should be(expectedResult3)

        Menu.doMove()
        val expectedResult4 =
          "Treasury: 42 coins left\n" + 
          "Player Money:\nAlpha = 0, Bravo = 2, Charlie = 2, Delta = 4\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Alpha, Bravo, Charlie, Delta]"
        Menu.showGameArea() should be(expectedResult4)

        Menu.doMove()
        val expectedResult5 =
          "Treasury: 42 coins left\n" + 
          "Player Money:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Bravo, Charlie, Delta, Alpha]"
        Menu.showGameArea() should be(expectedResult5)

        Menu.doTurn()
        val expectedResult6 =
          "Treasury: 42 coins left\n" + 
          "Player Money:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Bravo, Charlie, Delta, Alpha]"
        Menu.showGameArea() should be(expectedResult6)

        Menu.doTurn()
        val expectedResult7 =
          "Treasury: 42 coins left\n" + 
          "Player Money:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Bravo, Charlie, Delta, Alpha]"
        Menu.showGameArea() should be(expectedResult7)
      }

      it("Can do move with player strategy (Income-Assassinate)") {
        Menu.initialize()

        val expectedresult1 = "Income-Assassinate"
        for player <- PlayerOrder do
          player.setStrategy("Income-Assassinate")
          player.showStrategy should be(expectedresult1)
        
        Menu.doMove()
        val expectedResult1 =
          "Treasury: 41 coins left\n" + 
          "Player Money:\nAlpha = 3, Bravo = 2, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Bravo, Charlie, Delta, Alpha]"
        Menu.showGameArea() should be(expectedResult1)

        Menu.doMove()
        val expectedResult2 =
          "Treasury: 40 coins left\n" + 
          "Player Money:\nAlpha = 3, Bravo = 3, Charlie = 2, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Charlie, Delta, Alpha, Bravo]"
        Menu.showGameArea() should be(expectedResult2)

        Menu.doMove()
        val expectedResult3 =
          "Treasury: 39 coins left\n" + 
          "Player Money:\nAlpha = 3, Bravo = 3, Charlie = 3, Delta = 2\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Delta, Alpha, Bravo, Charlie]"
        Menu.showGameArea() should be(expectedResult3)

        Menu.doMove()
        val expectedResult4 =
          "Treasury: 38 coins left\n" + 
          "Player Money:\nAlpha = 3, Bravo = 3, Charlie = 3, Delta = 3\n" +
          "Player Influence:\nAlpha = 2, Bravo = 2, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Captain, Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Alpha, Bravo, Charlie, Delta]"
        Menu.showGameArea() should be(expectedResult4)

        Menu.doMove()
        val expectedResult5 =
          "Treasury: 41 coins left\n" + 
          "Player Money:\nAlpha = 0, Bravo = 3, Charlie = 3, Delta = 3\n" +
          "Player Influence:\nAlpha = 2, Bravo = 1, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Duke, Captain]\n" +
          "Bravo Cards: [Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Bravo, Charlie, Delta, Alpha]"
        Menu.showGameArea() should be(expectedResult5)

        Menu.doMove()
        val expectedResult6 =
          "Treasury: 44 coins left\n" + 
          "Player Money:\nAlpha = 0, Bravo = 0, Charlie = 3, Delta = 3\n" +
          "Player Influence:\nAlpha = 1, Bravo = 1, Charlie = 2, Delta = 2\n\n" +
          "Alpha Cards: [Captain]\n" +
          "Bravo Cards: [Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Contessa, Ambassador]\n\n" +
          "Player Order: [Charlie, Delta, Alpha, Bravo]"
        Menu.showGameArea() should be(expectedResult6)

        Menu.doMove()
        val expectedResult7 =
          "Treasury: 47 coins left\n" + 
          "Player Money:\nAlpha = 0, Bravo = 0, Charlie = 0, Delta = 3\n" +
          "Player Influence:\nAlpha = 1, Bravo = 1, Charlie = 2, Delta = 1\n\n" +
          "Alpha Cards: [Captain]\n" +
          "Bravo Cards: [Duke]\n" +
          "Charlie Cards: [Assassin, Ambassador]\n" +
          "Delta Cards: [Ambassador]\n\n" +
          "Player Order: [Delta, Alpha, Bravo, Charlie]"
        Menu.showGameArea() should be(expectedResult7)

        Menu.doMove()
        val expectedResult8 =
          "Treasury: 50 coins left\n" + 
          "Player Money:\nAlpha = 0, Bravo = 0, Charlie = 0, Delta = 0\n" +
          "Player Influence:\nAlpha = 1, Bravo = 1, Charlie = 1, Delta = 1\n\n" +
          "Alpha Cards: [Captain]\n" +
          "Bravo Cards: [Duke]\n" +
          "Charlie Cards: [Ambassador]\n" +
          "Delta Cards: [Ambassador]\n\n" +
          "Player Order: [Alpha, Bravo, Charlie, Delta]"
        Menu.showGameArea() should be(expectedResult8)
        // Add 
      }
    }
  }
}