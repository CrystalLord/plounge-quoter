package com.crystal.ploungequoter

class Vector2 {
    
    companion object Statics {
        val ZERO: Vector2 = Vector2()
    }
        
    var x: Float
    var y: Float

    constructor() : this(0.0f,0.0f) {}

    constructor(setX: Float, setY: Float) {
        x = setX
        y = setY
    }

    operator fun plus(other: Vector2): Vector2 {
        return Vector2(x + other.x, y + other.y)
    }

    operator fun minus(other: Vector2): Vector2 {
        return Vector2(x - other.x, y - other.y)
    }

    operator fun times(other: Float): Vector2 {
        return Vector2(x*other, y*other)
    }

}

operator fun Float.times(other: Vector2): Vector2 {
    return other * this
}
