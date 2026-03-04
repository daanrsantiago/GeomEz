package plane

import io.github.danielTucano.matplotlib.Axes
import io.github.danielTucano.matplotlib.AxesBase
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.pyplot.grid
import io.github.danielTucano.matplotlib.pyplot.subplots
import io.github.danielTucano.matplotlib.show
import io.github.danielTucano.python.pythonExecution
import plane.elements.xValues
import plane.elements.yValues

/** Creates a standalone matplotlib plot of this [Polygon2D] and displays it. */
fun Polygon2D.plot() {
    pythonExecution {
        val (_, ax) = this.addPlotCommands()
        ax.set_aspect(AxesBase.AspectOptions.equal, AxesBase.AjustableOptions.datalim)
        grid()
        show()
    }
}

/**
 * Adds plot commands for this [Polygon2D] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing matplotlib axes, or null to create a new subplot.
 * @return A pair of (Figure, Axes) for further composition.
 */
fun Polygon2D.addPlotCommands(figure: Figure? = null, axes: Axes? = null): Pair<Figure, Axes> {
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
    ax.plot(pointsClosedPolygon.xValues, pointsClosedPolygon.yValues)
    return fig to ax
}