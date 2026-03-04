package space

import plane.elements.Point2D
import plane.functions.Function2D
import space.elements.Direction3D
import space.elements.Point3D
import space.elements.Vector3D

/**
 * This abstraction provides an easy way to position single variable functions in 3D space without losing some important
 * features like interpolation, normal and tangent direction functions
 */
class Function2DInPlane(val function2D: Function2D, val plane: Plane) {

    /** Evaluates [function2D] at [x] and transforms the resulting 2D point into 3D space using the plane. */
    fun interpolate(x: Double): Point3D = Point2D(x,function2D(x)).changeBasis(
        asWrittenIn = plane.coordinateSystem3D,
        to = CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM
    )

    /** Returns the tangent direction of the curve at [x] in 3D space. */
    fun tangentDirection(x: Double): Direction3D {
        val tangentVectorEntity = function2D.tangentDirection(x)
            .changeBasis(asWrittenIn = plane.coordinateSystem3D, to = CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM)
        return when (tangentVectorEntity) {
            is Direction3D -> tangentVectorEntity
            is Vector3D -> tangentVectorEntity.direction
        }
    }

    /** Returns the normal direction of the curve at [x] in 3D space. */
    fun normalDirection(x: Double): Direction3D {
        val normalDirectionVector = function2D.normalDirection(x)
            .changeBasis(asWrittenIn = plane.coordinateSystem3D, to = CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM)
        return when (normalDirectionVector) {
            is Direction3D -> normalDirectionVector
            is Vector3D -> normalDirectionVector.direction
        }
    }

    /** Expresses this function in the [to] coordinate system, as if currently written in [asWrittenIn]. */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Function2DInPlane {
        val newPlane = plane.changeBasis(asWrittenIn, to)
        return Function2DInPlane(function2D, newPlane)
    }

    /** Evaluates the function at [x], returning the 3D position. */
    operator fun invoke(x: Double): Point3D = interpolate(x)

    /** Evaluates the function at all values in [xList]. */
    operator fun invoke(xList: List<Double>): List<Point3D> = xList.map { x -> interpolate(x)}

}