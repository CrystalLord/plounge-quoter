package org.crystal.ploungequoter

import java.io.File
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths
import org.apache.commons.io.FilenameUtils

class BackgroundRetriever(input: String) {
    val downloaded: Boolean
    lateinit var path: Path
    lateinit var file: File

    init {
        val httpPrefix: String = "http://"
        val httpsPrefix: String = "https://"

        if (input.subSequence(0,httpPrefix.length) == httpPrefix
                || input.subSequence(0,httpsPrefix.length) == httpsPrefix) {
            this.downloaded = true
            File(System.getProperty("java.io.tmpdir")).mkdir()
            val downloadDirectory: Path = Paths.get(
                System.getProperty("java.io.tmpdir")
            )
            download(URL(input), downloadDirectory)
        } else {
            this.downloaded = false
            this.path = Paths.get(input)
            this.file = File(input)
        }
    }

    /**
     * Download a file from a URL to
     */
    fun download(url: URL, directory: Path) {
        // Use a hash as a basename to prevent unrelated overlaps.
        val basename: String = url.hashCode().toString()
        this.path = directory.resolve(basename)
        val hashName: String = url.hashCode().toString()
        this.file = File(directory.resolve(hashName).toString())

        if (this.file.exists() && !this.file.isDirectory) {
            println("Download file already cached in: " + directory)
            return
        }

        println("Downloading background... this may take a bit.")

        // Retrieval stream for online content
        val bis: BufferedInputStream = BufferedInputStream(url.openStream())
        // File write stream for local content
        val fis: FileOutputStream = FileOutputStream(this.file)
        val buffer: ByteArray = ByteArray(1024)
        var count: Int = bis.read(buffer, 0, 1024)
        while (count != -1) {
            fis.write(buffer, 0, count)
            count = bis.read(buffer, 0, 1024)
        }
        fis.close()
        bis.close()
        println("Downloaded to: " + directory)
    }

    fun deleteFile() {
        if (!this.downloaded) {
            throw RuntimeException("Attempted to delete non-downloaded file.")
        } else {
            println("Did not delete downloaded file yet...")
        }
    }
}