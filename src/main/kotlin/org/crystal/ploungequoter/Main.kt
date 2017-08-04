package org.crystal.ploungequoter

import sun.plugin.dom.exception.InvalidStateException
import java.io.File
import java.nio.file.Paths
import java.nio.file.Path
import java.awt.Color
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.security.InvalidParameterException

fun main(args: Array<String>) {
    // Handle parsing.
    PQParser.parse(args)
    // Make sure not to do anything if we're using a help flag.
    if (!PQParser.isHelp) {
        if (PQParser.showFonts) {
            // Print out all available fonts so that we can actually see what
            // fonts are available.
            printFonts()
        } else {
            // Generate the PLounge Quote only if all other requirements have
            // been met.
            if (PQParser.getQuoteFile() != null) {
                try {
                    println("Reading quote file...")
                    val so = SetupParser.readQuoteFile(
                            File(PQParser
                            .getQuoteFile()
                            ?: throw NullPointerException())
                    )
                    println("Quote file successfully read.")
                    println("Attempting to generate plounge quote...")
                    generatePloungeQuote(so.backgroundFile, so.quoteInfos)
                } catch (e: NullPointerException) {
                    throw InvalidStateException("Quote file was null.")
                }
            }
        }
    }
}

/**
 * Run the full Plounge Quote generation.
 * Add all the generation objects, then run the renderer.
 */
fun generatePloungeQuote(backgroundFile: File?, quoteInfos: List<QuoteInfo>) {
    val PNGTYPE: String = "png"
    //val JPGTYPE: String = "jpg"
    val OUTPUT_PATH: String = "output_new.png"


    // Load all the system fonts for any text generation.
    //FontMap.loadFonts()

    if (backgroundFile == null) {
        val message: String = "Background File was null. Could not read."
        throw IllegalArgumentException(message)
    }

    // Create the renderer with the background file
    val renderer: Renderer = Renderer(backgroundFile)

    /*
    quote.setContent(msg)
    quote.font = Font("Birds of Paradise  Personal use", Font.PLAIN, 40)
    quote.color = Color(255,255,255,255)
    quote.anchor = Anchor.TOP_CENTER
    quote.alignment = Alignment.CENTER

    // Make the layers now.
    val rlayer: RasterLayer = renderer.addRasterLayer()
    val glayer: GraphicsLayer = renderer.addGraphicsLayer()
    glayer.addGraphicsObj(quote)

    val outliner: Outliner = Outliner()
    outliner.growthRadius = 2.0
    outliner.outline(glayer, rlayer)
    outliner.color = Color(0,0,0,170)
    outliner.growthRadius = 3.0
    outliner.outline(glayer, rlayer)
    */

    addOutlinedQuoteLayers(renderer, quoteInfos[0])

    //renderer.addRenderObj(quote)
    println("Rendering...")
    renderer.render(PNGTYPE,File(OUTPUT_PATH))
    print("Output at: "); println(OUTPUT_PATH)
}

/**
 * Adds a quote layer and a outline layer to a given Renderer.
 *
 * @param[renderer] The renderer we wish to put layers on.
 * @param[quoteInfo] The quoteInfo object to retrieve data from. It contains
 * the positioning, colouring, content, and all other important details
 * @post [renderer] has two layers placed on top of it, a GraphicsLayer
 * with properly positioned text on it from the quoteInfo object, and a
 * RasterLayer underneath that holds the outlining pixel data.
 */
fun addOutlinedQuoteLayers(renderer: Renderer, quoteInfo: QuoteInfo) {
    val rLayer: RasterLayer = renderer.addRasterLayer()
    val gLayer: GraphicsLayer = renderer.addGraphicsLayer()
    val outliner: Outliner = Outliner()


    val contentText: Text = quoteInfo.getContentTextObj(
            rLayer.getImage().width,
            rLayer.getImage().height
    )

    val authorText: Text = quoteInfo.getAuthorTextObj(
            rLayer.getImage().width,
            rLayer.getImage().width
    )

    gLayer.addGraphicsObj(contentText)
    gLayer.addGraphicsObj(authorText)

    // Create the outline on the raster layer.
    outliner.growthRadius = quoteInfo.outlineThickness
    outliner.color = quoteInfo.outlineColor
    outliner.outline(gLayer, rLayer)
}

/**
 * Print out the system fonts available for the quoter.
 */
fun printFonts() {
    val fonts = (
            GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .availableFontFamilyNames
    )

    for (f in fonts)
    {
      println(f)
    }
}
