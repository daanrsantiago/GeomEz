# Instalação

GeomEz é publicado no Maven Central sob o grupo `com.geomez`.

---

## Requisitos

- **JVM 21+**
- **Kotlin 2.x**
- **Python 3 + `matplotlib`** *(somente módulo de visualização)*

---

## Gradle (Kotlin DSL)

### Apenas Core

Use quando precisar somente dos tipos geométricos e operações, sem dependência de renderização.

```kotlin title="build.gradle.kts"
dependencies {
    implementation("com.geomez:geomez-core:1.0.0")
}
```

### Core + Visualização

Adiciona `.plot()` e `.addPlotCommands()` a todos os tipos do core. Requer um ambiente Python com `matplotlib` instalado.

```kotlin title="build.gradle.kts"
dependencies {
    implementation("com.geomez:geomez-core:1.0.0")
    implementation("com.geomez:geomez-visualization:1.0.0")
}
```

---

## Maven

### Apenas Core

```xml title="pom.xml"
<dependency>
    <groupId>com.geomez</groupId>
    <artifactId>geomez-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Core + Visualização

```xml title="pom.xml"
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

## Configuração do Python (visualização)

O módulo de visualização delega a renderização ao Python matplotlib via bridge [`matplotlib4k`](https://github.com/daniel-tucano/matplotlib4k). Instale o matplotlib no seu ambiente Python antes de usar qualquer chamada `.plot()`:

```bash
pip install matplotlib
```

Certifique-se de que o binário `python` (ou `python3`) está no `PATH` do seu sistema.
