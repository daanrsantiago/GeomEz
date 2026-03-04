# Coordinate Systems (3D)

## CoordinateSystem3D

A 3D coordinate system defined by three `Direction3D` axes and an origin `Point3D`. Internally it builds a 4×4 affine matrix for basis-change transforms.

```kotlin
import space.CoordinateSystem3D
import space.elements.Direction3D
import space.elements.Point3D
import units.Angle

// Standard right-handed Cartesian system
val standard = CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM

// Custom coordinate system
val custom = CoordinateSystem3D(
    xDirection = Direction3D(1.0, 0.0, 0.0),
    yDirection = Direction3D(0.0, 0.0, 1.0),   // y-axis pointing "up" = world z
    zDirection = Direction3D(0.0, -1.0, 0.0),  // z-axis pointing "into" = world -y
    origin     = Point3D(1.0, 2.0, 0.0)
)
```

### Properties

| Member | Description |
|---|---|
| `xDirection` | Unit x-axis |
| `yDirection` | Unit y-axis |
| `zDirection` | Unit z-axis |
| `origin` | Origin point |
| `matrix` | 3×3 rotation matrix |
| `affineMatrix` | 4×4 affine transform matrix |

### Rotation

```kotlin
val sys = CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM

// Rotate the system 45° around the z-axis
val rotated = sys.rotate(Direction3D.MAIN_Z_DIRECTION, Angle.Degrees(45.0))
```

![CoordinateSystem3D example](../img/coordinate-system-3d.png)

---

## Changing basis (3D)

Any `Entity3D` can be re-expressed in a different coordinate system:

```kotlin
val systemA = CoordinateSystem3D(
    xDirection = Direction3D.MAIN_X_DIRECTION,
    yDirection = Direction3D.MAIN_Z_DIRECTION,   // y → z
    zDirection = Direction3D.MAIN_Y_DIRECTION,   // z → y
    origin     = Point3D(1.0, 0.0, 0.0)
)

val point = Point3D(2.0, 0.0, 1.0)   // as written in systemA

val pointInMain = point.changeBasis(
    asWrittenIn = systemA,
    to          = CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM
)
```

The computation is:

```
to.affineMatrix⁻¹ × asWrittenIn.affineMatrix × point.affineMatrix
```

---

## Plane

A 3D plane defined by an origin point and two spanning directions. The normal direction is computed automatically as the cross product of the two span vectors.

```kotlin
import space.Plane
import space.elements.Direction3D
import space.elements.Point3D

val xzPlane = Plane(
    planeOrigin     = Point3D(0.0, 0.0, 0.0),
    planeXDirection = Direction3D.MAIN_X_DIRECTION,
    planeYDirection = Direction3D.MAIN_Z_DIRECTION
)

println(xzPlane.normalDirection)   // Direction3D(0, -1, 0) or (0, 1, 0)

// Check whether a point lies in the plane
println(xzPlane.pointIsInPlane(Point3D(3.0, 0.0, 5.0)))   // true
println(xzPlane.pointIsInPlane(Point3D(0.0, 1.0, 0.0)))   // false
```

### As a coordinate system

Every `Plane` exposes a `CoordinateSystem3D` for basis changes:

```kotlin
val coordinateSystem: CoordinateSystem3D = xzPlane.coordinateSystem3D
```
