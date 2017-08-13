package org.crystal.ploungequoter

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D

enum class QuotePosType {
    /** Absolute (global) pixel positioning */
    ABS,
    /** Positioning relative to image size */
    IMAGE
}

/** Position types of the author text. */
enum class AuthorPos {
    /** Attached to the bottom right of the quote. */
    BOT_RIGHT_ATTACHED,
    /** Placed in the bottom right of the image. */
    BOT_RIGHT,
    /** Attached to the bottom left of the quote. */
    BOT_LEFT_ATTACHED,
    /** Placed in the bottom left of the image. */
    BOT_LEFT
}

/**
 * A data class to store qualities about a TextObject
 * It allows easy storage and retrieval of a data, while also providing a way
 * to generate a useful text object.
 */
class QuoteInfo {
    /** The Anchor setting of the text object. */
    var anchor: Anchor = Anchor.TOP_CENTER
    /** The alignment (justification) of the text object. */
    var alignment: Alignment = Alignment.CENTER
    /** The position (described by positionType), of the text object. */
    var position: Vector2 = Vector2(0.5f,0.15f)
    /** The type of position. */
    var positionType: QuotePosType = QuotePosType.IMAGE
    /** The fill colour of the text. */
    var fillColor: Color = Color.WHITE
    /** The colour of the outline around the text. */
    var outlineColor: Color = Color.BLACK
    /** The thickness of the text outline in pixels. */
    var outlineThickness: Double = 3.0
    /** The name of the typeface to use. */
    var typefaceName: String = "SansSerif"
    /** The style of the font, e.g. Italics, Bold, Plain, etc. */
    var contentFontStyle: Int = Font.PLAIN
    /** The size of the font, in pixels. */
    var contentFontSize: Int = 80
    /** The position of the Author text. */
    var authorPos: AuthorPos = AuthorPos.BOT_RIGHT
    /** The size of the author annotation. */
    var authorFontSize: Int = 60
    /** The style of the author annotation, e.g. Italics, Bold, Plain, etc. */
    var authorFontStyle: Int = Font.ITALIC
    /** The raw string content. */
    var content: String = ""
    /** The author of the quote, including any leading marks. */
    var author: String = ""
    /** The left pixel bound of the content for text wrapping. */
    var contentLeftBound: Float? = null
    /** The right pixel bound of the content for text wrapping. */
    var contentRightBound: Float? = null
    /** The Margin for the author's X position, in pixels. */
    var authorXMargin: Int = 10
    /** The Margin for the author's Y position, in pixels. */
    var authorYMargin: Int = 10

    /**
     * Retrieve a Text object that represents the quote content of this
     * QuoteInfo. Note however, that the Text object has a null Graphics object.
     * It must be assigned prior to rendering.
     *
     * @param[imgWidth] The width of the full image, in pixels.
     * @param[imgHeight] The height of the full image, in pixels.
     * @param[g] Optional parameter to set the Graphics2D object of the text
     * object at the start.
     */
    fun getContentTextObj(
            imgWidth: Int,
            imgHeight: Int,
            g: Graphics2D? = null
    ): Text {
        val contentText: Text = Text()
        contentText.alignment = alignment
        contentText.setContent(content)
        contentText.anchor = anchor
        contentText.color = fillColor
        contentText.font = Font(
                this.typefaceName,
                this.contentFontStyle,
                this.contentFontSize
        )
        contentText.graphics2D = g

        // Compute the correct position of the text.
        when (positionType) {
            QuotePosType.ABS -> contentText.globalPosition = position
            QuotePosType.IMAGE -> {
                contentText.globalPosition =
                        Vector2(position.x*imgWidth, position.y * imgHeight)
            }
        }

        // Add wrapping if set. Note, this MUST come last.
        if (this.contentLeftBound != null && this.contentRightBound != null) {
            contentText.leftPixelBound = (this.contentLeftBound!! * imgWidth).
                    toInt()
            contentText.rightPixelBound = (this.contentRightBound!! * imgWidth).
                    toInt()
        }

        return contentText
    }

    /**
     * Create a Text object for the author field.
     * This method requires the width and height of the image for positioning
     * purposes.
     * @param[imgHeight] The height of the image in pixels.
     */
    fun getAuthorTextObj(imgWidth: Int,
                         imgHeight: Int,
                         g: Graphics2D? = null
    ): Text {
        val authorText: Text = Text()
        val marginShift: Vector2
        authorText.setContent(this.author)
        authorText.color = this.fillColor
        authorText.font = Font(
                this.typefaceName,
                this.authorFontStyle,
                this.authorFontSize
        )
        authorText.graphics2D = g

        when (this.authorPos) {
            AuthorPos.BOT_RIGHT -> {
                marginShift = Vector2(-this.authorXMargin, -this.authorYMargin)
                authorText.alignment = Alignment.RIGHT
                authorText.anchor = Anchor.BOT_RIGHT
                authorText.globalPosition =
                        Vector2(imgWidth,imgHeight) + marginShift
            }
            AuthorPos.BOT_LEFT -> {
                marginShift = Vector2(this.authorXMargin, -this.authorYMargin)
                authorText.alignment = Alignment.LEFT
                authorText.anchor = Anchor.BOT_LEFT
                authorText.globalPosition =
                        Vector2(0,imgHeight) + marginShift
            }
            AuthorPos.BOT_RIGHT_ATTACHED -> {
                // So this is a bit complicated, because we need to figure
                // out where the bottom right corner of the quote is.
                // This requires that the content have a Graphics Object to
                // evaluate where things are for metrics.
                // This is why we pass g in, and we wrap the content.
                val content: Text = this.getContentTextObj(
                        imgWidth,
                        imgHeight,
                        g
                )
                // Make sure, for certain, that the content is wrapped before
                // we get the lower right corner.
                content.wrapContent()
                val cPos: Vector2 = content.getCornerPos(3)
                authorText.alignment = Alignment.RIGHT
                authorText.anchor = Anchor.TOP_RIGHT
                authorText.globalPosition =
                        cPos

            }
            AuthorPos.BOT_LEFT_ATTACHED -> {
                // See my comment in BOT_RIGHT_ATTACHED case.
                val content: Text = this.getContentTextObj(
                        imgWidth,
                        imgHeight,
                        g
                )
                content.wrapContent()
                authorText.graphics2D = g

                val cPos: Vector2 = content.getCornerPos(2)
                authorText.alignment = Alignment.LEFT
                authorText.anchor = Anchor.TOP_LEFT
                authorText.globalPosition =
                        cPos
            }
        }
        return authorText
    }
}
