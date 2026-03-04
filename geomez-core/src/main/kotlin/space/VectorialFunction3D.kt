package space

import plane.functions.Function2D
import space.elements.Vector3D

/** A 3D vector field defined by three component Function2D instances (functions of a single parameter t). */
class VectorialFunction3D(
    /** Parametric function for the x component. */
    val xComponentFunction: Function2D,
    /** Parametric function for the y component. */
    val yComponentFunction: Function2D,
    /** Parametric function for the z component. */
    val zComponentFunction: Function2D
) {

    /** Evaluates the vector function at parameter [t], returning a Vector3D. */
    operator fun invoke(t: Double): Vector3D {
        return Vector3D(
            xComponentFunction(t),
            yComponentFunction(t),
            zComponentFunction(t)
        )
    }

}