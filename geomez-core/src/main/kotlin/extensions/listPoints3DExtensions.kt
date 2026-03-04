package extensions

import space.elements.Point3D

// ── List<Point3D> extensions ─────────────────────────────────────────────────

/** Returns a list of the x coordinates of all points. */
val List<Point3D>.xValues: List<Double>
    get() = this.map { it.x }

/** Returns a list of the y coordinates of all points. */
val List<Point3D>.yValues: List<Double>
    get() = this.map { it.y }

/** Returns a list of the z coordinates of all points. */
val List<Point3D>.zValues: List<Double>
    get() = this.map { it.z }

/** Total arc length computed as the sum of distances between consecutive points. */
val List<Point3D>.length: Double
    get() = this.foldIndexed(0.0) { index, acc, point3D ->
        if (index != this.size - 1) acc + point3D.distanceBetween(this[index + 1]) else acc
    }

/** Geometric centroid of all points. */
val List<Point3D>.centroid: Point3D
    get() = Point3D(xValues.average(), yValues.average(), zValues.average())

/** Minimum x coordinate among all points. */
val List<Point3D>.minX: Double
    get() = this.minOf { it.x }

/** Maximum x coordinate among all points. */
val List<Point3D>.maxX: Double
    get() = this.maxOf { it.x }

/** Minimum y coordinate among all points. */
val List<Point3D>.minY: Double
    get() = this.minOf { it.y }

/** Maximum y coordinate among all points. */
val List<Point3D>.maxY: Double
    get() = this.maxOf { it.y }

/** Minimum z coordinate among all points. */
val List<Point3D>.minZ: Double
    get() = this.minOf { it.z }

/** Maximum z coordinate among all points. */
val List<Point3D>.maxZ: Double
    get() = this.maxOf { it.z }

/** Returns true if [value] is within the x range of this list. */
fun List<Point3D>.inXRange(value: Double): Boolean = value in minX..maxX

/** Returns true if [value] is within the y range of this list. */
fun List<Point3D>.inYRange(value: Double): Boolean = value in minY..maxY

/** Returns true if [value] is within the z range of this list. */
fun List<Point3D>.inZRange(value: Double): Boolean = value in minZ..maxZ
