package space.elements

import units.Angle
import utils.cos
import utils.sin

/**
 * Represents a cylindrical coordinate with positive angle in anti-clockwise direction and 0 at x-axis
 */
class CylindricalCoordinate(
    /** Radial distance from the z-axis. */
    val radius: Double,
    /** Counter-clockwise angle from the positive x-axis in the xy-plane. */
    val angle: Angle,
    /** Height along the z-axis. */
    val z: Double
) {

    /** Converts this cylindrical coordinate to a Cartesian Point3D. */
    fun toPoint3D(): Point3D = Point3D(radius * cos(angle),radius * sin(angle),z)
    /** Converts this cylindrical coordinate to a Cartesian Vector3D. */
    fun toVector3D(): Vector3D = Vector3D(radius * cos(angle),radius * sin(angle),z)

    /**
     * Returns the tangent towards the anti-clockwise direction
     */
    val tangentDirection: Direction3D
        get() = Direction3D.MAIN_Z_DIRECTION cross this.toVector3D().direction

    /**
     * Returns the normal direction
     */
    val normalDirection: Direction3D
        get() = this.toVector3D().direction
}