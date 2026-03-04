package space

import space.elements.Point3D
import space.elements.Vector3D

/** Interface for a 3D vector field: maps (x,y,z) to a Vector3D. */
interface VectorField3D {

    /** Evaluates the vector field at the given coordinates. */
    operator fun invoke(x: Double, y: Double, z: Double): Vector3D

    /** Evaluates the vector field at the given [point3D]. */
    operator fun invoke(point3D: Point3D): Vector3D {
        return this(point3D.x, point3D.y, point3D.z)
    }

    /** Returns a new vector field that is the sum of this field and [vectorField3D]. */
    operator fun plus(vectorField3D: VectorField3D): VectorField3D {
        return object: VectorField3D {
            override fun invoke(x: Double, y: Double, z: Double): Vector3D {
                return this@VectorField3D(x,y,z) + vectorField3D(x,y,z)
            }
        }
    }

    /** Returns a new vector field that is the difference of this field and [vectorField3D]. */
    operator fun minus(vectorField3D: VectorField3D): VectorField3D {
        return object: VectorField3D {
            override fun invoke(x: Double, y: Double, z: Double): Vector3D {
                return this@VectorField3D(x,y,z) - vectorField3D(x,y,z)
            }
        }
    }

    /** Returns a new vector field scaled by [scalar]. */
    operator fun times(scalar: Double): VectorField3D {
        return object: VectorField3D {
            override fun invoke(x: Double, y: Double, z: Double): Vector3D {
                return this@VectorField3D(x,y,z) * scalar
            }
        }
    }

    /** Returns a new vector field divided by [scalar]. */
    operator fun div(scalar: Double): VectorField3D {
        return object: VectorField3D {
            override fun invoke(x: Double, y: Double, z: Double): Vector3D {
                return this@VectorField3D(x,y,z) / scalar
            }
        }
    }
}