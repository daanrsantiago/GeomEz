package plane


import extensions.xValues
import extensions.yValues
import extensions.zValues
import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.python.pythonExecution
import space.elements.Point3D

/** Creates a standalone matplotlib plot of this list of [Point3D] and displays it. */
fun List<Point3D>.plot(kwargs: Map<Line2D.Line2DArgs, KwargValue>? = null) {
    pythonExecution {
        this.addPlotCommands(kwargs = kwargs)
        show()
    }
}

/**
 * Adds plot commands for this list of [Point3D] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing Axes3D, or null to create a new 3D subplot.
 * @param kwargs Optional matplotlib line kwargs.
 * @return A pair of (Figure, Axes3D) for further composition.
 */
fun List<Point3D>.addPlotCommands(
    figure: Figure? = null,
    axes: Axes3D? = null,
    kwargs: Map<Line2D.Line2DArgs, KwargValue>? = null
): Pair<Figure, Axes3D> {
    val fig = when (figure) {
        null -> figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }
    ax.plot(xValues, yValues, zValues, kwargs = kwargs)
    return fig to ax
}

/**
 * Adds scatter plot commands for this list of [Point3D] to the given [figure] and [axes].
 * If [figure] or [axes] are null, new instances are created automatically.
 * @param figure Existing matplotlib figure, or null to create a new one.
 * @param axes Existing Axes3D, or null to create a new 3D subplot.
 * @param kwargs Optional matplotlib scatter kwargs.
 * @return A pair of (Figure, Axes3D) for further composition.
 */
fun List<Point3D>.addScatterPlotCommands(
    figure: Figure? = null,
    axes: Axes3D? = null,
    kwargs: Map<Axes3D.Scatter3DKwargsKeys, KwargValue>? = null
): Pair<Figure, Axes3D> {

    val fig = when (figure) {
        null -> figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }

    ax.scatter(this.xValues, this.yValues, this.zValues, kwargs = kwargs)

    return fig to ax
}

