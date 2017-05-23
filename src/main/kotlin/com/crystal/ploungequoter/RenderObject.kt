package com.crystal.ploungequoter

import com.crystal.ploungequoter.Vector2

abstract class RenderObject {
    
    var absPosition: Vector2 = Vector2()
    var parent: RenderObject? = null

    fun relPosition(): Vector2 {
        return (this.absPosition
         - (parent?.absPosition ?: Vector2.ZERO))
    }

    abstract fun render(): Unit
}
