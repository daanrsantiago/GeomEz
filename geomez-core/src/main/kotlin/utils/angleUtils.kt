package utils

import units.Angle

/** Computes cos of the given [angle], handling both radians and degrees. */
fun cos(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.cos(angle.value)
    is Angle.Degrees -> kotlin.math.cos(angle.toRadians().value)
}

/** Computes sin of the given [angle], handling both radians and degrees. */
fun sin(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.sin(angle.value)
    is Angle.Degrees -> kotlin.math.sin(angle.toRadians().value)
}

/** Computes tan of the given [angle], handling both radians and degrees. */
fun tan(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.tan(angle.value)
    is Angle.Degrees -> kotlin.math.tan(angle.toRadians().value)
}

/** Returns the arc-cosine of [angle]'s value as a Double. */
fun acos(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.acos(angle.value)
    is Angle.Degrees -> kotlin.math.acos(angle.toRadians().value)
}
/** Returns the arc-sine of [angle]'s value as a Double. */
fun asin(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.asin(angle.value)
    is Angle.Degrees -> kotlin.math.asin(angle.toRadians().value)
}

/** Returns the arc-tangent of [angle]'s value as a Double. */
fun atan(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.atan(angle.value)
    is Angle.Degrees -> kotlin.math.atan(angle.toRadians().value)
}