package space

import extensions.equalsDelta
import space.elements.Direction3D
import space.elements.Point3D

/** A 3D plane defined by an origin point and two direction vectors spanning the plane. */
class Plane(
    /** Origin point of the plane. */
    val planeOrigin: Point3D = Point3D(0.0, 0.0, 0.0),
    /** First spanning direction of the plane. */
    val planeXDirection: Direction3D,
    /** Second spanning direction of the plane. */
    val planeYDirection: Direction3D
) {

    /** The normal direction to this plane, computed as the cross product of the two spanning directions. */
    val normalDirection = planeXDirection cross planeYDirection

    /** A CoordinateSystem3D whose xy-plane coincides with this plane. */
    val coordinateSystem3D = CoordinateSystem3D(
        planeXDirection,
        planeYDirection,
        normalDirection,
        planeOrigin
    )

    /**
     * Checks if the point belong to the plane
     */
    fun pointIsInPlane(point: Point3D): Boolean {
        return normalDirection.dot((point - planeOrigin).asVector3D()).equalsDelta(0.0, delta = 1e-7)
    }

    /** Expresses this plane in the [to] coordinate system, as if currently written in [asWrittenIn]. */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Plane {
        return Plane(
            planeOrigin = planeOrigin.changeBasis(asWrittenIn, to),
            planeXDirection = planeXDirection.changeBasis(asWrittenIn, to).direction,
            planeYDirection = planeYDirection.changeBasis(asWrittenIn, to).direction
        )
    }

}