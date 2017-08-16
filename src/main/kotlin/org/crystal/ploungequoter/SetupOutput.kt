package org.crystal.ploungequoter

import java.io.File

/**
 * Output class of the SetupParser.
 * It houses data for a set of quotes, as well as the background image to use.
 *
 * @param[backgroundFile] Image file of the background.
 * @param[quoteInfos] List of QuoteInfo objects which detail the text on the
 * final output.
 */
data class SetupOutput (
        val backgroundFile: File,
        val quoteInfos: List<QuoteInfo>,
        val outputType: String = PNGTYPE,
        val source: String = ""
)