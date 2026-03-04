package space.elements

import extensions.equalsDelta
import units.Angle
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Base class from which Vector3D and Direction3D can extend
 */
/**
 * Base class for 3D vectorial entities (Vector3D, Direction3D) providing magnitude, angle,
 * dot/cross products and arithmetic operations.
 */
sealed class VectorialEntity3D (
    final override val x: Double,
    final override val y: Double,
    final override val z: Double,
): Entity3D {
    /** Euclidean magnitude of this 3D vector. */
    var module = sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0))

    /** Converts to a Point3D with the same coordinates. */
    fun toPoint3D(): Point3D = Point3D(x,y,z)

    /** Returns the angle in radians between this vector and [vector3D]. */
    infix fun angleBetween(vector3D: VectorialEntity3D): Angle.Radians {
        return Angle.Radians(acos(this dot vector3D / (this.module * vector3D.module)))
    }

//    Vector elementwise operations

    /** Element-wise vector addition. */
    override operator fun plus(entity3D: Entity3D): Vector3D {
        return Vector3D(
            x + entity3D.x,
            y + entity3D.y,
            z + entity3D.z
        )
    }

    /** Element-wise vector subtraction. */
    override operator fun minus(entity3D: Entity3D): Vector3D {
        return Vector3D(
            x - entity3D.x,
            y - entity3D.y,
            z - entity3D.z
        )
    }

    /** Element-wise vector multiplication. */
    operator fun times(vector3D: Entity3D): Vector3D {
        return Vector3D(
            x * vector3D.x,
            y * vector3D.y,
            z * vector3D.z
        )
    }

    /** Element-wise vector division. */
    operator fun div(vector3D: Entity3D): Vector3D {
        return Vector3D(
            x / vector3D.x,
            y / vector3D.y,
            z / vector3D.z
        )
    }

    abstract override operator fun unaryMinus(): VectorialEntity3D

//    Vectorial operations

    /** Returns the dot product of this vector with [vector3D]. */
    infix fun dot(vector3D: VectorialEntity3D): Double {
        return x * vector3D.x +
                y * vector3D.y +
                z * vector3D.z
    }

    /** Returns the cross product of this vector with [vector3D]. */
    infix fun cross(vector3D: VectorialEntity3D): Vector3D {
        val (a1, a2, a3) = this
        val (b1, b2, b3) = vector3D

        return Vector3D(a2 * b3 - a3 * b2, a3 * b1 - a1 * b3, a1 * b2 - a2 * b1)
    }

//    Component get

    /** Returns component by index (0=x, 1=y, 2=z). */
    operator fun get(index: Int): Double {
        return when (index) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw IllegalAccessError("Index out of bounds")
        }
    }

//    Scalar operations

    /** Scalar addition applied to all three components. */
    override operator fun plus(scalar: Double): Vector3D {
        return Vector3D(x + scalar, y + scalar, z + scalar)
    }

    /** Scalar subtraction applied to all three components. */
    override operator fun minus(scalar: Double): Vector3D {
        return Vector3D(x - scalar, y - scalar, z - scalar)
    }

    /** Scalar multiplication applied to all three components. */
    override operator fun times(scalar: Double): Vector3D {
        return Vector3D(x * scalar, y * scalar, z * scalar)
    }

    /** Scalar division applied to all three components. */
    override operator fun div(scalar: Double): Vector3D {
        return Vector3D(x / scalar, y / scalar, z / scalar)
    }

//    Equals and HashCode

    /** Equality uses delta comparison for floating-point safety. */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VectorialEntity3D

        if (!x.equalsDelta(other.x)) return false
        if (!y.equalsDelta(other.y)) return false
        if (!z.equalsDelta(other.z)) return false
        return true
    }

    /** Returns a hash code consistent with [equals]. */
    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }


}