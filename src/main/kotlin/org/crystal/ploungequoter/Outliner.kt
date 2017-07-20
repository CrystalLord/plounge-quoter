package org.crystal.ploungequoter

import java.awt.image.BufferedImage
import java.awt.image.WritableRaster
import java.awt.Color
import java.awt.Rectangle
import org.crystal.struct.Queue

class Outliner {
    var color: Color
    var featherRadius: Int
    var growthRadius: Double
    var opacityThreshold: Int


    private data class SelectionSample(
            val pos: Vector2Int,
            val a: Int
    )

    constructor() {
        this.color = Color(0,0,0,255)
        this.featherRadius = 0
        this.growthRadius = 0.0
        this.opacityThreshold = 0
    }

    /**
     * Outline a layer to another write layer.
     *
     * @param[layerToOutline]
     * @param
     * @post writeLayer is rewritten to outline
     */
    fun outline(layerToOutline: RenderLayer, writeLayer: RasterLayer) {
        val rasterToOutline: WritableRaster = layerToOutline.getRaster()
        val width: Int = rasterToOutline.getBounds().width
        val height: Int = rasterToOutline.getBounds().height

        var allocatedSamples: IntArray = intArrayOf(0,0,0,0)
        var count: Int = 0
        var queue: Queue<SelectionSample> = Queue<SelectionSample>()

        // Grab opaque colours and put them in a queue.
        println("Finding...")
        for (y: Int in 0 until height) {
            for (x: Int in 0 until width) {
                rasterToOutline.getPixel(
                        x,
                        y,
                        allocatedSamples
                )
                if (allocatedSamples[3] > opacityThreshold) {
                    val selectSample: SelectionSample = SelectionSample(
                            Vector2Int(x,y),
                            allocatedSamples[3]
                    )
                    queue.enqueue(selectSample)
                }
            }
        }

        // Growing the selection.
        println("Growing...")
        var colorQueue: Queue<SelectionSample> = Queue<SelectionSample>()

        if (this.growthRadius > 0.0) {
            while (!queue.isEmpty()) {
                val selected: SelectionSample = (
                        queue.dequeue()
                        ?: throw RuntimeException()
                )

                var growthQueue = this.getCircleSelection(
                        selected.pos,
                        this.growthRadius
                )

                while (!growthQueue.isEmpty()) {
                    val s: SelectionSample = (
                            growthQueue.dequeue()
                            ?: throw RuntimeException()
                    )
                    colorQueue.enqueue(s)
                }
            }
        } else {
            // If we aren't growing, just assign the colorQueue directly.
            colorQueue = queue
        }

        // Iterate through the opaque queue and assign colours.
        println("Writing...")
        while (!colorQueue.isEmpty()) {
            // Get the first item from the queue.
            val selected: SelectionSample = (
                    colorQueue.dequeue()
                    ?: throw RuntimeException()
            )
            val pX: Int = selected.pos.x
            val pY: Int = selected.pos.y
            val aFrac: Double = selected.a.toDouble() / 255.0
            // Calculate the colour to Add to the image.
            val colorAdd: Color = Color(
                    (this.color.getRed().toDouble() * aFrac).toInt(),
                    (this.color.getGreen().toDouble() * aFrac).toInt(),
                    (this.color.getBlue().toDouble() * aFrac).toInt(),
                    (this.color.getAlpha().toDouble() * aFrac).toInt()
            )
            val currCol: Color = writeLayer.getPixel(pX,pY)
            val invARed = (currCol.getRed().toDouble() * (1-aFrac)).toInt()
            val invAGreen = (currCol.getRed().toDouble() * (1-aFrac)).toInt()
            val invABlue = (currCol.getRed().toDouble() * (1-aFrac)).toInt()

            writeLayer.setPixel(
                    pX,
                    pY,
                    Color(
                            (invARed + colorAdd.getRed())/2,
                            (invAGreen + colorAdd.getGreen())/2,
                            (invABlue + colorAdd.getBlue())/2,
                            Math.min(
                                    currCol.getAlpha() + colorAdd.getAlpha(),
                                    255
                            ).toInt()
                    )
            )
        }
    }

    /**
     * Retrieve a queue of SelectionSamples.
     */
    private fun getCircleSelection(
            center: Vector2Int,
            radius: Double
    ) : Queue<SelectionSample> {

        var selectQueue: Queue<SelectionSample> = Queue<SelectionSample>()

        val minX: Int = center.x - Math.floor(radius).toInt()
        val maxX: Int = center.x + Math.ceil(radius).toInt()
        val minY: Int = center.y - Math.floor(radius).toInt()
        val maxY: Int = center.y + Math.ceil(radius).toInt()

        // Create a rectangle around the centre, and iterate through all the
        // pixels in the rectangle.
        // Then calculate the distance that pixel is away from the centre.
        // If that distance ends up being less than 1 pixel, then
        // make select that pixel by that percentage.
        for (roundY: Int in minY..maxY) {
            for (roundX: Int in minX..maxX) {
                val pixelDist: Double = Math.pow(
                        Math.pow((roundX - center.x).toDouble(),2.0)
                        + Math.pow((roundY - center.y).toDouble(),2.0),
                        0.5
                )
                // Opacity must be between 0 and 255.
                // First scale it between 0 and 1, then multiply by 255.
                val opacityDouble: Double = 255.0*Math.min(
                        Math.max(
                                1.0 - (pixelDist - radius),
                                0.0
                        ),
                        1.0
                )
                val opacityInt: Int = Math.round(opacityDouble).toInt()

                selectQueue.enqueue(
                        SelectionSample(Vector2Int(roundX,roundY), opacityInt)
                )
            }
        }

        return selectQueue
    }
}
