package org.crystal.ploungequoter

import java.io.File

class SetupParser(val file: File) {
    //private val textObjectSettings:

    init {
        val lines: List<String> = this.file.readLines()
    }
}
