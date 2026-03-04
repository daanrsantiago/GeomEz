package plane.elements

import units.Angle
import utils.cos
import utils.sin

/** Represents a 2D point in polar form (radius, angle). */
class PolarCoordinate(
    /** Distance from the origin. */
    val radius: Double,
    /** Counter-clockwise angle from the positive x-axis. */
    val angle: Angle
) {

    /** Converts to Cartesian Point2D. */
    fun toPoint2D(): Point2D = Point2D( radius * cos(angle), radius * sin(angle))

}