package space

import io.github.danielTucano.matplotlib.Axes3D
import io.github.danielTucano.matplotlib.Colormap
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.show
import io.github.danielTucano.python.pythonExecution
import org.ejml.simple.SimpleMatrix

/** Creates a standalone matplotlib plot of this [ParametricSurface3D] and displays it. */
fun ParametricSurface3D.plot(T: SimpleMatrix, S: SimpleMatrix, colormap: Colormap? = null) {
    pythonExecution {
        this.addPlotCommands(T, S, colormap = colormap)
        show()
    }
}

/**
 * Adds plot commands for this [ParametricSurface3D] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param T Parameter mesh matrix for the first surface parameter.
 * @param S Parameter mesh matrix for the second surface parameter.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing Axes3D, or null to create a new 3D subplot.
 * @param colormap The colormap to use for surface coloring, or null for the default.
 * @return A pair of (Figure, Axes3D) for further composition.
 */
fun ParametricSurface3D.addPlotCommands(
    T: SimpleMatrix,
    S: SimpleMatrix,
    figure: Figure? = null,
    axes: Axes3D? = null,
    colormap: Colormap? = null
): Pair<Figure, Axes3D> {
    val fig = when (figure) {
        null -> figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }

    val (X, Y, Z) = this(T, S)

    ax.plot_surface(X, Y, Z, colormap)

    return fig to ax
}
