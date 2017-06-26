package com.crystal.ploungequoter

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Color
import java.awt.Image

class Text : RenderObject {
    var content: String
    var fontName: String?
    
    constructor() : this(Vector2.ZERO, "", "")
    
    constructor(position: Vector2) : this(position, "", "")
    
    constructor(position: Vector2, content: String, fontName: String) {
        this.globalPosition = position
        this.content = content
        this.fontName = fontName
    }

    /**
     * render()
     * @param img Image object to use when 
     */
    override fun render(g: Graphics2D): Unit {
        var color: Color = Color(0,0,0)
        g.setPaint(color)
        g.drawString(
            this.content,
            Math.round(this.globalPosition.x),
            Math.round(this.globalPosition.y)
        )
    }
}
