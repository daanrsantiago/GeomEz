package space

import io.github.danielTucano.matplotlib.Axes3D
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.KwargKey
import io.github.danielTucano.matplotlib.KwargValue
import space.elements.Vector3D


/**
 * Adds plot commands for this list of [Vector3D] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * Each vector is drawn as a quiver arrow from its tail position.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing Axes3D, or null to create a new 3D subplot.
 * @param kwargs Optional matplotlib kwargs passed to the quiver call.
 * @param scale Scale factor applied to each vector's components.
 * @return A pair of (Figure, Axes3D) for further composition.
 */
fun List<Vector3D>.addPlotCommands(
    figure: Figure? = null,
    axes: Axes3D? = null,
    kwargs: Map<KwargKey, KwargValue>? = null,
    scale: Double = 1.0
): Pair<Figure, Axes3D> {
    val fig = when (figure) {
        null -> io.github.danielTucano.matplotlib.pyplot.figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }

    ax.quiver(
        this.map { it.position.x },
        this.map { it.position.y },
        this.map { it.position.z },
        this.map { it.x * scale},
        this.map { it.y * scale},
        this.map { it.z * scale},
        kwargs = kwargs
    )
    return fig to ax
}