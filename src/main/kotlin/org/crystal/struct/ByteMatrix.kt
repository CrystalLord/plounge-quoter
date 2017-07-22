package org.crystal.struct

/**
 * Library class to help represent Byte Matrices.
 */
open class ByteMatrix {
    private var internalList: ByteArray
    val width: Int
    val height: Int
    private val defaultVal: Byte

    constructor(width: Int, height: Int) : this(width, height, 0x00)

    constructor(width: Int, height: Int, defaultVal: Byte) {
        this.width = width
        this.height = height
        this.defaultVal = defaultVal
        val size: Int = this.width * this.height
        this.internalList = ByteArray(size)
        for (i: Int in 0 until size) {
                this.internalList[i] = defaultVal
        }
    }

    /**
     * Get the element at a specified row/column.
     */
    fun getXY(x: Int, y: Int): Byte {
        if (x < 0 || y < 0 || y >= this.height || x >= this.width) {
            throw IllegalArgumentException("Out of index for ByteMatrix")
        }
        val index: Int = y * this.width + x
        return this.internalList[index]
    }

    /**
     * Set the element at a specified row/column.
     */
    fun setXY(x: Int, y: Int, value: Byte) {
        if (x < 0 || y < 0 || y >= this.height || x >= this.width) {
            throw IllegalArgumentException("Out of index for ByteMatrix")
        }
        val index: Int = y * this.width + x
        this.internalList[index] = value
    }

    /**
     * Return a new Matrix object with the same contents.
     */
    fun clone(): ByteMatrix {
        var newMatrix: ByteMatrix = ByteMatrix(
                this.width,
                this.height,
                this.defaultVal
        )
        var newList: ByteArray = ByteArray(
                this.internalList.size,
                {x -> this.internalList[x]}
        )
        newMatrix.internalList = newList
        return newMatrix
    }
}
