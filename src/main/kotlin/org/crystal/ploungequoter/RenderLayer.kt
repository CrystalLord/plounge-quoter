package org.crystal.ploungequoter

import java.awt.image.BufferedImage
import java.awt.image.WritableRaster

interface RenderLayer {
    /**
     * Retrieve an Image representation of this layer.
     * Modifying the object returned here will not modify the internals of the
     * layer.
     */
    fun getImage(): BufferedImage

    /**
     * Retrieve a WritableRaster object of this layer.
     * Note, this raster object is not gauranteed to be attached to the layer.
     */
    fun getRaster(): WritableRaster
}
