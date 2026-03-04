package utils

/**
 * Returns a list where each element is linearly interpolated between the corresponding element in
 * [startValues] and [endValues] by [endFraction] (0.0 = start, 1.0 = end).
 */
fun lerpList(startValues: List<Double>, endValues: List<Double>, endFraction: Double): List<Double> {
    return startValues.mapIndexed { index, startValue -> lerp(startValue, endValues[index], endFraction) }
}