package org.crystal.ploungequoter

import java.awt.Graphics2D
import java.awt.Color
import java.awt.Image
import java.awt.Font
import java.awt.FontMetrics

/**
 * Enum for text align
 */
enum class Alignment {
    LEFT,
    RIGHT,
    CENTRE
}

class Text : GraphicsObject {


    companion object {
        /** The default Settings */
        val DEFAULT_FONT = Font("SansSerif", Font.PLAIN, 12)
        val DEFAULT_ANCHOR = Anchor.TOP_LEFT
        val DEFAULT_LINE_SPACE = 1
    }

    /**
     * The content in this text object to render.
     * Each line of the content is stored as an element in this list.
     */
    var contentList: List<String>
    /** The foreground colour of the text. Default is black. */
    var color: Color
    /** The font used to render the text. Default is Plain SansSerif, 12pt. */
    var font: Font?
    /** The amount of vertical spacing between lines, in points */
    var lineSpacing: Int
    /** Alignment for the text, i.e. the justification. */
    var alignment: Alignment

    /** The default constructor */
    constructor() : this(Vector2.ZERO, "", Text.DEFAULT_FONT)

    constructor(position: Vector2) : this(position, "", Text.DEFAULT_FONT)

    constructor(position: Vector2, content: String, font: Font?) {
        this.globalPosition = position
        this.contentList = content.split("\n")
        this.color = Color(0,0,0)
        this.font = font
        this.anchor = Text.DEFAULT_ANCHOR
        this.lineSpacing = Text.DEFAULT_LINE_SPACE
        this.alignment = Alignment.LEFT
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
     * Render the text object.
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
        g.setFont(this.font ?: Text.DEFAULT_FONT)

        // Retrieve the position we want for this object.
        val anchorPos: Vector2 = this.getAnchoredPosition(g)
        val metrics: FontMetrics = g.getFontMetrics(this.font)

        for (i: Int in this.contentList.indices) {
            // Retrieve the line position using alignments.
            // Convert pts to pixels
            val pixelSpacing: Int = Utils.ptsToPixels(this.lineSpacing)
            // Create shifts for the text
            val vertShift: Int = i * (metrics.getHeight() + pixelSpacing)
            val horzShift: Int = getAlignmentShift(g, this.contentList[i])

            // Actually draw the string
            g.drawString(
                this.contentList[i],
                Math.round(anchorPos.x)+horzShift,
                Math.round(anchorPos.y)+vertShift
            )
        }

        // Reset the graphics properties
        g.setColor(stoColor)
        g.setFont(stoFont)
    }


    /**
     * Retrieve the length of one line of text.
     */
    private fun getLineWidth(g: Graphics2D, line: String): Int {
        val metrics: FontMetrics = g.getFontMetrics(this.font)
        return metrics.stringWidth(line)
    }


    /**
     * Retrieve the number of pixels that must be
     * @param g Graphics2D object to get the font Metrics for.
     * @param line Line of text represents one, unbroken (with carriage returns)
     * line of text.
     * @return Returns the number of pixels this line of text should be shifted
     * such that the line is aligned correctly.
     */
    private fun getAlignmentShift(g: Graphics2D, line: String): Int {
        val lineWidth: Int = this.getLineWidth(g, line)
        var shift: Int = 0
        when (this.alignment) {
            Alignment.RIGHT -> shift = -lineWidth + this.getWidth(g)
            Alignment.CENTRE -> shift = -lineWidth/2 + this.getWidth(g)
            else -> shift = 0
        }
        return shift
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
                shift = Vector2(-this.getWidth(g).toFloat(), 0f)
            }
            Anchor.BOT_LEFT -> {
                shift = Vector2(0f, -this.getHeight(g).toFloat())
            }
            Anchor.BOT_RIGHT -> {
                shift = Vector2(
                        -this.getWidth(g).toFloat(),
                        -this.getHeight(g).toFloat()
                )
            }
            Anchor.TOP_CENTRE -> {
                shift = Vector2(-this.getWidth(g).toFloat()/2f, 0f)
            }
            Anchor.BOT_CENTRE -> {
                shift = Vector2(
                        -this.getWidth(g).toFloat()/2f,
                        -this.getHeight(g).toFloat()
                )
            }
            Anchor.CENTRE_CENTRE -> {
                shift = Vector2(
                        -this.getWidth(g).toFloat()/2f,
                        -this.getHeight(g).toFloat()/2f
                )
            }
            else -> shift = Vector2.ZERO
        }
        return this.globalPosition + shift
    }
}
