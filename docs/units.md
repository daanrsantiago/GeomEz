# Units

## Angle

`Angle` is a sealed class with two subtypes: `Angle.Radians` and `Angle.Degrees`. All trigonometric-aware methods in GeomEz accept an `Angle`, so you never have to manually convert.

```kotlin
import units.Angle

val deg = Angle.Degrees(90.0)
val rad = Angle.Radians(Math.PI / 2)
```

### Conversion

```kotlin
val deg = Angle.Degrees(180.0)
val rad: Angle.Radians = deg.toRadians()   // Angle.Radians(π)

val rad2 = Angle.Radians(Math.PI)
val deg2: Angle.Degrees = rad2.toDegrees() // Angle.Degrees(180.0)
```

### Arithmetic

All arithmetic operators work between angles of the same or different subtypes. Mixed operations auto-convert the right-hand operand to the left-hand type.

```kotlin
val a = Angle.Degrees(90.0)
val b = Angle.Degrees(45.0)

val sum  = a + b          // Angle.Degrees(135.0)
val diff = a - b          // Angle.Degrees(45.0)
val half = a / 2.0        // Angle.Degrees(45.0)
val dbl  = a * 2.0        // Angle.Degrees(180.0)
val neg  = -a             // Angle.Degrees(-90.0)
```

### Adding a scalar

```kotlin
val a = Angle.Degrees(45.0)
val b = a + 10.0   // Angle.Degrees(55.0)
```

### Comparison

```kotlin
val a = Angle.Degrees(90.0)
val b = Angle.Radians(Math.PI / 4)   // 45°

println(a > b)    // true
println(a == b)   // false
```

### Usage in GeomEz

Wherever a rotation or angular parameter is accepted, pass any `Angle`:

```kotlin
// Rotate a point
val rotated = Point2D(1.0, 0.0).rotate(Angle.Degrees(90.0))

// Rotate a 3D direction around an axis
val rotated3D = Direction3D.MAIN_X_DIRECTION.rotate(
    axis  = Direction3D.MAIN_Z_DIRECTION,
    angle = Angle.Radians(Math.PI / 2)
)

// Define a smooth spline segment by its tangent angle
SmoothCubicBezierSplineControlPoints(
    pointOnCurve              = Point2D(0.0, 0.0),
    angle                     = Angle.Degrees(30.0),
    distanceControlPointAfter = 0.5
)
```
