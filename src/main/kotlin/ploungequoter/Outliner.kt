package ploungequoter

import java.awt.image.BufferedImage

class Outliner {
    private var selectedPixels: Matrix<Byte> = Matrix<Byte>(1,1,0x0)
    private var image: BufferedImage? = null

    constructor(img: BufferedImage) {
        this.loadImage(img)
    }

    fun loadImage(img: BufferedImage) {
        this.image = img
        selectedPixels = Matrix<Byte>(img.getWidth(), img.getHeight())
    }

    fun outline() {
        return
    }
}
