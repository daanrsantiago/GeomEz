package plane

import org.junit.jupiter.api.Test
import plane.elements.*
import kotlin.test.assertEquals

internal class Curve2DTest {

    @Test
    fun `must get all x values`() {
        val curve = Curve2D(
            Point2D(1.0, 2.0),
            Point2D(3.0, 4.0),
            Point2D(5.0, 6.0)
        )
        val xPoints = curve.points.xPoints

        assertEquals(listOf(1.0,3.0,5.0), xPoints)
    }

    @Test
    fun `must get all y values`() {
        val curve = Curve2D(
            Point2D(1.0, 2.0),
            Point2D(3.0, 4.0),
            Point2D(5.0, 6.0)
        )
        val yPoints = curve.points.yPoints

        assertEquals(listOf(2.0,4.0,6.0), yPoints)
    }

    @Test
    fun `must wrap around when invoking curve points with index out of bounds`() {
        val curve = Curve2D(
            Point2D(1.0, 2.0),
            Point2D(3.0, 4.0),
            Point2D(5.0, 6.0)
        )
        val lastPoint = curve(-1)
        val firstPoint = curve(3)

        assertEquals(5.0,lastPoint.x)
        assertEquals(6.0,lastPoint.y)

        assertEquals(1.0,firstPoint.x)
        assertEquals(2.0,firstPoint.y)
    }

}
