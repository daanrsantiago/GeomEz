# Visualização

O módulo `geomez-visualization` estende cada tipo do core com dois métodos de renderização, suportados pelo Python matplotlib via bridge [`matplotlib4k`](https://github.com/daniel-tucano/matplotlib4k).

---

## Configuração

Adicione o módulo às suas dependências (veja [Instalação](installation.md)) e certifique-se de que o Python com matplotlib está disponível no seu `PATH`:

```bash
pip install matplotlib
```

---

## API de dois métodos

Cada tipo geométrico recebe duas funções de extensão:

| Método | Descrição |
|---|---|
| `.plot(...)` | Plot rápido independente — abre uma janela matplotlib imediatamente |
| `.addPlotCommands(figure?, axes?, ...)` | Adiciona comandos de desenho a um par figura/eixos existente (ou novo); retorna `Pair<Figure, Axes>` |

---

## Plots rápidos

Cada tipo abre uma janela matplotlib com valores padrão adequados:

```kotlin
// 2D
point2DList.plot()
polygon.plot()
bezierCurve.plot()
cubicSpline.plot()
parametricCurve.plot()
vector2D.plot()
direction2D.plot()
coordinateSystem2D.plot()

// 3D
points3DList.plot()
vector3D.plot()
direction3D.plot()
coordinateSystem3D.plot()
parametricSurface.plot()
```

---

## Compondo múltiplos elementos

Use `addPlotCommands()` para sobrepor elementos em uma única figura. Todas as chamadas retornam `Pair<Figure, Axes>` para que você possa encadeá-las:

```kotlin
import io.github.danielTucano.matplotlib.pyplot.show
import io.github.danielTucano.python.pythonExecution
import plane.BezierCurve
import plane.CoordinateSystem2D
import plane.Polygon2D
import plane.elements.Point2D

pythonExecution {
    val poligono = Polygon2D(listOf(
        Point2D(0.0, 0.0),
        Point2D(2.0, 0.0),
        Point2D(1.0, 2.0)
    ))

    val bezier = BezierCurve(listOf(
        Point2D(0.0, 0.0),
        Point2D(0.5, 1.5),
        Point2D(1.5, 1.5),
        Point2D(2.0, 0.0)
    ))

    // Primeira chamada cria a figura e os eixos
    val (fig, ax) = poligono.addPlotCommands()

    // Chamadas seguintes os reutilizam
    bezier.addPlotCommands(fig, ax)
    CoordinateSystem2D.MAIN_2D_COORDINATE_SYSTEM.addPlotCommands(fig, ax)

    show()
}
```

---

## Composição 3D

Plots 3D funcionam exatamente da mesma forma. Os eixos retornados são uma instância de `Axes3D`:

```kotlin
import io.github.danielTucano.matplotlib.pyplot.show
import io.github.danielTucano.python.pythonExecution
import space.CoordinateSystem3D
import space.elements.Direction3D
import space.elements.Point3D
import space.elements.Vector3D

pythonExecution {
    val v = Vector3D(1.0, 0.0, 1.0, position = Point3D(1.0, 1.0, 1.0))
    val (fig, ax) = v.addPlotCommands()

    Direction3D.MAIN_Z_DIRECTION.addPlotCommands(fig, ax)
    CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM.addPlotCommands(fig, ax)

    show()
}
```

---

## Opções de plot do BezierCurve

As funções de plot do `BezierCurve` aceitam um parâmetro `tList` para controlar a densidade de amostragem:

```kotlin
import utils.linspace

// Maior resolução
bezierCurve.plot(tList = linspace(0.0, 1.0, 500))

// Ou ao compor
bezierCurve.addPlotCommands(fig, ax, tList = linspace(0.0, 1.0, 500))
```

O plot sempre renderiza o polígono de controle (tracejado) ao lado da curva.

---

## Extensões disponíveis

| Arquivo | Tipos cobertos |
|---|---|
| `plane/bezierCurveExtensions.kt` | `BezierCurve` |
| `plane/cubicSplineExtensions.kt` | `CubicSpline` |
| `plane/parametricCurve2DExtensions.kt` | `ParametricCurve2D` |
| `plane/piecewiseCubicBezierCurveExtensions.kt` | `CubicBezierSpline2D` |
| `plane/polygon2DExtensions.kt` | `Polygon2D` |
| `plane/point2DListExtensions.kt` | `List<Point2D>` |
| `plane/vector2DExtensions.kt` | `Vector2D` |
| `plane/direction2DExtensions.kt` | `Direction2D` |
| `plane/coordinateSystem2DExtensions.kt` | `CoordinateSystem2D` |
| `space/parametricCurve3DExtensions.kt` | `ParametricCurve3D` |
| `space/parametricSurface3DExtensions.kt` | `ParametricSurface3D` |
| `space/points3DListExtensions.kt` | `List<Point3D>` |
| `space/vector3DExtensions.kt` | `Vector3D` |
| `space/vector3DListExtensions.kt` | `List<Vector3D>` |
| `space/direction3DExtensions.kt` | `Direction3D` |
| `space/coordinateSystem3DExtensions.kt` | `CoordinateSystem3D` |
