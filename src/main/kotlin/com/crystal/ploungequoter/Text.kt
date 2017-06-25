package com.crystal.ploungequoter

import java.io.*
import java.awt.Graphics2D
import java.awt.Image
import com.crystal.ploungequoter.*

class Text : RenderObject {
    var content: String = ""
    var fontName: String?

    constructor(fontName: String) {
        this.globalPosition = Vector2.ZERO
        this.fontName = fontName
    }
    
    constructor(fontName: String, content: String) {
        this.globalPosition = Vector2.ZERO
        this.content = content
        this.fontName = fontName
    }
    
    /**
     * render()
     *
     * @param text Text to put on the image
     *
     */
    override fun render(img: Image): Unit {
        var graphics = img.getGraphics()
        graphics.drawString("HELLO WORLD", 100, 100)
    }
}
