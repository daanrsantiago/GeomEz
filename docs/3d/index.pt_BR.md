# Geometria 3D

O pacote `space` espelha o pacote 2D `plane`, adicionando um eixo z e atualizando as transformações para matrizes afins 4×4.

---

## Hierarquia de tipos

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

## Contrato Entity3D

Todos os objetos geométricos 3D expõem:

| Membro | Tipo | Descrição |
|---|---|---|
| `x`, `y`, `z` | `Double` | componentes |
| `matrix` | `SimpleMatrix` | matriz coluna 3×1 |
| `affineMatrix` | `SimpleMatrix` | matriz coluna afim 4×1 |
| `rotate(axis, angle)` | `Entity3D` | rotacionar em torno de um eixo arbitrário |
| `changeBasis(from, to)` | `Entity3D` | expressar em outro sistema de coordenadas |
| `+`, `-`, `*`, `/` | operadores | aritmética elemento a elemento e com escalares |
| `component1/2/3()` | desestruturação | `val (x, y, z) = ponto` |

---

## Páginas

- [Primitivas](primitives.md) — `Point3D`, `Vector3D`, `Direction3D`, `CylindricalCoordinate`
- [Sistemas de Coordenadas](coordinate-systems.md) — `CoordinateSystem3D`, `Plane`
- [Curvas e Superfícies](curves-and-surfaces.md) — `ParametricCurve3D`, `ParametricSurface3D`, `Curve3D`
