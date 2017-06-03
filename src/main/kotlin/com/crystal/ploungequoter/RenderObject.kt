package com.crystal.ploungequoter

import com.crystal.ploungequoter.Vector2
import java.awt.Image

/**
 * An abstract rendering object
 *
 * An abstract class from which all objects that are rendered in an image are
 * derived from.
 *
 */
abstract class RenderObject {
    
    // Absolute position
    var globalPosition: Vector2 = Vector2.ZERO
    // Render objects allow parent
    var parentObj: RenderObject? = null

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

    abstract fun render(img: Image): Unit
}
