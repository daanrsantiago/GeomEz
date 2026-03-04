package plane

import plane.elements.Point2D

/** A convex polygon; construction validates convexity of the input point list. */
class ConvexPolygon2D(points: List<Point2D>) : Polygon2D(points) {

    /**
     * Checks if its convex
     */
    init {
        points.forEachIndexed { index, point2D ->
            val nextEdgeVector = (this(index + 1) - point2D).toVector3D
            val prevEdgeVector = (this(index - 1) - point2D).toVector3D
            if (nextEdgeVector.cross(prevEdgeVector).z > 0) {
                throw IllegalArgumentException("List of points don't form a convex polygon")
            }
        }
    }

    /** Returns a new convex polygon scaled by [scalar] around the origin. */
    override fun scale(scalar: Double): ConvexPolygon2D {
        return ConvexPolygon2D(points.map { point2D -> point2D * scalar })
    }
}