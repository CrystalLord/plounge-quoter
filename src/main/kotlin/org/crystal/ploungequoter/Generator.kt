package org.crystal.ploungequoter

import java.io.File

// Global constants
val PNGTYPE: String = "png"
val JPGTYPE: String = "jpg"

/**
 * Pure companion object to generate a plounge quote image.
 * This is the high level object that stores the renderer, and writes files.
 */
class Generator {
    companion object {
        /**
         * Run the full Plounge Quote generation.
         * Add all the generation objects, then run the renderer.
         * @param[backgroundFile] File object that stores the background image
         * we want to use.
         * @param[quoteInfos] List of quote info objects that store the
         * information needed to write a quote.
         */
        fun generatePloungeQuote(
                backgroundFile: File?,
                quoteInfos: List<QuoteInfo>
        ) {
            // The file type of the output.
            val outputType: String = JPGTYPE

            val currentTime: Long = System.currentTimeMillis()
            val prime: Long = 6691L
            val uglyHash: Long = currentTime % prime
            val output_path: String = (
                    "output"+uglyHash.toString()+"" +
                    "."+outputType
            )

            if (backgroundFile == null) {
                val message: String = "Background File was null." +
                        "Could not read."
                throw IllegalArgumentException(message)
            }

            // Create the renderer with the background file
            val renderer: Renderer = Renderer(backgroundFile)

            // Iterate though the quote data and generate all the text.
            for (i: Int in quoteInfos.indices) {
                println("Rendering quote: "+(i+1).toString())
                addOutlinedQuoteLayers(renderer, quoteInfos[i])
            }

            println("Rendering...")
            renderer.render(JPGTYPE, File(output_path))
            print("Output at: ")
            println(output_path)
        }

        /**
         * Adds a quote layer and a outline layer to a given Renderer.
         *
         * @param[renderer] The renderer we wish to put layers on.
         * @param[quoteInfo] The quoteInfo object to retrieve data from. It
         * contains the positioning, colouring, content, and all other
         * important details.
         * @post [renderer] has two layers placed on top of it, a GraphicsLayer
         * with properly positioned text on it from the quoteInfo object, and a
         * RasterLayer underneath that holds the outlining pixel data.
         */
        fun addOutlinedQuoteLayers(renderer: Renderer, quoteInfo: QuoteInfo) {
            val rLayer: RasterLayer = renderer.addRasterLayer()
            val gLayer: GraphicsLayer = renderer.addGraphicsLayer()
            val outliner: Outliner = Outliner()

            val contentText: Text = quoteInfo.getContentTextObj(
                    rLayer.getImage().width, rLayer.getImage().height
            )
            contentText.graphics2D = gLayer.getGraphics2D()
            // Force wrapping of the contentText object.
            contentText.wrapContent()

            // Make sure to pass the graphics2D object of the gLayer to the
            // author text creation method.
            // This is because we need to know metrics about the contentText
            // object internally, and we can't do that without the Graphics2D
            // object to draw on.
            val authorText: Text = quoteInfo.getAuthorTextObj(
                    rLayer.getImage().width,
                    rLayer.getImage().height,
                    gLayer.getGraphics2D()
            )

            gLayer.addGraphicsObj(contentText)
            gLayer.addGraphicsObj(authorText)

            // Create the outline on the raster layer.
            outliner.growthRadius = quoteInfo.outlineThickness
            outliner.color = quoteInfo.outlineColor
            outliner.outline(gLayer, rLayer)
        }
    }
}