package com.crystal.ploungequoter

import com.crystal.ploungequoter.*
import java.awt.image.*
import java.io.*
import javax.imageio.ImageIO
import java.nio.file.*

class Renderer(val backgroundPath: Path?) {
    var renderObjs: MutableList<RenderObject> = mutableListOf<RenderObject>()

    open fun render(): Unit {
        if (backgroundPath == null) {
            throw IllegalStateException("No Background File Set")
        }
        
        // Read the background image
        println(Files.exists(backgroundPath, LinkOption.NOFOLLOW_LINKS))
        var img: BufferedImage = ImageIO.read(backgroundPath.toFile())
        
        for (obj in renderObjs) {
            obj.render(img)
        }
    }

}
