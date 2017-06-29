package com.crystal.ploungequoter

import java.io.File
import java.nio.file.Paths
import java.nio.file.Path

fun main(args: Array<String>) {
    // TODO: add argument parsing
    generatePloungeQuote(args[0])
}

/**
 * Run the full Plounge Quote generation.
 * Add all the generation objects, then run the renderer.
 */
fun generatePloungeQuote(background_path: String) {
    val PNGTYPE: String = "png"
    val JPGTYPE: String = "jpg"

    var backgroundImagePath: Path =
            Paths.get(background_path)
    var renderer: Renderer = Renderer(backgroundImagePath)

    var quote: Text = Text(Vector2(100.0f,100.0f))
    quote.content = "HELLO WORLD!"

    renderer.addRenderObj(quote)
    renderer.render(PNGTYPE,File("output.png"))
}
