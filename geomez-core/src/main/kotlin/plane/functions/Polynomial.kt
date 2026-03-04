package plane.functions

import extensions.equalsDelta
import kotlin.math.max
import kotlin.math.pow

/**
 * @param coefficients Coefficients of the polynomial from the lowest order to the highest
 *
 * EXAMPLE: coefficients = listOf(1.0, 1.5, 3.0) represents p(x) = 1.0 + 1.5 * x^2 + 3.0 * x^3
 */
class Polynomial(coefficients: List<Double>) : Function2D {

    /** List of polynomial coefficients ordered from degree 0 to highest (a0 + a1·x + a2·x² + ...). */
    var coefficients: List<Double> = coefficients
        private set

    /** Degree of the polynomial (number of coefficients minus one). */
    var order: Int
        private set

    init {
        // Remove leading zeros
        while (this.coefficients.last().equalsDelta(0.0)) {
            this.coefficients = this.coefficients.dropLast(1)
            if (this.coefficients.size < 2) throw IllegalArgumentException("Polynomial must have at least 2 coefficients")
        }
        this.order = this.coefficients.size - 1
    }

    /**
     * Derivative polynomial
     */
    fun derivative(): Polynomial {
        if (order == 1) throw IllegalStateException("Polynomial of first order it's not differentiable")
        val newCoefficients = this.coefficients.mapIndexedNotNull { index, coeff ->
            if (index == 0) return@mapIndexedNotNull null
            coeff * index
        }
        return Polynomial(newCoefficients)
    }

    /** Evaluates the analytical derivative at [x]. */
    override fun derivative(x: Double): Double = derivative()(x)

    /**
     * Integral polynomial
     */
    fun integral(integrationConstant: Double = 0.0): Polynomial {
        val newCoefficients = mutableListOf(integrationConstant)
        this.coefficients.forEachIndexed { index, coeff ->
            newCoefficients.add(coeff / (index + 1))
        }
        return Polynomial(newCoefficients)
    }

    /** Analytically integrates the polynomial between [xStart] and [xEnd]. */
    override fun integrate(xStart: Double, xEnd: Double): Double {
        val integralPolynomial = integral()
        return integralPolynomial(xEnd) - integralPolynomial(xStart)
    }

    /** Evaluates the polynomial at [x]. */
    override fun invoke(x: Double): Double {
        return  coefficients.reduceIndexed { index, acc, coeff -> acc + x.pow(index) * coeff }
    }

    // Polynomial operations

    /** Adds this polynomial with [polynomial]. */
    operator fun plus(polynomial: Polynomial): Polynomial {
        val maxOrder = max(this.order, polynomial.order)
        val newCoefficients = (0..maxOrder).map { index ->
            this.coefficients.getOrElse(index) { 0.0 } + polynomial.coefficients.getOrElse(index) { 0.0 }
        }
        return Polynomial(newCoefficients)
    }

    /** Subtracts [polynomial] from this polynomial. */
    operator fun minus(polynomial: Polynomial): Polynomial {
        val maxOrder = max(this.order, polynomial.order)
        val newCoefficients = (0..maxOrder).map { index ->
            this.coefficients.getOrElse(index) { 0.0 } - polynomial.coefficients.getOrElse(index) { 0.0 }
        }
        return Polynomial(newCoefficients)
    }

    /** Multiplies this polynomial by [polynomial]. */
    operator fun times(polynomial: Polynomial): Polynomial {
        val newCoefficients = MutableList(this.order + polynomial.order + 1) {0.0}
        this.coefficients.forEachIndexed {thisCoeffIndex, thisCoeff ->
            polynomial.coefficients.forEachIndexed { otherCoeffIndex, otherCoeff ->
                newCoefficients[thisCoeffIndex + otherCoeffIndex] += thisCoeff * otherCoeff
            }
        }
        return Polynomial(newCoefficients)
    }

    infix fun pow (exponent: Int): Polynomial {
        if (exponent < 1) throw IllegalArgumentException("Exponent must be greater then 1")
        return when(exponent) {
            1 -> this
            else -> (2..exponent).fold(this) { acc, _ -> acc * this  }
        }
    }

    // Scalar operations

    /** Adds [scalar] to the constant term of this polynomial. */
    operator fun plus(scalar: Double): Polynomial {
        return Polynomial(
            this.coefficients.mapIndexed { index, coeff ->
                if (index == 0) {
                    coeff + scalar
                } else {
                    coeff
                }
            }
        )
    }

    /** Subtracts [scalar] from the constant term of this polynomial. */
    operator fun minus(scalar: Double): Polynomial {
        return Polynomial(
            this.coefficients.mapIndexed { index, coeff ->
                if (index == 0) {
                    coeff - scalar
                } else {
                    coeff
                }
            }
        )
    }

    /** Multiplies all coefficients by [scalar]. */
    operator fun times(scalar: Double): Polynomial {
        return Polynomial(
            this.coefficients.map { it * scalar }
        )
    }

    /** Divides all coefficients by [scalar]. */
    operator fun div(scalar: Double): Polynomial {
        return Polynomial(
            this.coefficients.map { it / scalar }
        )
    }

}