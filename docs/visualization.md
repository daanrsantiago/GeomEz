# Visualization

The `geomez-visualization` module extends every core type with two rendering methods, backed by Python matplotlib via the [`matplotlib4k`](https://github.com/daniel-tucano/matplotlib4k) bridge.

---

## Setup

Add the module to your dependencies (see [Installation](installation.md)), and ensure Python with matplotlib is available on your `PATH`:

```bash
pip install matplotlib
```

---

## Two-method API

Every geometric type gets two extension functions:

| Method | Description |
|---|---|
| `.plot(...)` | Standalone quick plot — opens a matplotlib window immediately |
| `.addPlotCommands(figure?, axes?, ...)` | Adds drawing commands to an existing (or new) figure/axes pair; returns `Pair<Figure, Axes>` |

---

## Quick plots

Each type opens a matplotlib window with sensible defaults:

```kotlin
// 2D
point2DList.plot()
polygon.plot()
bezierCurve.plot()
cubicSpline.plot()
parametricCurve.plot()
vector2D.plot()
direction2D.plot()
coordinateSystem2D.plot()

// 3D
points3DList.plot()
vector3D.plot()
direction3D.plot()
coordinateSystem3D.plot()
parametricSurface.plot()
```

---

## Composing multiple elements

Use `addPlotCommands()` to layer elements onto a single figure. All calls return `Pair<Figure, Axes>` so you can chain them:

```kotlin
import io.github.danielTucano.matplotlib.pyplot.show
import io.github.danielTucano.python.pythonExecution
import plane.BezierCurve
import plane.CoordinateSystem2D
import plane.Polygon2D
import plane.elements.Point2D

pythonExecution {
    val polygon = Polygon2D(listOf(
        Point2D(0.0, 0.0),
        Point2D(2.0, 0.0),
        Point2D(1.0, 2.0)
    ))

    val bezier = BezierCurve(listOf(
        Point2D(0.0, 0.0),
        Point2D(0.5, 1.5),
        Point2D(1.5, 1.5),
        Point2D(2.0, 0.0)
    ))

    // First call creates the figure + axes
    val (fig, ax) = polygon.addPlotCommands()

    // Subsequent calls reuse them
    bezier.addPlotCommands(fig, ax)
    CoordinateSystem2D.MAIN_2D_COORDINATE_SYSTEM.addPlotCommands(fig, ax)

    show()
}
```

---

## 3D composition

3D plots work exactly the same way. The axes returned are an `Axes3D` instance:

```kotlin
import io.github.danielTucano.matplotlib.pyplot.show
import io.github.danielTucano.python.pythonExecution
import space.CoordinateSystem3D
import space.elements.Direction3D
import space.elements.Point3D
import space.elements.Vector3D

pythonExecution {
    val v = Vector3D(1.0, 0.0, 1.0, position = Point3D(1.0, 1.0, 1.0))
    val (fig, ax) = v.addPlotCommands()

    Direction3D.MAIN_Z_DIRECTION.addPlotCommands(fig, ax)
    CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM.addPlotCommands(fig, ax)

    show()
}
```

---

## BezierCurve plot options

The `BezierCurve` plot functions accept a `tList` parameter to control sampling density:

```kotlin
import utils.linspace

// Higher resolution
bezierCurve.plot(tList = linspace(0.0, 1.0, 500))

// Or when composing
bezierCurve.addPlotCommands(fig, ax, tList = linspace(0.0, 1.0, 500))
```

The plot always renders the control polygon (dashed) alongside the curve.

---

## Available extensions

| File | Types covered |
|---|---|
| `plane/bezierCurveExtensions.kt` | `BezierCurve` |
| `plane/cubicSplineExtensions.kt` | `CubicSpline` |
| `plane/parametricCurve2DExtensions.kt` | `ParametricCurve2D` |
| `plane/piecewiseCubicBezierCurveExtensions.kt` | `CubicBezierSpline2D` |
| `plane/polygon2DExtensions.kt` | `Polygon2D` |
| `plane/point2DListExtensions.kt` | `List<Point2D>` |
| `plane/vector2DExtensions.kt` | `Vector2D` |
| `plane/direction2DExtensions.kt` | `Direction2D` |
| `plane/coordinateSystem2DExtensions.kt` | `CoordinateSystem2D` |
| `space/parametricCurve3DExtensions.kt` | `ParametricCurve3D` |
| `space/parametricSurface3DExtensions.kt` | `ParametricSurface3D` |
| `space/points3DListExtensions.kt` | `List<Point3D>` |
| `space/vector3DExtensions.kt` | `Vector3D` |
| `space/vector3DListExtensions.kt` | `List<Vector3D>` |
| `space/direction3DExtensions.kt` | `Direction3D` |
| `space/coordinateSystem3DExtensions.kt` | `CoordinateSystem3D` |
