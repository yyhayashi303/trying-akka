package sample.akka.actor

import akka.actor._
import akka.util.Timeout
import akka.pattern.ask
import concurrent.Await

/**
 * Date: 13/01/13
 * Time: 0:45
 */
object AkkaActor {
  def main(args: Array[String]) {
    val system = ActorSystem("MySystem")

    // Actor生成
    val actor = system.actorOf(Props[MessageActor], name = "messageActor")
    // 非同期処理
    actor ! Message1
    // 同期処理
    implicit val timeout = Timeout(5)
    val future = actor ? Message2
    val result = Await.result(future, timeout.duration).asInstanceOf[String]
    println(result)

    // コンストラクタにパラメータがあるActorを生成
    val myActor = system.actorOf(Props(new MyActor("param")), name = "myActor")
    myActor ! Message1
  }

  case object Message1
  case object Message2
  class MessageActor extends Actor {
    def receive = {
      case Message1 => println("message1")
      case Message2 => sender ! "reply message"
      case _ => println("other message")
    }
    // 生成された際に呼ばれる
    override def preStart() = {
      println("initialization code")
    }
  }
  class MyActor(param: String) extends Actor {
    // Actor内で他のActorを生成
    // この時、MyActorはMessageActorのSupervisorとなる
    val messageActor = context.actorOf(Props[MessageActor], name = "messageActor")
    def receive = {
      case _ => messageActor ! Message1
    }
  }
}
