package view

import scala.swing._
import controller.Controller
import model.Model

/** Composes the Model, View, & Controller into the GUI interface.
  * @constructor Creates a new Coup GUI Interface.
*/
object Coup {
  
  @main def main(): Unit = {
      
    val model = new Model
    val view  = new View   
    val controller = new Controller(view, model)
    
    view.init(controller)    
  } 
}