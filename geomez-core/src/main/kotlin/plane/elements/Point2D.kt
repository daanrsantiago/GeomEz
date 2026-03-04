package plane.elements

import extensions.equalsDelta
import extensions.times
import plane.CoordinateSystem2D
import space.CoordinateSystem3D
import space.elements.Point3D
import space.elements.Vector3D
import units.Angle
import utils.rotationMatrix2D
import kotlin.math.pow

/** Immutable 2D point with floating-point equality via delta comparison. */
class Point2D(
    override val x: Double,
    override val y: Double
) : Entity2D {

    /** Constructs a Point2D from a pair of doubles. */
    constructor(pair: Pair<Double, Double>) : this(pair.first, pair.second)

    /** Euclidean distance from the origin (0,0). */
    val distanceFromOrigin: Double
        get() = kotlin.math.sqrt(x.pow(2) + y.pow(2))

    /** Converts this point to a Vector3D with z=0. */
    val toVector3D
        get() = Vector3D(x, y, 0.0)

    /** Lifts this 2D point into 3D space with the given [z] coordinate (default 0). */
    fun toPoint3D(z: Double = 0.0) = Point3D(x, y, z)

    /** Returns the Euclidean distance between this point and [point2D]. */
    fun distanceBetween(point2D: Point2D): Double = (this - point2D).distanceFromOrigin

    override fun rotate(angle: Angle): Point2D = rotationMatrix2D(angle) * this

    override fun rotate(centerOfRotation: Point2D, angle: Angle): Point2D =
        rotationMatrix2D(angle, centerOfRotation) * this

    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): Point2D =
        to.affineMatrix.invert() * asWrittenIn.affineMatrix * this

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Point3D =
        to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.toPoint3D()

    //    Point operations

    /** Element-wise addition with [entity2D]. */
    override operator fun plus(entity2D: Entity2D): Point2D {
        return Point2D(x + entity2D.x, y + entity2D.y)
    }

    /** Element-wise subtraction of [entity2D]. */
    override operator fun minus(entity2D: Entity2D): Point2D {
        return Point2D(x - entity2D.x, y - entity2D.y)
    }

    /** Element-wise multiplication with [entity2D]. */
    operator fun times(entity2D: Entity2D): Point2D {
        return Point2D(x * entity2D.x, y * entity2D.y)
    }

    /** Element-wise division by [entity2D]. */
    operator fun div(entity2D: Entity2D): Point2D {
        return Point2D(x / entity2D.x, y / entity2D.y)
    }

//    Scalar operations

    /** Scalar addition applied to both components. */
    override operator fun plus(scalar: Double): Point2D {
        return Point2D(x + scalar, y + scalar)
    }

    /** Scalar subtraction applied to both components. */
    override operator fun minus(scalar: Double): Point2D {
        return Point2D(x - scalar, y - scalar)
    }

    /** Scalar multiplication applied to both components. */
    override operator fun times(scalar: Double): Point2D {
        return Point2D(x * scalar, y * scalar)
    }

    /** Scalar division applied to both components. */
    override operator fun div(scalar: Double): Point2D {
        return Point2D(x / scalar, y / scalar)
    }

    /** Negates both components. */
    override operator fun unaryMinus(): Point2D {
        return Point2D(-x, -y)
    }

    /** Uses delta comparison for floating-point safety. */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point2D

        if (!x.equalsDelta(other.x)) return false
        if (!y.equalsDelta(other.y)) return false

        return true
    }

    /** Returns a hash code consistent with [equals]. */
    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

}