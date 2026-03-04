# Installation

GeomEz is published to Maven Central under the group `com.geomez`.

---

## Requirements

- **JVM 21+**
- **Kotlin 2.x**
- **Python 3 + `matplotlib`** *(visualization module only)*

---

## Gradle (Kotlin DSL)

### Core only

Use this when you only need geometric types and operations, with no rendering dependency.

```kotlin title="build.gradle.kts"
dependencies {
    implementation("com.geomez:geomez-core:1.0.0")
}
```

### Core + Visualization

Adds `.plot()` and `.addPlotCommands()` to every core type. Requires a Python environment with `matplotlib` installed.

```kotlin title="build.gradle.kts"
dependencies {
    implementation("com.geomez:geomez-core:1.0.0")
    implementation("com.geomez:geomez-visualization:1.0.0")
}
```

---

## Maven

### Core only

```xml title="pom.xml"
<dependency>
    <groupId>com.geomez</groupId>
    <artifactId>geomez-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Core + Visualization

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

## Python setup (visualization)

The visualization module delegates rendering to Python matplotlib via the [`matplotlib4k`](https://github.com/daniel-tucano/matplotlib4k) bridge. Install matplotlib in your Python environment before using any `.plot()` call:

```bash
pip install matplotlib
```

Make sure the `python` (or `python3`) binary is on your system `PATH`.
