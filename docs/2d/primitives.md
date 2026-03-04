# 2D Primitives

## Point2D

An immutable 2D point. Equality uses a delta comparison (≈ 1×10⁻¹⁰) for numerical safety.

```kotlin
val p = Point2D(3.0, 4.0)
println(p.x)                    // 3.0
println(p.y)                    // 4.0
println(p.distanceFromOrigin)   // 5.0
```

### Arithmetic

All standard arithmetic operators work element-wise or with a scalar:

```kotlin
val a = Point2D(1.0, 2.0)
val b = Point2D(3.0, 4.0)

val sum     = a + b         // Point2D(4.0, 6.0)
val diff    = b - a         // Point2D(2.0, 2.0)
val scaled  = a * 2.0       // Point2D(2.0, 4.0)
val halved  = b / 2.0       // Point2D(1.5, 2.0)
val neg     = -a            // Point2D(-1.0, -2.0)
```

### Destructuring

```kotlin
val (x, y) = Point2D(5.0, 7.0)
```

### Distance

```kotlin
val p1 = Point2D(0.0, 0.0)
val p2 = Point2D(3.0, 4.0)
println(p1.distanceBetween(p2))  // 5.0
```

### Rotation

```kotlin
import units.Angle

val p = Point2D(1.0, 0.0)

// Rotate 90° around the origin
val rotated = p.rotate(Angle.Degrees(90.0))
// → Point2D(0.0, 1.0)

// Rotate 45° around a custom center
val center  = Point2D(1.0, 1.0)
val rotated2 = p.rotate(center, Angle.Degrees(45.0))
```

### Conversion to 3D

```kotlin
val p3D = Point2D(1.0, 2.0).toPoint3D()        // z defaults to 0.0
val p3Dz = Point2D(1.0, 2.0).toPoint3D(z = 5.0)
```

---

## Vector2D

A 2D vector defined by its components and an optional tail position (`Point2D(0,0)` by default).

```kotlin
// Vector from origin with components (2, 3)
val v = Vector2D(2.0, 3.0)

// Vector anchored at a point
val v2 = Vector2D(1.0, 1.0, position = Point2D(2.0, 0.0))

// Construct from two points (head − tail)
val v3 = Vector2D(headPosition = Point2D(3.0, 4.0), tailPosition = Point2D(1.0, 1.0))
```

### Properties

```kotlin
val v = Vector2D(3.0, 4.0)
println(v.module)           // 5.0   (Euclidean magnitude)
println(v.direction)        // Direction2D(0.6, 0.8)
println(v.headPosition)     // Point2D(3.0, 4.0)  (when position is origin)
```

### Dot and cross product

```kotlin
val a = Vector2D(1.0, 0.0)
val b = Vector2D(0.0, 1.0)

println(a.dot(b))    // 0.0
val cross = a.cross(b)  // VectorialEntity3D pointing along z
```

### Angle between vectors

```kotlin
import units.Angle

val a = Vector2D(1.0, 0.0)
val b = Vector2D(0.0, 1.0)
val angle: Angle.Radians = a.angleBetween(b)
```

---

## Direction2D

A unit 2D direction vector. Components are automatically normalized on construction.

```kotlin
val d = Direction2D(3.0, 4.0)   // stored as (0.6, 0.8)
println(d.module)               // 1.0
```

### Built-in constants

```kotlin
Direction2D.MAIN_X_DIRECTION   // (1, 0)
Direction2D.MAIN_Y_DIRECTION   // (0, 1)
```

### Perpendicular direction

Returns the anti-clockwise perpendicular:

```kotlin
val right = Direction2D.MAIN_X_DIRECTION
val up    = right.perpendicularDirection()  // Direction2D(0, 1)
```

### Rotation

```kotlin
import units.Angle

val d = Direction2D.MAIN_X_DIRECTION
val rotated = d.rotate(Angle.Degrees(45.0))  // Direction2D(√2/2, √2/2)
```

---

## PolarCoordinate

Represents a point in polar form (radius, angle) and converts to `Point2D`.

```kotlin
import units.Angle

val polar = PolarCoordinate(radius = 5.0, angle = Angle.Degrees(53.13))
val cartesian: Point2D = polar.toPoint2D()
```
