import net.zomis.advent.Day

class Day12: Day<Map<Int, List<Int>>> {
    override fun parse(text: String): Map<Int, List<Int>> {
        return text.split("\n").associate {
            val key = it.substringBefore(" <-> ").toInt()
            val values = it.substringAfter(" <-> ").split(", ").filter { !it.isEmpty() }.map {
                it.trim().toInt()
            }
            Pair(key, values)
        }
    }

    override fun part1(input: Map<Int, List<Int>>): Any {
        return connect(input, 0).size
    }

    fun connect(input: Map<Int, List<Int>>, value: Int): Set<Int> {
        val connected = mutableSetOf(value)
        var size: Int
        do {
            size = connected.size
            for (v in connected.toSet()) {
                connected.addAll(input[v]!!)
            }
        } while (connected.size != size)
        return connected
    }

    override fun part2(input: Map<Int, List<Int>>): Any {
        val found = mutableSetOf<Int>()
        var count = 0
        do {
            val next: Int = input.keys.filter { !found.contains(it) }.min() ?: return count
            found.addAll(connect(input, next))
            count++
        } while (found.size < input.size)
        return count
    }

}