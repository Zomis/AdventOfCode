import net.zomis.advent.Day
import java.util.*

class Day3: Day<Int> {

    override fun part1(input: Int): Any {
        var layer = 1
        var number = 1
        var x = 0
        var y = 0
        var stepsAway = 0
        var quadrant = 0
        while (number < input) {
            number += layer * 2 * 4
            layer++
            x++
            y++
            stepsAway += 2
        }
        while (number > input) {
            number -= (layer - 1)
            quadrant++
        }
        if (quadrant % 2 == 1) {
            stepsAway /= 2
            val diff = Math.abs(number - input)
            return stepsAway + diff
        }

        return stepsAway - Math.abs(number - input)
    }

    override fun part2(input: Int): Any {
        val values = HashMap<String, Int>()
        values["0,0"] = 1
        var lastWritten = 1
        var x = 0
        var y = 0
        val spiralOrder = arrayOf(Dir4.RIGHT, Dir4.UP, Dir4.LEFT, Dir4.DOWN)
        var spiralCurrent = 0
        while (lastWritten <= input) {
            val dir = spiralOrder[spiralCurrent]
            x += dir.x
            y += dir.y
            lastWritten = determineValue(values, x, y)
            values["$x,$y"] = lastWritten
            spiralCurrent = checkNextDirection(values, x, y, spiralOrder, spiralCurrent)
        }
        return lastWritten
    }

    private fun checkNextDirection(values: MutableMap<String, Int>, x: Int, y: Int,
           spiralOrder: Array<Dir4>, spiralCurrent: Int): Int {
        val nextIndex = (spiralCurrent + 1) % spiralOrder.size
        val next = spiralOrder[nextIndex]
        val nextX = x + next.x
        val nextY = y + next.y
        if (!values.containsKey("$nextX,$nextY")) {
            return nextIndex
        }
        return spiralCurrent
    }

    private fun determineValue(values: MutableMap<String, Int>, x: Int, y: Int): Int {
        var sum = 0
        for (xx in -1..1) {
            for (yy in -1..1) {
                if (xx != 0 || yy != 0) {
                    val wx = xx + x
                    val wy = yy + y
                    sum += values["$wx,$wy"] ?: 0
                }
            }
        }
        return sum
    }

    override fun parse(text: String): Int {
        return text.toInt()
    }


}