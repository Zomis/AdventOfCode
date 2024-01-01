package net.zomis.advent.common

enum class Direction8(val deltaX: Int, val deltaY: Int) {
    W(-1, 0),
    NW(-1, -1),
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    ;

    fun delta(): Point = Point(deltaX, deltaY)

    fun rotateClockwise() = when (this) {
        W -> NW
        NW -> N
        N -> NE
        NE -> E
        E -> SE
        SE -> S
        S -> SW
        SW -> W
    }

    fun opposite(): Direction8 = when (this) {
        W -> E
        E -> W
        NW -> SE
        SE -> NW
        N -> S
        S -> N
        NE -> SW
        SW -> NE
    }

    companion object {
        fun diagonals() = listOf(NW, NE, SW, SE)
    }
}