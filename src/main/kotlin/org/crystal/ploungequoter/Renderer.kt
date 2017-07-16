package org.crystal.ploungequoter

import java.io.File
import javax.imageio.ImageIO
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import java.awt.RenderingHints
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 *
 */
class Renderer(val backgroundPath: Path?) {

    /**
     * List of layers in the program.
     */
    private var layers: MutableList<RenderLayer>

    init {
        // Assign the render object list to an empty list.
        this.layers = mutableListOf<RenderLayer>()
    }

    /**
     * Render all render objects into a file.
     */
    fun render(outputType: String, outputFile: File) {
        // Check to make sure we actually have a background first.
        if (this.backgroundPath == null
                || this.backgroundPath == Paths.get("")) {
            throw IllegalStateException("No background file set.")
        } else if (!Files.exists(backgroundPath)) {
            throw IllegalStateException("Background file not found.")
        }

        try {
            // Read the background image file in.
            // Note, the background is the base layer for the rendering system.
            // Sensibly, this would be changed for a more general
            // implementation.
            var img: BufferedImage = ImageIO.read(this.backgroundPath.toFile())
            var overarchingGraphics: Graphics2D = img.createGraphics()

            // Render all the objects in the queue.
            for (layer in this.layers) {
                // Pass each of the layers into the overarching graphics.

                overarchingGraphics.drawImage(
                        layer.getImage(), // The image to draw
                        AffineTransformOp(
                                AffineTransform(),
                                AffineTransformOp.TYPE_BICUBIC
                        ), // Identity transformation
                        0, // x pos
                        0 // y pos
                )
            }

            // TODO:
            // Consider closing the graphics object here?

            // Actually write the file
            ImageIO.write(img,outputType,outputFile)
            //ImageIO.write(textLayer,outputType,File("/home/crystal/test.png"))

        } catch (e: IllegalStateException) {
            throw IllegalStateException("Background file could not be read.")
        }
    }

    /**
     * Retrieve a layer from the renderer.
     * @param index Index of the layer to retrieve.
     */
    fun getLayer(index: Int): RenderLayer = this.layers[index]

    /**
     * Place a layer to the back of the queue.
     * @param layer Layer to stick ontop of the Renderer.
     */
    fun addLayer(layer: RenderLayer) {
        this.layers.add(layer)
    }

}
