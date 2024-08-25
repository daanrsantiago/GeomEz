package plane

import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.pyplot.grid
import io.github.danielTucano.matplotlib.pyplot.subplots
import io.github.danielTucano.python.pythonExecution
import plane.elements.xValues
import plane.elements.yValues
import utils.linspace

fun ParametricCurve2D.plot(tList: List<Double> = linspace(0.0, 1.0, 100)) {
    pythonExecution {
        val (_, ax) = this.addPlotCommands(tList = tList)
        ax.set_aspect(AxesBase.AspectOptions.equal, AxesBase.AjustableOptions.datalim)
        grid()
        show()
    }
}

fun ParametricCurve2D.addPlotCommands(
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

    val points = this(tList)
    ax.plot(points.xValues, points.yValues)
    return fig to ax
}