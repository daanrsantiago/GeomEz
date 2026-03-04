# Primitivas 3D

## Point3D

Um ponto 3D imutável com igualdade segura para ponto flutuante (delta ≈ 1×10⁻¹⁰).

```kotlin
import space.elements.Point3D

val p = Point3D(1.0, 2.0, 3.0)
println(p.distanceFromOrigin)   // √(1² + 2² + 3²) ≈ 3.74
```

### Aritmética

```kotlin
val a = Point3D(1.0, 2.0, 3.0)
val b = Point3D(4.0, 5.0, 6.0)

val soma   = a + b        // Point3D(5.0, 7.0, 9.0)
val dif    = b - a        // Point3D(3.0, 3.0, 3.0)
val escalado = a * 2.0    // Point3D(2.0, 4.0, 6.0)
val neg    = -a           // Point3D(-1.0, -2.0, -3.0)
```

### Desestruturação

```kotlin
val (x, y, z) = Point3D(1.0, 2.0, 3.0)
```

### Distância

```kotlin
val d = Point3D(0.0, 0.0, 0.0).distanceBetween(Point3D(1.0, 2.0, 2.0))  // 3.0
```

### Rotação

A rotação usa um eixo (qualquer `VectorialEntity3D`) e um ângulo:

```kotlin
import space.elements.Direction3D
import units.Angle

val p = Point3D(1.0, 0.0, 0.0)

// Rotacionar 90° em torno do eixo z
val rotado = p.rotate(Direction3D.MAIN_Z_DIRECTION, Angle.Degrees(90.0))
// → Point3D(0.0, 1.0, 0.0)
```

### Conversão

```kotlin
// Para um Vector3D (posição na origem)
val v: Vector3D = p.asVector3D()

// Para coordenadas cilíndricas
val cil: CylindricalCoordinate = p.toCylindricalCoordinate()
```

---

## Vector3D

Um vetor 3D com posição de cauda explícita (`Point3D(0,0,0)` por padrão).

```kotlin
import space.elements.Vector3D
import space.elements.Point3D

// Apenas componentes (cauda na origem)
val v = Vector3D(1.0, 0.0, 0.0)

// Vetor ancorado
val v2 = Vector3D(0.0, 0.0, 1.0, position = Point3D(1.0, 1.0, 0.0))

// A partir de dois pontos
val v3 = Vector3D(
    headPosition = Point3D(3.0, 4.0, 5.0),
    tailPosition = Point3D(1.0, 2.0, 3.0)
)
```

### Propriedades

```kotlin
val v = Vector3D(0.0, 3.0, 4.0)
println(v.module)         // 5.0
println(v.direction)      // Direction3D(0.0, 0.6, 0.8)
println(v.headPosition)   // Point3D(0.0, 3.0, 4.0)
```

### Produto escalar e vetorial

```kotlin
val a = Vector3D(1.0, 0.0, 0.0)
val b = Vector3D(0.0, 1.0, 0.0)

println(a.dot(b))    // 0.0

val cross: Vector3D = a.cross(b)   // (0, 0, 1)
```

### Ângulo entre vetores

```kotlin
import units.Angle

val angulo: Angle.Radians = a.angleBetween(b)
```

---

## Direction3D

Um vetor de direção 3D unitário. Os componentes são normalizados automaticamente.

```kotlin
import space.elements.Direction3D

val d = Direction3D(1.0, 1.0, 0.0)  // normalizado para (√2/2, √2/2, 0)
println(d.module)                    // 1.0
```

### Constantes embutidas

```kotlin
Direction3D.MAIN_X_DIRECTION   // (1, 0, 0)
Direction3D.MAIN_Y_DIRECTION   // (0, 1, 0)
Direction3D.MAIN_Z_DIRECTION   // (0, 0, 1)
```

### Produto vetorial

```kotlin
val x = Direction3D.MAIN_X_DIRECTION
val y = Direction3D.MAIN_Y_DIRECTION

val z: Direction3D = x.cross(y)   // (0, 0, 1)
```

### Rotação

```kotlin
import units.Angle

val d = Direction3D.MAIN_X_DIRECTION

// Rotacionar 90° em torno do eixo z
val rotado = d.rotate(Direction3D.MAIN_Z_DIRECTION, Angle.Degrees(90.0))
// → Direction3D(0, 1, 0)
```

---

## CylindricalCoordinate

Representa um ponto na forma cilíndrica (raio, ângulo, z) e converte para `Point3D`.

```kotlin
import space.elements.CylindricalCoordinate
import units.Angle

val cil = CylindricalCoordinate(radius = 2.0, angle = Angle.Degrees(90.0), z = 3.0)
val p: Point3D = cil.toPoint3D()
```
