package plane.functions

import plane.CoordinateSystem2D
import plane.elements.*
import space.CoordinateSystem3D
import space.elements.Point3D

/**
 * Piecewise linear interpolation implementing Function2D; connects given data points with
 * straight-line segments.
 */
class LinearSpline(val points: List<Point2D>) : Function2D {

    /**
     * Certifies that all x values are in ascending order and do not have repetition
     */
    init {
        if (points.xPoints.sorted() != points.xPoints) {
            throw IllegalArgumentException("X values are not in ascending order")
        } else if (points.xPoints != points.xPoints.distinct()) {
            throw IllegalArgumentException("Repeated X value present")
        }
    }

    /**
     * Get Y value interpolating linearly along provided points
     */
    private fun interpolate(x: Double): Double {
        if (!points.inXRange(x)) {
            throw IllegalArgumentException("X value out of function range")
        }
        // Finding indexes of the points where the provided x would be between
        val nextXIndex = if (x < points.xPoints.last()) points.xPoints.indexOfFirst { it > x } else points.xPoints.lastIndex
        val previousXIndex = nextXIndex - 1

        /**
         * Fraction corresponding to the proximity of the provided x to the next x in
         * relation to the previous x. The closest it gets from 1 the closest it is from the
         * next point x, the closest it is from 0 the closest it is from the previous point x
         */
        val fractionOfNextX = (x - points.xPoints[previousXIndex]) / (points.xPoints[nextXIndex] - points.xPoints[previousXIndex])

        return points[nextXIndex].y * fractionOfNextX + points[previousXIndex].y * (1 - fractionOfNextX)
    }

    /**
     * Return the function with the corresponding derivatives values using the mid-point approximation
     * for inner points and lateral approximation for boundary points
     */
    override fun derivative(x: Double): Double {
        if (!points.inXRange(x)) {
            throw IllegalArgumentException("X value out of function range")
        }
        // Finding indexes of the points where the provided x would be between
        val nextXIndex = points.xPoints.indexOfFirst { it > x }
        val previousXIndex = nextXIndex - 1

        return (points[nextXIndex].y - points[previousXIndex].y) / (points[nextXIndex].x - points[previousXIndex].x)
    }


    /** Analytically integrates the piecewise linear function between [xStart] and [xEnd]. */
    override fun integrate(xStart: Double, xEnd: Double): Double {
        if (!points.inXRange(xStart) || !points.inXRange(xEnd)) {
            throw IllegalArgumentException("X value out of function range")
        }
        val integrationPoints =
            listOf(
                Point2D(xStart,this(xStart)),
                *this.points.filter { it.x > xStart && it.x < xEnd }.toTypedArray(),
                Point2D(xEnd,this(xEnd))
            )
        return integrationPoints.foldRightIndexed(0.0) { index, point2D, acc ->
            when (index) {
                integrationPoints.lastIndex -> acc
                else -> acc + (point2D.y + integrationPoints[index + 1].y) / 2 * (integrationPoints[index + 1].x - point2D.x)
            }
        }
    }

    /** Expresses all points in the [to] coordinate system, as if currently written in [asWrittenIn]. */
    fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): List<Point2D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): List<Point3D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    fun translateTo(newCentroid: Point2D): LinearSpline {
        return LinearSpline(this.points.map { it + newCentroid - this.points.centroid })
    }

    fun scale(scalar: Double): LinearSpline {
        return LinearSpline(this.points.map { it * scalar })
    }

    /** Evaluates the spline at [x] by linear interpolation. */
    override operator fun invoke(x: Double): Double {
        return interpolate(x)
    }
}
