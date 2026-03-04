# GeomEz

**GeomEz** is a Kotlin library for building and visualizing 2D and 3D geometric elements. It provides a clean, expressive API for representing points, vectors, directions, coordinate systems, curves, surfaces, and polygons — with full support for affine transforms, analytical calculus on functions, and optional matplotlib-powered visualization.

---

## Modules

| Module | Artifact | Description |
|---|---|---|
| `geomez-core` | `com.geomez:geomez-core:1.0.0` | Geometric types, math primitives, and operations. No rendering dependency. |
| `geomez-visualization` | `com.geomez:geomez-visualization:1.0.0` | Adds `.plot()` and `.addPlotCommands()` to all core types via Python matplotlib. |

---

## Highlights

- Immutable, arithmetic-friendly `Point2D`, `Vector2D`, `Direction2D` (and 3D counterparts)
- Affine transforms — `rotate()`, `changeBasis()` — backed by 3×3 / 4×4 matrices
- Scalar functions with analytical derivatives and integrals: `Polynomial`, `LinearSpline`, `CubicSpline`
- Parametric curves: `BezierCurve`, `CubicBezierSpline2D`, `ParametricCurve3D`
- Parametric surfaces: `ParametricSurface3D` with numerical normal vector computation
- Floating-point–safe equality via delta comparison
- One-liner visualization for every type

---

## Quick example

```kotlin
import plane.BezierCurve
import plane.elements.Point2D

val bezier = BezierCurve(listOf(
    Point2D(0.0, 0.0),
    Point2D(0.5, 1.5),
    Point2D(1.5, 1.5),
    Point2D(2.0, 0.0)
))

val midPoint = bezier(0.5)   // evaluate at t = 0.5
bezier.plot()                // opens a matplotlib window
```

---

## Getting started

→ [Installation](installation.md)
