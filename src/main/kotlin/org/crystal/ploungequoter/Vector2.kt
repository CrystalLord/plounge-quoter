package org.crystal.ploungequoter

/**
 * A way to represent floating point vectors.
 *
 * It supports addition, scaling, and a Zero vector.
 */
class Vector2 {

    // This creates a singleton that allows us to
    // retrieve the ZERO property like a Java static class.
    companion object {
        val ZERO: Vector2 = Vector2()
    }

    /** X component of the vector */
    val x: Float
    /** Y component of the vector */
    val y: Float

    // Empty constructor
    constructor() : this(0.0f,0.0f)

    /**
     * Create a Vector2 from two Floats.
     * param[setX] Value of X component.
     * param[setY] Value of Y component.
     */
    constructor(setX: Float, setY: Float) {
        x = setX
        y = setY
    }

    /**
     * Create a Vector2 from two Ints.
     * param[setX] Value of X component.
     * param[setY] Value of Y component.
     */
    constructor(setX: Int, setY: Int) {
        x = setX.toFloat()
        y = setY.toFloat()
    }

    // Overload the + operation
    operator fun plus(other: Vector2): Vector2 {
        return Vector2(x + other.x, y + other.y)
    }

    // Overload the - operation
    operator fun minus(other: Vector2): Vector2 {
        return Vector2(x - other.x, y - other.y)
    }

    // Overload the * operation for scalars.
    operator fun times(other: Float): Vector2 {
        return Vector2(x*other, y*other)
    }

}

// Allow floats to be multiplied with vectors.
operator fun Float.times(other: Vector2): Vector2 {
    return other * this
}
