package plane.elements

import extensions.times
import plane.CoordinateSystem2D
import space.CoordinateSystem3D
import space.elements.Vector3D
import space.elements.VectorialEntity3D
import units.Angle
import utils.rotationMatrix2D

/** A 2D vector rooted at [position] with direction and magnitude defined by its x/y components. */
class Vector2D(
    x: Double,
    y: Double,
    val position: Point2D = Point2D(0.0, 0.0)
) : VectorialEntity2D(x, y) {

    /** Constructs a vector from [tailPosition] to [headPosition]. */
    constructor(headPosition: Point2D, tailPosition: Point2D) : this(
        headPosition.x - tailPosition.x,
        headPosition.y - tailPosition.y,
        position = tailPosition
    )

    /** Unit direction of this vector as a Direction2D. */
    val direction: Direction2D
        get() = Direction2D(x, y)

    /** The tip (head) of this vector in absolute coordinates. */
    val headPosition: Point2D
        get() = position + Point2D(x, y)

    /** Converts this 2D vector to a 3D vector with z=0. */
    fun toVector3D(): Vector3D = Vector3D(x, y, 0.0)

    /** Returns the cross product as a Vector3D (z-component only, tail at position). */
    override infix fun cross(vectorialEntity2D: VectorialEntity2D): VectorialEntity3D {
        return Vector3D(
            0.0,
            0.0,
            this.x * vectorialEntity2D.y - this.y * vectorialEntity2D.x,
            position = position.toPoint3D()
        )
    }

    /** Rotates both head and tail around the origin by [angle]. */
    override fun rotate(angle: Angle): Vector2D {
        val rotationMatrix = rotationMatrix2D(angle)
        return Vector2D(
            headPosition = rotationMatrix * this.headPosition,
            tailPosition = rotationMatrix * this.position
        )
    }

    /** Rotates both head and tail around [centerOfRotation] by [angle]. */
    override fun rotate(centerOfRotation: Point2D, angle: Angle): Vector2D {
        val rotationMatrix = rotationMatrix2D(angle, centerOfRotation)
        return Vector2D(
            headPosition = rotationMatrix * this.headPosition,
            tailPosition = rotationMatrix * this.position
        )
    }

    /** Expresses this vector in the [to] coordinate system, as if currently written in [asWrittenIn]. */
    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): Vector2D {
        val transformationMatrix = to.affineMatrix.invert() * asWrittenIn.affineMatrix
        return Vector2D(
            headPosition = transformationMatrix * this.headPosition,
            tailPosition = transformationMatrix * this.position
        )
    }

    /** Expresses this vector in the 3D [to] coordinate system, as if currently written in [asWrittenIn]. */
    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Vector3D {
        return this.toVector3D().changeBasis(asWrittenIn, to)
    }

    /** Negates both components, keeping the tail position. */
    override fun unaryMinus(): VectorialEntity2D {
        return Vector2D(-x, -y, position)
    }
}