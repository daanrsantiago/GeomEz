package space

import org.ejml.simple.SimpleMatrix
import space.elements.Point3D
import space.elements.Vector3D
import space.functions.Function3D

/** A 3D parametric surface defined by three Function3D instances mapping (t,s) → (x,y,z). */
open class ParametricSurface3D(
    /** Parametric function for the x coordinate. */
    val xParametricFunction: Function3D,
    /** Parametric function for the y coordinate. */
    val yParametricFunction: Function3D,
    /** Parametric function for the z coordinate. */
    val zParametricFunction: Function3D
) {

    /** Computes the surface normal at ([t],[s]) numerically via finite differences. */
    fun numericalNormalDirection(t: Double, s: Double, delta: Double = 10.0e-5): Vector3D {
        val position = this(t, s)
        val tangentDirectionT = Vector3D(this(t + delta, s), position).direction
        val tangentDirectionS = Vector3D(this(t, s + delta), position).direction
        val normalDirection = tangentDirectionT.cross(tangentDirectionS)
        return Vector3D(normalDirection.x, normalDirection.y, normalDirection.z, position = position)
    }

    /** Evaluates the surface at parameters [x] and [y], returning a Point3D. */
    open operator fun invoke(x: Double, y: Double): Point3D {
        return Point3D(xParametricFunction(x, y), yParametricFunction(x, y), zParametricFunction(x, y))
    }

    /**
     * Evaluates the surface over parameter meshes [T] and [S], returning a triple of x/y/z matrices.
     */
    operator fun invoke(T: SimpleMatrix, S: SimpleMatrix): Triple<SimpleMatrix, SimpleMatrix, SimpleMatrix> {

        val X = SimpleMatrix(T.numRows(), S.numCols())
        val Y = SimpleMatrix(T.numRows(), S.numCols())
        val Z = SimpleMatrix(T.numRows(), S.numCols())

        (0 until T.numRows()).forEach { rowIndex ->
            (0 until T.numCols()).forEach { colIndex ->
                val point = this(T[rowIndex, colIndex], S[rowIndex, colIndex])
                X[rowIndex, colIndex] = point.x
                Y[rowIndex, colIndex] = point.y
                Z[rowIndex, colIndex] = point.z
            }
        }

        return Triple(X, Y, Z)
    }
}