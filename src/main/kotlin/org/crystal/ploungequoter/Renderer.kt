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
 * The Renderer stores all visual objects, then when finally processing, it
 * can generate an output file.
 *
 * To use this class, add layers to the renderer, then call render.
 *
 * @param[backgroundPath] Path that details where the background image is.
 */
class Renderer(val backgroundPath: Path?) {

    /**
     * List of layers in the program.
     */
    private var layers: MutableList<RenderLayer>
    /**
     * The stored overarching image.
     */
    private var img: BufferedImage

    init {
        // Check to make sure we actually have a background first.
        if (this.backgroundPath == null
                || this.backgroundPath == Paths.get("")) {
            throw IllegalStateException("No background file set.")
        } else if (!Files.exists(backgroundPath)) {
            throw IllegalStateException("Background file not found.")
        }

        // Assign the render object list to an empty list.
        this.layers = mutableListOf<RenderLayer>()
        try {
            this.img = ImageIO.read(this.backgroundPath?.toFile())

            if (this.img != null) {
                println("Renderer Loaded Background")
            }
        } catch (e: IllegalStateException) {
            throw IllegalStateException("Background file could not be read.")
        }
    }

    /**
     * Render all render objects into a file.
     * @param[outputType] The file type for the output, as a string.
     * @param[outputFile] The File to actually output to.
     */
    fun render(outputType: String, outputFile: File) {
        // Read the background image file in.
        // Note, the background is the base layer for the rendering system.
        // Sensibly, this would be changed for a more general
        // implementation.
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

    /**
     * Place a transparent RasterLayer on the render queue
     *
     * @return The newly added RasterLayer
     */
    fun addRasterLayer(): RasterLayer {
        var newLayer: RasterLayer = RasterLayer(
                this.img.getWidth(),
                this.img.getHeight()
        )
        this.layers.add(newLayer)
        return newLayer
    }

    /**
     * Place a transparent GraphicsLayer on the render queue
     * @return The newly added layer.
     */
    fun addGraphicsLayer(): GraphicsLayer {
        var newLayer: GraphicsLayer = GraphicsLayer(
                this.img.getWidth(),
                this.img.getHeight()
        )
        this.layers.add(newLayer)
        return newLayer
    }
}
