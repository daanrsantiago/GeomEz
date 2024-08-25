package plane.elements

import units.Angle
import utils.cos
import utils.sin

class SmoothCubicBezierSplineControlPoints (
    private val pointOnCurve: Point2D,
    private val angle: Angle,
    private val distanceControlPointBefore: Double? = null,
    private val distanceControlPointAfter: Double? = null
) {
    fun getControlPoints(): List<Point2D> {
        val controlPointBefore = if (distanceControlPointBefore != null ) {
            Point2D(
                pointOnCurve.x - distanceControlPointBefore * cos(angle),
                pointOnCurve.y - distanceControlPointBefore * sin(angle)
            )
        } else null

        val controlPointAfter = if (distanceControlPointAfter != null ) {
            Point2D(
                pointOnCurve.x + distanceControlPointAfter * cos(angle),
                pointOnCurve.y + distanceControlPointAfter * sin(angle)
            )
        } else null

        return listOfNotNull(
            controlPointBefore,
            pointOnCurve,
            controlPointAfter
        )
    }
}