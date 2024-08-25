package plane

import org.junit.jupiter.api.Test
import plane.elements.Point2D
import plane.elements.SmoothCubicBezierSplineControlPoints
import units.Angle
import kotlin.test.assertEquals

internal class CubicBezierSpline2DTest {

    @Test
    fun `Cubic bezier spline must have points with index (i - 1) % 3 == 0 in their curve`() {
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

        val firstPoint = cubicBezierSpline2D(0.0)
        val midPoint = cubicBezierSpline2D(0.5)
        val lastPoint = cubicBezierSpline2D(1.0)

        assertEquals(0.0, firstPoint.x, 10e-9)
        assertEquals(0.0, firstPoint.y, 10e-9)

        assertEquals(1.5, midPoint.x, 10e-9)
        assertEquals(0.0, midPoint.y, 10e-9)

        assertEquals(3.0, lastPoint.x, 10e-9)
        assertEquals(0.0, lastPoint.y, 10e-9)
    }

    @Test
    fun `Must be able to build smooth cubic bezier spline`() {
        val smoothCubicBezierSplineControlPoints = listOf(
            SmoothCubicBezierSplineControlPoints(
                Point2D(0.0,0.0),
                Angle.Degrees(45.0),
                distanceControlPointAfter = 1.0
            ),
            SmoothCubicBezierSplineControlPoints(
                Point2D(2.0,0.0),
                Angle.Degrees(45.0),
                distanceControlPointBefore = 1.0,
                distanceControlPointAfter = 1.0
            ),
            SmoothCubicBezierSplineControlPoints(
                Point2D(4.0,0.0),
                Angle.Degrees(45.0),
                distanceControlPointBefore = 1.0
            )
        )

        val cubicBezierSpline2D = CubicBezierSpline2D(smoothCubicBezierSplineControlPoints)

        val firstPoint = cubicBezierSpline2D(0.0)
        val midPoint = cubicBezierSpline2D(0.5)
        val lastPoint = cubicBezierSpline2D(1.0)

        assertEquals(0.0, firstPoint.x, 10e-9)
        assertEquals(0.0, firstPoint.y, 10e-9)

        assertEquals(2.0, midPoint.x, 10e-9)
        assertEquals(0.0, midPoint.y, 10e-9)

        assertEquals(4.0, lastPoint.x, 10e-9)
        assertEquals(0.0, lastPoint.y, 10e-9)
    }

}