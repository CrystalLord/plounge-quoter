package ploungequoter

/**
 *
 */
class Vector2 {

    // This creates a singleton that allows us to
    // retrieve the ZERO property like a Java static class.
    companion object Statics {
        val ZERO: Vector2 = Vector2()
    }

    // Components
    var x: Float
    var y: Float

    // Empty constructor
    constructor() : this(0.0f,0.0f) {}

    constructor(setX: Float, setY: Float) {
        x = setX
        y = setY
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
