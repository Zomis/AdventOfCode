package net.zomis.advent.common

enum class Direction4(val deltaX: Int, val deltaY: Int) {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
    ;

    fun order(): Int {
        return when (this) {
            UP -> 0
            LEFT -> 1
            RIGHT -> 2
            DOWN -> 3
        }
    }

    fun opposite(): Direction4 = this.rotateClockwise().rotateClockwise()

    fun rotateClockwise(): Direction4 = when (this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
    }

    fun delta(): Point = Point(deltaX, deltaY)
}