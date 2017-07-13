package org.crystal.ploungequoter

import java.io.File
import java.nio.file.Paths
import java.nio.file.Path
import org.apache.commons.cli.Options
import java.awt.Color
import java.awt.Font

fun main(args: Array<String>) {
    // Handle parsing.
    PQParser.parse(args)
    // Make sure not to do anything if we're using help.
    if (!PQParser.isHelp) {
        // Generate the PLounge Quote.
        generatePloungeQuote(PQParser.getBackground())
    }
}


/**
 * Run the full Plounge Quote generation.
 * Add all the generation objects, then run the renderer.
 */
fun generatePloungeQuote(background_path: String?) {
    val PNGTYPE: String = "png"
    val JPGTYPE: String = "jpg"

    // Load all the system fonts for any text generation.
    //FontMap.loadFonts()

    if (background_path == null) {
        throw IllegalArgumentException("background_path was null.")
    }

    var backgroundImagePath: Path =
            Paths.get(background_path)
    var renderer: Renderer = Renderer(backgroundImagePath)

    var quote: Text = Text(Vector2(100.0f,100.0f))
    quote.setContent("HELLO WORLD!")
    quote.font = Font("DejaVu Serif", Font.PLAIN, 40)
    quote.color = Color(0,255,255)

    renderer.addRenderObj(quote)
    renderer.render(PNGTYPE,File("output.png"))
}
