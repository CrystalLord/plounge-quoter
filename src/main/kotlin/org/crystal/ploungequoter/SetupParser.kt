package org.crystal.ploungequoter

import java.awt.Color
import java.io.File
import java.awt.Font

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
            // The type of file output. (png, jpg)
            var outputType: String = PNGTYPE
            // The source for the quote.
            var source: String = ""

            // Make a new QuoteInfo list to represent all the quotes.
            val quoteInfos: ArrayList<QuoteInfo> = arrayListOf()
            // Grab all the lines in the files
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

                // First, check the empty line case. If the line is empty,
                // skip it.
                if (stripedLine == "") {
                    i++
                    continue
                }

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
                var count: Int = 0
                while (lineContinues) {
                    count++ // Go to the next line.
                    val nextLine = Utils.stripBlank(lines[i+count])
                    value = value.substring(0,value.length-1) + nextLine
                    lineContinues = (nextLine.last() == '\\')
                }
                i += count // Make sure the entire parser also moves forwards.

                // -------------------------------------------------------------

                // Catch cases where a quote line hasn't been set.
                if (quoteInd < 0) {
                    when (parameterName) {
                        "background" -> {
                            // Allow both local file reading and URL parsing.

                            // Retrieve the background regardless if it's a URL
                            // or a local file.
                            val backgroundRetriever: BackgroundRetriever =
                                    BackgroundRetriever(value)
                            // Set the retrieved file.
                            bgFile = backgroundRetriever.file
                            i++ // Remember to increment i
                        }
                        "source" -> {
                            source = value
                            i++
                        }
                        "outputtype" -> {
                            outputType = value
                            i++
                        }
                        else -> {
                            // If the global variable doesn't exist...
                            throw RuntimeException("Global parameter \"" +
                                    parameterName + "\" not found.")
                        }
                    }
                    continue
                }

                // Send the parameter and value off to the interpreter.
                // This will modify the QuoteInfo object we need.
                SetupParser.interpretQuoteParameter(
                        quoteInfos[quoteInd],
                        parameterName,
                        value
                )

                // Remember to increment i
                i++
            }
            // Return the combined output in SetupOutput object.
            if (bgFile != null) {
                return SetupOutput(bgFile, quoteInfos, outputType, source)
            } else {
                throw RuntimeException("Background File Not Set.")
            }
        }


        /**
         * Edit a quoteInfo object with the given parameter and value.
         *
         * @param[quoteInfo] QuoteInfo object to modify.
         * @param[param] Parameter name.
         * @param[value] Value of the parameter. Note, it cannot have any
         * leading or trailing spaces.
         * @post The input [quoteInfo] is modified with the new param's value.
         */
        private fun interpretQuoteParameter(
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
                "author" -> quoteInfo.author = value.replace("\\n","\n")
                "authorfontsize" -> quoteInfo.authorFontSize = value.toInt()
                "authorfontstyle" -> {
                    when (value) {
                        "plain" -> quoteInfo.authorFontStyle = Font.PLAIN
                        "italic" -> quoteInfo.authorFontStyle = Font.ITALIC
                        "bold" -> quoteInfo.authorFontStyle = Font.BOLD
                        "italicbold" -> quoteInfo.authorFontStyle =
                                Font.ITALIC + Font.BOLD
                    }
                }
                "authormargin" -> {
                    val xy: List<String> = value.split(",")
                    try {
                        quoteInfo.authorXMargin =
                                Utils.stripBlank(xy[0]).toInt()
                        quoteInfo.authorYMargin =
                                Utils.stripBlank(xy[1]).toInt()
                    } catch (e: RuntimeException) {
                        throw RuntimeException("authormargin parameter not in" +
                                " correct format.")
                    }
                }
                "authorposition" -> {
                    when (value) {
                        "botleft" -> quoteInfo.authorPos = AuthorPos.BOT_LEFT
                        "botright" -> quoteInfo.authorPos = AuthorPos.BOT_RIGHT
                        "botleftattached" -> quoteInfo.authorPos = AuthorPos
                                .BOT_LEFT_ATTACHED
                        "botrightattached" -> quoteInfo.authorPos = AuthorPos
                                .BOT_RIGHT_ATTACHED
                    }
                }
                "typeface" -> quoteInfo.typefaceName = value
                "content" -> quoteInfo.content = value.replace("\\n","\n")
                "contentfontsize" -> quoteInfo.contentFontSize = value.toInt()
                "contentfontstyle" -> {
                    when (value) {
                        "plain" -> quoteInfo.contentFontStyle = Font.PLAIN
                        "italic" -> quoteInfo.contentFontStyle = Font.ITALIC
                        "bold" -> quoteInfo.contentFontStyle = Font.BOLD
                        "italicbold" -> quoteInfo.contentFontStyle =
                                Font.ITALIC + Font.BOLD
                    }
                }
                "contentwrap" -> {
                    val xy: List<String> = value.split(",")
                    if (xy.size != 2) {
                        throw RuntimeException("contentwrap not in correct " +
                                "format.")
                    }
                    quoteInfo.contentLeftBound = Utils.stripBlank(xy[0])
                            .toFloat()
                    quoteInfo.contentRightBound = Utils.stripBlank(xy[1])
                            .toFloat()
                }
                "fillcolour" -> {
                    val cl: List<Int> =
                            value.split(",").map {
                                s ->  Utils.stripBlank(s).toInt()
                            }
                    try {
                        quoteInfo.fillColor =
                                Color(cl[0], cl[1], cl[2], cl[3])
                    } catch (e: IllegalArgumentException) {
                        throw IllegalArgumentException(
                                "Colour parameter not using valid values."
                        )
                    }
                }
                "outlinecolour" -> {
                    val cl: List<Int> =
                            value.split(",").map { s ->  s.toInt() }
                    try {
                        quoteInfo.outlineColor =
                                Color(cl[0], cl[1], cl[2], cl[3])
                    } catch (e: IllegalArgumentException) {
                        throw IllegalArgumentException(
                                "Colour parameter not using valid values."
                        )
                    }
                }
                "position" -> {
                    val xy: List<String> = value.split(",")
                    if (xy.size != 2) {
                        throw RuntimeException("contentwrap not in correct " +
                                "format.")
                    }
                    quoteInfo.position = Vector2(
                            Utils.stripBlank(xy[0]).toFloat(),
                            Utils.stripBlank(xy[1]).toFloat()
                    )
                }
                "positiontype" -> {
                    when (value) {
                        "img" -> quoteInfo.positionType = QuotePosType.IMAGE
                        "abs" -> quoteInfo.positionType = QuotePosType.ABS
                        else -> throw RuntimeException("Invalid positiontype.")
                    }
                }
                else -> {
                    throw RuntimeException(
                            "Unknown Parameter given: "
                                    +param
                    )
                }
            }
        }
    }

}
