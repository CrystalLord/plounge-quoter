package org.crystal.ploungequoter

/**
 * A way to represent integer based vectors.
 *
 * It supports addition, scaling, and a Zero vector.
*/
class Vector2Int {

    // This creates a singleton that allows us to
    // retrieve the ZERO property like a Java static class.
    companion object {
        /** Retrieve a zero vector */
        val ZERO: Vector2Int = Vector2Int()
    }

    /** X component of the vector */
    val x: Int
    /** Y component of the vector */
    val y: Int

    /** Empty constructor creates a zero vector*/
    constructor() : this(0,0)

    /**
     * Construct a Vector2Int from 2 Integers
     *
     * @param[setX] X component of the new vector.
     * @param[setY] Y component of the new vector.
     */
    constructor(setX: Int, setY: Int) {
        x = setX
        y = setY
    }

    /**
     * Convert this Vector2Int to a Vector2
     * @return Returns a Vector2 representation of this Vector2Int.
     */
    fun toVector2(): Vector2 {
        val newVect2 = Vector2(this.x.toFloat(), this.y.toFloat())
        return newVect2
    }

    // Overload the + operation
    operator fun plus(other: Vector2Int): Vector2Int {
        return Vector2Int(x + other.x, y + other.y)
    }

    // Overload the - operation
    operator fun minus(other: Vector2Int): Vector2Int {
        return Vector2Int(x - other.x, y - other.y)
    }

    // Overload the * operation for scalars.
    operator fun times(other: Int): Vector2Int {
        return Vector2Int(x*other, y*other)
    }

}

// Allow Ints to be multiplied with Vector2Ints
operator fun Int.times(other: Vector2Int): Vector2Int {
    return other * this
}
