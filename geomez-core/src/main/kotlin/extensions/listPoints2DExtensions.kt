package plane.elements

import plane.functions.Function2D
import plane.functions.LinearSpline

// ── List<Point2D> extensions ─────────────────────────────────────────────────

/** Returns a list of the x coordinates of all points. */
val List<Point2D>.xValues: List<Double>
    get() = this.map { it.x }

/** Returns a list of the y coordinates of all points. */
val List<Point2D>.yValues: List<Double>
    get() = this.map { it.y }

/** Returns a list of the x coordinates of all points. */
val List<Point2D>.xPoints: List<Double>
    get() = xValues

/** Returns a list of the y coordinates of all points. */
val List<Point2D>.yPoints: List<Double>
    get() = yValues

/** Minimum x coordinate among all points. */
val List<Point2D>.minX: Double
    get() = this.minOf { it.x }

/** Maximum x coordinate among all points. */
val List<Point2D>.maxX: Double
    get() = this.maxOf { it.x }

/** Minimum y coordinate among all points. */
val List<Point2D>.minY: Double
    get() = this.minOf { it.y }

/** Maximum y coordinate among all points. */
val List<Point2D>.maxY: Double
    get() = this.maxOf { it.y }

/** Total arc length computed as the sum of distances between consecutive points. */
val List<Point2D>.length: Double
    get() = this.foldRightIndexed(0.0) { index, point, acc ->
        if (index != (this.size - 1)) acc + point.distanceBetween(this[index + 1]) else acc
    }

/** Geometric centroid of all points. */
val List<Point2D>.centroid: Point2D
    get() = Point2D(xPoints.average(), yPoints.average())

/** Returns true if [value] is within the x range of this list. */
fun List<Point2D>.inXRange(value: Double): Boolean = value in minX..maxX

/** Returns true if [value] is within the y range of this list. */
fun List<Point2D>.inYRange(value: Double): Boolean = value in minY..maxY

/** Returns a parametric function mapping t ∈ [0,1] to x coordinates via linear interpolation. */
fun List<Point2D>.xParametricFunction(): Function2D =
    LinearSpline(xPoints.mapIndexed { index, x -> Point2D(index.toDouble() / (xPoints.size - 1), x) })

/** Returns a parametric function mapping t ∈ [0,1] to y coordinates via linear interpolation. */
fun List<Point2D>.yParametricFunction(): Function2D =
    LinearSpline(yPoints.mapIndexed { index, y -> Point2D(index.toDouble() / (yPoints.size - 1), y) })
