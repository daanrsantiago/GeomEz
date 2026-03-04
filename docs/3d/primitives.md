# 3D Primitives

## Point3D

An immutable 3D point with floating-point–safe equality (delta ≈ 1×10⁻¹⁰).

```kotlin
import space.elements.Point3D

val p = Point3D(1.0, 2.0, 3.0)
println(p.distanceFromOrigin)   // √(1² + 2² + 3²) ≈ 3.74
```

### Arithmetic

```kotlin
val a = Point3D(1.0, 2.0, 3.0)
val b = Point3D(4.0, 5.0, 6.0)

val sum    = a + b        // Point3D(5.0, 7.0, 9.0)
val diff   = b - a        // Point3D(3.0, 3.0, 3.0)
val scaled = a * 2.0      // Point3D(2.0, 4.0, 6.0)
val neg    = -a           // Point3D(-1.0, -2.0, -3.0)
```

### Destructuring

```kotlin
val (x, y, z) = Point3D(1.0, 2.0, 3.0)
```

### Distance

```kotlin
val d = Point3D(0.0, 0.0, 0.0).distanceBetween(Point3D(1.0, 2.0, 2.0))  // 3.0
```

### Rotation

Rotation uses an axis (any `VectorialEntity3D`) and an angle:

```kotlin
import space.elements.Direction3D
import units.Angle

val p = Point3D(1.0, 0.0, 0.0)

// Rotate 90° around the z-axis
val rotated = p.rotate(Direction3D.MAIN_Z_DIRECTION, Angle.Degrees(90.0))
// → Point3D(0.0, 1.0, 0.0)
```

### Conversion

```kotlin
// To a Vector3D (position at origin)
val v: Vector3D = p.asVector3D()

// To cylindrical coordinates
val cyl: CylindricalCoordinate = p.toCylindricalCoordinate()
```

---

## Vector3D

A 3D vector with an explicit tail position (`Point3D(0,0,0)` by default).

```kotlin
import space.elements.Vector3D
import space.elements.Point3D

// Components only (tail at origin)
val v = Vector3D(1.0, 0.0, 0.0)

// Anchored vector
val v2 = Vector3D(0.0, 0.0, 1.0, position = Point3D(1.0, 1.0, 0.0))

// From two points
val v3 = Vector3D(
    headPosition = Point3D(3.0, 4.0, 5.0),
    tailPosition = Point3D(1.0, 2.0, 3.0)
)
```

### Properties

```kotlin
val v = Vector3D(0.0, 3.0, 4.0)
println(v.module)         // 5.0
println(v.direction)      // Direction3D(0.0, 0.6, 0.8)
println(v.headPosition)   // Point3D(0.0, 3.0, 4.0)
```

### Dot and cross product

```kotlin
val a = Vector3D(1.0, 0.0, 0.0)
val b = Vector3D(0.0, 1.0, 0.0)

println(a.dot(b))    // 0.0

val cross: Vector3D = a.cross(b)   // (0, 0, 1)
```

### Angle between vectors

```kotlin
import units.Angle

val angle: Angle.Radians = a.angleBetween(b)
```

---

## Direction3D

A unit 3D direction vector. Components are automatically normalized.

```kotlin
import space.elements.Direction3D

val d = Direction3D(1.0, 1.0, 0.0)  // normalized to (√2/2, √2/2, 0)
println(d.module)                    // 1.0
```

### Built-in constants

```kotlin
Direction3D.MAIN_X_DIRECTION   // (1, 0, 0)
Direction3D.MAIN_Y_DIRECTION   // (0, 1, 0)
Direction3D.MAIN_Z_DIRECTION   // (0, 0, 1)
```

### Cross product

```kotlin
val x = Direction3D.MAIN_X_DIRECTION
val y = Direction3D.MAIN_Y_DIRECTION

val z: Direction3D = x.cross(y)   // (0, 0, 1)
```

### Rotation

```kotlin
import units.Angle

val d = Direction3D.MAIN_X_DIRECTION

// Rotate 90° around the z-axis
val rotated = d.rotate(Direction3D.MAIN_Z_DIRECTION, Angle.Degrees(90.0))
// → Direction3D(0, 1, 0)
```

---

## CylindricalCoordinate

Represents a point in cylindrical form (radius, angle, z) and converts to `Point3D`.

```kotlin
import space.elements.CylindricalCoordinate
import units.Angle

val cyl = CylindricalCoordinate(radius = 2.0, angle = Angle.Degrees(90.0), z = 3.0)
val p: Point3D = cyl.toPoint3D()
```
