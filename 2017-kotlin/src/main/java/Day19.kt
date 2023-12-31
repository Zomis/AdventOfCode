import net.zomis.advent.Day

class Day19: Day<Array<CharArray>> {
    override fun parse(text: String): Array<CharArray> {
        return text.lines().map { it.toCharArray() }.toTypedArray()
    }

    override fun part1(input: Array<CharArray>): Any {
        return packetTravel(input).first
    }

    private fun packetTravel(input: Array<CharArray>): Pair<String, Int> {
        var y = 0
        var x = input[y].indexOf('|')
        var dir = Dir4.DOWN
        var steps = 0
        val result = StringBuilder()
        do {
            val char = input[y][x]
            if (char == '+') {
                dir = Dir4.values().filter { it != dir && it != dir.opposite() }.filter {
                    val ch = input.getOrNull(y + it.y)?.getOrNull(x + it.x)
                    ch != null && ch != ' '
                }.first()
            } else if (char == '-') {

            } else if (char == '|') {

            } else {
                result.append(char)
            }
            steps++
            x += dir.x
            y += dir.y
        } while (input[y][x] != ' ')
        return Pair(result.toString(), steps)
    }

    override fun part2(input: Array<CharArray>): Any {
        return packetTravel(input).second
    }

}