package com.crystal.ploungequoter

import java.io.*
import java.awt.Graphics
import com.crystal.ploungequoter.Vector2

class Text {
    
    var relPosition: Vector2
    var content: String?
    var fontName: String?

    constructor(fontName: String) {
        this.relPosition = Vector2()
        this.content = null
        this.fontName = fontName
    }
    
    constructor(fontName: String, content: String) {
        this.relPosition = Vector2()
        this.content = content
        this.fontName = fontName
    }
    
    /**
     * render()
     *
     * @param text Text to put on the image
     *
     */
    fun render() {
        return
    }

    fun getAbsPosition() {
        return 
    }
}
