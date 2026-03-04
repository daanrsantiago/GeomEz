package space

import extensions.xValues
import extensions.yValues
import extensions.zValues
import io.github.danielTucano.matplotlib.Axes3D
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.show
import io.github.danielTucano.python.pythonExecution
import utils.linspace

/** Creates a standalone matplotlib plot of this [ParametricCurve3D] and displays it. */
fun ParametricCurve3D.plot(tList: List<Double> = linspace(0.0, 1.0, 100)) {
    pythonExecution {
        this.addPlotCommands(tList = tList)
        show()
    }
}

/**
 * Adds plot commands for this [ParametricCurve3D] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing Axes3D, or null to create a new 3D subplot.
 * @param tList Parameter values to evaluate the curve at. Defaults to 100 evenly spaced points on [0, 1].
 * @return A pair of (Figure, Axes3D) for further composition.
 */
fun ParametricCurve3D.addPlotCommands(
    figure: Figure? = null,
    axes: Axes3D? = null,
    tList: List<Double> = linspace(0.0, 1.0, 100)
): Pair<Figure, Axes3D> {
    val fig = when (figure) {
        null -> figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }
    val points = this(tList)
    ax.plot(points.xValues, points.yValues, points.zValues)
    return fig to ax
}
