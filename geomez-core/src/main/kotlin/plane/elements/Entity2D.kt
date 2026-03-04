package plane.elements

import org.ejml.simple.SimpleMatrix
import plane.CoordinateSystem2D
import space.CoordinateSystem3D
import space.elements.Entity3D
import units.Angle

/**
 * Interface representing a 2D geometric entity with x/y components and support for affine
 * transforms and arithmetic operations.
 */
interface Entity2D {

    /** The x coordinate component. */
    val x: Double
    /** The y coordinate component. */
    val y: Double

    /**
     * Column matrix representation of that entity with its x and y components respectively
     */
    val matrix: SimpleMatrix
        get() = SimpleMatrix(
            arrayOf(
                doubleArrayOf(x),
                doubleArrayOf(y)
            )
        )

    /**
     * Matrix used for affine transformations that perform scaling, translation and rotation
     * It's the same as 'matrix' but with an extra element at the end with value of 1
     */
    val affineMatrix: SimpleMatrix
        get() = SimpleMatrix(
            arrayOf(
                doubleArrayOf(x),
                doubleArrayOf(y),
                doubleArrayOf(1.0)
            )
        )

    /**
     * Rotate entity in the anti-clockwise direction along the origin
     */
    fun rotate(angle: Angle): Entity2D

    /**
     * Rotate entity in the anti-clockwise direction along the given center of rotation
     */
    fun rotate(centerOfRotation: Point2D, angle: Angle): Entity2D

    /**
     * Describe entity as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): Entity2D

    /**
     * Describe entity as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Entity3D

    // Two-dimensional Entity operations

    /** Element-wise entity addition. */
    operator fun plus(entity2D: Entity2D): Entity2D

    /** Element-wise entity subtraction. */
    operator fun minus(entity2D: Entity2D): Entity2D

    /** Element-wise entity negation. */
    operator fun unaryMinus(): Entity2D

    //    Scalar operations

    /** Scalar addition applied to both components. */
    operator fun plus(scalar: Double): Entity2D

    /** Scalar subtraction applied to both components. */
    operator fun minus(scalar: Double): Entity2D

    /** Scalar multiplication applied to both components. */
    operator fun times(scalar: Double): Entity2D

    /** Scalar division applied to both components. */
    operator fun div(scalar: Double): Entity2D

    /** Destructuring support returning x. */
    operator fun component1(): Double = x

    /** Destructuring support returning y. */
    operator fun component2(): Double = y

    //    Component get

    /** Returns x for index 0, y for index 1. */
    operator fun get(index: Int): Double {
        return when (index) {
            0 -> x
            1 -> y
            else -> throw IllegalAccessError("Index out of bounds")
        }
    }
}