# Geometria 2D

O pacote `plane` contém todos os tipos 2D. Cada tipo concreto implementa a interface `Entity2D`, que fornece um contrato comum para coordenadas, representações matriciais, transformações e aritmética.

---

## Hierarquia de tipos

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

## Contrato Entity2D

Todos os objetos geométricos 2D expõem:

| Membro | Tipo | Descrição |
|---|---|---|
| `x` | `Double` | componente x |
| `y` | `Double` | componente y |
| `matrix` | `SimpleMatrix` | matriz coluna 2×1 |
| `affineMatrix` | `SimpleMatrix` | matriz coluna afim 3×1 |
| `rotate(angle)` | `Entity2D` | rotacionar em torno da origem |
| `rotate(center, angle)` | `Entity2D` | rotacionar em torno de um ponto dado |
| `changeBasis(from, to)` | `Entity2D` | expressar a entidade em outro sistema de coordenadas |
| `+`, `-`, `*`, `/` | operadores | aritmética elemento a elemento e com escalares |
| `component1()`, `component2()` | desestruturação | `val (x, y) = ponto` |

---

## Páginas

- [Primitivas](primitives.md) — `Point2D`, `Vector2D`, `Direction2D`, `PolarCoordinate`
- [Sistemas de Coordenadas](coordinate-systems.md) — `CoordinateSystem2D`
- [Funções](functions.md) — `Polynomial`, `LinearSpline`, `CubicSpline`
- [Curvas](curves.md) — `ParametricCurve2D`, `BezierCurve`, `CubicBezierSpline2D`
- [Polígonos e Coleções](polygons.md) — `Polygon2D`, `ConvexPolygon2D`, `Curve2D`
