// import org.scalatest.funspec.AnyFunSpec
// import org.scalatest.matchers.should._
// import scala.collection.mutable._
// import model._
// import view._

// class Strategy_Test extends AnyFunSpec with Matchers {
//   def fixture = Menu

//   describe("Coup Game Strategies") {
//     describe("Has a Strategy") {
//       it("Can show the strategy") {
//         val testPlayer = Player("Test")
        
//         IncomeCoup(testPlayer).showName should be("Default")
//         DukeCoup(testPlayer).showName should be("Duke-Coup")
//         StealCoup(testPlayer).showName 
//       }
//     }
//     describe("Executes a Strategy") {
//       it("Executes Income-Coup") {
//         val expectedResult = // Removes 1 coin/influence card per player
//           """
//           Treasury: 42 coins left 
//           Player Money:
//           Alpha = 2, Bravo = 2, Charlie = 2, Delta = 2
//           Player Influence:
//           Alpha = 2, Bravo = 2, Charlie = 2, Delta = 2

//           Alpha Cards: [Duke, Captain]
//           Bravo Cards: [Captain, Duke]
//           Charlie Cards: [Assassin, Ambassador]
//           Delta Cards: [Contessa, Ambassador]

//           Player Order: [Alpha, Bravo, Charlie, Delta]
//           """
//       }
//     }
//   }
// }