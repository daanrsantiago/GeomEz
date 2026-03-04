<p align="center">
  <img src="../assets/GeomEz-logo.png" alt="GeomEz Logo" width="200"/>
</p>

# GeomEz

[🇺🇸 English](..) | 🇧🇷 Português

**GeomEz** é uma biblioteca Kotlin para construir e visualizar elementos geométricos 2D e 3D. Oferece uma API limpa e expressiva para representar pontos, vetores, direções, sistemas de coordenadas, curvas, superfícies e polígonos — com suporte completo a transformações afins, cálculo analítico em funções e visualização opcional via matplotlib.

---

## Módulos

| Módulo | Artefato | Descrição |
|---|---|---|
| `geomez-core` | `com.geomez:geomez-core:1.0.0` | Tipos geométricos, primitivas matemáticas e operações. Sem dependência de renderização. |
| `geomez-visualization` | `com.geomez:geomez-visualization:1.0.0` | Adiciona `.plot()` e `.addPlotCommands()` a todos os tipos do core via Python matplotlib. |

---

## Destaques

- `Point2D`, `Vector2D`, `Direction2D` imutáveis e amigáveis à aritmética (e equivalentes 3D)
- Transformações afins — `rotate()`, `changeBasis()` — suportadas por matrizes 3×3 / 4×4
- Funções escalares com derivadas e integrais analíticas: `Polynomial`, `LinearSpline`, `CubicSpline`
- Curvas paramétricas: `BezierCurve`, `CubicBezierSpline2D`, `ParametricCurve3D`
- Superfícies paramétricas: `ParametricSurface3D` com cálculo numérico do vetor normal
- Igualdade segura para ponto flutuante via comparação com delta
- Visualização em uma linha para qualquer tipo

---

## Exemplo rápido

```kotlin
import plane.BezierCurve
import plane.elements.Point2D

val bezier = BezierCurve(listOf(
    Point2D(0.0, 0.0),
    Point2D(0.5, 1.5),
    Point2D(1.5, 1.5),
    Point2D(2.0, 0.0)
))

val pontoCentral = bezier(0.5)   // avalia em t = 0.5
bezier.plot()                    // abre uma janela matplotlib
```

---

## Primeiros passos

→ [Instalação](installation.md)
