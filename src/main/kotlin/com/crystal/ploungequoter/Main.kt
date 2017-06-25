package com.crystal.ploungequoter

import com.crystal.ploungequoter.*
import java.io.File
import java.nio.file.Paths
import java.nio.file.Path

fun main(args: Array<String>) {
    generatePloungeQuote(args[0])
}

/**
 * Run the full Plounge Quote generation. 
 *
 * Add all the generation objects, then run the renderer.
 */
fun generatePloungeQuote(background_path: String) {
    val PNGTYPE: String = "png"
    val JPGTYPE: String = "jpg"
    
    var backgroundImagePath: Path =
        Paths.get(background_path)
    var renderer: Renderer = Renderer(backgroundImagePath)
    renderer.addRenderObj(Text("Hello World"))
    renderer.render(PNGTYPE,File("output.png"))
}
