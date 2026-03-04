package space.functions

/** Interface for a scalar function of two variables: f(x,y) → Double. */
interface Function3D {

    /**
     * Evaluate that function at given x and y values
     */
    operator fun invoke(x: Double, y: Double): Double
}