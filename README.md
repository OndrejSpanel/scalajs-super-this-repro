# Scala.js constructor capture reproducer

With Scala 3.9.0, sbt 2.0.9, and sbt-scalajs 1.22.0, run:

```powershell
sbt fullLinkJS
```

Compilation succeeds. Full linking fails with `Restricted use of this before the super constructor call` at `Some(1).map(ActionSource.Entity(_, ""))` in `ConstructorCapture.scala`.
