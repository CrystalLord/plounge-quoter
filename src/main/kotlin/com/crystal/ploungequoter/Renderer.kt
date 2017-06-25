package com.crystal.ploungequoter

import com.crystal.ploungequoter.*
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
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
    open fun render(outputType: String, outputFile: File): Unit {
        // Check to make sure we actually have a background first.
        if (this.backgroundPath == null
                || this.backgroundPath == Paths.get("")) {
            throw IllegalStateException("No background file set.")
        }
        else if (!Files.exists(backgroundPath)) {
            throw IllegalStateException("Background file not found.")
        }
        
        try {
            var img: BufferedImage = ImageIO.read(this.backgroundPath.toFile())
            for (rObj in this.renderObjs) {
                // Pass the image to each of the render objects in the stack
                rObj.render(img)
            }

            ImageIO.write(img,outputType,outputFile)
        }
        catch (e: IllegalStateException) {
            throw IllegalStateException("Background file could not be read.")
        }
    }
    
    /**
     * @param obj Object to add to the render objects
     */
    open fun addRenderObj(obj: RenderObject): Unit {
        this.renderObjs.add(obj)
    }
}
