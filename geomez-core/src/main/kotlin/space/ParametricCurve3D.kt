package space

import plane.functions.Function2D
import space.elements.Point3D

/** A 3D parametric curve defined by x(t), y(t), z(t) functions over t ∈ [0,1]. */
class ParametricCurve3D(
    /** Parametric function for the x coordinate. */
    val xParametricFunction: Function2D,
    /** Parametric function for the y coordinate. */
    val yParametricFunction: Function2D,
    /** Parametric function for the z coordinate. */
    val zParametricFunction: Function2D
) {

    /** Evaluates the curve at parameter [t], returning a Point3D. */
    operator fun invoke(t: Double): Point3D {
        return Point3D(
            xParametricFunction(t),
            yParametricFunction(t),
            zParametricFunction(t)
        )
    }

    /** Evaluates the curve at all parameter values in [tList]. */
    operator fun invoke(tList: List<Double>): List<Point3D> {
        return tList.map { t ->
            Point3D(
                xParametricFunction(t),
                yParametricFunction(t),
                zParametricFunction(t)
            )
        }
    }

}