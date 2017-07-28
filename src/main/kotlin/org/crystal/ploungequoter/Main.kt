package org.crystal.ploungequoter

import java.io.File
import java.nio.file.Paths
import java.nio.file.Path
import java.awt.Color
import java.awt.Font
import java.awt.GraphicsEnvironment

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
            generatePloungeQuote(PQParser.getBackground())
        }
    }
}


/**
 * Run the full Plounge Quote generation.
 * Add all the generation objects, then run the renderer.
 */
fun generatePloungeQuote(background_path: String?) {
    val PNGTYPE: String = "png"
    //val JPGTYPE: String = "jpg"
    val OUTPUT_PATH: String = "output_new.png"


    // Load all the system fonts for any text generation.
    //FontMap.loadFonts()

    if (background_path == null) {
        throw IllegalArgumentException("background_path was null.")
    }

    val backgroundImagePath: Path =
            Paths.get(background_path)
    val renderer: Renderer = Renderer(backgroundImagePath)

    val quote: Text = Text(Vector2(800.0f,100.0f))
    val msg: String = ("I dunno where you've been hanging on the\ninternet "
            +"where you ain't "
            +"seeing lil Hitlets\nscampering around."
    )
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

    //renderer.addRenderObj(quote)
    println("Rendering...")
    renderer.render(PNGTYPE,File(OUTPUT_PATH))
    print("Output at: "); println(OUTPUT_PATH)
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
