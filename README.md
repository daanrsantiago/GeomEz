# GeomEz

[![Maven Central](https://img.shields.io/maven-central/v/com.geomez/geomez-core?label=Maven%20Central)](https://central.sonatype.com/artifact/com.geomez/geomez-core)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.21-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Documentation](https://img.shields.io/badge/docs-GitHub%20Pages-blue)](https://daanrsantiago.github.io/GeomEz)

An easy-to-use Kotlin library for building and visualizing 2D and 3D geometric elements.

**[Full documentation →](https://daanrsantiago.github.io/GeomEz)**

---

## Features

- **2D & 3D primitives** — `Point2D`, `Vector2D`, `Direction2D`, `Point3D`, `Vector3D`, `Direction3D`
- **Coordinate systems** — `CoordinateSystem2D` / `CoordinateSystem3D` with full affine transform support (`rotate()`, `changeBasis()`)
- **Scalar functions** — `Polynomial`, `LinearSpline`, `CubicSpline` with analytical derivatives and integrals
- **Parametric curves** — `ParametricCurve2D`, `BezierCurve`, `CubicBezierSpline2D`, `ParametricCurve3D`
- **Parametric surfaces** — `ParametricSurface3D` with numerical normal vector computation
- **Polygon utilities** — `Polygon2D`, `ConvexPolygon2D`, convex hull
- **Plane geometry in 3D** — `Plane`, `Function2DInPlane`
- **Angle unit** — `Angle.Radians` / `Angle.Degrees` with full arithmetic and conversion
- **Visualization** — `.plot()` and `.addPlotCommands()` on every type via a Python matplotlib bridge (optional module)

---

## Requirements

- JVM 21+
- Kotlin 2.x
- **Visualization module only:** Python 3 with `matplotlib` installed

---

## Installation

### Gradle (Kotlin DSL)

**Core only** (geometry types, math, no rendering dependency):
```kotlin
dependencies {
    implementation("com.geomez:geomez-core:1.0.0")
}
```

**Core + Visualization** (adds `.plot()` / `.addPlotCommands()` to all types):
```kotlin
dependencies {
    implementation("com.geomez:geomez-core:1.0.0")
    implementation("com.geomez:geomez-visualization:1.0.0")
}
```

### Maven

**Core only:**
```xml
<dependency>
    <groupId>com.geomez</groupId>
    <artifactId>geomez-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

**Core + Visualization:**
```xml
<dependency>
    <groupId>com.geomez</groupId>
    <artifactId>geomez-core</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.geomez</groupId>
    <artifactId>geomez-visualization</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## Modules

### `geomez-core`

Pure geometry — types, math primitives, and operations with no rendering dependencies. Suitable for use in any JVM project.

Includes the full 2D (`plane` package) and 3D (`space` package) type hierarchies, scalar/parametric function types, polygon utilities, and the `Angle` unit.

### `geomez-visualization`

Extension functions that add `.plot()` and `.addPlotCommands()` to every core type. Rendering is delegated to Python matplotlib via [`matplotlib4k`](https://github.com/daniel-tucano/matplotlib4k), so a Python environment with `matplotlib` installed is required.

- **`.plot()`** — standalone quick plot; opens a matplotlib window immediately.
- **`.addPlotCommands(figure, axes)`** — returns `Pair<Figure, Axes>` for composing multiple elements onto a single figure.

---

## Quick Start

### 2D point arithmetic and rotation

```kotlin
import plane.elements.Point2D
import units.Angle

val p1 = Point2D(1.0, 2.0)
val p2 = Point2D(3.0, 4.0)

val sum     = p1 + p2               // Point2D(4.0, 6.0)
val scaled  = p1 * 2.0              // Point2D(2.0, 4.0)
val rotated = p1.rotate(Angle.Degrees(90.0))
```

### Bézier curve

```kotlin
import plane.BezierCurve
import plane.elements.Point2D

val bezier = BezierCurve(listOf(
    Point2D(0.0, 0.0),
    Point2D(0.5, 1.5),
    Point2D(1.5, 1.5),
    Point2D(2.0, 0.0)
))

val pointAtHalf = bezier(0.5)  // Point2D on the curve at t = 0.5

// Visualize (requires geomez-visualization + Python matplotlib)
bezier.plot()
```

### Cubic spline with derivative and integral

```kotlin
import plane.elements.Point2D
import plane.functions.CubicSpline

val spline = CubicSpline(listOf(
    Point2D(0.0, 0.0),
    Point2D(1.0, 2.0),
    Point2D(2.0, 1.5),
    Point2D(3.0, 3.0)
))

val y          = spline(1.5)               // interpolated y-value
val slope      = spline.derivative(1.5)    // dy/dx at x = 1.5
val area       = spline.integrate(0.0, 3.0)
```

### Composing multiple elements on one plot

```kotlin
import io.github.danielTucano.matplotlib.pyplot.show
import io.github.danielTucano.python.pythonExecution
import plane.CoordinateSystem2D
import plane.Polygon2D
import plane.elements.Point2D

pythonExecution {
    val polygon = Polygon2D(listOf(
        Point2D(0.0, 0.0),
        Point2D(1.0, 1.5),
        Point2D(2.0, 0.0)
    ))
    val (fig, ax) = polygon.addPlotCommands()
    CoordinateSystem2D.MAIN_2D_COORDINATE_SYSTEM.addPlotCommands(fig, ax)
    show()
}
```

### Parametric surface in 3D

```kotlin
import space.ParametricSurface3D
import space.elements.Point3D
import utils.linspace
import utils.meshgrid

val sphere = object : ParametricSurface3D(
    xParametricFunction = { t, s -> Math.cos(t) * Math.sin(s) },
    yParametricFunction = { t, s -> Math.sin(t) * Math.sin(s) },
    zParametricFunction = { t, s -> Math.cos(s) }
) {}

val (T, S) = meshgrid(linspace(0.0, 2 * Math.PI, 40), linspace(0.0, Math.PI, 40))
// Evaluate mesh: surface(T, S) → Triple<X, Y, Z> matrices
```

---

## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).
