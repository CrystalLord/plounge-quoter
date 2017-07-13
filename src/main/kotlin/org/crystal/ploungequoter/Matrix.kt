package org.crystal.ploungequoter

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
            for (i: Int in 0..size) {
                this.internalList[i] = defaultVal
            }
        }

    }

    /**
     * Get the element at a specified row/column.
     */
    fun getRC(row: Int, col: Int): T? {
        if (row < 0 || col < 0 || row >= this.height || col >= this.width) {
            throw IllegalArgumentException("Out of index for Matrix")
        }
        val index: Int = row * this.width + col
        return this.internalList[index]
    }

    /**
     * Set the element at a specified row/column.
     */
    fun setRC(row: Int, col: Int, value: T) {
        if (row < 0 || col < 0 || row >= this.height || col >= this.width) {
            throw IllegalArgumentException("Out of index for Matrix")
        }
        val index: Int = row * this.width + col
        this.internalList[index] = value
    }

    /**
     * Return a new Matrix object with the same contents.
     */
    fun clone(): Matrix<T> {
        var newMatrix: Matrix<T> = Matrix<T>(this.width, this.height,
            this.defaultVal)
        var i: Int = 0
        var newList: ArrayList<T?> = ArrayList<T?>(this.internalList)
        newMatrix.internalList = newList
        return newMatrix
    }
}
