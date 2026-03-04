# 2D Geometry

The `plane` package contains all 2D types. Every concrete type implements the `Entity2D` interface, which provides a common contract for coordinates, matrix representations, transforms, and arithmetic.

---

## Type hierarchy

```
Entity2D (interface)
├── Point2D
├── VectorialEntity2D (sealed)
│   ├── Vector2D
│   └── Direction2D
└── PolarCoordinate

CoordinateSystem2D

Function2D (interface)
├── Polynomial
├── LinearSpline
└── CubicSpline

ParametricCurve2D
├── BezierCurve
└── CubicBezierSpline2D

Curve2D
Polygon2D
└── ConvexPolygon2D
```

---

## Entity2D contract

All 2D geometric objects expose:

| Member | Type | Description |
|---|---|---|
| `x` | `Double` | x-component |
| `y` | `Double` | y-component |
| `matrix` | `SimpleMatrix` | 2×1 column matrix |
| `affineMatrix` | `SimpleMatrix` | 3×1 affine column matrix |
| `rotate(angle)` | `Entity2D` | rotate around the origin |
| `rotate(center, angle)` | `Entity2D` | rotate around a given point |
| `changeBasis(from, to)` | `Entity2D` | express this entity in a different coordinate system |
| `+`, `-`, `*`, `/` | operators | entity-wise and scalar arithmetic |
| `component1()`, `component2()` | destructuring | `val (x, y) = point` |

---

## Pages

- [Primitives](primitives.md) — `Point2D`, `Vector2D`, `Direction2D`, `PolarCoordinate`
- [Coordinate Systems](coordinate-systems.md) — `CoordinateSystem2D`
- [Functions](functions.md) — `Polynomial`, `LinearSpline`, `CubicSpline`
- [Curves](curves.md) — `ParametricCurve2D`, `BezierCurve`, `CubicBezierSpline2D`
- [Polygons & Collections](polygons.md) — `Polygon2D`, `ConvexPolygon2D`, `Curve2D`
