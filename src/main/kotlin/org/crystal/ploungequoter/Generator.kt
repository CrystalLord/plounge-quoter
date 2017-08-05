package org.crystal.ploungequoter

import java.io.File

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
            val PNGTYPE: String = "png"
            //val JPGTYPE: String = "jpg"
            val OUTPUT_PATH: String = "output_new.png"


            // Load all the system fonts for any text generation.
            //FontMap.loadFonts()

            if (backgroundFile == null) {
                val message: String = "Background File was null." +
                        "Could not read."
                throw IllegalArgumentException(message)
            }

            // Create the renderer with the background file
            val renderer: Renderer = Renderer(backgroundFile)

            // Iterate though the quote data and generate all the text.
            for (quoteInfo: QuoteInfo in quoteInfos) {
                addOutlinedQuoteLayers(renderer, quoteInfo)
            }

            println("Rendering...")
            renderer.render(PNGTYPE, File(OUTPUT_PATH))
            print("Output at: ")
            println(OUTPUT_PATH)
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

            val authorText: Text = quoteInfo.getAuthorTextObj(
                    rLayer.getImage().width, rLayer.getImage().height
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