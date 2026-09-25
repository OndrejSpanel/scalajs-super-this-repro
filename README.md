# Scala.js constructor capture reproducer — Scala 3.9 / sbt 2, main scope

The reproducer source is in `src/main/scala/repro/ConstructorCapture.scala` and is linked as production code.

Versions:

- Scala 3.9.0
- sbt 2.0.9
- sbt-scalajs 1.22.0

From this directory, run:

```powershell
sbt fullLinkJS
```

Expected result: compilation succeeds, then Scala.js full linking fails at `sourceId.map(ActionSource.Entity(_, path))` with `Restricted use of this before the super constructor call`.

As a control, compute that expression in a local `val` before constructing the anonymous subclass. Under the previous test-scope version, this made `Test/fullLinkJS` succeed.
