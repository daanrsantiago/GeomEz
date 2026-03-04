package plane

import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.pyplot.grid
import io.github.danielTucano.matplotlib.pyplot.subplots
import io.github.danielTucano.python.pythonExecution
import plane.elements.xValues
import plane.elements.yValues
import utils.linspace

/** Creates a standalone matplotlib plot of this [BezierCurve] and displays it. */
fun BezierCurve.plot(tList: List<Double> = linspace(0.0, 1.0, 100)) {
    pythonExecution {
        val (_, ax) = this.addPlotCommands(tList = tList)
        ax.set_aspect(AxesBase.AspectOptions.equal, AxesBase.AjustableOptions.datalim)
        grid()
        show()
    }
}

/**
 * Adds plot commands for this [BezierCurve] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing matplotlib axes, or null to create a new subplot.
 * @return A pair of (Figure, Axes) for further composition.
 */
fun BezierCurve.addPlotCommands(
    figure: Figure? = null,
    axes: Axes? = null,
    tList: List<Double> = linspace(0.0, 1.0, 100)
): Pair<Figure, Axes> {
    lateinit var fig: Figure
    lateinit var ax: Axes
    when {
        (figure == null && axes == null) -> {
            val (fig2, ax2) = subplots()
            fig = fig2
            ax = ax2
        }
        (figure == null && axes != null) -> {
            fig = figure()
            ax = axes
        }
        (figure != null && axes == null) -> {
            fig = figure
            ax = figure.add_subplot()
        }
        else -> {
            fig = figure!!
            ax = axes!!
        }
    }
    ax.plot(
        controlPoints.map { it.x },
        controlPoints.map { it.y },
        "o",
        kwargs = mapOf(Line2D.Line2DArgs.linestyle to KwargValue.Quoted("--"))
    )
    val points = this(tList)
    ax.plot(points.xValues, points.yValues)
    return fig to ax
}