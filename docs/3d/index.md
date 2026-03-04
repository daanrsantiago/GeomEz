# 3D Geometry

The `space` package mirrors the 2D `plane` package, adding a z-axis and upgrading transforms to 4×4 affine matrices.

---

## Type hierarchy

```
Entity3D (interface)
├── Point3D
└── VectorialEntity3D (sealed)
    ├── Vector3D
    └── Direction3D

CoordinateSystem3D
CylindricalCoordinate

Function3D (interface)
Function4D (interface)

ParametricSurface3D
ParametricCurve3D
Plane
Curve3D
```

---

## Entity3D contract

All 3D geometric objects expose:

| Member | Type | Description |
|---|---|---|
| `x`, `y`, `z` | `Double` | components |
| `matrix` | `SimpleMatrix` | 3×1 column matrix |
| `affineMatrix` | `SimpleMatrix` | 4×1 affine column matrix |
| `rotate(axis, angle)` | `Entity3D` | rotate around an arbitrary axis |
| `changeBasis(from, to)` | `Entity3D` | express in a different coordinate system |
| `+`, `-`, `*`, `/` | operators | element-wise and scalar arithmetic |
| `component1/2/3()` | destructuring | `val (x, y, z) = point` |

---

## Pages

- [Primitives](primitives.md) — `Point3D`, `Vector3D`, `Direction3D`, `CylindricalCoordinate`
- [Coordinate Systems](coordinate-systems.md) — `CoordinateSystem3D`, `Plane`
- [Curves & Surfaces](curves-and-surfaces.md) — `ParametricCurve3D`, `ParametricSurface3D`, `Curve3D`
