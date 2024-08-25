package plane

import plane.elements.Point2D
import plane.elements.SmoothCubicBezierSplineControlPoints
import plane.functions.Function2D

class CubicBezierSpline2D (
    val controlPoints: List<Point2D>
    ): ParametricCurve2D(createXAndYPiecewiseOpenCubicBezierPolynomial(controlPoints)) {

    val bezierCurves = createBezierCurves(controlPoints)

    constructor(
        smoothCubicBezierSplineControlPoints: Collection<SmoothCubicBezierSplineControlPoints>
    ): this(smoothCubicBezierSplineControlPoints.flatMap { it.getControlPoints() })

    companion object {
        fun createBezierCurves(controlPoints: List<Point2D>): List<BezierCurve> {
            check(controlPoints.size >= 4) { "Must have at least 4 points to make bezier curve" }
            check((controlPoints.size - 4) % 3 == 0) { "Must have 4 + 3n points" }

            val bezierCurves = mutableListOf<BezierCurve>()
            val numberOfBezierCurves = 1 + (controlPoints.size - 4)/3

            for ( iBezierCurve in 0 until numberOfBezierCurves) {
                bezierCurves.add(
                    BezierCurve(controlPoints.subList(3 * iBezierCurve, 3 * (iBezierCurve + 1) + 1))
                )
            }
            return bezierCurves
        }

        fun selectBezierCurveAndT(bezierCurves: List<BezierCurve>, t: Double): Triple<BezierCurve, Double, Int> {
            check(t in 0.0..1.0) { "x must be in 0.0 to 1.0 range" }
            val tIntervalBetweenCurves = 1.0/bezierCurves.size
            val selectedBezierCurveIndex =  if (t == 1.0) bezierCurves.lastIndex else (t/tIntervalBetweenCurves).toInt()
            val selectedBezierCurve = bezierCurves[selectedBezierCurveIndex]
            val selectedBezierT = if (t == 1.0) 1.0 else (t % tIntervalBetweenCurves) / tIntervalBetweenCurves

            return Triple(selectedBezierCurve, selectedBezierT, selectedBezierCurveIndex)
        }

        private fun createXAndYPiecewiseOpenCubicBezierPolynomial(controlPoints: List<Point2D>): Pair<Function2D,Function2D> {
            val bezierCurves = createBezierCurves(controlPoints)

            val xParametricFunction =  object: Function2D {
                override fun derivative(x: Double): Double {
                    val (selectedBezierCurve, selectedBezierT) = selectBezierCurveAndT(bezierCurves, x)
                    return selectedBezierCurve.xParametricFunction.derivative(selectedBezierT)
                }

                override fun integrate(xStart: Double, xEnd: Double): Double {
                    check(xStart in 0.0..1.0) { "xStart must be in 0.0 to 1.0 range" }
                    check(xEnd in 0.0..1.0) { "xEnd must be in 0.0 to 1.0 range" }
                    check(xStart <= xEnd) { "xStart must be less or equal to xEnd" }
                    val (selectedStartBezierCurve, selectedBezierTStart, selectedStartBezierCurveIndex) = selectBezierCurveAndT(bezierCurves, xStart)
                    val (selectedEndBezierCurve, selectedBezierTEnd, selectedEndBezierCurveIndex) = selectBezierCurveAndT(bezierCurves, xEnd)

                    if (selectedStartBezierCurveIndex == selectedEndBezierCurveIndex) {
                        return selectedStartBezierCurve.xParametricFunction.integrate(selectedBezierTStart, selectedBezierTEnd)
                    } else {
                        val startBezierIntegral =
                            selectedStartBezierCurve.xParametricFunction.integrate(selectedBezierTStart, 1.0)
                        val endBezierIntegral =
                            selectedEndBezierCurve.xParametricFunction.integrate(0.0, selectedBezierTEnd)
                        val middleBezierIntegrals =
                            bezierCurves.subList(selectedStartBezierCurveIndex + 1, selectedEndBezierCurveIndex - 1)
                                .fold(0.0) { acc, bezierCurve ->
                                    return acc + bezierCurve.xParametricFunction.integrate(0.0, 1.0)
                                }
                        return startBezierIntegral + middleBezierIntegrals + endBezierIntegral
                    }
                }

                override fun invoke(x: Double): Double {
                    val (selectedBezierCurve, selectedBezierT) = selectBezierCurveAndT(bezierCurves, x)
                    return selectedBezierCurve(selectedBezierT).x
                }
            }

            val yParametricFunction =  object: Function2D {
                override fun derivative(x: Double): Double {
                    val (selectedBezierCurve, selectedBezierT) = selectBezierCurveAndT(bezierCurves, x)
                    return selectedBezierCurve.yParametricFunction.derivative(selectedBezierT)
                }

                override fun integrate(xStart: Double, xEnd: Double): Double {
                    check(xStart in 0.0..1.0) { "xStart must be in 0.0 to 1.0 range" }
                    check(xEnd in 0.0..1.0) { "xEnd must be in 0.0 to 1.0 range" }
                    check(xStart <= xEnd) { "xStart must be less or equal to xEnd" }
                    val (selectedStartBezierCurve, selectedBezierTStart, selectedStartBezierCurveIndex) = selectBezierCurveAndT(bezierCurves, xStart)
                    val (selectedEndBezierCurve, selectedBezierTEnd, selectedEndBezierCurveIndex) = selectBezierCurveAndT(bezierCurves, xEnd)

                    if (selectedStartBezierCurveIndex == selectedEndBezierCurveIndex) {
                        return selectedStartBezierCurve.yParametricFunction.integrate(selectedBezierTStart, selectedBezierTEnd)
                    } else {
                        val startBezierIntegral =
                            selectedStartBezierCurve.yParametricFunction.integrate(selectedBezierTStart, 1.0)
                        val endBezierIntegral =
                            selectedEndBezierCurve.yParametricFunction.integrate(0.0, selectedBezierTEnd)
                        val middleBezierIntegrals =
                            bezierCurves.subList(selectedStartBezierCurveIndex + 1, selectedEndBezierCurveIndex - 1)
                                .fold(0.0) { acc, bezierCurve ->
                                    return acc + bezierCurve.yParametricFunction.integrate(0.0, 1.0)
                                }
                        return startBezierIntegral + middleBezierIntegrals + endBezierIntegral
                    }
                }

                override fun invoke(x: Double): Double {
                    val (selectedBezierCurve, selectedBezierT) = selectBezierCurveAndT(bezierCurves, x)
                    return selectedBezierCurve(selectedBezierT).y
                }
            }
            return Pair(xParametricFunction, yParametricFunction)
        }
    }

}