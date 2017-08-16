package org.crystal.ploungequoter

import sun.plugin.dom.exception.InvalidStateException
import java.io.File
import java.awt.GraphicsEnvironment

/**
 * This is where the program starts.
 */
fun main(args: Array<String>) {
    // Handle parsing.
    PQParser.parse(args)
    if (!PQParser.isHelp) {
        if (PQParser.showFonts) {
            // Print out all available fonts so that we can actually see what
            // fonts are available.
            printTypefaces()
        } else {
            if (PQParser.getQuoteFile() != null) {
                try {
                    println("Reading quote file...")
                    // Read the quote file.
                    val so: SetupOutput = SetupParser.readQuoteFile(
                            File(PQParser
                            .getQuoteFile()
                            ?: throw NullPointerException())
                    )
                    println("Quote file successfully read.")
                    // Make the plounge quote.
                    Generator.generatePloungeQuote(
                            so.backgroundFile,
                            so.quoteInfos,
                            so.outputType,
                            so.source
                    )
                } catch (e: NullPointerException) {
                    throw InvalidStateException("Quote file was null.")
                }
            }
        }
    }
}

/**
 * Print out the system fonts available for the quoter.
 * These are the fonts installed on your computer.
 */
fun printTypefaces() {
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
