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
        val img: BufferedImage = BufferedImage(
                this.width,
                this.height,
                BufferedImage.TYPE_INT_ARGB
        )

        // Retrieve the graphics object for the transparent image.
        val g: Graphics2D = img.graphics as Graphics2D

        // Turn on antialising for text.
        val hints: RenderingHints = RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        )
        g.setRenderingHints(hints)

        // Render all the objects in the stack.
        for (gObj in this.graphicsObjs) {
            // Pass the image to each of the render objects in the stack
            gObj.render(g)
        }
        return img
    }

    override fun getRaster(): WritableRaster {
        return this.getImage().getRaster()
    }

    /**
     * Add a GraphicsObject to this layer for drawing.
     * @param[obj] Graphics object to add.
     * @post The layer will now be drawn with this GraphicsObject.
     */
    fun addGraphicsObj(obj: GraphicsObject) {
        this.graphicsObjs.add(obj)
    }
}
