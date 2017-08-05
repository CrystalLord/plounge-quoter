package org.crystal.ploungequoter

import java.awt.Graphics2D
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics

/**
 * Enum for text align
 */
enum class Alignment {
    LEFT,
    RIGHT,
    CENTER
}

class Text : GraphicsObject {


    companion object {
        /** The default font */
        val DEFAULT_FONT = Font("SansSerif", Font.PLAIN, 12)
        /** The default anchor setting */
        val DEFAULT_ANCHOR = Anchor.TOP_LEFT
        /** The default line space factor. */
        val DEFAULT_LINE_SPACE = 1
    }

    /**
     * The content in this text object to render.
     * Each line of the content is stored as an element in this list.
     */
    var contentList: MutableList<String>
    /** The foreground colour of the text. Default is black. */
    var color: Color
    /** The font used to render the text. Default is Plain SansSerif, 12pt. */
    var font: Font?
    /** The amount of vertical spacing between lines, in points */
    var lineSpacing: Int
    /** Alignment for the text, i.e. the justification. */
    var alignment: Alignment
    /**
     * The position of the left pixel bound for wrapping. If set to null,
     * will not wrap.
     */
    var leftPixelBound: Int? = null
    /**
     * The position of the right pixel bound for wrapping. If set to null,
     * will not wrap.
     */
    var rightPixelBound: Int? = null

    /** The default constructor */
    constructor() : this(Vector2.ZERO, "", Text.DEFAULT_FONT)

    constructor(position: Vector2) : this(position, "", Text.DEFAULT_FONT)

    constructor(position: Vector2, content: String, font: Font?) {
        this.globalPosition = position
        this.contentList = content.split("\n") as MutableList<String>
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
        this.contentList = content.split("\n") as MutableList<String>
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
        println(lineCount)
        return metrics.getHeight() * lineCount
    }


