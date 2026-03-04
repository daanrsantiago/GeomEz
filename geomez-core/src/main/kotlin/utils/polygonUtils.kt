package utils

import plane.ConvexPolygon2D
import plane.elements.Point2D
import space.elements.Direction3D

/**
 * Computes the convex hull of [points2D] using Graham scan, returning a pair of the hull as a
 * ConvexPolygon2D and the remaining interior points.
 */
fun convexHull(points2D: List<Point2D>): Pair<ConvexPolygon2D, List<Point2D>> {
    val minYPoint = points2D.sortedBy { it.y }[0]
    val angleSortedPoints = points2D.sortedBy {
        if (it == minYPoint) {
            return@sortedBy 0.0
        }
        (it - minYPoint).toVector3D.angleBetween(Direction3D.MAIN_X_DIRECTION).value
    }
    val convexHullPoints = mutableListOf(minYPoint, angleSortedPoints[1])
    val remainingPoints = (angleSortedPoints - convexHullPoints + minYPoint).toMutableList()
    var currentPoint = convexHullPoints.last()

    while (currentPoint != minYPoint) {
        val previousEdgeVector = (convexHullPoints[convexHullPoints.lastIndex - 1] - currentPoint).toVector3D
        val nextEdgeVector = (remainingPoints[0] - currentPoint).toVector3D
        if (nextEdgeVector.cross(previousEdgeVector).z > 0) {
            if (remainingPoints[0] == minYPoint) break
            convexHullPoints.add(remainingPoints[0])
            remainingPoints.remove(remainingPoints[0])
        } else {
            convexHullPoints.removeLast()
        }
        currentPoint = convexHullPoints.last()
    }

    return Pair(ConvexPolygon2D(convexHullPoints.reversed()), points2D - convexHullPoints)
}