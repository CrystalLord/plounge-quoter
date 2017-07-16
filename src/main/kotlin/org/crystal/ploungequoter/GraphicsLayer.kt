package org.crystal.ploungequoter

import java.awt.image.BufferedImage
import java.awt.RenderingHints
import java.awt.Graphics2D
import java.awt.image.WritableRaster

/**
 * Layer which uses the underlying Java AWT Graphics object to render itself.
 *
 * @param width Width of the layer, in pixels.
 * @param height Height of the
 */
class GraphicsLayer(
        width: Int,
        height: Int
) : RenderLayer(width, height) {

    /** The objects that are placed on this layer to be rendered */
    lateinit private var graphicsObjs: MutableList<GraphicsObject>

    override fun getImage(): BufferedImage {
        var img: BufferedImage = BufferedImage(
                this.width,
                this.height,
                BufferedImage.TYPE_INT_ARGB
        )

        // Retrieve the graphics object generated from the background image.
        var g: Graphics2D = img.getGraphics() as Graphics2D

        // Turn on antialising for text.
        var hints: RenderingHints = RenderingHints(
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

    fun addGraphicsObj(obj: GraphicsObject) {
        this.graphicsObjs.add(obj)
    }
}
