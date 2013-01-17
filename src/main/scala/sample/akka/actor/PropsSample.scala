package sample.akka.actor

import akka.actor._

/**
 * Date: 13/01/16
 * Time: 22:50
 */
object PropsSample {
  def main(args: Array[String]) {

  }
  class MyActor extends Actor {
    def receive = {
      case s: String => println(s)
    }
  }
}
