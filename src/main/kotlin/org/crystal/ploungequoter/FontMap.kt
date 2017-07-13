package org.crystal.ploungequoter

import java.io.File
import java.awt.Font
import java.nio.file.Path
import java.awt.GraphicsEnvironment

class FontMap {
    companion object {
        /** Store the fonts */
        private var fontFileMap: MutableMap<String, Font> = (
                HashMap<String, Font>()
        )


        /**
         * Loads fonts from the user's system.
         * @param path Path to font directory
         */
        fun loadFonts() {
            // Get the graphics environment of the current operating system
            // and retrieve its installed fonts.
            val env: GraphicsEnvironment = (
                    GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
            )
            val fonts: Array<Font> = env.getAllFonts()

            // Load all the files into the map
            for (f: Font in fonts) {
                this.fontFileMap.put(f.getFontName(), f)
            }
        }

        /** Remove all elements from the font map. */
        fun clearFonts() { this.fontFileMap.clear(); }

        /**
         * Retrieve a File object representing a font stored somewhere in the
         * system.
         * @param fontName Name fo the font to return.
         * @return Returns the desired font.
         */
        fun getFont(fontName: String): Font? {
            return this.fontFileMap.get(fontName)
        }
    }
}
