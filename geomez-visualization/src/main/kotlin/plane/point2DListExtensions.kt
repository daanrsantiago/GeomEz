package plane

import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.pyplot.grid
import io.github.danielTucano.matplotlib.pyplot.subplots
import io.github.danielTucano.python.pythonExecution
import plane.elements.Point2D
import plane.elements.xValues
import plane.elements.yValues

/** Creates a standalone matplotlib plot of this list of [Point2D] and displays it. */
fun List<Point2D>.plot(kwargs: Map<Line2D.Line2DArgs, KwargValue>? = null) {
    pythonExecution {
        this.addPlotCommands(kwargs = kwargs)
        grid()
        show()
    }
}

/**
 * Adds plot commands for this list of [Point2D] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing matplotlib axes, or null to create a new subplot.
 * @return A pair of (Figure, Axes) for further composition.
 */
fun List<Point2D>.addPlotCommands(figure: Figure? = null, axes: Axes? = null, kwargs: Map<Line2D.Line2DArgs, KwargValue>? = null): Pair<Figure, Axes> {
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
    ax.plot(x = this.xValues, y = this.yValues, kwargs = kwargs)
    return fig to ax
}