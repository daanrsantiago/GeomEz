package plane

import org.junit.jupiter.api.Test
import plane.elements.Point2D
import plane.elements.SmoothCubicBezierSplineControlPoints
import units.Angle

internal class CubicBezierSpline2DTest {

    @Test
    fun `Must visualize PiecewiseCubicBezierCurve`() {
        val controlPoints = listOf(
            Point2D(0.0,0.0),
            Point2D(0.5, 0.5),
            Point2D(1.0, 0.5),
            Point2D(1.5,0.0),
            Point2D(2.0,-0.5),
            Point2D(2.5, -0.5),
            Point2D(3.0,0.0)
        )

        val cubicBezierSpline2D = CubicBezierSpline2D(controlPoints)

        cubicBezierSpline2D.plot()
    }

    @Test
    fun `Must be able to visualize cubic bezier spline build with smooth control points`() {
        val smoothCubicBezierSplineControlPoints = listOf(
            SmoothCubicBezierSplineControlPoints(
                Point2D(0.0, 0.0),
                Angle.Degrees(45.0),
                distanceControlPointAfter = 1.0
            ),
            SmoothCubicBezierSplineControlPoints(
                Point2D(2.0, 0.0),
                Angle.Degrees(45.0),
                distanceControlPointBefore = 1.0,
                distanceControlPointAfter = 1.0
            ),
            SmoothCubicBezierSplineControlPoints(
                Point2D(4.0, 0.0),
                Angle.Degrees(45.0),
                distanceControlPointBefore = 1.0
            )
        )

        val cubicBezierSpline2D = CubicBezierSpline2D(smoothCubicBezierSplineControlPoints)

        cubicBezierSpline2D.plot()
    }
}