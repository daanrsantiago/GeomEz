package space.elements

import org.ejml.simple.SimpleMatrix
import space.CoordinateSystem3D
import units.Angle

/**
 * Interface representing a 3D geometric entity with x/y/z components and affine transform support.
 */
interface Entity3D {
    /** The x coordinate component. */
    val x: Double
    /** The y coordinate component. */
    val y: Double
    /** The z coordinate component. */
    val z: Double

    /**
     * Column matrix representation of that entity with its x, y and z components respectively
     */
    val matrix: SimpleMatrix
        get() = SimpleMatrix(
            arrayOf(
                doubleArrayOf(x),
                doubleArrayOf(y),
                doubleArrayOf(z)
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
                doubleArrayOf(z),
                doubleArrayOf(1.0)
            )
        )

    /**
     * Rotate entity in the anti-clockwise direction along the given axis
     */
    fun rotate(axis: VectorialEntity3D, angle: Angle): Entity3D

    /**
     * Describe entity as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Entity3D

    // Three-dimensional Entity operations

    /** Element-wise entity addition. */
    operator fun plus(entity3D: Entity3D): Entity3D

    /** Element-wise entity subtraction. */
    operator fun minus(entity3D: Entity3D): Entity3D

    /** Element-wise entity negation. */
    operator fun unaryMinus(): Entity3D

    //    Scalar operations

    /** Scalar addition applied to all three components. */
    operator fun plus(scalar: Double): Entity3D

    /** Scalar subtraction applied to all three components. */
    operator fun minus(scalar: Double): Entity3D

    /** Scalar multiplication applied to all three components. */
    operator fun times(scalar: Double): Entity3D

    /** Scalar division applied to all three components. */
    operator fun div(scalar: Double): Entity3D

    /** Destructuring support returning x. */
    operator fun component1(): Double = x

    /** Destructuring support returning y. */
    operator fun component2(): Double = y

    /** Destructuring support returning z. */
    operator fun component3(): Double = z

}