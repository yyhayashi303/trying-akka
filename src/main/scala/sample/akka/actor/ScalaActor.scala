package sample.akka.actor

import actors.Actor

/**
 * Date: 13/01/13
 * Time: 1:05
 */
object ScalaActor {
  def main(args: Array[String]) {
    SampleActor.start()
    // 非同期
    SampleActor ! Message1
    // 返信(reply)を受け取る
    val rep = SampleActor !? Message2
    println(rep)
    // 返信を受け取るFutureを受け取る
    val future = SampleActor !! Message2
    println(future())
  }

  case object Message1
  case object Message2
  object SampleActor extends Actor {
    def act = loop {
      react {
        case Message1 => println("message1")
        case Message2 => reply("reply message")
        case _ => println("other message")
      }
    }
  }
}
