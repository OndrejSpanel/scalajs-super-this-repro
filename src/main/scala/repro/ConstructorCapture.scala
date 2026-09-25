package repro

import org.scalatest.AsyncTestSuite
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.ExecutionContext

trait AsyncTestExecutor extends AsyncTestSuite {
  implicit override def executionContext: ExecutionContext = ExecutionContext.global
}

class EntityState
object EntityId {
  type A = EntityId[EntityState]
  def apply[T <: EntityState](id: Int): EntityId[T] = id
}
opaque type EntityId[+T <: EntityState] = Int

case class PathItem(value: String)
type TreePath = Seq[PathItem]
object TreePath {
  def empty: TreePath = Seq.empty
}

class Localized(val value: String)
object Localized {
  val Empty = new Localized("")
  def plainText(value: String): Localized = new Localized(value)
}

trait ActionSource
case class EntityActionSource(id: EntityId.A, path: TreePath) extends ActionSource
object ActionSource {
  object Entity {
    def apply(id: EntityId.A, path: TreePath): ActionSource = EntityActionSource(id, path)
    def apply(id: EntityId.A, path: TreePath, paths: Set[TreePath]): ActionSource = EntityActionSource(id, path)
  }
}

trait AnyAction {
  def actionSource: Option[ActionSource] = None
  def disableActions: Boolean = false
  def priority: Int = 0
}

object TestActions {
  case class TestAction(
    uiPosition: Int = 0,
    actionPath: TreePath = TreePath.empty,
    text: Localized = Localized.Empty,
    override val actionSource: Option[ActionSource] = None,
    override val disableActions: Boolean = false
  ) extends AnyAction
}

import TestActions.TestAction

class Reproducer extends AsyncFlatSpec, AsyncTestExecutor, Matchers {
  private def actionPath(depth: Int): TreePath =
    (0 until depth).map(index => PathItem(s"test-$index"))

  private def selectionAction(
    name: String,
    actionPriority: Int,
    pathDepth: Int,
    sourceId: Option[EntityId.A] = None
  ): TestAction = {
    val path = actionPath(pathDepth)
    new TestAction(
      actionPath = path,
      text = Localized.plainText(name),
      actionSource = sourceId.map(ActionSource.Entity(_, path))
    ) {
      override def priority: Int = actionPriority
    }
  }

  behavior of "CameraPlayerControl"

  it should "construct an action" in {
    selectionAction("synthetic", 10, 1, Some(EntityId[EntityState](1))).priority shouldBe 10
  }
}
