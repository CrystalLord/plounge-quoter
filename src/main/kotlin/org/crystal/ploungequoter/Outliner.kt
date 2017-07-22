package org.crystal.ploungequoter

import java.awt.image.BufferedImage
import java.awt.image.WritableRaster
import java.awt.Color
import java.awt.Rectangle
import org.crystal.struct.Queue
import org.crystal.struct.IntMatrix


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
     * @param[layerToOutline] Render layer to get opaque from.
     * @param[writeLayer] Raster layer to write the outline on.
     * @post writeLayer is rewritten to outline
     */
    fun outline(readLayer: RenderLayer, writeLayer: RasterLayer) {
        val readRaster = readLayer.getRaster()
        val width: Int = readRaster.getBounds().width
        val height: Int = readRaster.getBounds().height

        var allocatedSamples: IntArray = intArrayOf(0,0,0,0)
        var queue: Queue<SelectionSample> = Queue<SelectionSample>()

        // Grab opaque colours and put them in a queue.
        println("Finding...")
        for (y: Int in 0 until height) {
            for (x: Int in 0 until width) {
                readRaster.getPixel(
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
        var selectionMask: IntMatrix = IntMatrix(
                writeLayer.getImage().getWidth(),
                writeLayer.getImage().getHeight()
        )

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

                    // Check to make sure that the we are within the matrix
                    // bounds
                    if (selectionMask.inMatrix(s.pos.x, s.pos.y)) {
                        val currentMaskVal: Int = selectionMask.getXY(
                                s.pos.x,
                                s.pos.y
                        )
                        // Set only the alpha which is largest in the selection
                        // mask.
                        selectionMask.setXY(
                                s.pos.x,
                                s.pos.y,
                                Math.max(
                                        currentMaskVal.toInt(),
                                        s.a
                                ).toInt()
                        )
                    }
                }
            }
        }

        // Now fill in the writeLayer...
        println("Writing...")
        this.fillMask(selectionMask, writeLayer)
    }

    /**
     * Retrieve a queue of SelectionSamples that list the alphas and positions
     * of each pixel that needs to be selected.
     *
     * @param[center] The center of the circle to select.
     * @param[radius] The radius of the circle to select.
     * @return Returns a Queue of SelectionSamples.
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


    /**
     * Given an IntMatrix of alphas, fill the image by mixing in
     * the Outliner's colours.
     * @param[selectionMask] An IntMatrix which stores the Alphas for each
     * pixel.
     * @param[writeLayer] Layer to write on with the selectionMask.
     * @post The writeLayer is filled with the Outliner's colour.
     */
    private fun fillMask(selectionMask: IntMatrix, writeLayer: RasterLayer) {
        // Current X and Y positions in the matrix for iteration purposes.
        var currX: Int = 0
        var currY: Int = 0
        while (currY < selectionMask.height) {
            // If we went too far to the right, move down to the next
            // row in the matrix.
            if (currX >= selectionMask.width) {
                currX = 0
                currY++
                continue
            }

            // Gather the alpha fraction for multiplication.
            val aFrac: Double = selectionMask.getXY(
                    currX,
                    currY
            ).toDouble() / 255.0

            // store the colour in a shorter name.
            val myCol: Color = this.color


            // Calculate the colour to mix with the image.
            val aRed: Int = (myCol.getRed().toDouble() * aFrac).toInt()
            val aGreen: Int = (myCol.getGreen().toDouble() * aFrac).toInt()
            val aBlue: Int = (myCol.getBlue().toDouble() * aFrac).toInt()
            val aAlpha: Int = (myCol.getAlpha().toDouble() * aFrac).toInt()

            // Get the colours stored on the write layer currently.
            val wCol: Color = writeLayer.getPixel(currX,currY)
            val invARed = (wCol.getRed().toDouble() * (1-aFrac)).toInt()
            val invAGreen = (wCol.getRed().toDouble() * (1-aFrac)).toInt()
            val invABlue = (wCol.getRed().toDouble() * (1-aFrac)).toInt()

            // Actually write the colour.
            writeLayer.setPixel(
                    currX,
                    currY,
                    Color(
                            invARed + aRed,
                            invAGreen + aGreen,
                            invABlue + aBlue,
                            Math.min(
                                    wCol.getAlpha() + aAlpha,
                                    255
                            ).toInt()
                    )
            )
            // Make sure to increment to the next column.
            currX++
        }

    }
}
