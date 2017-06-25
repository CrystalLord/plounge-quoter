package com.crystal.ploungequoter

import com.crystal.ploungequoter.*
import java.awt.image.*
import java.io.*
import javax.imageio.ImageIO
import java.nio.file.*

class Renderer(val backgroundPath: Path?) {
    /**
     * List of render objects
     */
    private var renderObjs: MutableList<RenderObject>
    
    init {
        renderObjs = mutableListOf<RenderObject>()
    }

    /**
     * Render all render objects into a file.
     */
    open fun render(): Unit {
        if (backgroundPath == null) {
            throw IllegalStateException("No Background File Set")
        }
        
        // Read the background image
        println(backgroundPath)
        println(Files.exists(backgroundPath, LinkOption.NOFOLLOW_LINKS))
        var img: BufferedImage = ImageIO.read(backgroundPath.toFile())
        
        for (obj in renderObjs) {
            obj.render(img)
        }
    }
    
    /**
     * 
     * @param obj Object to add to the render objects
     */
    open fun addRenderObj(obj: RenderObject): Unit {
        renderObjs.add(obj)
    }

}
