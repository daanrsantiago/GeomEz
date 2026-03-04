package space.elements

import extensions.times
import plane.elements.Direction2D
import space.CoordinateSystem3D
import units.Angle
import utils.rotationMatrix3D
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Represent a direction (unit vector at the origin) in 3D space
 */
class Direction3D(x: Double, y: Double, z: Double) :
    VectorialEntity3D(
        x / sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0)),
        y / sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0)),
        z / sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0))
    ) {

    companion object {
        /** Unit direction along the X axis. */
        val MAIN_X_DIRECTION = Direction3D(1.0, 0.0, 0.0)
        /** Unit direction along the Y axis. */
        val MAIN_Y_DIRECTION = Direction3D(0.0, 1.0, 0.0)
        /** Unit direction along the Z axis. */
        val MAIN_Z_DIRECTION = Direction3D(0.0, 0.0, 1.0)
    }

    /** Rotates this direction around [axis] by [angle]; returns a Vector3D if the axis has a position. */
    override fun rotate(axis: VectorialEntity3D, angle: Angle): VectorialEntity3D {
        return when (axis) {
            is Vector3D -> {
                val rotationMatrix = rotationMatrix3D(axis.direction, angle, axis.position)
                Vector3D(
                    headPosition = rotationMatrix * this.toPoint3D(),
                    tailPosition = rotationMatrix * Point3D(0.0, 0.0, 0.0)
                )
            }
            is Direction3D -> {
                rotationMatrix3D(axis, angle) * this
            }
        }
    }

    /** Expresses this direction in the [to] coordinate system, as if currently written in [asWrittenIn]. */
    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Vector3D {
        val transformationMatrix = to.affineMatrix.invert() * asWrittenIn.affineMatrix
        return Vector3D(
            headPosition = transformationMatrix * this.toPoint3D(),
            tailPosition = transformationMatrix * Point3D(0.0, 0.0, 0.0)
        )
    }

    /** Returns the cross product of this direction with [direction]. */
    infix fun cross(direction: Direction3D): Direction3D {
        val (a1, a2, a3) = this
        val (b1, b2, b3) = direction

        return Direction3D(a2 * b3 - a3 * b2, a3 * b1 - a1 * b3, a1 * b2 - a2 * b1)
    }

    /**
     * Treat a 2D direction as a vector in XY plane (z component = 0)
     */
    infix fun cross(direction: Direction2D): Direction3D {
        val (a1, a2, a3) = this
        val (b1, b2) = direction

        return Direction3D(-a3 * b2, a3 * b1, a1 * b2 - a2 * b1)
    }

    /** Returns the opposite direction. */
    override fun unaryMinus(): Direction3D {
        return Direction3D(-x, -y, -z)
    }
}