package com.crystal.ploungequoter

import java.io.*
import java.awt.Graphics
import com.crystal.ploungequoter.*

class Text : RenderObject {
    var content: String?
    var fontName: String?

    constructor(fontName: String) {
        this.absPosition = Vector2()
        this.content = null
        this.fontName = fontName
    }
    
    constructor(fontName: String, content: String) {
        this.absPosition = Vector2()
        this.content = content
        this.fontName = fontName
    }
    
    /**
     * render()
     *
     * @param text Text to put on the image
     *
     */
    override fun render(): Unit {
        return
    }
}
