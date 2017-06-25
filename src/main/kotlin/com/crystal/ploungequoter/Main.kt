package com.crystal.ploungequoter

import com.crystal.ploungequoter.*
import java.io.*
import java.nio.file.*

fun main(args: Array<String>) {
    generatePloungeQuote()
}

/**
 * Run the full Plounge Quote generation. 
 *
 * Add all the generation objects, then run the renderer.
 */
fun generatePloungeQuote() {
    var backgroundImagePath: Path =
        Paths.get("/home/crystal/.gitconfig")
    var renderer: Renderer = Renderer(backgroundImagePath)
    renderer.addRenderObj(Text("Hello World"))
    renderer.render()
}
