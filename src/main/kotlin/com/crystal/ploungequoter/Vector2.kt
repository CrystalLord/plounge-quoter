package com.crystal.ploungequoter

class Vector2 {
    
    var x: Double
    var y: Double

    constructor() : this(0.0,0.0) {}

    constructor(setX: Double, setY: Double) {
        x = setX
        y = setY
    }

    fun plus(other: Vector2): Vector2 {
        return Vector2(x + other.x, y + other.y)
    }

    fun minus(other: Vector2): Vector2 {
        return Vector2(x - other.x, y - other.y)
    }
    
    fun scale(other: Float): Vector2 {
        return Vector2(x*other, y*other)
    }
}
