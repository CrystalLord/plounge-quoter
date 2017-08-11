package org.crystal.ploungequoter

import java.awt.image.BufferedImage
import java.awt.RenderingHints
import java.awt.Graphics2D
import java.awt.image.WritableRaster

/**
 * Layer which uses the underlying Java AWT Graphics object to render itself.
 *
 * @param[width] Width of the graphics layer in pixels.
 * @param[height] Height of the graphics layer in pixels.
 */
class GraphicsLayer(
        private var width: Int,
        private var height: Int
) : RenderLayer {

    private var img: BufferedImage = BufferedImage(
            this.width,
            this.height,
            BufferedImage.TYPE_INT_ARGB
    )
    private var g: Graphics2D

    init {
        // Get the graphics object at the start.
        this.g = img.createGraphics()
        // Turn on antialising for text.
        val hints: RenderingHints = RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        )
        g.setRenderingHints(hints)
    }

    // ------------------------------------------------------------------------
    // Properties
    // ------------------------------------------------------------------------

    // The objects that are placed on this layer to be rendered
    private var graphicsObjs: MutableList<GraphicsObject> = (
            mutableListOf<GraphicsObject>()
    )

    // ------------------------------------------------------------------------
    // Methods
    // ------------------------------------------------------------------------

    override fun getImage(): BufferedImage {
        // Create a transparent image object to draw on.
        // Render all the objects in the stack.
        for (gObj in this.graphicsObjs) {
            // Pass the image to each of the render objects in the stack
            gObj.render()
        }
        return img
    }

    override fun getRaster(): WritableRaster {
        return this.getImage().getRaster()
    }

    /** Retrieve the internal Graphics2D object of this GraphicsLayer */
    fun getGraphics2D(): Graphics2D = this.g

    /**
     * Add a GraphicsObject to this layer for drawing.
     * @param[obj] Graphics object to add.
     * @post The layer will now be drawn with this GraphicsObject.
     */
    fun addGraphicsObj(obj: GraphicsObject) {
        obj.graphics2D = this.g
        this.graphicsObjs.add(obj)
    }
}
