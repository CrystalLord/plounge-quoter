package com.crystal.ploungequoter

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Color
import java.awt.Image

class Text : RenderObject {
    var content: String
    var fontName: String?
    var italics: Boolean
    var bold: Boolean

    constructor() : this(Vector2.ZERO, "", null)

    constructor(position: Vector2) : this(position, "", null)

    constructor(position: Vector2, content: String, fontName: String) {
        this.globalPosition = position
        this.content = content
        this.fontName = fontName
        this.italics = false
        this.bold = false
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
