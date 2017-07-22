package org.crystal.struct

/**
 * Library class to help represent arbitrary Matrices.
 */
open class Matrix<T> {
    private var internalList: ArrayList<T?>
    val width: Int
    val height: Int
    private val defaultVal: T?

    constructor(width: Int, height: Int) : this(width, height, null)

    constructor(width: Int, height: Int, defaultVal: T?) {
        this.width = width
        this.height = height
        this.defaultVal = defaultVal
        val size: Int = this.width * this.height
        this.internalList = ArrayList<T?>(size)
        if (this.defaultVal != null) {
            for (i: Int in 0 until size) {
                this.internalList[i] = defaultVal
            }
        }

    }

    /**
     * Get the element at a specified XY position in the matrix.
     * @param[x] X position (column) of the value.
     * @param[y] Y position (row) of the value.
     * @return Returns the value at the position given.
     */
    fun getRC(x: Int, y: Int): T? {
        if (y < 0 || x < 0 || y >= this.height || x >= this.width) {
            throw IllegalArgumentException("Out of index for Matrix")
        }
        val index: Int = y * this.width + x
        return this.internalList[index]
    }

    /**
     * Set the element at a specified XY position in the matrix.
     * @param[x] X position (column) of the value.
     * @param[y] Y position (row) of the value.
     * @param[value] Value to place in the matrix.
     */
    fun setRC(x: Int, y: Int, value: T) {
        if (y < 0 || x < 0 || y >= this.height || x >= this.width) {
            throw IllegalArgumentException("Out of index for Matrix")
        }
        val index: Int = y * this.width + x
        this.internalList[index] = value
    }

    /**
     * Return a new Matrix object with the same contents.
     */
    fun clone(): Matrix<T> {
        var newMatrix: Matrix<T> = Matrix<T>(this.width, this.height,
            this.defaultVal)
        var newList: ArrayList<T?> = ArrayList<T?>(this.internalList)
        newMatrix.internalList = newList
        return newMatrix
    }
}
