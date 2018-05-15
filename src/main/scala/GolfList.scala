package state_monad_for_listStack

object GolfList extends App {
  
  case class Stack(myList: List[Int])

  def push(a: Int): State[Stack, Int] = State {(s:Stack) =>
    val newList = a :: s.myList // 3 :: List(1, 2) = List(3, 1, 2)
    // val newList = s.myList :+ a // List(1, 2) :+ 3 = List(1, 2, 3)
    println("After push, ", (Stack(newList), a))
    (Stack(newList), a)
  }

  // State[S, A]
  def pop: State[Stack, Int] = State {(s:Stack) =>
    println("After pop, ", s)
    (Stack(s.myList.tail), s.myList.head)
  }

  def stackManip: State[Stack, Int] = for {
    _ <- push(3)
    a <- pop
    b <- pop
  } yield(b) 

  // The action begin

  val beginningState = Stack(List(2, 5, 8))

  val result: (Stack, Int) = stackManip.run(beginningState)

  println(s"Stack state: ${result._1}")  //Stack(List(5, 8))
  println(s"my Value: ${result._2}") // 2

/*
[info] Running state_monad_for_listStack.GolfList

(After push, ,(Stack(List(3, 2, 5, 8)),3))
(After pop, ,Stack(List(3, 2, 5, 8)))
(After pop, ,Stack(List(2, 5, 8)))
Stack state: Stack(List(5, 8))
my Value: 2
*/

}