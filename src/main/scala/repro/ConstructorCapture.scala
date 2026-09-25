package repro

import scala.scalajs.js.annotation.JSExportTopLevel

object ActionSource {
  object Entity {
    def apply(id: Int, path: String): Int = id
  }
}

class TestAction(uiPosition: Int = 0, actionSource: Option[Int] = None) {
  def priority: Int = 0
}

class Reproducer {
  def selectionAction(
    actionPriority: Int,
    sourceId: Option[Int]
  ): TestAction = {
    val path = ""
    new TestAction(
      actionSource = sourceId.map(ActionSource.Entity(_, path))
    ) {
      override def priority: Int = actionPriority
    }
  }
}

@JSExportTopLevel("main")
def main(): Unit = new Reproducer().selectionAction(10, Some(1))
