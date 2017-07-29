package org.crystal.ploungequoter

import java.awt.Color

enum class TextObjPosType {
    /** Absolute (global) pixel positioning */
    ABS,
    /** Positioning relative to image size */
    IMAGE
}

/**
 *
 */
data class TextObjectSettings(
        /** The Anchor setting of the text object. */
        val anchor: Anchor,
        /** The alignment (justification) of the text object. */
        val alignment: Alignment,
        /** The position (described by positionType), of the text object. */
        val position: Vector2,
        /** The type of position */
        val positionType: TextObjPosType,
        val fillColor: Color,
        val outlineColor: Color,
        val fontName: String,
        val fontStyle: Int,
        val fontSize: Int,
        val textWidth: Int,
        val content: String
)
