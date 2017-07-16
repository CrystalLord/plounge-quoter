package org.crystal.ploungequoter

import java.awt.image.BufferedImage
import java.awt.image.WritableRaster

class Outliner {
    private var selectedPixels: Matrix<Byte> = Matrix<Byte>(1,1,0x0)
    lateinit private var image: BufferedImage
    lateinit private var raster: WritableRaster

    constructor(img: BufferedImage) {
        this.loadImage(img)
    }

    fun loadImage(img: BufferedImage) {
        this.image = img
        this.raster = this.image.getRaster()
        selectedPixels = Matrix<Byte>(img.getWidth(), img.getHeight())
        // Debug
        var count: Int = 0
        for (i in 0 until (this.raster.getWidth() * this.raster.getHeight())) {
            count++
        }
        println("Image size was: " + count.toString())
    }

    fun outline() {
    }
}
