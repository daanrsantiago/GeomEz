# Primitivas 2D

## Point2D

Um ponto 2D imutável. A igualdade usa comparação com delta (≈ 1×10⁻¹⁰) para segurança numérica.

```kotlin
val p = Point2D(3.0, 4.0)
println(p.x)                    // 3.0
println(p.y)                    // 4.0
println(p.distanceFromOrigin)   // 5.0
```

### Aritmética

Todos os operadores aritméticos padrão funcionam elemento a elemento ou com um escalar:

```kotlin
val a = Point2D(1.0, 2.0)
val b = Point2D(3.0, 4.0)

val soma    = a + b         // Point2D(4.0, 6.0)
val dif     = b - a         // Point2D(2.0, 2.0)
val escalado = a * 2.0      // Point2D(2.0, 4.0)
val metade  = b / 2.0       // Point2D(1.5, 2.0)
val neg     = -a            // Point2D(-1.0, -2.0)
```

### Desestruturação

```kotlin
val (x, y) = Point2D(5.0, 7.0)
```

### Distância

```kotlin
val p1 = Point2D(0.0, 0.0)
val p2 = Point2D(3.0, 4.0)
println(p1.distanceBetween(p2))  // 5.0
```

### Rotação

```kotlin
import units.Angle

val p = Point2D(1.0, 0.0)

// Rotacionar 90° em torno da origem
val rotado = p.rotate(Angle.Degrees(90.0))
// → Point2D(0.0, 1.0)

// Rotacionar 45° em torno de um centro personalizado
val centro   = Point2D(1.0, 1.0)
val rotado2  = p.rotate(centro, Angle.Degrees(45.0))
```

### Conversão para 3D

```kotlin
val p3D  = Point2D(1.0, 2.0).toPoint3D()        // z padrão = 0.0
val p3Dz = Point2D(1.0, 2.0).toPoint3D(z = 5.0)
```

---

## Vector2D

Um vetor 2D definido por seus componentes e uma posição de cauda opcional (`Point2D(0,0)` por padrão).

```kotlin
// Vetor da origem com componentes (2, 3)
val v = Vector2D(2.0, 3.0)

// Vetor ancorado em um ponto
val v2 = Vector2D(1.0, 1.0, position = Point2D(2.0, 0.0))

// Construir a partir de dois pontos (cabeça − cauda)
val v3 = Vector2D(headPosition = Point2D(3.0, 4.0), tailPosition = Point2D(1.0, 1.0))
```

### Propriedades

```kotlin
val v = Vector2D(3.0, 4.0)
println(v.module)           // 5.0   (magnitude euclidiana)
println(v.direction)        // Direction2D(0.6, 0.8)
println(v.headPosition)     // Point2D(3.0, 4.0)  (quando position é a origem)
```

### Produto escalar e vetorial

```kotlin
val a = Vector2D(1.0, 0.0)
val b = Vector2D(0.0, 1.0)

println(a.dot(b))      // 0.0
val cross = a.cross(b) // VectorialEntity3D apontando ao longo de z
```

### Ângulo entre vetores

```kotlin
import units.Angle

val a = Vector2D(1.0, 0.0)
val b = Vector2D(0.0, 1.0)
val angulo: Angle.Radians = a.angleBetween(b)
```

---

## Direction2D

Um vetor de direção 2D unitário. Os componentes são normalizados automaticamente na construção.

```kotlin
val d = Direction2D(3.0, 4.0)   // armazenado como (0.6, 0.8)
println(d.module)               // 1.0
```

### Constantes embutidas

```kotlin
Direction2D.MAIN_X_DIRECTION   // (1, 0)
Direction2D.MAIN_Y_DIRECTION   // (0, 1)
```

### Direção perpendicular

Retorna a perpendicular no sentido anti-horário:

```kotlin
val direita = Direction2D.MAIN_X_DIRECTION
val cima    = direita.perpendicularDirection()  // Direction2D(0, 1)
```

### Rotação

```kotlin
import units.Angle

val d = Direction2D.MAIN_X_DIRECTION
val rotado = d.rotate(Angle.Degrees(45.0))  // Direction2D(√2/2, √2/2)
```

---

## PolarCoordinate

Representa um ponto na forma polar (raio, ângulo) e converte para `Point2D`.

```kotlin
import units.Angle

val polar = PolarCoordinate(radius = 5.0, angle = Angle.Degrees(53.13))
val cartesiano: Point2D = polar.toPoint2D()
```
