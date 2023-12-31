import net.zomis.advent.Day

class Day11: Day<List<String>> {
    override fun parse(text: String): List<String> {
        return text.split(",")
    }

    override fun part1(input: List<String>): Any {
        var halfStepsNorth = 0
        var stepsEast = 0
        for (dir in input) {
            halfStepsNorth += addHalfStepsNorth(dir)
            stepsEast += addStepsEast(dir)
        }
        return path(halfStepsNorth, stepsEast)
    }

    private fun addHalfStepsNorth(dir: String): Int {
        return when (dir) {
            "sw" -> -1
            "se" -> -1
            "nw" -> 1
            "ne" -> 1
            "n" -> 2
            "s" -> -2
            else -> 0
        }
    }

    private fun addStepsEast(dir: String): Int {
        return when (dir) {
            "sw", "nw" -> -1
            "se", "ne" -> 1
            else -> 0
        }
    }

    override fun part2(input: List<String>): Any {
        var halfStepsNorth = 0
        var stepsEast = 0
        var stepsAwayMax = 0
        for (dir in input) {
            halfStepsNorth += addHalfStepsNorth(dir)
            stepsEast += addStepsEast(dir)
            val stepsAway = path(halfStepsNorth, stepsEast)
            stepsAwayMax = Math.max(stepsAway, stepsAwayMax)
        }
        return stepsAwayMax
    }

    fun path(halfStepsNorth: Int, stepsEast: Int): Int {
        val halfNorth = Math.abs(halfStepsNorth)
        val east = Math.abs(stepsEast)
        return (halfNorth - east) / 2 + east
    }

}