package plane

import plane.elements.Point2D
import plane.functions.Function2D
import utils.linspace

/** A 2D parametric curve defined by separate x(t) and y(t) functions over t ∈ [0,1]. */
open class ParametricCurve2D(
    /** Parametric function for the x coordinate. */
    val xParametricFunction: Function2D,
    /** Parametric function for the y coordinate. */
    val yParametricFunction: Function2D
) {

    /** Constructs from a pair of parametric functions. */
    constructor(xAndYParametricFunctions: Pair<Function2D, Function2D>): this(xAndYParametricFunctions.first, xAndYParametricFunctions.second)

    /** Returns dy/dx at parameter [x] via the chain rule. */
    fun derivative(x: Double): Double = yParametricFunction.derivative(x) / xParametricFunction.derivative(x)

    /** Numerically integrates ∫y dx along the curve using 200 points. */
    fun integrate(xStart: Double, xEnd: Double): Double = integrate(xStart,xEnd,200.toUInt())

    /**
     * @param nPoints Number of points used to perform numerical integration mathod
     */
    fun integrate(xStart: Double, xEnd: Double, nPoints: UInt): Double {
        // integration among a parametric curve it's the area under the curve, which is the integral of y * dx
        val tVec = linspace(xStart, xEnd, nPoints.toInt())
        val dt = (xEnd - xStart) / nPoints.toInt()
        return tVec.foldRightIndexed(0.0) { index, t, acc ->
            when (index) {
                tVec.lastIndex -> acc
                else -> acc + (yParametricFunction(tVec[index + 1]) * xParametricFunction.derivative(tVec[index + 1])
                        + yParametricFunction(t) * xParametricFunction.derivative(t)) / 2 * dt
            }
        }
    }

    /** Evaluates the curve at parameter [x], returning a Point2D. */
    open operator fun invoke(x: Double): Point2D {
        return Point2D(xParametricFunction(x), yParametricFunction(x))
    }

    /** Evaluates the curve at all given parameter values. */
    open operator fun invoke(xCollection: Collection<Double>): List<Point2D> {
        return xCollection.map { x -> this(x) }
    }
}