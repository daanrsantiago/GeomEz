package units

import kotlin.math.PI

/**
 * Sealed class representing an angle value, either in Radians or Degrees.
 * Arithmetic operations automatically convert between representations.
 */
sealed class Angle {
    /** The numeric angle value in the unit of this subclass. */
    abstract val value: Double

    /** Adds a scalar offset to this angle. */
    abstract operator fun plus(value: Double): Angle
    /** Subtracts a scalar offset from this angle. */
    abstract operator fun minus(value: Double): Angle
    /** Multiplies this angle by a scalar. */
    abstract operator fun times(value: Double): Angle
    /** Divides this angle by a scalar. */
    abstract operator fun div(value: Double): Angle

    /** Adds another angle, converting units as needed. */
    abstract operator fun plus(angle: Angle): Angle
    /** Subtracts another angle, converting units as needed. */
    abstract operator fun minus(angle: Angle): Angle
    /** Multiplies by another angle, converting units as needed. */
    abstract operator fun times(angle: Angle): Angle
    /** Divides by another angle, converting units as needed. */
    abstract operator fun div(angle: Angle): Angle

    /** Compares this angle to [angle] by numeric value. */
    abstract operator fun compareTo(angle: Angle): Int

    /** An angle measured in radians. */
    data class Radians(override var value: Double) : Angle() {
        /** Converts this angle to Degrees. */
        fun toDegrees(): Degrees = Degrees(this.value * 180.0 / PI)

        override operator fun plus(value: Double): Radians = Radians(this.value + value)
        override operator fun minus(value: Double): Radians = Radians(this.value - value)
        override operator fun times(value: Double): Radians = Radians(this.value * value)
        override operator fun div(value: Double): Radians = Radians(this.value / value)

        override operator fun plus(angle: Angle): Radians {
            return when (angle) {
                is Radians -> Radians(this.value + angle.value)
                is Degrees -> Radians(this.value + angle.value * PI / 180)
            }
        }

        override operator fun minus(angle: Angle): Radians {
            return when (angle) {
                is Radians -> Radians(this.value - angle.value)
                is Degrees -> Radians(this.value - angle.value * PI / 180)
            }
        }

        override operator fun times(angle: Angle): Radians {
            return when (angle) {
                is Radians -> Radians(this.value * angle.value)
                is Degrees -> Radians(this.value * angle.value * PI / 180)
            }
        }

        override operator fun div(angle: Angle): Radians {
            return when (angle) {
                is Radians -> Radians(this.value * angle.value)
                is Degrees -> Radians(this.value * angle.value * PI / 180)
            }
        }

        override operator fun compareTo(angle: Angle): Int {
            when (angle) {
                is Radians -> {
                    if (this.value > angle.value) {
                        return 1
                    } else if (this.value < angle.value) {
                        return -1
                    }
                    return 0
                }
                is Degrees -> {
                    if (this.value * 180.0 / PI > angle.value) {
                        return 1
                    } else if (this.value * 180.0 / PI < angle.value) {
                        return -1
                    }
                    return 0
                }
            }
        }
    }

    /** An angle measured in degrees. */
    data class Degrees(override var value: Double) : Angle() {
        /** Converts this angle to Radians. */
        fun toRadians(): Radians = Radians(this.value * PI / 180.0)

        override operator fun plus(value: Double): Degrees = Degrees(this.value + value)
        override operator fun minus(value: Double): Degrees = Degrees(this.value - value)
        override operator fun times(value: Double): Degrees = Degrees(this.value * value)
        override operator fun div(value: Double): Degrees = Degrees(this.value / value)

        override operator fun plus(angle: Angle): Degrees {
            return when (angle) {
                is Radians -> Degrees(this.value + angle.value * 180 / PI)
                is Degrees -> Degrees(this.value + angle.value)
            }
        }

        override operator fun minus(angle: Angle): Degrees {
            return when (angle) {
                is Radians -> Degrees(this.value + angle.value * 180 / PI)
                is Degrees -> Degrees(this.value + angle.value)
            }
        }

        override operator fun times(angle: Angle): Degrees {
            return when (angle) {
                is Radians -> Degrees(this.value + angle.value * 180 / PI)
                is Degrees -> Degrees(this.value + angle.value)
            }
        }

        override operator fun div(angle: Angle): Degrees {
            return when (angle) {
                is Radians -> Degrees(this.value + angle.value * 180 / PI)
                is Degrees -> Degrees(this.value + angle.value)
            }
        }

        override operator fun compareTo(angle: Angle): Int {
            when (angle) {
                is Radians -> {
                    if (this.value > angle.value) {
                        return 1
                    } else if (this.value < angle.value) {
                        return -1
                    }
                    return 0
                }
                is Degrees -> {
                    if (this.value * PI / 180.0 > angle.value) {
                        return 1
                    } else if (this.value * 180.0 / PI < angle.value) {
                        return -1
                    }
                    return 0
                }
            }
        }
    }
}

