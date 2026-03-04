package space

import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.*
import io.github.danielTucano.python.pythonExecution
import space.elements.Vector3D
import kotlin.math.abs

/** Creates a standalone matplotlib plot of this [Vector3D] and displays it. */
fun Vector3D.plot() {
    pythonExecution {
        val (_, ax) = this.addPlotCommands()
        xlabel("X")
        ylabel("Y")
        ax.set_zlabel("Z")
        xlim((position.x - abs(x)) * 0.5, (position.x + abs(x)) * 1.5)
        ylim((position.y - abs(y)) * 0.5, (position.y + abs(y)) * 1.5)
        ax.set_zlim3d((position.z - abs(z)) * 0.5, (position.z + abs(z)) * 1.5)
        show()
    }
}

/**
 * Adds plot commands for this [Vector3D] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing Axes3D, or null to create a new 3D subplot.
 * @param kwargs Optional matplotlib kwargs passed to the quiver call.
 * @return A pair of (Figure, Axes3D) for further composition.
 */
fun Vector3D.addPlotCommands(
    figure: Figure? = null,
    axes: Axes3D? = null,
    kwargs: Map<KwargKey, KwargValue>? = null
): Pair<Figure, Axes3D> {
    val fig = when (figure) {
        null -> figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }
    ax.quiver(
        listOf(this.position.x),
        listOf(this.position.y),
        listOf(this.position.z),
        listOf(this.x),
        listOf(this.y),
        listOf(this.z),
        kwargs = kwargs
    )
    return fig to ax
}
