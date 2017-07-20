package org.crystal.ploungequoter

/**
 *
 */
class Vector2Int {

    // This creates a singleton that allows us to
    // retrieve the ZERO property like a Java static class.
    companion object Statics {
        val ZERO: Vector2Int = Vector2Int()
    }

    // Components
    var x: Int
    var y: Int

    // Empty constructor
    constructor() : this(0,0) {}

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
