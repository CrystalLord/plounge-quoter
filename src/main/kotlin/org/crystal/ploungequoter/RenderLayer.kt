package org.crystal.ploungequoter

import java.awt.image.BufferedImage
import java.awt.image.WritableRaster

interface RenderLayer {
    /**
     * Retrieve an Image representation of this layer.
     * Modifying the image returnew will cause modifications to the layer.
     */
    fun getImage(): BufferedImage

    /**
     * Retrieve a WritableRaster object of this layer.
     * Note, this raster object is attached to this layer, and modifying it will
     * alter the layer.
     */
    fun getRaster(): WritableRaster
}
