package space

import org.ejml.simple.SimpleMatrix
import space.elements.Direction3D
import space.elements.Point3D
import units.Angle

/** A 3D coordinate system defined by three Direction3D axes and an origin Point3D. */
open class CoordinateSystem3D(
    val xDirection: Direction3D = Direction3D.MAIN_X_DIRECTION,
    val yDirection: Direction3D = Direction3D.MAIN_Y_DIRECTION,
    val zDirection: Direction3D = Direction3D.MAIN_Z_DIRECTION,
    val origin: Point3D = Point3D(0.0, 0.0, 0.0)
) {

    companion object {
        /** The standard Cartesian 3D coordinate system. */
        val MAIN_3D_COORDINATE_SYSTEM =
            CoordinateSystem3D(
                Direction3D.MAIN_X_DIRECTION,
                Direction3D.MAIN_Y_DIRECTION,
                Direction3D.MAIN_Z_DIRECTION,
                Point3D(0.0, 0.0, 0.0)
            )
    }

    /** 3×3 rotation matrix formed from the axis direction column vectors. */
    val matrix: SimpleMatrix = xDirection.matrix
        .combine(0, 1, yDirection.matrix)
        .combine(0, 2, zDirection.matrix)

    /**
     * Matrix used for affine transformations that perform scaling and translation
     */
    val affineMatrix: SimpleMatrix
        get() {
            val (x1, x2, x3) = xDirection
            val (y1, y2, y3) = yDirection
            val (z1, z2, z3) = zDirection
            val (p1, p2, p3) = origin

            return SimpleMatrix(
                arrayOf(
                    doubleArrayOf(x1, y1, z1, p1),
                    doubleArrayOf(x2, y2, z2, p2),
                    doubleArrayOf(x3, y3, z3, p3),
                    doubleArrayOf(0.0, 0.0, 0.0, 1.0)
                )
            )
        }

    /** Returns a new coordinate system rotated around [axis] by [angle]. */
    fun rotate(axis: Direction3D, angle: Angle): CoordinateSystem3D {
        return CoordinateSystem3D(
            xDirection.rotate(axis, angle) as Direction3D,
            yDirection.rotate(axis, angle) as Direction3D,
            zDirection.rotate(axis, angle) as Direction3D
        )
    }

    /** Checks equality based on axes directions. */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CoordinateSystem3D

        if (xDirection != other.xDirection) return false
        if (yDirection != other.yDirection) return false
        if (zDirection != other.zDirection) return false

        return true
    }

    /** Returns a hash code consistent with [equals]. */
    override fun hashCode(): Int {
        var result = xDirection.hashCode()
        result = 31 * result + yDirection.hashCode()
        result = 31 * result + zDirection.hashCode()
        return result
    }
}