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
    }
}
