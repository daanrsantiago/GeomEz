package plane

import plane.elements.*
import space.CoordinateSystem3D
import space.elements.Point3D
import units.Angle

/** An ordered sequence of Point2D objects representing a 2D curve. */
class Curve2D(var points: List<Point2D>) {

    /** Constructs a curve from a vararg list of points. */
    constructor(vararg points2D: Point2D) : this(points2D.toList())

    /**
     * Gets a point by index, wrapping around when the index is out of bounds.
     */
    operator fun invoke(index: Int): Point2D {
        if (index >= 0) return points[index.rem(points.size)]
        return points[points.size + index.rem(points.size)]
    }

    /** Concatenates this curve with [curve2D], returning a new Curve2D. */
    fun concat(curve2D: Curve2D): Curve2D = Curve2D(this.points + curve2D.points)

    /** Rotates all points around the origin by [angle]. */
    fun rotate(angle: Angle): Curve2D {
        return Curve2D(points.map { it.rotate(angle) })
    }

    /** Rotates all points around [centerOfRotation] by [angle]. */
    fun rotate(centerOfRotation: Point2D, angle: Angle): Curve2D {
        return Curve2D(points.map { it.rotate(centerOfRotation, angle) })
    }

    /** Expresses all points in the [to] coordinate system, as if currently written in [asWrittenIn]. */
    fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): List<Point2D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): List<Point3D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    fun translateTo(newCentroid: Point2D): Curve2D {
        return Curve2D(this.points.map { it + newCentroid - this.points.centroid })
    }

    fun scale(scalar: Double): Curve2D {
        return Curve2D(this.points.map { it * scalar })
    }

    // Point operations

    /**
     * Summing points is equivalent to translate the curve
     */
    operator fun plus(point: Point2D): Curve2D = Curve2D(this.points.map { it + point })
    /** Subtracts [point] from every point (translates curve). */
    operator fun minus(point: Point2D): Curve2D = Curve2D(this.points.map { it - point })

    // Scalar operations

    /**
     * Multiplying the points by a scalar is equivalent to scale the curve along with its centroid
     */
    operator fun times(scalar: Double): Curve2D = Curve2D(this.points.map { it * scalar })
    /** Divides every point by [scalar]. */
    operator fun div(scalar: Double): Curve2D = Curve2D(this.points.map { it / scalar })
}
