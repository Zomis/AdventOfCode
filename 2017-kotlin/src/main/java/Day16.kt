import net.zomis.advent.Day

typealias DanceMove = (MutableList<String>) -> Unit
class Day16: Day<List<DanceMove>> {
    override fun parse(text: String): List<DanceMove> {
        return text.split(",").map { createMove(it) }
    }

    private fun createMove(it: String): DanceMove {
        val params = it.substring(1).split('/')
        when (it[0]) {
            'x' -> return {
                val intParams = params.map { it.toInt() }
                // Swap indices
                val tmp = it[intParams[0]]
                it[intParams[0]] = it[intParams[1]]
                it[intParams[1]] = tmp
            }
            's' -> return {
                val count = params[0].toInt()
                // Spin
                for (i in 1..count) {
                    val value = it.removeAt(it.size - 1)
                    it.add(0, value)
                }
            }
            'p' -> return {
                // Swap program names
                val index0 = it.indexOf(params[0])
                val index1 = it.indexOf(params[1])
                createMove("x$index0/$index1").invoke(it)
            }
            else -> throw IllegalArgumentException(it)
        }
    }

    override fun part1(input: List<DanceMove>): Any {
        val range = 'a'..'p'
        val list = range.map { it.toString() }.toMutableList()
        input.forEach { it.invoke(list) }
        return list.joinToString("")
    }

    override fun part2(input: List<DanceMove>): Any {
        val range = 'a'..'p'
        val list = range.map { it.toString() }.toMutableList()
        val original = list.joinToString("")
        var timesLeft = 1_000_000_000
        var firstOriginal = -1
        while (timesLeft > 0) {
            timesLeft--
            input.forEach { it.invoke(list) }
            if (list.joinToString("") == original) {
                if (firstOriginal == -1) {
                    firstOriginal = timesLeft
                } else {
                    val diff = firstOriginal - timesLeft
                    timesLeft %= diff
                }
            }
        }
        return list.joinToString("")
    }

}