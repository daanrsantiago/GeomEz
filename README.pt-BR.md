<p align="center">
  <img src="docs/assets/GeomEz-logo.png" alt="GeomEz Logo" width="200"/>
</p>

# GeomEz

[🇺🇸 English](README.md) | 🇧🇷 Português

[![Maven Central](https://img.shields.io/maven-central/v/com.geomez/geomez-core?label=Maven%20Central)](https://central.sonatype.com/artifact/com.geomez/geomez-core)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.21-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Documentation](https://img.shields.io/badge/docs-GitHub%20Pages-blue)](https://daanrsantiago.github.io/GeomEz)

Uma biblioteca Kotlin fácil de usar para construir e visualizar elementos geométricos 2D e 3D.

**[Documentação completa →](https://daanrsantiago.github.io/GeomEz)**

---

## Funcionalidades

- **Primitivas 2D e 3D** — `Point2D`, `Vector2D`, `Direction2D`, `Point3D`, `Vector3D`, `Direction3D`
- **Sistemas de coordenadas** — `CoordinateSystem2D` / `CoordinateSystem3D` com suporte completo a transformações afins (`rotate()`, `changeBasis()`)
- **Funções escalares** — `Polynomial`, `LinearSpline`, `CubicSpline` com derivadas e integrais analíticas
- **Curvas paramétricas** — `ParametricCurve2D`, `BezierCurve`, `CubicBezierSpline2D`, `ParametricCurve3D`
- **Superfícies paramétricas** — `ParametricSurface3D` com cálculo numérico do vetor normal
- **Utilitários de polígonos** — `Polygon2D`, `ConvexPolygon2D`, envoltória convexa
- **Geometria plana em 3D** — `Plane`, `Function2DInPlane`
- **Unidade de ângulo** — `Angle.Radians` / `Angle.Degrees` com aritmética completa e conversão
- **Visualização** — `.plot()` e `.addPlotCommands()` em todos os tipos via bridge Python matplotlib (módulo opcional)

---

## Requisitos

- JVM 21+
- Kotlin 2.x
- **Somente módulo de visualização:** Python 3 com `matplotlib` instalado

---

## Instalação

### Gradle (Kotlin DSL)

**Apenas Core** (tipos geométricos, matemática, sem dependência de renderização):
```kotlin
dependencies {
    implementation("com.geomez:geomez-core:1.0.0")
}
```

**Core + Visualização** (adiciona `.plot()` / `.addPlotCommands()` a todos os tipos):
```kotlin
dependencies {
    implementation("com.geomez:geomez-core:1.0.0")
    implementation("com.geomez:geomez-visualization:1.0.0")
}
```

### Maven

**Apenas Core:**
```xml
<dependency>
    <groupId>com.geomez</groupId>
    <artifactId>geomez-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

**Core + Visualização:**
```xml
<dependency>
    <groupId>com.geomez</groupId>
    <artifactId>geomez-core</artifactId>
    <version>1.0.0</version>
</dependency>
<dependency>
    <groupId>com.geomez</groupId>
    <artifactId>geomez-visualization</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## Módulos

### `geomez-core`

Geometria pura — tipos, primitivas matemáticas e operações sem dependências de renderização. Adequado para uso em qualquer projeto JVM.

Inclui as hierarquias de tipos completas 2D (pacote `plane`) e 3D (pacote `space`), tipos de funções escalares/paramétricas, utilitários de polígonos e a unidade `Angle`.

### `geomez-visualization`

Funções de extensão que adicionam `.plot()` e `.addPlotCommands()` a todos os tipos do core. A renderização é delegada ao Python matplotlib via [`matplotlib4k`](https://github.com/daniel-tucano/matplotlib4k), portanto é necessário um ambiente Python com `matplotlib` instalado.

- **`.plot()`** — plot rápido independente; abre uma janela matplotlib imediatamente.
- **`.addPlotCommands(figure, axes)`** — retorna `Pair<Figure, Axes>` para compor múltiplos elementos em uma única figura.

---

## Início Rápido

### Aritmética e rotação de ponto 2D

```kotlin
import plane.elements.Point2D
import units.Angle

val p1 = Point2D(1.0, 2.0)
val p2 = Point2D(3.0, 4.0)

val soma     = p1 + p2               // Point2D(4.0, 6.0)
val escalado = p1 * 2.0              // Point2D(2.0, 4.0)
val rotado   = p1.rotate(Angle.Degrees(90.0))
```

### Curva de Bézier

```kotlin
import plane.BezierCurve
import plane.elements.Point2D

val bezier = BezierCurve(listOf(
    Point2D(0.0, 0.0),
    Point2D(0.5, 1.5),
    Point2D(1.5, 1.5),
    Point2D(2.0, 0.0)
))

val pontoNoMeio = bezier(0.5)  // Point2D na curva em t = 0.5

// Visualizar (requer geomez-visualization + Python matplotlib)
bezier.plot()
```

### Spline cúbica com derivada e integral

```kotlin
import plane.elements.Point2D
import plane.functions.CubicSpline

val spline = CubicSpline(listOf(
    Point2D(0.0, 0.0),
    Point2D(1.0, 2.0),
    Point2D(2.0, 1.5),
    Point2D(3.0, 3.0)
))

val y      = spline(1.5)               // valor y interpolado
val slope  = spline.derivative(1.5)    // dy/dx em x = 1.5
val area   = spline.integrate(0.0, 3.0)
```

### Compondo múltiplos elementos em um único plot

```kotlin
import io.github.danielTucano.matplotlib.pyplot.show
import io.github.danielTucano.python.pythonExecution
import plane.CoordinateSystem2D
import plane.Polygon2D
import plane.elements.Point2D

pythonExecution {
    val poligono = Polygon2D(listOf(
        Point2D(0.0, 0.0),
        Point2D(1.0, 1.5),
        Point2D(2.0, 0.0)
    ))
    val (fig, ax) = poligono.addPlotCommands()
    CoordinateSystem2D.MAIN_2D_COORDINATE_SYSTEM.addPlotCommands(fig, ax)
    show()
}
```

### Superfície paramétrica em 3D

```kotlin
import space.ParametricSurface3D
import space.elements.Point3D
import utils.linspace
import utils.meshgrid

val esfera = object : ParametricSurface3D(
    xParametricFunction = { t, s -> Math.cos(t) * Math.sin(s) },
    yParametricFunction = { t, s -> Math.sin(t) * Math.sin(s) },
    zParametricFunction = { t, s -> Math.cos(s) }
) {}

val (T, S) = meshgrid(linspace(0.0, 2 * Math.PI, 40), linspace(0.0, Math.PI, 40))
// Avaliar malha: esfera(T, S) → Triple<X, Y, Z> matrizes
```

---

## Licença

Este projeto está licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT).
