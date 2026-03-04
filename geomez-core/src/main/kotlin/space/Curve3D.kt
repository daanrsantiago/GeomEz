package space

import space.elements.Point3D
import space.elements.Vector3D
import units.Angle

/** An ordered sequence of Point3D objects representing a 3D curve. */
open class Curve3D(val points: List<Point3D>) {

    /** Constructs a curve from a vararg list of points. */
    constructor(vararg points3D: Point3D): this(points3D.toList())

    /** Concatenates this curve with [curve], returning a new Curve3D. */
    fun concat(curve: Curve3D): Curve3D = Curve3D(points + curve.points)

    /**
     * Rotate points in the anti-clockwise direction along the given axis
     */
    fun rotate(axis: Vector3D, angle: Angle): Curve3D {
        return Curve3D(points.map { it.rotate(axis, angle) })
    }

    /**
     * Describe point as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Curve3D {
        return Curve3D(points.map { it.changeBasis(asWrittenIn, to) })
    }

    // Point operations

    /**
     * Summing points is equivalent to translate the curve
     */
    operator fun plus(point: Point3D): Curve3D = Curve3D(this.points.map { it + point })
    operator fun minus(point: Point3D): Curve3D = Curve3D(this.points.map { it - point })

    // Scalar operations

    /**
     * Multiplying the points by a scalar is equivalent to scale the curve along with its centroid
     */
    operator fun times(scalar: Double): Curve3D = Curve3D(this.points.map { it * scalar })
    operator fun div(scalar: Double): Curve3D = Curve3D(this.points.map { it / scalar })
}
