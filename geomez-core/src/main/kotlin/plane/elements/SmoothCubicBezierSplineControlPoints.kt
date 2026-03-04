package plane.elements

import units.Angle
import utils.cos
import utils.sin

/**
 * Represents a smooth cubic Bézier segment by its anchor point and two control points; used to
 * build [plane.CubicBezierSpline2D].
 *
 * @param pointOnCurve The anchor point that lies on the curve.
 * @param angle The tangent angle at the anchor point.
 * @param distanceControlPointBefore Distance from the anchor to the preceding control point, or null if this is the first segment.
 * @param distanceControlPointAfter Distance from the anchor to the following control point, or null if this is the last segment.
 */
class SmoothCubicBezierSplineControlPoints (
    private val pointOnCurve: Point2D,
    private val angle: Angle,
    private val distanceControlPointBefore: Double? = null,
    private val distanceControlPointAfter: Double? = null
) {
    /** Returns the ordered list of control points for this segment. */
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