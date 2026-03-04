package extensions

import org.ejml.simple.SimpleMatrix
import plane.elements.Point2D
import space.elements.Direction3D
import space.elements.Point3D

operator fun SimpleMatrix.times(matrix: SimpleMatrix): SimpleMatrix {
    return this.mult(matrix)
}

/** Flattens the matrix into a list of all elements in row-major order. */
fun SimpleMatrix.toList(): List<Double> {
    return MutableList(this.numElements) {0.0}.mapIndexed {index, _ -> this.get(index) }
}

/** Destructuring: returns the first element (index 0). */
operator fun SimpleMatrix.component1(): Double {
    return this[0]
}

/** Destructuring: returns the second element (index 1). */
operator fun SimpleMatrix.component2(): Double {
    return this[1]
}

/** Destructuring: returns the third element (index 2). */
operator fun SimpleMatrix.component3(): Double {
    return this[2]
}

/** Calls [transform] on every element in place; returns nothing (modifies this matrix). */
fun SimpleMatrix.onEach(transform: (element: Double) -> Double) {
    (0 until this.numElements).forEach {
        this[it] = transform(this[it])
    }
}

/** Calls [transform] with the linear index on every element in place. */
fun SimpleMatrix.onEachIndexed(transform: (index: Int, element: Double) -> Double) {
    (0 until this.numElements).forEach {
        this[it] = transform(it,this[it])
    }
}

/** Transforms each element with [transform] and returns a new matrix. */
fun SimpleMatrix.map(transform: (element: Double) -> Double): SimpleMatrix {
    val newMatrix = this.copy()
    newMatrix.onEach(transform)
    return newMatrix
}

/** Transforms each element with its linear index using [transform] and returns a new matrix. */
fun SimpleMatrix.mapIndexed(transform: (index: Int, element: Double) -> Double): SimpleMatrix {
    val newMatrix = this.copy()
    newMatrix.onEachIndexed(transform)
    return newMatrix
}

/** Iterates over every element with its linear index, calling [operation] on each. */
fun SimpleMatrix.forEachIndexed(operation: (index: Int, element: Double) -> Unit) {
    (0 until this.numElements).forEach {
        operation(it, this[it])
    }
}

/**
 * When multiplying a 2x2 or 3x3 matrix by a point2D returns a point2D equivalent to that transformation
 */
operator fun SimpleMatrix.times(point2D: Point2D): Point2D {
    if (this.numRows() != this.numCols()) throw IllegalArgumentException("Matrix must be 2x2 or 3x3 to perform multiplication")
    return when (this.numRows()) {
        2 -> {
            val (newX, newY) = this * point2D.matrix
            Point2D(newX, newY)
        }
        3 -> {
            val (newX, newY) = this * point2D.affineMatrix
            Point2D(newX, newY)
        }
        else -> throw IllegalArgumentException("Matrix must be 2x2 or 3x3 to perform multiplication")
    }
}

/**
 * When multiplying a 3x3 or 4x4 matrix by a point3D returns a point3D equivalent to that transformation
 */
operator fun SimpleMatrix.times(point3D: Point3D): Point3D {
    if (this.numRows() != this.numCols()) throw IllegalArgumentException("Matrix must be 3x3 or 4x4 to perform multiplication")
    return when (this.numRows()) {
        3 -> {
            val (newX, newY, newZ) = this * point3D.matrix
            Point3D(newX, newY, newZ)
        }
        4 -> {
            val (newX, newY, newZ) = this * point3D.affineMatrix
            Point3D(newX, newY, newZ)
        }
        else -> throw IllegalArgumentException("Matrix must be 3x3 or 4x4 to perform multiplication")
    }
}

/**
 * When multiplying a 3x3 matrix by a direction3D returns a direction3D equivalent to that transformation
 */
operator fun SimpleMatrix.times(direction3D: Direction3D): Direction3D {
    if (this.numRows() != 3 || this.numCols() != 3) throw IllegalArgumentException("Matrix must be 3x3 to perform multiplication")
    val (newX, newY, newZ) = this * direction3D.matrix
    return Direction3D(newX, newY, newZ)
}