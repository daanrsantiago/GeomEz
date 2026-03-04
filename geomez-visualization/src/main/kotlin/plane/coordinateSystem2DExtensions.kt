package plane

import io.github.danielTucano.matplotlib.Axes
import io.github.danielTucano.matplotlib.AxesBase
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.pyplot.*
import io.github.danielTucano.matplotlib.show
import io.github.danielTucano.python.pythonExecution
import java.awt.Color
import kotlin.math.min

/** Creates a standalone matplotlib plot of this [CoordinateSystem2D] and displays it. */
fun CoordinateSystem2D.plot() {
    pythonExecution {
        val (_, ax) = this.addPlotCommands()
        ax.set_aspect(AxesBase.AspectOptions.equal, AxesBase.AjustableOptions.box)
        xlim(min(-(this.origin.x + 2),-2.0), maxOf((this.origin.x + 2),2.0))
        ylim(min(-(this.origin.y + 2),-2.0), maxOf((this.origin.y + 2),2.0))
        grid()
        show()
    }
}

/**
 * Adds plot commands for this [CoordinateSystem2D] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing matplotlib axes, or null to create a new subplot.
 * @return A pair of (Figure, Axes) for further composition.
 */
fun CoordinateSystem2D.addPlotCommands(figure: Figure? = null, axes: Axes? = null): Pair<Figure,Axes> {
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
    val quiverX = ax.quiver(
        listOf(this.origin.x),
        listOf(this.origin.y),
        listOf(this.xDirection.x),
        listOf(this.xDirection.y),
        scale = 1.0,
        scale_units = Axes.QuiverScaleUnitsOptions.xy,
        angles = Axes.QuiverAnglesOptions.xy,
        color = listOf(Color.RED)
    )
    val quiverY = ax.quiver(
        listOf(this.origin.x),
        listOf(this.origin.y),
        listOf(this.yDirection.x),
        listOf(this.yDirection.y),
        scale = 1.0,
        scale_units = Axes.QuiverScaleUnitsOptions.xy,
        angles = Axes.QuiverAnglesOptions.xy,
        color = listOf(Color.GREEN)
    )
    quiverX.set_zorder(3.0)
    quiverY.set_zorder(3.0)
    return fig to ax
}