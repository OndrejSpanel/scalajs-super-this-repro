package repro

import scala.scalajs.js.annotation.JSExportTopLevel

object ActionSource {
  object Entity {
    def apply(id: Int, path: String): Int = id
  }
}

class TestAction(uiPosition: Int = 0, actionSource: Option[Int]) {
  def priority: Int = 0
}

@JSExportTopLevel("main")
def main(): Unit = new Reproducer()

class Reproducer {
  new TestAction(
    actionSource = Some(1).map(ActionSource.Entity(_, ""))
  ) {
    override def priority: Int = 10
  }
}
