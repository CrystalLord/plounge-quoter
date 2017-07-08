package ploungequoter

import java.io.File
import java.awt.Font
import java.nio.file.Path

class FontFileMap {

    /** Store the fonts */
    private var fontFileMap: MutableMap<String, Path> = HashMap<String, Path>()

    /**
     * Loads fonts from a given directory.
     * @param path Path to font directory
     */
    fun loadFonts(path: Path) {
        val folder: File = path.toFile()
        val fontFiles: Array<File> = folder.listFiles()

        // Load all the files into the map
        //for (f: File in fontFiles) {
        //    this.fontFileMap.put(f.getName(), f)
        //}
    }

    /** Remove all elements from the font map. */
    fun clearFonts() { this.fontFileMap.clear(); }

    /**
     * Retrieve a File object representing a font stored somewhere in the
     * system.
     * @param fontName String to represent the
     */
    fun getFont(fontName: String): Font {
        return Font.getFont(this.fontFileMap.get(fontName).toString())
    }
}