    /**
     * Render the text object.
     * @param g Graphics2D object to pass in to render on.
     */
    override fun render(g: Graphics2D) {

        // Force content wrapping if needed.
        if (this.leftPixelBound != null && this.rightPixelBound != null) {
            this.wrapToBound(
                    this.leftPixelBound!!,
                    this.rightPixelBound!!,
                    g
            )
        }

        // Store the graphics properties
        val stoColor: Color = g.getColor()
        val stoFont: Font = g.getFont()

        // Set graphics properties
        g.setColor(this.color)
        // Retrieve the font we want to use
        // If the font doesn't exist, use the default.
        g.setFont(this.font ?: Text.DEFAULT_FONT)

        // Retrieve the position we want for this object.
        val anchorPos: Vector2 = this.getUnanchoredPosition(g)
        val metrics: FontMetrics = g.getFontMetrics(this.font)

        for (i: Int in this.contentList.indices) {
            // Retrieve the line position using alignments.
            // Convert pts to pixels
            // TODO: Fix this. It's not correct.
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
     * Retrieve the length of one line of text in pixels.
     */
    private fun getLineWidth(g: Graphics2D, line: String): Int {
        val metrics: FontMetrics = g.getFontMetrics(this.font)
        return metrics.stringWidth(line)
    }


    /**
     * Retrieve the number of pixels that this text object must be shifted
     * so that it can be aligned properly with either left, right or centre
     * justification.
     *
     * @param[g] Graphics2D object to get the font Metrics for.
     * @param[line] Line of text represents one, unbroken (with carriage
     * returns) line of text.
     * @return Returns the number of pixels this line of text should be shifted
     * such that the line is aligned correctly.
     */
    private fun getAlignmentShift(g: Graphics2D, line: String): Int {
        val lineWidth: Int = this.getLineWidth(g, line)
        val shift: Int
        when (this.alignment) {
            Alignment.RIGHT -> shift = -lineWidth + this.getWidth(g)
            Alignment.CENTER -> shift = -lineWidth/2 + this.getWidth(g)/2
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
    private fun getUnanchoredPosition(g: Graphics2D): Vector2 {
        val shift: Vector2

        when (this.anchor) {
            Anchor.TOP_LEFT -> shift = Vector2(0f,this.getHeight(g).toFloat())
            Anchor.TOP_RIGHT -> {
                shift = Vector2(-this.getWidth(g).toFloat(), 0f)
            }
            Anchor.BOT_LEFT -> {
                shift = Vector2.ZERO
            }
            Anchor.BOT_RIGHT -> {
                shift = Vector2(
                        -this.getWidth(g).toFloat(),
                        0f
                )
            }
            Anchor.TOP_CENTER -> {
                shift = Vector2(-this.getWidth(g).toFloat()/2f, 0f)
            }
            Anchor.BOT_CENTER -> {
                shift = Vector2(
                        -this.getWidth(g).toFloat()/2f,
                        -this.getHeight(g).toFloat()
                )
            }
            Anchor.CENTER_CENTER -> {
                shift = Vector2(
                        -this.getWidth(g).toFloat()/2f,
                        this.getHeight(g).toFloat()/2f
                )
            }
        }
        return this.globalPosition + shift
    }

    /**
     * Force a wrapping on this text object to wrap within given pixel bounds.
     * @param[leftBound] The left bound's pixel coordinate.
     * @param[rightBound] The right bound's pixel coordinate.
     * @param[g] The graphics object this TextObject is on.
     * @post The Text object is wrapped within bounds.
     */
    private fun wrapToBound(leftBound: Int, rightBound: Int, g: Graphics2D) {
        val newContentList: MutableList<String> = mutableListOf()

        for (line: String in this.contentList) {
            newContentList.addAll(
                    this.cascadeWrap(leftBound, rightBound, line, g)
            )
        }

        this.contentList = newContentList
    }

    /**
     * Cascade wrap a single line using left and right bounds.
     *
     * @param[leftBound] The left bound's pixel coordinate.
     * @param[rightBound] The right bound's pixel coordinate.
     * @param[line] Line to wrap.
     * @param[g] The graphics object this TextObject is on.
     * @return Returns a MutableList of lines that were formed from this
     * cascade wrap. Each line is stored as a single element.
     */
    private fun cascadeWrap(
            leftBound: Int,
            rightBound: Int,
            line: String,
            g: Graphics2D
    ): MutableList<String> {

        // LL stands for "Line List"
        val ll: MutableList<String> = mutableListOf<String>(line)
        // Index of the current line we're on.
        var i: Int = 0
        while (i < ll.size) {
            var rightEdge: Int = this.getRightEdgeOfLine(g, ll[i])
            var leftEdge: Int = this.getLeftEdgeOfLine(g, ll[i])
            var tooLong: Boolean = (
                    rightEdge > rightBound
                            || leftEdge < leftBound
                    )
            while (tooLong && Utils.hasBlank(ll[i])) {
                val split: MutableList<String> =
                        this.splitLastSpace(ll[i])
                ll[i] = split[0]

                // Check if there's a line below this one.
                if (ll.size > i+1) {
                    ll[i+1] = split[1] + " " + ll[i+1]
                } else {
                    ll.add(split[1])
                }

                // Recaculate the edges and length to see if this line is
                // still too long.
                rightEdge = this.getRightEdgeOfLine(g, ll[i])
                leftEdge = this.getLeftEdgeOfLine(g, ll[i])
                tooLong = (
                        rightEdge > rightBound
                        || leftEdge < leftBound
                )
            }
            i++
        }
        return ll
    }

    /**
     * Retrieve the pixel coordinate of the right edge of a given line.
     */
    private fun getRightEdgeOfLine(g: Graphics2D, line: String): Int {
        val lineWidth: Int = this.getLineWidth(g, line)
        return this.getUnanchoredPosition(g).x.toInt() + lineWidth +
                this.getAlignmentShift(g, line)
    }

    /**
     * Retrieve the pixel coordinate of the left edge of a given line.
     */
    private fun getLeftEdgeOfLine(g: Graphics2D, line: String): Int {
        return this.getUnanchoredPosition(g).x.toInt() +
                this.getAlignmentShift(g, line)
    }

    /**
     * Replace the last space in the string with a newline.
     * @param[s] String to insert into.
     * @return New string with a newline if there's a space.
     */
    private fun splitLastSpace(s: String): MutableList<String> {
        for (i in s.length-1 downTo 0) {
            if (s[i] == ' ') {
                val front = s.substring(0,i)
                val rear = s.substring(i+1,s.length)
                return mutableListOf<String>(front, rear)
            }
        }
        return mutableListOf<String>(s)
    }
}
