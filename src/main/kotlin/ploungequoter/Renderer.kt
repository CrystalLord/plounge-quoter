package ploungequoter

import java.io.File
import javax.imageio.ImageIO
import java.awt.Graphics2D
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
     * List of render objects.
     */
    private var renderObjs: MutableList<RenderObject>

    init {
        // Assign the render object list to an empty list.
        this.renderObjs = mutableListOf<RenderObject>()
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
            var img: BufferedImage = ImageIO.read(this.backgroundPath.toFile())
            // Retrieve the graphics object generated from the background image.
            var g: Graphics2D = img.getGraphics() as Graphics2D

            // Turn on antialising for the text.
            var hints: RenderingHints = RenderingHints(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
            g.setRenderingHints(hints)

            // Render all the objects in the queue.
            for (rObj in this.renderObjs) {
                // Pass the image to each of the render objects in the stack
                rObj.render(g)
            }

            // Actually write the file
            ImageIO.write(img,outputType,outputFile)
        } catch (e: IllegalStateException) {
            throw IllegalStateException("Background file could not be read.")
        }
    }

    /**
     * @param obj Object to add to the render objects
     */
    fun addRenderObj(obj: RenderObject): Unit {
        this.renderObjs.add(obj)
    }
}
