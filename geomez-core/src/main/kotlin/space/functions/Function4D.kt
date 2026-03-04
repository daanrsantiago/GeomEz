package space.functions

/** Interface for a scalar function of three variables: f(x,y,z) → Double. */
interface Function4D {

    /**
     * Evaluate that function at given x, y and z values
     */
    operator fun invoke(x: Double, y: Double, z: Double): Double
}