package org.crystal.ploungequoter

class Utils {
    companion object {
        /**
         * Convert Font points to pixels, accepting an Int
         * @param pts Points to convert
         * @return Returns points in terms of Pixels.
         */
        fun ptsToPixels(pts: Int) = Companion.ptsToPixels(pts.toFloat())

        /**
         * Convert Font points to pixels, accepting a Float
         * @param pts Points to convert
         * @return Returns points in terms of Pixels.
         */
        fun ptsToPixels(pts: Float): Int {
            return Math.round(0.32183062433425f + 1.3192940509307f * pts)
        }

        /**
         * Strip white space at the beginning and end of the string.
         * @param[s] string to strip.
         * @return Returns the stripped string.
         */
        fun stripBlank(s: String): String {
            val startRegex: Regex = Regex("^\\s+")
            val startMatch: MatchResult? = startRegex.find(s)
            val startingSliceIndex: Int

            if (startMatch != null) {
                startingSliceIndex = startMatch.range.last + 1
            } else {
                startingSliceIndex = 0
            }

            val endRegex: Regex = Regex("\\s+$")
            val endMatch: MatchResult? = endRegex.find(s)
            val endingSliceIndex: Int

            if (endMatch != null) {
                endingSliceIndex = endMatch.range.first
            } else {
                endingSliceIndex = s.length
            }

            return s.substring(startingSliceIndex, endingSliceIndex)
        }

        /**
         * Returns whether the given string has a blank in it or not.
         * @param[s] String to check.
         * @return Returns whether or not it has a blank in it.
         */
        fun hasBlank(s: String): Boolean {
            return s.contains(" ")
        }

        /**
         * Replace the last space in the string with a newline.
         * @param[s] String to insert into.
         * @return New string with a newline if there's a space.
         */
        fun insertEndNewline(s: String): String {
            for (i in s.length-1 downTo 0) {
                if (s[i] == ' ') {
                    val front = s.substring(0,i)
                    val rear = s.substring(i+1,s.length)
                    return front + "\n" + rear
                }
            }
            return s
        }
    }
}
