package ploungequoter

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Color
import java.awt.Image
import java.awt.Font

class Text : RenderObject {
    var content: String
    var fontName: String?
    var italics: Boolean
    var bold: Boolean
    var color: Color

    constructor() : this(Vector2.ZERO, "", null)

    constructor(position: Vector2) : this(position, "", null)

    constructor(position: Vector2, content: String, fontName: String?) {
        this.globalPosition = position
        this.content = content
        this.fontName = fontName
        this.italics = false
        this.bold = false
        this.color = Color(0,0,0)
    }

    /**
     * Render the text object.
     * @param g Graphics2D object to pass in to render on.
     */
    override fun render(g: Graphics2D) {
        g.setPaint(this.color)
        // Retrieve the Font object.
        val font: Font = Font.getFont(this.fontName)
        g.drawString(
            this.content,
            Math.round(this.globalPosition.x),
            Math.round(this.globalPosition.y)
        )
    }

    override fun getWidth(): Int {
        return 0
    }

    override fun getHeight(): Int {
        return 0
    }
}
