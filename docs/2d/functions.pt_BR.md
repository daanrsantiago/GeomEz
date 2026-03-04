# Funções Escalares (2D)

A interface `Function2D` representa uma função escalar **f(x) → Double** e é a base para splines, curvas paramétricas e curvas de Bézier.

## Interface Function2D

```kotlin
interface Function2D {
    operator fun invoke(x: Double): Double
    operator fun invoke(xArray: DoubleArray): List<Double>
    operator fun invoke(xCollection: Collection<Double>): List<Double>
    fun derivative(x: Double): Double
    fun integrate(xStart: Double, xEnd: Double): Double
    fun tangentDirection(x: Double): Direction2D
    fun normalDirection(x: Double): Direction2D
}
```

---

## Polynomial

Um polinômio cujos coeficientes são ordenados do **menor grau para o maior**:

```
Polynomial([a0, a1, a2, a3]) = a0 + a1·x + a2·x² + a3·x³
```

```kotlin
import plane.functions.Polynomial

// p(x) = 1 + 2x + 3x²
val p = Polynomial(listOf(1.0, 2.0, 3.0))

println(p(2.0))          // 1 + 4 + 12 = 17.0
println(p.order)         // 2
```

### Derivada e integral

```kotlin
val p = Polynomial(listOf(1.0, 2.0, 3.0))   // 1 + 2x + 3x²

// Derivada analítica → 2 + 6x
val dp = p.derivative()
println(dp(1.0))   // 8.0

// Integral indefinida analítica (constante de integração = 0)
val ip = p.integral()

// Integral definida entre limites
val area = p.integrate(0.0, 2.0)
```

### Aritmética entre polinômios

```kotlin
val p1 = Polynomial(listOf(1.0, 2.0))   // 1 + 2x
val p2 = Polynomial(listOf(0.0, 1.0))   // x

val soma    = p1 + p2          // 1 + 3x
val produto = p1 * p2          // x + 2x²
val quadrado = p1 pow 2        // (1 + 2x)²
```

### Aritmética com escalares

```kotlin
val p = Polynomial(listOf(1.0, 2.0, 3.0))

val deslocado = p + 5.0    // adiciona 5 ao termo constante
val escalado  = p * 2.0    // multiplica cada coeficiente por 2
val metade    = p / 2.0
```

---

## LinearSpline

Interpolação linear por partes através de um conjunto de nós `Point2D`. Os valores de x devem ser estritamente crescentes.

```kotlin
import plane.functions.LinearSpline
import plane.elements.Point2D

val spline = LinearSpline(listOf(
    Point2D(0.0, 0.0),
    Point2D(1.0, 2.0),
    Point2D(2.0, 1.0),
    Point2D(3.0, 3.0)
))

println(spline(0.5))              // 1.0  (ponto médio do primeiro segmento)
println(spline.derivative(0.5))   // 2.0  (inclinação do primeiro segmento)
println(spline.integrate(0.0, 3.0))
```

!!! note
    A derivada é constante dentro de cada segmento e indefinida nos nós (retorna a inclinação do segmento à esquerda).

---

## CubicSpline

Uma spline cúbica natural (segunda derivada = 0 nos dois extremos) ajustada a um conjunto de nós `Point2D`. Requer **pelo menos 4 pontos** com valores de x estritamente crescentes.

Cada segmento entre dois nós consecutivos é representado como um `Polynomial` de grau ≤ 3.

```kotlin
import plane.functions.CubicSpline
import plane.elements.Point2D

val spline = CubicSpline(listOf(
    Point2D(0.0, 0.0),
    Point2D(1.0, 2.0),
    Point2D(2.0, 1.5),
    Point2D(3.0, 3.0),
    Point2D(4.0, 1.0)
))

val y     = spline(1.5)               // valor interpolado
val slope = spline.derivative(1.5)    // primeira derivada suave
val area  = spline.integrate(0.0, 4.0)
```

### Acessando os polinômios subjacentes

```kotlin
spline.polynomials.forEachIndexed { i, poly ->
    println("Segmento $i: coeficientes = ${poly.coefficients}")
}
```

### Visualização

```kotlin
// requer geomez-visualization
spline.plot()
```

A linha tracejada azul mostra uma `LinearSpline` e a linha vermelha uma `CubicSpline` pelos mesmos pontos de nó (pontos pretos):

![Comparação de splines](../img/spline-comparison.png)

---

## Direções tangente e normal

Todas as implementações de `Function2D` expõem:

```kotlin
val spline = CubicSpline(pontos)

// Direção tangente unitária em x = 1.5
val tangente = spline.tangentDirection(1.5)   // Direction2D

// Direção normal unitária (perpendicular, anti-horária)
val normal   = spline.normalDirection(1.5)    // Direction2D
```
