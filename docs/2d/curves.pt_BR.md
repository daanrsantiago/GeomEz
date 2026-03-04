# Curvas Paramétricas (2D)

## ParametricCurve2D

A classe base para curvas paramétricas 2D. Uma `ParametricCurve2D` contém duas instâncias de `Function2D` — uma para x(t) e outra para y(t) — e avalia a curva para t ∈ [0, 1].

```kotlin
import plane.ParametricCurve2D
import plane.functions.Polynomial

// Círculo: x(t) = cos(2πt), y(t) = sin(2πt)  (aproximado com polinômios aqui)
val circulo = ParametricCurve2D(
    xParametricFunction = Polynomial(listOf(1.0, 0.0, -2.0)),  // qualquer Function2D
    yParametricFunction = Polynomial(listOf(0.0, 1.0, -1.0))
)

val ponto: Point2D = circulo(0.25)   // avaliar em t = 0.25
```

### Avaliação

```kotlin
// Parâmetro único
val p = curva(0.5)

// Múltiplos parâmetros de uma vez
val pontos: List<Point2D> = curva(listOf(0.0, 0.25, 0.5, 0.75, 1.0))
```

### Derivada e integração

```kotlin
// dy/dx para um dado t (via regra da cadeia)
val inclinacao = curva.derivative(0.5)

// Integral de comprimento de arco aproximada (numérica, padrão 200 pontos)
val comprimento  = curva.integrate(0.0, 1.0)
val comprimento2 = curva.integrate(0.0, 0.5, nPoints = 500u)
```

---

## BezierCurve

Uma curva de Bézier de qualquer grau definida por uma lista de pontos de controle. A curva passa pelo **primeiro e último** ponto de controle; os pontos de controle intermediários atuam como atratores.

Internamente, as funções paramétricas x e y são construídas a partir de [polinômios de base de Bernstein](https://en.wikipedia.org/wiki/Bernstein_polynomial).

### Construção

```kotlin
import plane.BezierCurve
import plane.elements.Point2D

// Bézier cúbica (4 pontos de controle)
val bezier = BezierCurve(listOf(
    Point2D(0.0, 0.0),   // início (na curva)
    Point2D(0.5, 2.0),   // ponto de controle 1
    Point2D(1.5, 2.0),   // ponto de controle 2
    Point2D(2.0, 0.0)    // fim (na curva)
))
```

Qualquer número de pontos de controle é aceito; o grau é igual a `n - 1` onde `n` é a quantidade.

```kotlin
// Linear (2 pontos) — linha reta
val linha = BezierCurve(listOf(Point2D(0.0, 0.0), Point2D(1.0, 1.0)))

// Quadrática (3 pontos)
val quad = BezierCurve(listOf(
    Point2D(0.0, 0.0),
    Point2D(1.0, 2.0),
    Point2D(2.0, 0.0)
))
```

### Avaliação

```kotlin
val inicio = bezier(0.0)   // sempre igual a controlPoints.first()
val fim    = bezier(1.0)   // sempre igual a controlPoints.last()
val meio   = bezier(0.5)
```

### Acessando pontos de controle

```kotlin
bezier.controlPoints.forEach { println(it) }
```

### Visualização

```kotlin
// requer geomez-visualization
bezier.plot()   // mostra curva + polígono de controle com linhas tracejadas
```

![Exemplo BezierCurve](../img/bezier-curve.png)

---

## CubicBezierSpline2D

Uma spline cúbica de Bézier por partes: uma sequência de segmentos cúbicos de Bézier unidos em extremidades compartilhadas.

### Layout dos pontos de controle

A lista plana de pontos de controle deve ter **4 + 3n** pontos (onde n ≥ 0 é o número de segmentos adicionais):

```
[P0, P1, P2, P3 | P4, P5, P6 | P7, P8, P9 | ...]
 ← segmento 0 →   ← seg 1 →    ← seg 2 →
```

P3 do segmento 0 e P3 do segmento 0 são o mesmo ponto que o início do segmento 1 (P3 = início do segmento 1).

```kotlin
import plane.CubicBezierSpline2D
import plane.elements.Point2D

// Dois segmentos cúbicos de Bézier (7 = 4 + 3×1 pontos de controle)
val spline = CubicBezierSpline2D(listOf(
    Point2D(0.0, 0.0),   // início seg 0 (na curva)
    Point2D(0.3, 1.0),   // pc1 seg 0
    Point2D(0.7, 1.0),   // pc2 seg 0
    Point2D(1.0, 0.0),   // fim seg 0 = início seg 1 (na curva)
    Point2D(1.3, -1.0),  // pc1 seg 1
    Point2D(1.7, -1.0),  // pc2 seg 1
    Point2D(2.0, 0.0)    // fim seg 1 (na curva)
))

val p = spline(0.5)   // t global ∈ [0,1]
```

### Construindo a partir de segmentos suaves

`SmoothCubicBezierSplineControlPoints` permite especificar cada âncora por sua posição e ângulo de tangente, de modo que os pontos de controle são derivados automaticamente:

```kotlin
import plane.CubicBezierSpline2D
import plane.elements.Point2D
import plane.elements.SmoothCubicBezierSplineControlPoints
import units.Angle

val spline = CubicBezierSpline2D(listOf(
    SmoothCubicBezierSplineControlPoints(
        pointOnCurve              = Point2D(0.0, 0.0),
        angle                     = Angle.Degrees(0.0),
        distanceControlPointBefore = null,    // primeiro segmento: sem alça "antes"
        distanceControlPointAfter  = 0.5
    ),
    SmoothCubicBezierSplineControlPoints(
        pointOnCurve              = Point2D(2.0, 1.0),
        angle                     = Angle.Degrees(30.0),
        distanceControlPointBefore = 0.5,
        distanceControlPointAfter  = 0.5
    ),
    SmoothCubicBezierSplineControlPoints(
        pointOnCurve              = Point2D(4.0, 0.0),
        angle                     = Angle.Degrees(0.0),
        distanceControlPointBefore = 0.5,
        distanceControlPointAfter  = null     // último segmento: sem alça "depois"
    )
))
```

### Acessando segmentos individuais

```kotlin
spline.bezierCurves.forEachIndexed { i, segmento ->
    println("Segmento $i: ${segmento.controlPoints}")
}
```

### Visualização

```kotlin
// requer geomez-visualization
spline.plot()
```

![Exemplo CubicBezierSpline2D](../img/cubic-bezier-spline.png)
