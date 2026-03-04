package plane

import plane.elements.*
import space.CoordinateSystem3D
import space.elements.Point3D
import units.Angle
import kotlin.math.absoluteValue

/**
 * Represent a closed polygon
 */
open class Polygon2D(var points: List<Point2D>) {

    /**
     * Area of the polygon
     * taken from: https://en.wikipedia.org/wiki/Polygon
     */
    val area: Double
        get() {
            return points.foldIndexed(0.0) { index, acc, point2D ->
                acc + point2D.x * this(index + 1).y - this(index + 1).x * point2D.y
            }.absoluteValue / 2
        }

    /** The polygon vertices repeated to close the polygon (last point equals first). */
    val pointsClosedPolygon = listOf(*points.toTypedArray(), points.first())

    /**
     * Gets a point by index, wrapping around when the index is out of bounds.
     */
    operator fun invoke(index: Int): Point2D {
        if (index >= 0) return points[index.rem(points.size)]
        return points[points.size + index.rem(points.size)]
    }

    /** Rotates all points by [angle] around the origin. */
    fun rotate(angle: Angle): Polygon2D {
        return Polygon2D(points.map { it.rotate(angle) })
    }

    /** Rotates all points around [centerOfRotation] by [angle]. */
    fun rotate(centerOfRotation: Point2D, angle: Angle): Polygon2D {
        return Polygon2D(points.map { it.rotate(centerOfRotation, angle) })
    }

    fun translateTo(newCentroid: Point2D): Polygon2D {
        return Polygon2D(this.points.map { it + newCentroid - this.points.centroid })
    }

    fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): List<Point2D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): List<Point3D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    /** Returns a new polygon with all points scaled by [scalar] around the origin. */
    open fun scale(scalar: Double): Polygon2D {
        return Polygon2D(this.points.map { it * scalar })
    }

}
