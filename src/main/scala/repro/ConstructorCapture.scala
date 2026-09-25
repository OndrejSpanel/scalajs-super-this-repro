package repro

import scala.scalajs.js.annotation.JSExportTopLevel

object ActionSource {
  def compute(id: Int): Int = id
}

class TestAction(uiPosition: Int = 0, actionSource: Option[Int]) {
  def priority: Int = 0
}

@JSExportTopLevel("main")
def main(): Unit = new Reproducer()

class Reproducer {
  new TestAction(
    actionSource = Some(1).map(ActionSource.compute)
  ) {
    override def priority: Int = 10
  }
}
