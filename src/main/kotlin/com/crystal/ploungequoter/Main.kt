package com.crystal.ploungequoter

import com.crystal.ploungequoter.*
import java.io.*
import java.nio.file.*

fun main(args: Array<String>) {
    generatePloungeQuote()
}

fun generatePloungeQuote() {
    var backgroundImagePath: Path =
        Paths.get("~/.gitconfig")
    var renderer: Renderer = Renderer(backgroundImagePath)
    renderer.renderObjs.add(Text("Hello World"))
    renderer.render()
}
