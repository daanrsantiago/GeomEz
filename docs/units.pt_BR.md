# Unidades

## Angle

`Angle` é uma classe selada com dois subtipos: `Angle.Radians` e `Angle.Degrees`. Todos os métodos que lidam com trigonometria no GeomEz aceitam um `Angle`, então você nunca precisa converter manualmente.

```kotlin
import units.Angle

val graus = Angle.Degrees(90.0)
val rad   = Angle.Radians(Math.PI / 2)
```

### Conversão

```kotlin
val graus = Angle.Degrees(180.0)
val rad: Angle.Radians = graus.toRadians()   // Angle.Radians(π)

val rad2 = Angle.Radians(Math.PI)
val graus2: Angle.Degrees = rad2.toDegrees() // Angle.Degrees(180.0)
```

### Aritmética

Todos os operadores aritméticos funcionam entre ângulos do mesmo subtipo ou de subtipos diferentes. Operações mistas convertem automaticamente o operando do lado direito para o tipo do lado esquerdo.

```kotlin
val a = Angle.Degrees(90.0)
val b = Angle.Degrees(45.0)

val soma  = a + b          // Angle.Degrees(135.0)
val dif   = a - b          // Angle.Degrees(45.0)
val metade = a / 2.0       // Angle.Degrees(45.0)
val dobro  = a * 2.0       // Angle.Degrees(180.0)
val neg    = -a             // Angle.Degrees(-90.0)
```

### Adicionando um escalar

```kotlin
val a = Angle.Degrees(45.0)
val b = a + 10.0   // Angle.Degrees(55.0)
```

### Comparação

```kotlin
val a = Angle.Degrees(90.0)
val b = Angle.Radians(Math.PI / 4)   // 45°

println(a > b)    // true
println(a == b)   // false
```

### Uso no GeomEz

Em qualquer lugar onde um parâmetro de rotação ou angular é aceito, passe qualquer `Angle`:

```kotlin
// Rotacionar um ponto
val rotado = Point2D(1.0, 0.0).rotate(Angle.Degrees(90.0))

// Rotacionar uma direção 3D em torno de um eixo
val rotado3D = Direction3D.MAIN_X_DIRECTION.rotate(
    axis  = Direction3D.MAIN_Z_DIRECTION,
    angle = Angle.Radians(Math.PI / 2)
)

// Definir um segmento suave de spline pelo seu ângulo de tangente
SmoothCubicBezierSplineControlPoints(
    pointOnCurve              = Point2D(0.0, 0.0),
    angle                     = Angle.Degrees(30.0),
    distanceControlPointAfter = 0.5
)
```
