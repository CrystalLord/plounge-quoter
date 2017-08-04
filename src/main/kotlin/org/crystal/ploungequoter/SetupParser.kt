package org.crystal.ploungequoter

import java.io.File

class SetupParser {
    companion object {
        /**
         * Read a quote file.
         *
         * @param[file] The File object of the quote file to read.
         * @return Returns a quoteInfo object, from which a set of text
         * objects can be generated.
         */
        fun readQuoteFile(file: File): SetupOutput {
            // Background quote file
            var bgFile: File? = null
            // Make a new QuoteInfo list to represent all the quotes.
            val quoteInfos: ArrayList<QuoteInfo> = arrayListOf()
            // Grab all the lines in the file.
            val lines: List<String> = file.readLines()
            // The index of the current quote we are parsing.
            var quoteInd: Int = -1

            // "i" is the iteration index for the key value pair.
            // It technically represents the line index, but due to the
            // ability for a parameter to span several lines, it's important
            // to allow i to arbitrarily jump around.
            var i: Int = 0
            while (i < lines.size) {
                val stripedLine = Utils.stripBlank(lines[i])

                // If a line says "quote", then the following parameters
                // belong to that quote.
                if (stripedLine == "quote") {
                    quoteInd++
                    quoteInfos.add(QuoteInfo())
                    i++
                    continue
                }

                // -------------------------------------------------------------

                // Strip the line on the first colon.
                val lineList: List<String> = stripedLine.split(':', limit=2)
                // Make sure there actually is a colon in there.
                if (lineList.size < 2) {
                    throw RuntimeException(
                            "Parameter line "+(i+1).toString()+" did not "
                            + "have a colon"
                    )
                }

                // -------------------------------------------------------------

                // Retrieve the parameter name and the value.
                val parameterName: String = lineList[0]
                var value: String = Utils.stripBlank(lineList[1])

                // -------------------------------------------------------------

                // Does the value line end in a backslash?
                var lineContinues: Boolean = (lineList[1].last() == '\\')
                // Handle continuation lines if there's a backslash at the end.
                var count: Int = 1
                while (lineContinues) {
                    val nextLine = Utils.stripBlank(lines[i+count])
                    value = value.substring(0,value.length-1) + nextLine
                    lineContinues = (nextLine.last() == '\\')
                    count++ // Go to the next line.
                    i++ // Make sure the entire parser also moves forwards.
                }

                // -------------------------------------------------------------

                // Catch cases where a quote line hasn't been set.
                if (quoteInd < 0) {
                    if (parameterName == "background") {
                        // TODO: Do some magic here.
                        // Allow both local file reading and URL parsing.
                        // Currently it just reads local files.
                        bgFile = File(value)
                        i++ // Remember to increment i
                        continue
                    } else {
                        // If the global variable doesn't exist...
                        throw RuntimeException(
                                "Global value "
                                +parameterName
                                +" not found."
                        )
                    }
                }

                // Send the parameter and value off to the interpreter.
                // This will modify the QuoteInfo object we need.
                SetupParser.interpretParameter(
                        quoteInfos[quoteInd],
                        parameterName,
                        value
                )

                // Remember to increment i
                i++
            }
            // Return the combined output in SetupOutput object.
            if (bgFile != null) {
                return SetupOutput(bgFile, quoteInfos)
            } else {
                throw RuntimeException("Background File Not Set.")
            }
        }


        /**
         * Edit a quoteInfo object with the given parameter and value.
         *
         * @param[quoteInfo] QuoteInfo object to modify.
         * @param[param] Parameter name.
         * @param[value] Value of the parameter.
         * @post The input [quoteInfo] is modified with the new param's value.
         */
        private fun interpretParameter(
                quoteInfo: QuoteInfo,
                param: String,
                value: String
        ) {
            when (param) {
                "anchor" -> {
                    when (Utils.stripBlank(value.toLowerCase())) {
                        "topleft" -> quoteInfo.anchor = Anchor.TOP_LEFT
                        "topright" -> quoteInfo.anchor = Anchor.TOP_RIGHT
                        "botleft" -> quoteInfo.anchor = Anchor.BOT_LEFT
                        "botright" -> quoteInfo.anchor = Anchor.BOT_RIGHT
                        "topcentre" -> quoteInfo.anchor = Anchor.TOP_CENTER
                        "botcentre" -> quoteInfo.anchor = Anchor.BOT_CENTER
                        "centrecentre" -> quoteInfo.anchor =
                                Anchor.CENTER_CENTER
                        else -> throw RuntimeException("Invalid Anchor")
                    }
                }
                "alignment" -> {
                    when (value) {
                        "left" -> quoteInfo.alignment = Alignment.LEFT
                        "right" -> quoteInfo.alignment = Alignment.RIGHT
                        "centre" -> quoteInfo.alignment = Alignment.CENTER
                        else -> throw RuntimeException("Invalid Alignment")
                    }
                }
                "typeface" -> quoteInfo.typefaceName = value
                "contentfontsize" -> quoteInfo.contentFontSize = value.toInt()
                "content" -> quoteInfo.content = value.replace("\\n","\n")
                "author" -> quoteInfo.author = value.replace("\\n","\n")
                "authorfontsize" -> quoteInfo.authorFontSize = value.toInt()
                else -> throw RuntimeException(
                        "Unknown Parameter given: "
                        +param
                )
            }
        }
    }

}
