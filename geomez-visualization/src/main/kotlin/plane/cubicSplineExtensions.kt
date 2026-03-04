package plane

import io.github.danielTucano.matplotlib.Axes
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.pyplot.grid
import io.github.danielTucano.matplotlib.pyplot.subplots
import io.github.danielTucano.matplotlib.show
import io.github.danielTucano.python.pythonExecution
import plane.elements.xPoints
import plane.elements.yPoints
import plane.functions.CubicSpline
import utils.linspace

/** Creates a standalone matplotlib plot of this [CubicSpline] and displays it. */
fun CubicSpline.plot() {
    pythonExecution {
        this.addPlotCommands()
        grid()
        show()
    }
}

/**
 * Adds plot commands for this [CubicSpline] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing matplotlib axes, or null to create a new subplot.
 * @return A pair of (Figure, Axes) for further composition.
 */
fun CubicSpline.addPlotCommands(figure: Figure? = null, axes: Axes? = null, nPoints: Int = 100): Pair<Figure, Axes> {
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
    this(linspace(this.points.xPoints.first(), this.points.xPoints.last(), nPoints))
    ax.plot(this.points.xPoints, this.points.yPoints, "o")
    return fig to ax
}