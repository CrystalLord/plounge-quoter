package org.crystal.struct

/**
 * Library class to help represent Int Matrices.
 */
open class IntMatrix {
    private var internalList: IntArray
    val width: Int
    val height: Int
    private val defaultVal: Int

    constructor(width: Int, height: Int) : this(width, height, 0)

    constructor(width: Int, height: Int, defaultVal: Int) {
        this.width = width
        this.height = height
        this.defaultVal = defaultVal
        val size: Int = this.width * this.height
        this.internalList = IntArray(size)
        for (i: Int in 0 until size) {
                this.internalList[i] = defaultVal
        }
    }

    /**
     * Get the element at a specified row/column.
     */
    fun getXY(x: Int, y: Int): Int {
        if (x < 0 || y < 0 || y >= this.height || x >= this.width) {
            throw IllegalArgumentException("Out of index for IntMatrix")
        }
        val index: Int = y * this.width + x
        return this.internalList[index]
    }

    /**
     * Set the element at a specified row/column.
     */
    fun setXY(x: Int, y: Int, value: Int) {
        if (x < 0 || y < 0 || y >= this.height || x >= this.width) {
            throw IllegalArgumentException("Out of index for IntMatrix")
        }
        val index: Int = y * this.width + x
        this.internalList[index] = value
    }

    /**
     * Is the given xy position within the bounds of the matrix?
     * @param[x] X position.
     * @param[y] Y position.
     * @return Returns a boolean indicating whether the xy position is within
     * bounds.
     */
    fun inMatrix(x: Int, y: Int): Boolean {
        return (
                (0 <= x && x < this.width)
                && (0 <= y && y < this.height)
        )
    }

    /**
     * Return a new Matrix object with the same contents.
     */
    fun clone(): IntMatrix {
        var newMatrix: IntMatrix = IntMatrix(
                this.width,
                this.height,
                this.defaultVal
        )
        var newList: IntArray = IntArray(
                this.internalList.size,
                {x -> this.internalList[x]}
        )
        newMatrix.internalList = newList
        return newMatrix
    }
}
