package sample.akka.actor

import akka.actor._

/**
 * ライフサイクルを監視するActor
 * Date: 13/01/16
 * Time: 23:56
 */
object WatchSample {
  lazy val system = ActorSystem("WatchSystem")
  def main(args: Array[String]) {
  }

  class WatchActor extends Actor {
    val child = context.actorOf(Props.empty, "child")
    context.watch(child)
    var lastSender = system.deadLetters

    def receive = {
      case "kill" => {
        context.stop(child)
        lastSender = sender
      }
      case Terminated(`child`) => lastSender ! "finished"
    }
  }
}
