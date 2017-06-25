package com.crystal.ploungequoter

import com.crystal.ploungequoter.*
import java.io.File
import java.nio.file.Paths
import java.nio.file.Path

fun main(args: Array<String>) {
    generatePloungeQuote()
}

/**
 * Run the full Plounge Quote generation. 
 *
 * Add all the generation objects, then run the renderer.
 */
fun generatePloungeQuote() {
    val PNGTYPE: String = "png"
    val JPGTYPE: String = "jpg"
    
    var backgroundImagePath: Path =
        Paths.get("/home/crystal/image.png")
    var renderer: Renderer = Renderer(backgroundImagePath)
    renderer.addRenderObj(Text("Hello World"))
    renderer.render(PNGTYPE,File("output.png"))
}
