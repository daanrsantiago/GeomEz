package plane.elements

import extensions.component1
import extensions.component2
import extensions.component3
import extensions.times
import plane.CoordinateSystem2D
import space.CoordinateSystem3D
import space.elements.Direction3D
import space.elements.Point3D
import space.elements.Vector3D
import space.elements.VectorialEntity3D
import units.Angle
import utils.rotationMatrix2D
import kotlin.math.sqrt

/** A unit 2D direction vector; components are normalized on construction. */
class Direction2D(
    x: Double,
    y: Double
) : VectorialEntity2D(x / sqrt(x * x + y * y), y / sqrt(x * x + y * y)) {

    companion object {
        /** Unit direction along the X axis. */
        val MAIN_X_DIRECTION = Direction2D(1.0, 0.0)
        /** Unit direction along the Y axis. */
        val MAIN_Y_DIRECTION = Direction2D(0.0, 1.0)
    }

    /** Converts this direction to a Point3D with z=0. */
    fun toPoint3D(): Point3D = Point3D(x, y, 0.0)

    /**
     * @return Anti-clockwise perpendicular direction
     */
    fun perpendicularDirection(): Direction2D = Direction2D(-y, x)

    /**
     * Returns the cross product result between both vectors.
     *
     * If the input is an direction2D, the result will be a direction3D
     *
     * If the input is an vector2D, the result will be a vector3D, positioned at the origin
     */
    override infix fun cross(vectorialEntity2D: VectorialEntity2D): VectorialEntity3D {
        return when (vectorialEntity2D) {
            is Direction2D -> Direction3D(0.0, 0.0, this.x * vectorialEntity2D.y - this.y * vectorialEntity2D.x)
            is Vector2D -> Vector3D(0.0, 0.0, this.x * vectorialEntity2D.y - this.y * vectorialEntity2D.x)
        }
    }

    /** Rotates this direction around the origin by [angle]. */
    override fun rotate(angle: Angle): Direction2D {
        val rotationMatrix = rotationMatrix2D(angle)
        val (vX, vY) = rotationMatrix * this.matrix
        return Direction2D(vX, vY)
    }

    /** Rotates this direction around [centerOfRotation] by [angle]; returns a Vector2D if the origin shifts. */
    override fun rotate(centerOfRotation: Point2D, angle: Angle): Entity2D {
        val rotationMatrix = rotationMatrix2D(angle, centerOfRotation)
        val (newHeadX, newHeadY) = rotationMatrix * this.affineMatrix
        val (newTailX, newTailY) = rotationMatrix * Point2D(
            0.0,
            0.0
        ).affineMatrix
        val newHead = Point2D(newHeadX, newHeadY)
        val newTail = Point2D(newTailX, newTailY)
        return Vector2D(headPosition = newHead, tailPosition = newTail)
    }

    /**
     * When the two Coordinate Systems shares the same origin the result it's a direction, otherwise it's a vector
     */
    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): VectorialEntity2D {
        val transformationMatrix = to.affineMatrix.invert() * asWrittenIn.affineMatrix
        return when (asWrittenIn.origin) {
            to.origin -> {
                val (newXComponent, newYComponent) = transformationMatrix * this.affineMatrix
                Direction2D(newXComponent, newYComponent)
            }
            else -> {
                val (newHeadX, newHeadY) = transformationMatrix * this.affineMatrix
                val (newTailX, newTailY) = transformationMatrix * Point2D(
                    0.0,
                    0.0
                ).affineMatrix
                val newHead = Point2D(newHeadX, newHeadY)
                val newTail = Point2D(newTailX, newTailY)
                Vector2D(headPosition = newHead, tailPosition = newTail)
            }
        }
    }

    /**
     * When the two Coordinate Systems shares the same origin the result it's a direction, otherwise it's a vector
     */
    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): VectorialEntity3D {
        val transformationMatrix = to.affineMatrix.invert() * asWrittenIn.affineMatrix
        return when (asWrittenIn.origin) {
            to.origin -> {
                val (newXComponent, newYComponent, newZComponent) = transformationMatrix * this.toPoint3D().affineMatrix
                Direction3D(newXComponent, newYComponent, newZComponent)
            }
            else -> {
                val (newHeadX, newHeadY, newHeadZ) = transformationMatrix * this.toPoint3D().affineMatrix
                val (newTailX, newTailY, newTailZ) = transformationMatrix * Point3D(
                    0.0,
                    0.0,
                    0.0
                ).affineMatrix
                val newHead = Point3D(newHeadX, newHeadY, newHeadZ)
                val newTail = Point3D(newTailX, newTailY, newTailZ)
                Vector3D(headPosition = newHead, tailPosition = newTail)
            }
        }
    }

    /** Returns the opposite direction. */
    override fun unaryMinus(): Direction2D {
        return Direction2D(-x, -y)
    }

}