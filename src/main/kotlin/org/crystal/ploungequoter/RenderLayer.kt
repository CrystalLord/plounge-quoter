package org.crystal.ploungequoter

import java.awt.image.BufferedImage
import java.awt.image.WritableRaster

abstract class RenderLayer(width: Int, height: Int) {
    /** Width of the layer, in pixels */
    var width: Int
    /** Height of the layer, in pixels */
    var height: Int

    init {
        this.width = width
        this.height = height
    }

    /**
     * Retrieve an Image representation of this layer.
     * Modifying the object returned here will not modify the internals of the
     * layer.
     */
    abstract fun getImage(): BufferedImage

    /**
     * Retrieve a WritableRaster object of this layer.
     * Note, this raster object is not gauranteed to be attached to the layer.
     */
    abstract fun getRaster(): WritableRaster

}
