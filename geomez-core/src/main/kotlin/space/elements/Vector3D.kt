package space.elements

import extensions.times
import space.CoordinateSystem3D
import units.Angle
import utils.rotationMatrix3D

/**
 * A 3D vector rooted at [position] with direction and magnitude defined by its x/y/z components.
 */
open class Vector3D(
    xComponent: Double,
    yComponent: Double,
    zComponent: Double,
    val position: Point3D = Point3D(0.0, 0.0, 0.0)
) : VectorialEntity3D(xComponent, yComponent, zComponent) {

    /** Constructs a vector from [tailPosition] to [headPosition]. */
    constructor(headPosition: Point3D, tailPosition: Point3D) : this(
        headPosition.x - tailPosition.x,
        headPosition.y - tailPosition.y,
        headPosition.z - tailPosition.z,
        position = tailPosition
    )

    /** Unit direction of this vector as a Direction3D. */
    val direction: Direction3D
        get() = Direction3D(x, y, z)

    /** The tip (head) of this vector in absolute coordinates. */
    val headPosition: Point3D
        get() = position + Point3D(x, y, z)

    /** Rotates both head and tail around [axis] by [angle]. */
    override fun rotate(axis: VectorialEntity3D, angle: Angle): Vector3D {
        val rotationMatrix = when (axis) {
            is Vector3D -> rotationMatrix3D(axis.direction, angle, axis.position)
            is Direction3D -> rotationMatrix3D(axis, angle)
        }
        return Vector3D(
            headPosition = rotationMatrix * this.headPosition,
            tailPosition = rotationMatrix * this.position
        )
    }

    /** Expresses this vector in the [to] coordinate system, as if currently written in [asWrittenIn]. */
    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Vector3D {
        val transformationMatrix = to.affineMatrix.invert() * asWrittenIn.affineMatrix
        return Vector3D(
            headPosition = transformationMatrix * this.headPosition,
            tailPosition = transformationMatrix * this.position
        )
    }

    /** Negates all components, keeping the tail position. */
    override fun unaryMinus(): Vector3D {
        return Vector3D(-x, -y, -z, position)
    }

}