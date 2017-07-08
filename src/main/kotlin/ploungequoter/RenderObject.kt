package ploungequoter

import java.awt.Image
import java.awt.Graphics2D

/**
 * Anchor class for easy positioning.
 */
enum class AnchorPos {
    TOPLEFT, BOTLEFT, TOPRIGHT, BOTRIGHT
}

/**
 * An abstract rendering object
 *
 * An abstract class from which all objects that are rendered in an image are
 * derived from.
 */
abstract class RenderObject {

    // Absolute position
    var globalPosition: Vector2 = Vector2.ZERO
    // Render objects allow parent
    var parentObj: RenderObject? = null
    // Anchor for the rendering
    var anchor: AnchorPos = AnchorPos.TOPLEFT

    /**
     * Get relative position compared to parent.
     *
     * @return Vector2 representing relative position
     */
    open fun getRelPosition(): Vector2 {
        return (this.globalPosition
         - (parentObj?.globalPosition ?: Vector2.ZERO))
    }

    /**
     * Get relative position compared to parent.
     *
     * @param relPos Relative Position to set
     */
    open fun setRelPosition(relPos : Vector2) {
        globalPosition = (relPos
         + (parentObj?.globalPosition ?: Vector2.ZERO))
    }

    /**
     * Render the object onto some graphics object.
     * @param g The graphics object to render on to.
     */
    abstract fun render(g: Graphics2D): Unit

    /**
     * @return Returns the width of the render object as an integer,
     * in pixels.
     */
    abstract fun getWidth(): Int

    /**
     * @return Returns the height of the render object as an integer,
     * in pixels.
     */
    abstract fun getHeight(): Int
}
