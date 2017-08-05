package org.crystal.ploungequoter

import java.awt.Graphics2D

/**
 * An abstract graphics object.
 *
 * An abstract class from which all vector objects that are rendered in an image
 * are derived from. Internally, they do not use a raster to draw pixels.
 * Instead, they use a Java AWT [Graphics2D][1] object to draw.
 *
 * [1]: https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
 */
abstract class GraphicsObject {

    // Absolute position
    var globalPosition: Vector2 = Vector2.ZERO
    // Graphics objects allow parents
    var parentObj: GraphicsObject? = null
    // Anchor for the rendering
    var anchor: Anchor = Anchor.TOP_LEFT

    /**
     * Get relative position compared to parent.
     *
     * @return Vector2 representing relative position
     */
    open fun getRelPosition(): Vector2 {
        return (this.globalPosition
         - (parentObj?.globalPosition ?: Vector2.ZERO))
    }

    /**
     * Get relative position compared to parent.
     *
     * @param relPos Relative Position to set
     */
    open fun setRelPosition(relPos : Vector2) {
        globalPosition = (relPos
         + (parentObj?.globalPosition ?: Vector2.ZERO))
    }

    /**
     * Render the object onto some graphics object.
     * @param g The graphics object to render on to.
     */
    abstract fun render(g: Graphics2D)

    /**
     * Get the width of this GraphicsObject in pixels.
     *
     * @param[g] Graphics2D object of the layer to evaluate the width as.
     * @return Returns the width of the render object as an integer,
     * in pixels.
     */
    abstract fun getWidth(g: Graphics2D): Int

    /**
     * Get the height of this GraphicsObject in pixels.
     *
     * @param[g] Graphics2D object of the layer to evaluate the height as.
     * @return Returns the height of the render object as an integer,
     * in pixels.
     */
    abstract fun getHeight(g: Graphics2D): Int
}
