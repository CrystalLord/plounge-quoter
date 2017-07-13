package org.crystal.ploungequoter

import java.awt.Graphics2D
import java.awt.Color
import java.awt.Image
import java.awt.Font
import java.awt.FontMetrics

class Text : RenderObject {
    companion object {
        private val DEFAULT_TYPEFACE = "SansSerif"
        private val DEFAULT_FONT_SIZE = 12
    }

    /** The content in this text object to render. */
    var contentList: List<String>
    /** The foreground colour of the text */
    var color: Color
    /** The font used to render the text. */
    var font: Font?

    /** The default constructor */
    constructor() : this(Vector2.ZERO, "", null)

    constructor(position: Vector2) : this(position, "", null)

    constructor(position: Vector2, content: String, font: Font?) {
        this.globalPosition = position
        this.contentList = content.split("\n")
        this.color = Color(0,0,0)
        this.font = font
        this.anchor = Anchor.TOP_LEFT
    }

    /**
     * Set the content of this text object.
     *
     * @param content String representing the text to actually display.
     */
    fun setContent(content: String) {
        this.contentList = content.split("\n")
    }


    /**
     * Retrieve the width of the longest line in this text object.
     */
    override fun getWidth(g: Graphics2D): Int {
        val metrics: FontMetrics = g.getFontMetrics(this.font)
        var longestLine: String = ""

        // Loop through every line, and get the longest.
        for (line: String in this.contentList) {
            if (line.length > longestLine.length) {
                longestLine = line
            }
        }

        // Return the width of the longest line.
        return metrics.stringWidth(longestLine)
    }


    /**
     * Retrieve the height of the entire Text object
     * @param g Graphics object to use for this calculation.
     */
    override fun getHeight(g: Graphics2D): Int {
        val metrics: FontMetrics = g.getFontMetrics(this.font)
        val lineCount: Int = this.contentList.size
        return metrics.getHeight() * lineCount
    }


    /**
     * Retrieve the true position to render at, instead of the skewed anchored
     * position.
     *
     * @param g Graphics object to evaluate the size of this Text.
     */
    private fun getAnchoredPosition(g: Graphics2D): Vector2 {
        var shift: Vector2 = Vector2.ZERO

        when (this.anchor) {
            Anchor.TOP_LEFT -> shift = Vector2.ZERO
            Anchor.TOP_RIGHT -> {
                shift = Vector2(-this.getWidth(g) as Float, 0f)
            }
            Anchor.BOT_LEFT -> {
                shift = Vector2(0f, -this.getHeight(g) as Float)
            }
            Anchor.BOT_RIGHT -> {
                shift = Vector2(
                        -this.getWidth(g) as Float,
                        -this.getHeight(g) as Float
                )
            }
            Anchor.TOP_CENTRE -> {
                shift = Vector2(-this.getWidth(g)/2f, 0f)
            }
            Anchor.BOT_CENTRE -> {
                shift = Vector2(
                        -this.getWidth(g)/2f,
                        -this.getHeight(g) as Float
                )
            }
            Anchor.CENTRE_CENTRE -> {
                shift = Vector2(
                        -this.getWidth(g)/2f,
                        -this.getHeight(g)/2f
                )
            }
            else -> shift = Vector2.ZERO
        }

        return this.globalPosition + shift
    }


    /**
     * Retrieve the length of one line of text.
     */
    private fun getLineWidth(g: Graphics2D, line: String): Int {
        val metrics: FontMetrics = g.getFontMetrics(this.font)
        return metrics.stringWidth(line)
    }


    /**
     * Render the text object.
     *
     * @param g Graphics2D object to pass in to render on.
     */
    override fun render(g: Graphics2D) {
        // Store the graphics properties
        val stoColor: Color = g.getColor()
        val stoFont: Font = g.getFont()

        // Set graphics properties
        g.setColor(this.color)
        // Retrieve the font we want to use
        // If the font doesn't exist, use the default.
        g.setFont(this.font
                ?: Font(
                        Text.DEFAULT_TYPEFACE,
                        Font.PLAIN,
                        Text.DEFAULT_FONT_SIZE
                )
        )

        // Actually draw the goddamn text.
        val printPos: Vector2 = this.getAnchoredPosition(g)
        // TODO Must fix
        g.drawString(
            this.contentList[0],
            Math.round(printPos.x),
            Math.round(printPos.y)
        )

        // Reset the graphics properties
        g.setColor(stoColor)
        g.setFont(stoFont)
    }
}
