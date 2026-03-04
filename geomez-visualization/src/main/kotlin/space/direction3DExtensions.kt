package plane

import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.*
import io.github.danielTucano.python.pythonExecution
import space.elements.Direction3D

/** Creates a standalone matplotlib plot of this [Direction3D] and displays it. */
fun Direction3D.plot() {
    pythonExecution {
        val (_, ax) = this.addPlotCommands()
        xlabel("X")
        ylabel("Y")
        ax.set_zlabel("Z")
        xlim(-1.0, 1.0)
        ylim(-1.0, 1.0)
        ax.set_zlim3d(-1.0, 1.0)
        show()
    }
}

/**
 * Adds plot commands for this [Direction3D] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing Axes3D, or null to create a new 3D subplot.
 * @param kwargs Optional matplotlib kwargs passed to the quiver call.
 * @return A pair of (Figure, Axes3D) for further composition.
 */
fun Direction3D.addPlotCommands(figure: Figure? = null, axes: Axes3D? = null, kwargs:  Map<KwargKey, KwargValue>? = null): Pair<Figure, Axes3D> {
    val fig = when(figure) {
        null -> figure()
        else -> figure
    }
    val ax = when(axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }
    ax.quiver(
        listOf(0.0),
        listOf(0.0),
        listOf(0.0),
        listOf(this.x),
        listOf(this.y),
        listOf(this.z),
        kwargs = kwargs
    )
    return fig to ax
}