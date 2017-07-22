package org.crystal.ploungequoter

import java.awt.image.BufferedImage
import java.awt.RenderingHints
import java.awt.Graphics2D
import java.awt.image.WritableRaster
import java.awt.Color

/**
 * Layer which uses the underlying Java AWT Graphics object to render itself.
 *
 * @param width Width of the layer, in pixels.
 * @param height Height of the
 */
class RasterLayer : RenderLayer {

    /**
     * The stored image object that represents this layer.
     */
    private var img: BufferedImage
    private var raster: WritableRaster

    /**
     * Create a new RasterLayer with specified width and height
     * @param[width] Width in pixels of the new layer.
     * @param[height] Height in pixels of the new layer.
     */
    constructor(width: Int, height: Int) {
        this.img = BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_ARGB
        )
        this.raster = img.getRaster()
    }

    override fun getImage(): BufferedImage {
        return this.img
    }

    override fun getRaster(): WritableRaster {
        return this.raster
    }

    /**
     * Set a single pixel's colour on this layer.
     *
     * @param[x] X position of the pixel.
     * @param[y] Y position of the pixel.
     * @param[col] Color of the pixel.
     */
    fun setPixel(x: Int, y: Int, col: Color) {
        this.setPixel(
                x,
                y,
                col.getRed(),
                col.getGreen(),
                col.getBlue(),
                col.getAlpha()
        )
    }

    /**
     * Set a single pixel's colour on this layer.
     *
     * @param[x] X position of the pixel.
     * @param[y] Y position of the pixel.
     * @param[r] Red component of the colour.
     * @param[g] Green component of the colour.
     * @param[b] Blue component of the colour.
     * @param[a] Alpha component of the colour.
     */
    fun setPixel(x: Int, y: Int, r: Int, g: Int, b: Int, a: Int) {
        if (
                0 <= x && x < this.img.getWidth()
                && 0 <= y && y < this.img.getHeight()
        ) {
            this.raster.setPixel(x,y,intArrayOf(r,g,b,a))
        }
        // If we're outside the bounds, do nothing.
    }

    /**
     * Set a set of pixels using an ArrayList.
     *
     * @param[coordinates] [ArrayList] of [IntArray]s. This should be changed.
     */
    fun setPixels(coordinates: ArrayList<IntArray>, col: Color) {
        for (p in coordinates) {
            this.setPixel(p[0], p[1], col)
        }
    }

    /**
     * Retrieve a pixel's colours on this layer.
     *
     * @param[x] X position of the pixel.
     * @param[y] Y position of the pixel.
     * @return The pixel's RGBA colour.
     */
    fun getPixel(x: Int, y: Int): Color {
        if (
                0 <= x && x < this.img.getWidth()
                && 0 <= y && y < this.img.getHeight()
        ) {
            var arr: IntArray = intArrayOf(0,0,0,0)
            this.raster.getPixel(x,y,arr)
            val color: Color = Color(arr[0],arr[1],arr[2],arr[3])
            return color
        } else {
            return Color(0,0,0,0)
        }
    }
}
